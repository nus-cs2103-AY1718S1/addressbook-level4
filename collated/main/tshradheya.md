# tshradheya
###### \java\seedu\address\commons\events\model\DisplayPictureChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to read and store image
 */
public class DisplayPictureChangedEvent extends BaseEvent {

    public final String path;
    public final int newPath;
    private boolean isRead;

    public DisplayPictureChangedEvent(String path, int newPath) {
        this.path = path;
        this.newPath = newPath;
        isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return newPath + " stored";
    }
}
```
###### \java\seedu\address\commons\events\model\PopularContactChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

/**
 * Indicates change of popular contact list
 */
public class PopularContactChangedEvent extends BaseEvent {

    private Model model;

    public PopularContactChangedEvent(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Updated Popular Contact List";
    }
}
```
###### \java\seedu\address\commons\events\model\UpdateListForSelectionEvent.java
``` java
package seedu.address.commons.events.model;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.ShowDefaultPanelEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Updates Person List Panel list for selection to match the Favourites Panel
 */
public class UpdateListForSelectionEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    private Index index;
    private ReadOnlyPerson person;

    public UpdateListForSelectionEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        logger.info("List will get updated to show all contacts");
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\model\UpdatePopularityCounterForSelectionEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Updates the Popularity Counter by one for the selected person
 * Guarantees: Some person is selected in UI in person List Panel
 */
public class UpdatePopularityCounterForSelectionEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public UpdatePopularityCounterForSelectionEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ClearSelectionEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to clear selection of list
 */
public class ClearSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\LoadPersonWebpageEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates 'select' command's successful execution
 * Triggers event to load person's webpage
 */
public class LoadPersonWebpageEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LoadPersonWebpageEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\PersonPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonCard;

/**
 * Indicates a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;
    private final ReadOnlyPerson person;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection, ReadOnlyPerson person) {
        this.newSelection = newSelection;
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\commons\events\ui\PopularContactPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PopularContactCard;

/**
 * Indicates a selection change in the Popular Contact Panel
 */
public class PopularContactPanelSelectionChangedEvent extends BaseEvent {

    private final PopularContactCard newSelection;
    private final ReadOnlyPerson person;


    public PopularContactPanelSelectionChangedEvent(PopularContactCard newSelection, ReadOnlyPerson person) {
        this.newSelection = newSelection;
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PopularContactCard getNewSelection() {
        return newSelection;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\commons\events\ui\SelectFirstAfterDeleteEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to select first person of list
 */
public class SelectFirstAfterDeleteEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\SendingEmailEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;

/**
 * Indicates 'email' command's successful execution
 * Trigger's event to launch email draft in default browser
 */
public class SendingEmailEvent extends BaseEvent {

    public final String recipients;
    public final Subject subject;
    public final Body body;
    public final Service service;

    public SendingEmailEvent(String recipients, Subject subject, Body body, Service service) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.service = service;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowDefaultPanelEvent.java
``` java
package seedu.address.commons.events.ui;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to show default panel on the `BrowserAndReminderPanel` of UI
 */
