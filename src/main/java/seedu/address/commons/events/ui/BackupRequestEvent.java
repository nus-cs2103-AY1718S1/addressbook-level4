package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

//@@author charlesgoh
/**
 * An Event for backing up of data to a selected location.
 */
public class BackupRequestEvent extends BaseEvent {

    private Model model;
    private String args;

    public BackupRequestEvent(Model model, String args) {
        this.model = model;
        this.args = args;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
