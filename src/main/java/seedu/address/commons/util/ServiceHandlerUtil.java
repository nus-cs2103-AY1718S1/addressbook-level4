package seedu.address.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//@@author cjianhui
/** Class to make requests to API */
public class ServiceHandlerUtil {

    /**
     * Utility class to make HTTP calls
     * @url - url to make request
     * @method - http request method
     * */
    public static String makeCall(String url) throws IOException {

        URL obj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        return getResponseString(connection);

    }

    public static String getResponseString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
