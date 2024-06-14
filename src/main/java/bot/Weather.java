package bot;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Weather {
    private static String weatherText;
    private static String APIkey = "074b0574d6ee1a26fe0242cf23e14957";

    // Метод для подключения к Web-странице и получения с неё данных.
    public static String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            } // Считываем содержимое страницы построчно и добавляем его в объект StringReader
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Город не найден!");
        }

        return content.toString(); // Получение строки с данными.
    }

    // Метод, который получает текущую погоду в указанном городе.
    public static String getWeather(String city) {
        // Делаем запрос к OpenWeatherMap. Возвращаемое значение метода getUrlContent() присваивается строке output.
        String output = getUrlContent(
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + city
                        + "&appid=" + APIkey + "&units=metric"
        );


        if (!output.isEmpty()) {
            JSONObject object = new JSONObject(output);
            weatherText = "Погода в городе " + city + ":"
                    + "\n\nТемпература: " + object.getJSONObject("main").getDouble("temp")
                    + "\nОщущается: " + object.getJSONObject("main").getDouble("feels_like")
                    + "\nВлажность: " + object.getJSONObject("main").getDouble("humidity")
                    + "\nДавление: " + object.getJSONObject("main").getDouble("pressure");
        }
        return weatherText;
    }
}