
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
public class CsvDataSource {

    private String filePath;

    public CsvDataSource(String filePath) {
        this.filePath = filePath;
    }

    public List<SensorData> readSensorData() {
        List<SensorData> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(",");

                String timestamp = values[0];
                double temperature = Double.parseDouble(values[1]);
                double humidity = Double.parseDouble(values[2]);

                SensorData data = new SensorData(
                        "RPI_01",
                        timestamp,
                        temperature,
                        humidity
                );

                dataList.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
