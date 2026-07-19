public class SensorData {

    private String deviceId;
    private String timestamp;
    private double temperature;
    private double humidity;

    
    public SensorData(String deviceId,
                      String timestamp,
                      double temperature,
                      double humidity) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    

    public String getDeviceId() {
        return deviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    
    @Override
    public String toString() {
        return "SensorData{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
