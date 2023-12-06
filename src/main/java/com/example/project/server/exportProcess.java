import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class exportProcess {

    public static void main(String[] args) {
        try {
            // Run tasklist command using ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            Process process = processBuilder.start();

            // Read process output
            try (InputStream inputStream = process.getInputStream();
                 OutputStreamWriter ow = new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.home") + "\\process_list.txt"));

                 PrintWriter writer = new PrintWriter(ow)) {

                byte[] bytes = new byte[1024];
                int count;
                while ((count = inputStream.read(bytes)) != -1) {
                    writer.write(new String(bytes, 0, count));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

