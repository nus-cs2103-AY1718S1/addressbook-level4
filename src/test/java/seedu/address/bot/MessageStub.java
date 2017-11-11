package seedu.address.bot;

import java.util.List;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;

/**
 * Created by Francis on 11/11/2017.
 */
public class MessageStub extends Message {

    private List<PhotoSize> photo;

    public MessageStub() {
        super();
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

}
