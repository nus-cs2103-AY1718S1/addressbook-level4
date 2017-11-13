//@@author PhuaJunJie
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class GetRedirectUrlEvent extends BaseEvent {

    private String reDirectUrl;

    public GetRedirectUrlEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    //Setter of redirect URL
    public void setRedirectUrl(String reDirectUrl) {
        this.reDirectUrl = reDirectUrl;
    }

    //Getter
    public String getReDirectUrl() {
        return reDirectUrl;
    }
}

