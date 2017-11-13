package seedu.address.ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

/**
 * This class get weather information from Yahoo can show to users
 */
//@@author eeching
public class WeatherRequest {

    private static final String WHERE_ON_EARTH_IDENTIFIER = "1062617"; //this is Yahoo's woeid for Singapore
    private static final int WEATHER_INDEX_FROM_CHANNEL = 0;
    private static final int TEMPERATURE_INDEX_FROM_CHANNEL = 2;
    private static final String LOCATION_INFORMATION = "Singapore GMT +0800";
    private static final String DEGREE_CELSIUS = "℃, ";



    public String getSgWeather() throws JAXBException, IOException {

        YahooWeatherService service = new YahooWeatherService();
        Channel channel = service.getForecast(WHERE_ON_EARTH_IDENTIFIER, DegreeUnit.CELSIUS);

        return weatherParser(channel.getItem().getCondition().toString());
    }

    /**
     * Extract the weather information from channel
     */
    private String weatherParser (String rawString) {
        ArrayList<String> information = new ArrayList<>();
        String infoString = rawString.substring(10, rawString.length() - 1);


        for (String piece : infoString.split(",")) {
            information.add(piece);
        }

        String weather = formatInfo(information.get(WEATHER_INDEX_FROM_CHANNEL)) + ", ";
        String temperature = formatInfo(information.get(TEMPERATURE_INDEX_FROM_CHANNEL)) + DEGREE_CELSIUS;
        String location = LOCATION_INFORMATION;

        StringBuilder builder = new StringBuilder();

        builder.append(weather)
               .append(temperature)
               .append(location);

        return builder.toString();

    }

    private String formatInfo (String rawInfo) {
        return Arrays.stream(rawInfo.split("="))
                .reduce((first, second) -> second).get();

    }
}
