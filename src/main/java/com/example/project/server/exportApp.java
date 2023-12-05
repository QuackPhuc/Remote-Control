import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class exportApp {

    public static void main(String[] args) {
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
                return;
            }

            // Extract application names
            Pattern pattern = Pattern.compile("([^-]+)\\s*-\\s*(.*)");
            Matcher matcher = pattern.matcher(visibleApps.toString());

            StringBuilder filteredApps = new StringBuilder();
            while (matcher.find()) {
                filteredApps.append(matcher.group(2).trim()).append("\n");
            }

            // Write filtered information to a file
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.home"), "filtered_apps.txt"), StandardCharsets.UTF_8)) {
                writer.write(filteredApps.toString());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
