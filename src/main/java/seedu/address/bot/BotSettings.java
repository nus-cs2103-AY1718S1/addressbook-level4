package seedu.address.bot;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class BotSettings implements Serializable {

    private static final String DEFAULT_BOT_TOKEN = "339790464:AAGUN2BmhnU0I2B2ULenDdIudWyv1d4OTqY";
    private static final String DEFAULT_BOT_USERNAME = "ArkBot";

    private String botToken;
    private String botUsername;

    public BotSettings() {
        this.botToken = DEFAULT_BOT_TOKEN;
        this.botUsername = DEFAULT_BOT_USERNAME;
    }

    public BotSettings(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    public String getBotToken() {
        return this.botToken;
    }

    public String getBotUsername() {
        return this.botUsername;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BotSettings)) { //this handles null as well.
            return false;
        }

        BotSettings o = (BotSettings) other;

        return Objects.equals(this.botToken, o.botToken)
                && Objects.equals(this.botUsername, o.botUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(botToken, botUsername);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bot Name : " + botUsername + "\n");
        return sb.toString();
    }
}
