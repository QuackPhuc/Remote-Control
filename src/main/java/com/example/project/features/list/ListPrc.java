package com.example.project.features.list;
import java.io.*;


public class ListPrc {
    private final String filePath = "src/main/resources/com/example/project/file/listPrc.txt";
    private File tempFile = new File(filePath);

    public File run() throws IOException {
        try {
            Process process = Runtime.getRuntime().exec("tasklist /FO CSV /NH");
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            while ((line = bufferedReader.readLine()) != null) {
                // Phân tích và trích xuất dữ liệu từ mỗi dòng dưới dạng CSV
                String[] data = line.split("\",\"");
                if (data.length >= 5) {
                    // Loại bỏ dấu ngoặc kép ở đầu và cuối các chuỗi
                    data[0] = data[0].replaceAll("^\"|\"$", "");
                    data[4] = data[4].replaceAll("^\"|\"$", "");

                    // Chỉ lấy các cột thông tin quan trọng
                    String outputLine = String.join("|", data[0], data[1], data[2], data[3], data[4]);
                    writer.write(outputLine);
                    writer.newLine();
                }
            }

            writer.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
