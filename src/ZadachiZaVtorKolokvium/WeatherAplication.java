//package src.ZadachiZaVtorKolokvium;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//class Weather{
//
//    private double temperature;
//    private double humidity;
//    private double pressure;
//
//    public Weather(double temperature, double humidity, double pressure) {
//        this.temperature = temperature;
//        this.humidity = humidity;
//        this.pressure = pressure;
//    }
//}
//
//class WeatherDispatcher{
//
//    private List<Weather> weathersList;
//
//    public WeatherDispatcher() {
//        this.weathersList = new ArrayList<>();
//    }
//
//    public void setMeasurements(float temperature, float humidity, float pressure) {
//        Weather weather = new Weather(temperature,humidity,pressure);
//        weathersList.add(weather);
//    }
//
//    public List<Weather> getWeathersList() {
//        return weathersList;
//    }
//
//    public void remove(ForecastDisplay forecastDisplay) {
//    }
//
//    public void register(CurrentConditionsDisplay forecastDisplay) {
//    }
//}
//
//class ForecastDisplay{
//
//    private WeatherDispatcher weatherDispatcher;
//
//    public ForecastDisplay(WeatherDispatcher weatherDispatcher) {
//        this.weatherDispatcher = weatherDispatcher;
//    }
//}
//
//public class WeatherApplication {
//
//    public static void main(String[] args) {
//        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();
//
//        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
//        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);
//
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split("\\s+");
//            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
//            if(parts.length > 3) {
//                int operation = Integer.parseInt(parts[3]);
//                if(operation==1) {
//                    weatherDispatcher.remove(forecastDisplay);
//                }
//                if(operation==2) {
//                    weatherDispatcher.remove(currentConditions);
//                }
//                if(operation==3) {
//                    weatherDispatcher.register(forecastDisplay);
//                }
//                if(operation==4) {
//                    weatherDispatcher.register(currentConditions);
//                }
//
//            }
//        }
//    }
//}