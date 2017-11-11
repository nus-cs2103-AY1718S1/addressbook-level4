package seedu.address.bot;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by Francis on 11/11/2017.
 */
public class UpdateStub extends Update {


    private Message message;

    public UpdateStub(Message message) {
        super();
        this.message = message;
    }
}
