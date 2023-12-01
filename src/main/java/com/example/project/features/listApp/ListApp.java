package com.example.project.features.listApp;
import java.io.*;


public class ListApp {
    private final String filePath = "listApp.txt";
    private File tempFile = new File(filePath);

    public File run() throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            // Run the 'tasklist' command to get a list of processes
            String taskListOutput = executeCommand("tasklist");

            // Print the output
            String[] lines = taskListOutput.split("\n");
            for (int i = 3; i < lines.length; i++) {
                String[] tokens = lines[i].split("\\s+");
                if (tokens.length > 0) {
                    String imageName = tokens[0];
                    writer.write("Server Name: " + imageName);
                    String Pid = tokens[1];
                    writer.write("   Pid: " + Pid + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public String executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // Read the output of the command
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        // Wait for the command to complete
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Return the output as a string
        return output.toString();
    }
}
