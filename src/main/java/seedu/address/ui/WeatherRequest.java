package seedu.address.ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
    private static final int WEATHER_INDEX_FROM_CHANNEL = 1;
    private static final int TEMPERATURE_INDEX_FROM_CHANNEL = 3;
    private static final int DAY_INDEX_FROM_CHANNEL = 4;
    private static final int MONTH_INDEX_FROM_CHANNEL = 5;
    private static final int DATE_INDEX_FROM_CHANNEL = 6;
    private static final int STARTING_INDEX_WEATHER = 6;
    private static final int STARTING_INDEX_TEMPERATURE = 5;
    private static final int END_INDEX_TEMPERATURE = 7;
    private static final int STARTING_INDEX_DAY = 5;
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
        StringTokenizer st = new StringTokenizer(rawString);
        while (st.hasMoreTokens()) {
            information.add(st.nextToken());
        }

        StringBuilder builder =  new StringBuilder();
        String weather = information.get(WEATHER_INDEX_FROM_CHANNEL)
                .substring(STARTING_INDEX_WEATHER) + " ";
        String temperature = information.get(TEMPERATURE_INDEX_FROM_CHANNEL)
                .substring(STARTING_INDEX_TEMPERATURE, END_INDEX_TEMPERATURE) + DEGREE_CELSIUS;
        String date = information.get(DAY_INDEX_FROM_CHANNEL).substring(STARTING_INDEX_DAY)
                + ", " + information.get(MONTH_INDEX_FROM_CHANNEL) + " "
                + information.get(DATE_INDEX_FROM_CHANNEL) + ", ";
        String location = LOCATION_INFORMATION;

        builder.append(weather)
               .append(temperature)
               .append(date)
               .append(location);

        return builder.toString();

    }
}
