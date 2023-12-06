package com.example.project.features.list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListApp {
    private final String filePath = "src/main/resources/com/example/project/file/listApp.txt";
    private File tempFile = new File(filePath);

    public File run() throws IOException {
        try {
            // Run PowerShell to get visible applications
            String powerShellScript = "$wd = New-Object -ComObject 'Word.Application';" +
                    "$wd.Tasks | Where-Object {$_.Visible} | Select-Object Name;" +
                    "$wd.Quit()";

            String[] powerShellCommand = {"powershell.exe", "-Command", powerShellScript};
            ProcessBuilder powerShellProcessBuilder = new ProcessBuilder(powerShellCommand);
            Process powerShellProcess = powerShellProcessBuilder.start();

            StringBuilder visibleApps = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    visibleApps.append(line).append("\n");
                }
            }

            int powerShellExitCode = powerShellProcess.waitFor();
            if (powerShellExitCode != 0) {
                System.err.println("PowerShell command returned an error code: " + powerShellExitCode);
                return tempFile;
            }

            // Extract application names after the last dash (-)
            Pattern pattern = Pattern.compile(".*\\s*-\\s*(.*)(?!.*-)");
            Matcher matcher = pattern.matcher(visibleApps.toString());

            StringBuilder filteredApps = new StringBuilder();
            while (matcher.find()) {
                filteredApps.append(matcher.group(1).trim()).append("\n");
            }

            // Write filtered information to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(filteredApps.toString());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

}