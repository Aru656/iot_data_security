

public class JsonUtil {

    public static String toJson(SensorData data) {

        return "{"
                + "\"device_id\":\"" + data.toString().split("'")[1] + "\","
                + "\"timestamp\":\"" + data.toString().split("'")[3] + "\","
                + "\"temperature\":" + data.toString().split(",")[2].split("=")[1] + ","
                + "\"humidity\":" + data.toString().split(",")[3].split("=")[1].replace("}", "")
                + "}";
    }
}