public class ShowDefaultPanelEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    @Override
    public String toString() {
        logger.info("Defalut panel will be displayed");
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowDetailsEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to show the details of a person
 */
public class ShowDetailsEvent extends BaseEvent {

    public final int targetIndex;

    public ShowDetailsEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowLocationEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates 'location' command's successful execution
 * Trigger's event to show the address of person in `BrowserPanel`
 */
public class ShowLocationEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\UpdatePersonListPanelSelectionEvent.java
``` java
package seedu.address.commons.events.ui;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates selection of a Person Card according to index specified and updates selection
 */
public class UpdatePersonListPanelSelectionEvent extends BaseEvent {

    private static final Logger logger = LogsCenter.getLogger(ShowDefaultPanelEvent.class);

    private Index index;

    public UpdatePersonListPanelSelectionEvent(Index index) {
        this.index = index;
    }

    public Index getIndex() {
        return index;
    }

    @Override
    public String toString() {
        logger.info("Specified index will get selected");
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
        model.showDefaultPanel();
```
###### \java\seedu\address\logic\commands\DetailsCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowDetailsEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Shows details of a person identified using it's last displayed index from the address book.
 * Shows details in `DetailPanel`
 */
public class DetailsCommand extends Command {

    public static final String COMMAND_WORD = "details";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows details the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DETAILS_PERSON_SUCCESS = "Showing Details: %1$s";

    private final Index targetIndex;

    public DetailsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            model.updatePersonsPopularityCounterByOne(lastShownList.get(targetIndex.getZeroBased()));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        EventsCenter.getInstance().post(new ShowDetailsEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_DETAILS_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsCommand // instanceof handles nulls
                && this.targetIndex.equals(((DetailsCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\address\logic\commands\DisplayPictureCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAYPICTURE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a display picture to an existing person in address book
 */
public class DisplayPictureCommand extends Command {

    public static final String COMMAND_WORD = "displaypic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Updates the profile picture of a person identified "
            + "by the index number used in the last person listing. "
            + "Existing Display picture will be updated by the image referenced in the input path. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DISPLAYPICTURE + "[PATH]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + "C:\\Users\\Admin\\Desktop\\pic.jpg";

    public static final String MESSAGE_ADD_DISPLAYPICTURE_SUCCESS = "Added Display Picture to Person: %1$s";

    public static final String MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS = "Removed Display Picture from Person: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_IMAGE_PATH_FAIL =
            "This specified path cannot be read. Please check it's validity and try again";
    private static final String EMPTY_STRING = "";

    private Index index;
    private DisplayPicture displayPicture;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param displayPicture of the person
     */
    public DisplayPictureCommand(Index index, DisplayPicture displayPicture) {
        requireNonNull(index);
        requireNonNull(displayPicture);

        this.index = index;
        this.displayPicture = displayPicture;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException, URISyntaxException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //Defensive coding
        if (displayPicture.getPath() == null) {
            assert false : "Should never be null";
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        if (displayPicture.getPath().equalsIgnoreCase(EMPTY_STRING)) {
            displayPicture.setPath(EMPTY_STRING);

            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                    displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

            updatePersonWithDisplayPicturePath(personToEdit, editedPerson);

            return new CommandResult(generateSuccessMessage(editedPerson));
        }


        boolean isExecutedProperly = model.addDisplayPicture(displayPicture.getPath(),
                    personToEdit.getEmail().hashCode());
        if (isExecutedProperly) {
            displayPicture.setPath(Integer.toString(personToEdit.getEmail().hashCode()));
        } else {
            displayPicture.setPath("");
            return new CommandResult(generateFailureMessage());
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(), personToEdit.getNickname(),
                displayPicture, personToEdit.getPopularityCounter(), personToEdit.getTags());

        updatePersonWithDisplayPicturePath(personToEdit, editedPerson);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates failure message
     * @return String wih failure message
     */
    private String generateFailureMessage() {
        return MESSAGE_IMAGE_PATH_FAIL;
    }

    /**
     * Generates success message
     * @param personToEdit is checked
     * @return String with success message
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!displayPicture.getPath().isEmpty()) {
            return String.format(MESSAGE_ADD_DISPLAYPICTURE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_DISPLAYPICTURE_SUCCESS, personToEdit);
        }
    }

    /**
     * Updates person in address book with new displaypicture path
     * @param personToEdit who has to be assigned display picture path
     * @param editedPerson person assigned display picture path
     * @throws CommandException when duplicate person found
     */
    private void updatePersonWithDisplayPicturePath(ReadOnlyPerson personToEdit,
                                                    Person editedPerson) throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayPictureCommand)) {
            return false;
        }

        // state check
        DisplayPictureCommand e = (DisplayPictureCommand) other;
        return index.equals(e.index)
                && displayPicture.equals(e.displayPicture);
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Emails all people with a particular tag either using gmail/outlook
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_EMAIL_SENT = "Email .";
    public static final String MESSAGE_NOT_SENT = "Email not sent. Please enter a valid tag and correct service ";
    public static final String EMAIL_SERVICE_GMAIL = "gmail";
    public static final String EMAIL_SERVICE_OUTLOOK = "outlook";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  people in the Address Book.\n"
            + "The 'service' field is compulsory\n"
            + "The 'to' field can take tag and it only supports one parameter.\n"
            + "Parameters: "
            + PREFIX_EMAIL_SERVICE + "SERVICE "
            + PREFIX_EMAIL_TO + "TAGS "
            + PREFIX_EMAIL_SUBJECT + "SUBJECT "
            + PREFIX_EMAIL_BODY + "BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_SERVICE + "gmail "
            + PREFIX_EMAIL_TO + "cs2103 "
            + PREFIX_EMAIL_SUBJECT + "Meeting "
            + PREFIX_EMAIL_BODY + "On Monday ";

    private static final Logger logger = LogsCenter.getLogger(EmailCommand.class);
    private PersonContainsTagPredicate predicate;
    private Subject subject;
    private Body body;
    private Service service;

    public EmailCommand(PersonContainsTagPredicate predicate, Subject subject, Body body, Service service) {
        this.predicate = predicate;
        this.subject = subject;
        this.body = body;
        this.service = service;

    }

    /**
     * Calls event for opening the url in browser panel
     * @param recipients String of all recipients
     * @throws ParseException when wrong recipients
     */
    public void processEmail(String recipients) throws ParseException {
        requireNonNull(recipients);

        if (recipients.equals("")) {
            throw new ParseException("Invalid recipients");
        }
        model.processEmailEvent(recipients, subject, body, service);
    }

    /**
     * Checks if service is one of the offered service of "gmail" or "outlook"
     * @param service mentioned by the user
     * @throws ParseException if not an offered service
     */
    public void checkServiceValid(Service service) throws ParseException {
        if (!service.service.equalsIgnoreCase(EMAIL_SERVICE_GMAIL)
                && !service.service.equalsIgnoreCase(EMAIL_SERVICE_OUTLOOK)) {
            throw new ParseException("Invalid service");
        }
    }


    @Override
    public CommandResult execute() {


        try {
            checkServiceValid(service);
            String emailTo = model.createEmailRecipients(predicate);
            processEmail(emailTo);
        } catch (ParseException e) {
            logger.info("Wrong service or tag");
            return new CommandResult(MESSAGE_NOT_SENT);
        }
        return new CommandResult(MESSAGE_EMAIL_SENT);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setPredicate(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LocationCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Shows location in Google maps of the specified person
 */
public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "location";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays  the location of specified person. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FIND_LOCATION_SUCCESS = "Location of %1$s: %2$s";

    private final Index index;

    public LocationCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personWhoseLocationIsToBeShown = lastShownList.get(index.getZeroBased());

        try {
            model.updatePersonsPopularityCounterByOne(lastShownList.get(index.getZeroBased()));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        try {
            model.showLocation(personWhoseLocationIsToBeShown);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_FIND_LOCATION_SUCCESS,
                personWhoseLocationIsToBeShown.getName().fullName, personWhoseLocationIsToBeShown.getAddress()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.index.equals(((LocationCommand) other).index)); // state check
    }


}
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java

        try {
            model.updatePersonsPopularityCounterByOne(lastShownList.get(targetIndex.getZeroBased()));
            model.showPersonWebpage(lastShownList.get(targetIndex.getZeroBased()));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
```
###### \java\seedu\address\logic\commands\ViewTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Finds and lists all persons in address book who are associated with the tag given in keywords
 * Keyword matching is case sensitive.
 */

public class ViewTagCommand extends Command {

    public static final String COMMAND_WORD = "viewtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are associated with the tag in "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " cs2103";


    private final PersonContainsTagPredicate predicate;

    public ViewTagCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonListForViewTag(predicate);
        model.showDefaultPanel();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTagCommand // instanceof handles nulls
                && this.predicate.equals(((ViewTagCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\LogicManager.java
``` java

    @Override
    public ObservableList<ReadOnlyPerson> getListOfPersonsForPopularContacts() {
        return model.getPopularContactList();
    }
```
###### \java\seedu\address\logic\parser\DetailsCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DetailsCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DetailCommand object
 */
public class DetailsCommandParser implements Parser<DetailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DetailsCommand
     * and returns an DetailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DetailsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DetailsCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DisplayPictureCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DisplayPicture;

/**
 * Parses input arguments and creates a new DisplayPictureCommand object
 */
public class DisplayPictureCommandParser implements Parser<DisplayPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayPictureCommand
     * and returns an DisplayPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String regex = "[\\s]+";
        String[] splitArgs = args.trim().split(regex, 2);

        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DisplayPictureCommand.MESSAGE_USAGE));
        }

        String path;
        if (splitArgs.length > 1) {
            path = splitArgs[1];
        } else {
            path = "";
        }


        return new DisplayPictureCommand(index, new DisplayPicture(path));
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Parses the input command and creates a EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_SERVICE, PREFIX_EMAIL_TO,
                        PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_SERVICE, PREFIX_EMAIL_TO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }

        try {
            Service service = new Service(argMultimap.getValue(PREFIX_EMAIL_SERVICE).get());
            String tagToWhichEmailHasToBeSent =
                    ParserUtil.parseRecipientTag(argMultimap.getValue(PREFIX_EMAIL_TO).get());
            Subject subject = new Subject(String.join("",
                    argMultimap.getAllValues(PREFIX_EMAIL_SUBJECT)).replace(" ", "+"));
            Body body = new Body(String.join("",
                    argMultimap.getAllValues(PREFIX_EMAIL_BODY)).replace(" ", "+"));
            return new EmailCommand(new PersonContainsTagPredicate(tagToWhichEmailHasToBeSent), subject, body, service);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
```
###### \java\seedu\address\logic\parser\exceptions\ImageException.java
``` java
package seedu.address.logic.parser.exceptions;

import java.io.IOException;

/**
 * Represents a image reading error encountered by a parser.
 */
public class ImageException extends IOException {

    public ImageException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\logic\parser\LocationCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the `location' command
 */
public class LocationCommandParser implements Parser<LocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LocationCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }

        return new LocationCommand(index);
    }
}
```
###### \java\seedu\address\logic\parser\ViewTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewTagCommandParser implements Parser<ViewTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE));
        }

        return new ViewTagCommand(new PersonContainsTagPredicate(trimmedArgs));
    }



}
```
###### \java\seedu\address\model\email\Body.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's the body of an Email
 */
public class Body {

    public final String body;

    public Body(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();

        this.body = trimmedBody;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Body // instanceof handles nulls
                && this.body.equals(((Body) other).body)); // state check
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
```
###### \java\seedu\address\model\email\Service.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Specifies email service to use for processing email command
 */
public class Service {
    public final String service;

    public Service(String service) {
        requireNonNull(service);
        String trimmedService = service.trim();

        this.service = trimmedService;
    }

    @Override
    public String toString() {
        return service;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Service // instanceof handles nulls
                && this.service.equals(((Service) other).service)); // state check
    }

    @Override
    public int hashCode() {
        return service.hashCode();
    }
}
```
###### \java\seedu\address\model\email\Subject.java
``` java
package seedu.address.model.email;

import static java.util.Objects.requireNonNull;

/**
 * Represent's subject of the Email being sent
 */
public class Subject {

    public final String subject;

    public Subject(String subject) {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();

        this.subject = trimmedSubject;
    }

    @Override
    public String toString() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.subject.equals(((Subject) other).subject)); // state check
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java

    /** Raises an event to indicate model has changed and favourite contacts might be updated */
    private void indicatePopularContactsChangedPossibility() {
        raise(new PopularContactChangedEvent(this));

    }

    /** Raises an event to indicate the picture has changed */
    private boolean indicateDisplayPictureChanged(String path, int newPath) throws IOException {
        DisplayPictureChangedEvent displayPictureChangedEvent = new DisplayPictureChangedEvent(path, newPath);
        raise(displayPictureChangedEvent);
        return displayPictureChangedEvent.isRead();
    }

    @Override
    public void clearSelection() {
        logger.info("Clears selection of person in list");
        raise(new ClearSelectionEvent());
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        clearSelection();
        addressBook.removePerson(target);
        if (sortedfilteredPersons.isEmpty()) {
            showDefaultPanel();
        } else {
            raise(new SelectFirstAfterDeleteEvent());
        }
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public boolean addDisplayPicture(String path, int newPath) throws IOException {
        return indicateDisplayPictureChanged(path, newPath);
    }

    /**
     * Shows location of the given person
     */
    @Override
    public void showLocation(ReadOnlyPerson person) throws PersonNotFoundException {
        raise(new ShowLocationEvent(person));
    }

    /**
     * Shows webpage of the given person
     */
    @Override
    public void showPersonWebpage(ReadOnlyPerson person) throws PersonNotFoundException {
        raise(new LoadPersonWebpageEvent(person));
    }

    /**
     * Raises event for processing Email
     */
    @Override
    public void processEmailEvent(String recipients, Subject subject, Body body, Service service) {
        raise(new SendingEmailEvent(recipients, subject, body, service));
    }

    /**
     * Makes a string of all intended recipient through the given @predicate
     */
    @Override
    public String createEmailRecipients(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);

        filteredPersonsForEmail.setPredicate(predicate);
        increaseCounterByOneForATag(filteredPersonsForEmail);

        List<String> validEmails = new ArrayList<>();
        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            if (Email.isValidEmail(person.getEmail().value)) {
                validEmails.add(person.getEmail().value);
            }
        }
        return String.join(",", validEmails);
    }

    @Override
    public void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonsForEmail) {

        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            try {
                this.updatePersonsPopularityCounterByOne(person);
            } catch (DuplicatePersonException dpe) {
                assert false : "Duplicate is not possible";
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    //=========== Update Popularity counter for the required commands =======================================

    /**
     * Increases the counter by 1 increasing popularity
     * @param person whose popularity counter increased
     */
    @Override
    public void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        ReadOnlyPerson editedPerson = increaseCounterByOne(person);

        addressBook.updatePerson(person, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
    }

    @Override
    public ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person) {
        person.getPopularityCounter().increasePopularityCounter();
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getBirthday(), person.getNickname(), person.getDisplayPicture(), person.getPopularityCounter(),
                person.getTags());
    }

    //=========== Filtered Popular Contact List Accessors and Mutators =======================================

    /**
     * Updates the popular contact list whenever address book is changed
     */
    @Override
    public void updatePopularContactList() {
        logger.info("Popular List getting Refreshed");
        refreshWithPopulatingAddressBook();
        listOfPersonsForPopularContacts.sort((o1, o2) ->
                o2.getPopularityCounter().getCounter() - o1.getPopularityCounter().getCounter());

        getOnlyTopFiveMaximum();
    }

    @Override
    public void getOnlyTopFiveMaximum() {
        while (listOfPersonsForPopularContacts.size() > 5) {
            listOfPersonsForPopularContacts.remove(listOfPersonsForPopularContacts.size() - 1);
        }
    }

    @Override
    public void refreshWithPopulatingAddressBook() {
        listOfPersonsForPopularContacts = new ArrayList<>(this.addressBook.getPersonList());
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPopularContactList() {
        updatePopularContactList();
        return FXCollections.observableList(listOfPersonsForPopularContacts);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);

        increaseCounterByOneForATag(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void showDefaultPanel() {
        logger.info("Default panel will be shown on right on refresh");
        raise(new ShowDefaultPanelEvent());
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Index getIndexOfGivenPerson(ReadOnlyPerson person) {
        for (int i = 0; i < sortedfilteredPersons.size(); i++) {
            ReadOnlyPerson readOnlyPerson = sortedfilteredPersons.get(i);
            if (readOnlyPerson.isSameStateAs(person)) {
                return Index.fromZeroBased(i);
            }
        }
        assert false : "Should not come here in any case";
        return Index.fromZeroBased(-1);
    }

    @Override
    @Subscribe
    public void handleUpdateListForSelectionEvent(UpdateListForSelectionEvent updateListForSelectionEvent) {
        updateFilteredListToShowAll();
        Index index = getIndexOfGivenPerson(updateListForSelectionEvent.getPerson());
        updateListForSelectionEvent.setIndex(index);
    }

    @Override
    @Subscribe
    public void handleUpdatePopularityCounterForSelectionEvent(
            UpdatePopularityCounterForSelectionEvent updatePopularityCounterForSelectionEvent) {
        try {
            updatePersonsPopularityCounterByOne(updatePopularityCounterForSelectionEvent.getPerson());
        } catch (DuplicatePersonException dpe) {
            assert false : "Is not possible as counter will be increased by one";
        } catch (PersonNotFoundException pnfe) {
            logger.info("Only possible when a person is deleted from a list containing single item");
        }

        updatePopularContactList();
    }
```
###### \java\seedu\address\model\person\DisplayPicture.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent's a person's display picture in the address book
 * Guarantees : immutable; is always valid
 */
public class DisplayPicture {


    private String path;

    public DisplayPicture(String path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && this.path.equals(((DisplayPicture) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
```
###### \java\seedu\address\model\person\Email.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\PersonContainsTagPredicate.java
``` java
package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches the keyword given.
 */

public class PersonContainsTagPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;

    public PersonContainsTagPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagsOfPerson = person.getTags();

        return tagsOfPerson.stream()
                .anyMatch(tagMatches -> tagMatches.getTagName().equalsIgnoreCase(keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagPredicate // instanceof handles nulls
                && this.keyword.equals(((PersonContainsTagPredicate) other).keyword)); // state check
    }

}
```
###### \java\seedu\address\model\person\PopularityCounter.java
``` java
package seedu.address.model.person;

/**
 * Represents a Person's frequency of visits in address book.
 * Initially the value is 0 for all
 */
public class PopularityCounter {

    private int counter;

    public PopularityCounter() {
        counter = 0;
    }

    public PopularityCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Increases popularity by 1 when searched or viewed by selecting
     */
    public void increasePopularityCounter() {
        counter++;
    }

    public void resetPopularityCounter() {
        counter = 0;
    }

    /**
     * Gets popularity counter to form the favourite list of contacts
     * @return popularity counter
     */
    public int getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PopularityCounter // instanceof handles nulls
                && this.getCounter() == ((PopularityCounter) other).getCounter()); // state check
    }
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java

    public String getAddressBookPicturesPath() {
        return addressBookPicturesPath;
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public void setAddressBookPicturesPath(String addressBookPicturesPath) {
        this.addressBookPicturesPath = addressBookPicturesPath;
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java

    public static ReadOnlyUniqueReminderList getSampleReminderList() {
        try {
            UniqueReminderList sampleReminderList = new UniqueReminderList();
            for (Reminder sampleReminder : getSampleReminders()) {
                sampleReminderList.add(sampleReminder);
            }
            return sampleReminderList;
        } catch (DuplicateReminderException e) {
            throw new AssertionError("sample data cannot contain duplicate reminders", e);
        }
    }
```
###### \java\seedu\address\storage\AddressBookPictureStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class AddressBookPictureStorage {

    private static final Logger logger = LogsCenter.getLogger(AddressBookPictureStorage.class);

    private String filePath;

    public AddressBookPictureStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the pictures folder.
     */
    public String getAddressBookPicturePath() {
        return filePath;
    }

    /**
     * Creates a new folder for pictures storage
     */
    public void createPictureStorageFolder() throws IOException {
        requireNonNull(filePath);

        logger.info("Picture folder "  + filePath + " created if missing");
        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
```
###### \java\seedu\address\storage\DisplayPictureStorage.java
``` java
package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a storage for Display Picture.
 */
public interface DisplayPictureStorage {

    void readImageFromDevice(String imagePath, int newPath) throws IOException;

    void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException;

}
```
###### \java\seedu\address\storage\ImageDisplayPictureStorage.java
``` java
package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * To read and store image
 */
public class ImageDisplayPictureStorage implements DisplayPictureStorage {

    private static final int IMAGE_WIDTH = 980;
    private static final int IMAGE_HEIGHT = 640;

    private static final String IMAGE_EXTENSION = ".png";
    private static final String DIRECTORY_SAVING_PATH = "pictures/";

    private static final Logger logger = LogsCenter.getLogger(ImageDisplayPictureStorage.class);


    public ImageDisplayPictureStorage() {
        logger.info("Constructor used to create instance of DisplayPictureStorage.class");
    }

    /**
     * Reads image from local device
     * @throws IOException to display imagePath is wrong
     */
    public void readImageFromDevice(String imagePath, int newPath) throws IOException {
        File fileToRead = null;
        BufferedImage image = null;
        String uniquePath = null;

        try {
            logger.info(" Image read from path " + imagePath);
            fileToRead = new File(imagePath);
            image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            saveImageInDirectory(image, uniquePath);
        } catch (IOException ioe) {
            logger.info("Image not read properly");
            throw new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));

        }
    }

    /**
     * To store image in directory
     * @throws IOException to display error message
     */
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        File fileToWrite = null;
        try {
            logger.info("image is being stored in directory ");
            fileToWrite = new File(DIRECTORY_SAVING_PATH + uniquePath + IMAGE_EXTENSION);
            ImageIO.write(image, IMAGE_EXTENSION, fileToWrite);
        } catch (IOException ioe) {
            logger.info("Image not saved properly");
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));
        }
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void readImageFromDevice(String path, int newPath) throws IOException {
        logger.fine("Attempting to read from file: " + path);
        displayPictureStorage.readImageFromDevice(path, newPath);
    }

    @Override
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        logger.fine("Attempting to write to file: " + uniquePath);
        displayPictureStorage.saveImageInDirectory(image, uniquePath);
    }

    @Override
    @Subscribe
    public void handleDisplayPictureChangedEvent(DisplayPictureChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, " Image changed, saving to file"));
        try {
            readImageFromDevice(event.path, event.newPath);
            event.setRead(true);
        } catch (IOException e) {
            event.setRead(false);
            raise(new DataSavingExceptionEvent(e));
        }
    }

```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java
    private void setUpToShowRemindersPanel() {
        logger.info("Reminders Panel visible");
        detailsPanel.setVisible(false);
        remindersPanel.setVisible(true);
        browser.setVisible(false);
    }

    private void setUpToShowDetailsPanel() {
        logger.info("Details Panel visible");
        detailsPanel.setVisible(true);
        remindersPanel.setVisible(false);
        browser.setVisible(false);
    }

    /**
     * Set's up the UI to bring browser to front and show location
     */
    private void setUpToShowLocation() {
        setUpToShowWebBrowser();
        browser.toFront();
        currentlyInFront = Node.BROWSER;
    }

    /**
     * Set's up the UI to bring browser to front
     */
    private void setUpToShowWebBrowser() {
        logger.info("Browser Panel visible");
        browser.setVisible(true);
        detailsPanel.setVisible(false);
        remindersPanel.setVisible(false);
    }

    /**
     * Creates url from given address
     * @param address of the specified person
     */
    public String loadPersonLocation(String address) {

        String[] splitAddressByWords = address.split("\\s");

        String keywordsOfUrl = "";

        for (String word: splitAddressByWords) {
            keywordsOfUrl += word;
            keywordsOfUrl += "+";
        }

        loadPage(GOOGLE_MAPS_URL + keywordsOfUrl);
        return GOOGLE_MAPS_URL + keywordsOfUrl;
    }

    /**
     * Sets up email Url for processing Email in Browser panel
     * @param service mentioned email service
     * @param recipients formed recipients string
     * @param subject of the email (optional, can be empty)
     * @param body of the email (optional, can be empty)
     */
    private void setUpEmailUrl(String service, String recipients, String subject, String body) {
        if (service.equalsIgnoreCase(EMAIL_SERVICE_GMAIL)) {
            loadEmailUrlGmail(recipients, subject, body);
        } else if (service.equalsIgnoreCase(EMAIL_SERVICE_OUTLOOK)) {
            loadEmailUrlOutlook(recipients, subject, body);
        }
    }

    /**
     * Loads page to send email through gmail
     * @param recipients in terms of a tag ( can be only 1 tag)
     * @param subject of the email (optional)
     * @param body of the email (optional)
     */
    public void loadEmailUrlGmail(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(GMAIL_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            assert false : "As long as google doesn't change its links this should not happen";
        } catch (IOException ioe) {
            assert false : "As long as google doesn't change its links this should not happen";
        }
    }

    /**
     * Loads page to send email through outlook
     * @param recipients in terms of a tag ( can be only 1 tag)
     * @param subject of the email (optional, can be empty)
     * @param body of the email( optional, can be empty)
     */
    private void loadEmailUrlOutlook(String recipients, String subject, String body) {
        try {
            Desktop.getDesktop().browse(new URI(String.format(OUTLOOK_EMAIL_URL, recipients, subject, body)));
        } catch (URISyntaxException urise) {
            assert false : "As long as outlook doesn't change its links this should not happen";
        } catch (IOException ioe) {
            assert false : "As long as outlook doesn't change its links this should not happen";
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUpToShowDetailsPanel();
        detailsPanel.toFront();
        currentlyInFront = Node.DETAILS;
        personDetails = new DetailsPanel(event.getPerson());
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(personDetails.getRoot());
    }

```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java

    @Subscribe
    private void handleShowLocationEvent(ShowLocationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing Location of " + event.person.getName().fullName));
        setUpToShowLocation();
        String url = loadPersonLocation(event.person.getAddress().value);
    }

    @Subscribe
    private void handleSendingEmailEvent(SendingEmailEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Processing email through service of " + event.service.service));
        setUpEmailUrl(event.service.service, event.recipients, event.subject.subject, event.body.body);
    }

    @Subscribe
    private void handlePopularContactPanelSelectionChangedEvent(PopularContactPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUpToShowDetailsPanel();
        detailsPanel.toFront();
        currentlyInFront = Node.DETAILS;
        personDetails = new DetailsPanel(event.getPerson());
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(personDetails.getRoot());
    }

    @Subscribe
    private void handleLoadPersonPageEvent(LoadPersonWebpageEvent event) {
        setUpToShowWebBrowser();
        currentlyInFront = Node.BROWSER;
        browser.toFront();
        loadPersonPage(event.getPerson());
    }

    @Subscribe
    private void handleShowDefaultPanelEvent(ShowDefaultPanelEvent event) {
        setUpToShowRemindersPanel();
        currentlyInFront = Node.REMINDERS;
        remindersPanel.toFront();
    }

```
###### \java\seedu\address\ui\DetailsPanel.java
``` java
package seedu.address.ui;

import java.io.File;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.MainApp;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.TagColor;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";
    private static final Integer IMAGE_WIDTH = 100;
    private static final Integer IMAGE_HEIGHT = 100;
    private static final String DIRECTORY_SAVING_PATH = "pictures/";
    private static final String DEFAULT_IMAGE_PATH = "/images/defaulddp.png";
    private static final String EMPTY_STRING = "";
    private static final String IMAGE_EXTENSION = ".png";
    private static String[] colors = {"#ff0000", "#0000ff", "#008000", "#ff00ff", "#00ffff"};
    private static Random random = new Random();



    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;
    private TagColor tagColorObject;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox mainCardPane;
    @FXML
    private VBox secondaryCardPane;
    @FXML
    private Label detailsName;
    @FXML
    private Label detailsPhone;
    @FXML
    private Label detailsAddress;
    @FXML
    private Label detailsEmail;
    @FXML
    private Label detailsNickname;
    @FXML
    private Label detailsBirthday;
    @FXML
    private FlowPane detailsTag;
    @FXML
    private ImageView detailsDisplayPicture;


    public DetailsPanel(ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        tagColorObject = TagColor.getInstance();
        initTags(person);
        bindListeners(person);
    }

    /**
     * Assigns a random color to a tag if it does not exist in the HashMap
     * returns a String containing the color
     */
    private String getTagColor(String tag) {
        //Defensive coding
        if (tagColorObject == null) {
            assert false : "Impossible as it is an singleton class and one object already created by PersonCard";
        }
        if (!tagColorObject.containsTag(tag)) {
            tagColorObject.addColor(tag, colors[random.nextInt(colors.length)]);
        }
        return tagColorObject.getColor(tag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        detailsName.textProperty().bind(Bindings.convert(person.nameProperty()));
        detailsPhone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        detailsAddress.textProperty().bind(Bindings.convert(person.addressProperty()));
        detailsEmail.textProperty().bind(Bindings.convert(person.emailProperty()));
        detailsNickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        detailsBirthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            detailsTag.getChildren().clear();
            initTags(person);
        });
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // To take care of image deleted manually
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION);
            if (!file.exists()) {
                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }

            centerImage();
            detailsDisplayPicture.setImage(image);

        }
    }



    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image img = detailsDisplayPicture.getImage();
        if (img != null) {
            double w;
            double h;

            double ratioX = detailsDisplayPicture.getFitWidth() / img.getWidth();
            double ratioY = detailsDisplayPicture.getFitHeight() / img.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            detailsDisplayPicture.setX((detailsDisplayPicture.getFitWidth() - w) / 2);
            detailsDisplayPicture.setY((detailsDisplayPicture.getFitHeight() - h) / 2);

        }
    }

    /**
     * Initialize tags for the respective person
     * @param person whose tags have to be added and assigned color
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            detailsTag.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailsPanel)) {
            return false;
        }

        // state check
        DetailsPanel panel = (DetailsPanel) other;
        return person.equals(panel.person);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java

    /**
     * Assigns image pattern to the shape to display image
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // Defensive programming to take care of image corruption
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION);
            if (!file.exists()) {
                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }
            displayPicture.setFill(new ImagePattern(image));

        } else {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            displayPicture.setFill(new ImagePattern(image));
        }
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue, newValue.person));
                        raise(new UpdatePopularityCounterForSelectionEvent(newValue.person));
                    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and don't select it
     */
    private void scrollToWithoutSelecting(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToWithoutSelecting(event.targetIndex);
    }

    @Subscribe
    private void handleShowDetailsEvent(ShowDetailsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java

    @Subscribe
    private void handleUpdatePersonListPanelSelectionEvent(UpdatePersonListPanelSelectionEvent event) {
        scrollTo(event.getIndex().getZeroBased());
    }

    @Subscribe
    private void handleClearSelection(ClearSelectionEvent event) {
        personListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handleSelectFirstAfterDeleteEvent(SelectFirstAfterDeleteEvent event) {
        personListView.scrollTo(0);
        personListView.getSelectionModel().selectFirst();
    }
```
###### \java\seedu\address\ui\PopularContactCard.java
``` java
package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a Popular Contact
 */
public class PopularContactCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PersonCard.class);
    private static final String FXML = "PopularContactCard.fxml";
    private static final Integer IMAGE_WIDTH = 70;
    private static final Integer IMAGE_HEIGHT = 70;
    private static final String DIRECTORY_SAVING_PATH = "pictures/";
    private static final String DEFAULT_IMAGE_PATH = "/images/defaulddp.png";
    private static final String EMPTY_STRING = "";
    private static final String IMAGE_EXTENSION = ".png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private VBox popularContactPane;
    @FXML
    private Label popularContactName;
    @FXML
    private Circle popularContactDisplayPicture;
    @FXML
    private Label rank;

    public PopularContactCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        rank.setText("#" + displayedIndex + " ");
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        popularContactName.textProperty().bind(Bindings.convert(person.nameProperty()));
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // To take care of image deleted manually
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath() + IMAGE_EXTENSION);

            //Defensive programming
            if (!file.exists()) {
                logger.info("Corrupted image. Loading default image now");

                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }

            popularContactDisplayPicture.setFill(new ImagePattern(image));

        } else {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            popularContactDisplayPicture.setFill(new ImagePattern(image));
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PopularContactCard card = (PopularContactCard) other;
        return rank.getText().equals(card.rank.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\PopularContactPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PopularContactChangedEvent;
import seedu.address.commons.events.model.UpdateListForSelectionEvent;
import seedu.address.commons.events.ui.PopularContactPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.UpdatePersonListPanelSelectionEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel to display top 5 popular most frequently accessed contacts.
 */
public class PopularContactPanel extends UiPart<Region> {

    private static final String FXML = "PopularContactPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PopularContactCard> popularContactListView;

    public PopularContactPanel(ObservableList<ReadOnlyPerson> popularContactList) {
        super(FXML);
        setConnections(popularContactList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> popularContactList) {
        ObservableList<PopularContactCard> mappedList = EasyBind.map(
                popularContactList, (person) -> new PopularContactCard(person, popularContactList.indexOf(person) + 1));
        popularContactListView.setItems(mappedList);
        popularContactListView.setCellFactory(listView -> new PopularContactPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        popularContactListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PopularContactPanelSelectionChangedEvent(newValue, newValue.person));
                        synchronizeListsOnClick(newValue);
                    }
                });
    }

    /**
     * Ensures the Popular Contact List and Person List is synchronized on click of any person
     */
    public void synchronizeListsOnClick(PopularContactCard newValue) {
        UpdateListForSelectionEvent updateListForSelectionEvent =
                new UpdateListForSelectionEvent(newValue.person);
        raise(updateListForSelectionEvent);
        raise(new UpdatePersonListPanelSelectionEvent(updateListForSelectionEvent.getIndex()));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PopularContactCard}.
     */
    class PersonListViewCell extends ListCell<PopularContactCard> {

        @Override
        protected void updateItem(PopularContactCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

    @Subscribe
    public void handlePopularContactChangedEvent(PopularContactChangedEvent ppce) {
        setConnections(ppce.getModel().getPopularContactList());
    }
}

```
###### \java\seedu\address\ui\util\TagColor.java
``` java
package seedu.address.ui.util;

import java.util.HashMap;


/**
 * Singleton Class
 * Stores hashmap which maps color to the tag
 */
public class TagColor {

    private static TagColor tagColor = null;
    private HashMap<String, String> colorsMapping = new HashMap<String, String>();


    private TagColor() {

    }

    public static TagColor getInstance() {
        if (tagColor == null) {
            tagColor = new TagColor();
        }
        return tagColor;
    }


    public boolean containsTag(String tag) {
        return colorsMapping.containsKey(tag);
    }

    public void addColor(String tag, String color) {
        colorsMapping.put(tag, color);
    }

    public String getColor(String tag) {
        return colorsMapping.get(tag);
    }
}
```
###### \resources\view\DayTheme.css
``` css
.details_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 30px;
    -fx-font-weight: 100;
    -fx-text-fill: gray;
}

.details_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 16px;
    -fx-font-weight: 100;
    -fx-text-fill: gray;
}

.details_medium_label {
    -fx-font-family: "cursive";
    -fx-font-size: 20px;
    -fx-font-weight: 100;
    -fx-text-fill: gray;
}

#detailsTag {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#detailsTag .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 18 18 18 18;
    -fx-background-radius: 18 18 18 18;
    -fx-font-size: 16;
}

```
###### \resources\view\DetailsPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<?import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView?>
<?import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView?>
<AnchorPane fx:id="anchorPane" prefHeight="200.0" prefWidth="400.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox fx:id="mainCardPane" prefHeight="125.0" prefWidth="700.0">
         <children>
            <ImageView fx:id="detailsDisplayPicture" fitHeight="123.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
               <image>
                  <Image url="@../images/defaulddp.png" />
               </image>
            </ImageView>
            <VBox prefHeight="200.0" prefWidth="549.0">
               <children>
                  <Label fx:id="detailsName" prefHeight="50.0" prefWidth="371.0" text="\$name" styleClass="details_big_label">
                     <font>
                        <Font name="Helvetica Bold" size="30.0" />
                     </font>
                  </Label>
                  <Label fx:id="detailsNickname" text="\$nickname" styleClass="details_small_label">
                     <font>
                        <Font name="Helvetica" size="15.0" />
                     </font>
                     <VBox.margin>
                        <Insets top="20.0" />
                     </VBox.margin>
                  </Label>
               </children>
               <HBox.margin>
                  <Insets left="50.0" />
               </HBox.margin>
            </VBox>
         </children>
         <opaqueInsets>
            <Insets />
         </opaqueInsets>
      </HBox>
      <VBox fx:id="secondaryCardPane" layoutY="125.0" prefHeight="242.0" prefWidth="700.0">
         <children>
            <FlowPane fx:id="detailsTag" hgap="10.0" prefHeight="54.0" prefWidth="685.0" />
            <Label fx:id="detailsPhone" prefHeight="39.0" prefWidth="700.0" text="\$phone"  styleClass="details_medium_label">
               <graphic >
                  <FontAwesomeIconView glyphName="PHONE" size="20" fill="green"  />
               </graphic>
               <VBox.margin>
                  <Insets bottom="20.0" />
               </VBox.margin>
            </Label>
            <Label fx:id="detailsAddress" prefHeight="50.0" prefWidth="700.0" text="\$address" styleClass="details_medium_label">
               <graphic>
                  <MaterialDesignIconView glyphName="MAP" size="20" fill="blue" />
               </graphic>
               <VBox.margin>
                  <Insets bottom="20.0" />
               </VBox.margin>
            </Label>
            <Label fx:id="detailsEmail" prefHeight="50.0" prefWidth="700.0" text="\$email" styleClass="details_medium_label">
               <graphic>
                  <FontAwesomeIconView glyphName="ENVELOPE" size="20" fill="crimson" />
               </graphic>
               <VBox.margin>
                  <Insets bottom="20.0" />
               </VBox.margin>
            </Label>
            <Label fx:id="detailsBirthday" prefHeight="50.0" prefWidth="700.0" text="\$birthday" styleClass="details_medium_label">
               <graphic>
                  <MaterialDesignIconView glyphName="CAKE_VARIANT" size="20" fill="yellow" />
               </graphic>
            </Label>
         </children>
         <padding>
            <Insets top="10.0" />
         </padding>
      </VBox>
   </children>
</AnchorPane>
```
###### \resources\view\NightTheme.css
``` css
.details_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 30px;
    -fx-text-fill: white;
}

.details_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.details_medium_label {
    -fx-font-family: "cursive";
    -fx-font-size: 20px;
    -fx-text-fill: white;
}

#detailsTag {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#detailsTag .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 18 18 18 18;
    -fx-background-radius: 18 18 18 18;
    -fx-font-size: 16;
}

```
###### \resources\view\PersonListCard.fxml
``` fxml

  <Circle fx:id="displayPicture" fill="WHITE" layoutX="150.0" layoutY="186.0" radius="35.0" stroke="WHITE" strokeType="INSIDE" strokeWidth="2.0">
      <HBox.margin>
         <Insets bottom="10.0" right="35.0" top="17.0" />
      </HBox.margin>
   </Circle>
```
###### \resources\view\PopularContactCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Circle?>

<?import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView?>
<VBox id="popularContactPane" fx:id="popularContactPane" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="94.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Circle fx:id="popularContactDisplayPicture" fill="WHITE" layoutX="150.0" layoutY="186.0" radius="35.0" stroke="WHITE" strokeType="INSIDE" strokeWidth="2.0">
         <VBox.margin>
            <Insets left="67.0" />
         </VBox.margin></Circle>
      <HBox prefHeight="100.0" prefWidth="200.0">
         <children>
            <Label fx:id="rank" prefHeight="15.0" prefWidth="25.0" styleClass="cell_small_label" />
            <Label fx:id="popularContactName" styleClass="cell_small_label" text="\$name" textAlignment="CENTER">
               <graphic>
                  <FontAwesomeIconView glyphName="STAR" fill="gold" size="14" />
               </graphic>
               <padding>
                  <Insets left="10.0" />
               </padding>
            </Label>
         </children>
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets left="50.0" />
         </padding>
      </HBox>
   </children>
</VBox>
```
###### \resources\view\PopularContactPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="popularContactListView" orientation="HORIZONTAL">
      <VBox.margin>
         <Insets left="130.0" right="130.0" />
      </VBox.margin></ListView>
</VBox>
```
