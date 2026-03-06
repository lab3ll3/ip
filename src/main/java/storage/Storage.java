package storage;

import task.Task;
import task.Todo;
import task.Deadline;
import task.Event;
import exceptions.LicMException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private final String filePath;
    private final String directoryPath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.directoryPath = new File(filePath).getParent();
    }

    /**
     * Loads tasks from the file.
     *
     * @return An ArrayList of tasks loaded from the file
     * @throws LicMException If there is an error loading or parsing the file
     */
    public ArrayList<Task> load() throws LicMException {
        ArrayList<Task> tasks = new ArrayList<>();
        createFileIfNotExists();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
            scanner.close();

            System.out.println("Loaded " + tasks.size() + " tasks from file.");

        } catch (IOException e) {
            throw new LicMException("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks The list of tasks to save
     * @throws LicMException If there is an error writing to the file
     */
    public void save(ArrayList<Task> tasks) throws LicMException {
        createFileIfNotExists();

        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(formatTask(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new LicMException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Creates the data file and its parent directory if they don't exist.
     *
     * @throws LicMException If there is an error creating the file or directory
     */
    private void createFileIfNotExists() throws LicMException {
        try {
            // Create directory if it doesn't exist
            if (directoryPath != null) {
                Path dirPath = Paths.get(directoryPath);
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }
            }

            // Create file if it doesn't exist
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new LicMException("Error creating data file: " + e.getMessage());
        }
    }

    /**
     * Formats a task into a string for file storage.
     *
     * @param task The task to format
     * @return A formatted string representation of the task
     */
    private String formatTask(Task task) {
        String type;
        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
        } else if (task instanceof Event) {
            type = "E";
        } else {
            return "";
        }

        String status = task.isDone() ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof Todo) {
            return String.join(" | ", type, status, description);
        } else if (task instanceof Deadline) {
            return String.join(" | ", type, status, description, ((Deadline) task).getBy().toString());
        } else if (task instanceof Event) {
            return String.join(" | ", type, status, description,
                    ((Event) task).getFrom().toString() + "-" + ((Event) task).getTo().toString());
        }
        return "";
    }

    /**
     * Parses a line from the file into a Task object.
     *
     * @param line The line from the file
     * @return The parsed Task, or null if the line is corrupted
     * @throws LicMException If there is a critical parsing error
     */
    private Task parseTask(String line) throws LicMException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            System.out.println("Warning: Skipping corrupted line (too few parts): " + line);
            return null;
        }

        try {
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;

                case "D":
                    if (parts.length < 4) {
                        System.out.println("Warning: Skipping corrupted deadline (missing date): " + line);
                        return null;
                    }
                    try {
                        task = new Deadline(description, parts[3]);
                    } catch (Exception e) {
                        System.out.println("Warning: Skipping deadline with invalid date: " + line);
                        return null;
                    }
                    break;

                case "E":
                    if (parts.length < 4) {
                        System.out.println("Warning: Skipping corrupted event (missing dates): " + line);
                        return null;
                    }
                    String[] times = parts[3].split("-", 2);
                    if (times.length < 2) {
                        System.out.println("Warning: Skipping corrupted event (invalid format): " + line);
                        return null;
                    }
                    try {
                        task = new Event(description, times[0], times[1]);
                    } catch (Exception e) {
                        System.out.println("Warning: Skipping event with invalid dates: " + line);
                        return null;
                    }
                    break;

                default:
                    System.out.println("Warning: Skipping unknown task type: " + line);
                    return null;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }

            return task;

        } catch (Exception e) {
            System.out.println("Warning: Error parsing line, skipping: " + line);
            return null;
        }
    }
}
