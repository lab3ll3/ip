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

public class Storage {
    private final String filePath;
    private final String directoryPath;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.directoryPath = new File(filePath).getParent();
    }

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
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            throw new LicMException("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

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
            // Save date in yyyy-mm-dd format using toString()
            return String.join(" | ", type, status, description, ((Deadline) task).getBy().toString());
        } else if (task instanceof Event) {
            // Save dates in yyyy-mm-dd format using toString()
            return String.join(" | ", type, status, description,
                    ((Event) task).getFrom().toString() + "-" + ((Event) task).getTo().toString());
        }
        return "";
    }

    private Task parseTask(String line) throws LicMException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new LicMException("Corrupted data file: invalid format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new LicMException("Corrupted data file: missing deadline info");
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 4) {
                    throw new LicMException("Corrupted data file: missing event info");
                }
                String[] times = parts[3].split("-", 2);
                if (times.length < 2) {
                    throw new LicMException("Corrupted data file: invalid event format");
                }
                task = new Event(description, times[0], times[1]);
                break;
            default:
                throw new LicMException("Corrupted data file: unknown task type");
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}