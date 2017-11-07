package seedu.address.ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

//@@author JYL123
/**
 * The class that retrieve weather information and format the string according to its unique pattern.
 */
public class YahooWeatherRequest {
    private final String woeid = "1062617";

    public String getYahooWeatherConditionSg() throws JAXBException, IOException {

        YahooWeatherService service = new YahooWeatherService();
        Channel channel = service.getForecast(woeid, DegreeUnit.CELSIUS);

        return conditionStringParser(channel.getItem().getCondition().toString());
    }

    /**
     * Format the string returned from Yahoo Weather
     */
    private String conditionStringParser (String rawString) {
        int startIndex = 0;
        int endIndex = 0;
        final int unusedCodeIndex = 1;
        final int unusedTimeIndex = 2;
        final String textBuffer = "     ";
        final String degree  = "\u00b0";

        for (int i = 0; i < rawString.length(); i++) {
            if (rawString.charAt(i) == '[') {
                startIndex = i;
            }

            if (rawString.charAt(i) == ']') {
                endIndex = i;
            }
        }

        String removedOverHeadString = rawString.substring(startIndex, endIndex);
        ArrayList<String> splitStrings = new ArrayList<String>();
        for (String piece : removedOverHeadString.split(",")) {
            splitStrings.add(piece);
        }
        splitStrings.remove(unusedCodeIndex);
        splitStrings.remove(unusedTimeIndex);

        splitStrings.set(1, splitStrings.get(1) + degree);

        StringBuilder builder = new StringBuilder();
        splitStrings.stream().forEach(e -> builder
                .append(Arrays.stream(e.split("="))
                .reduce((first, second) -> second).get() + " "));

        return builder.toString() + textBuffer;
    }
}
