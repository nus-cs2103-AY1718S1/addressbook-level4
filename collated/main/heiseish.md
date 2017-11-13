# heiseish
###### /java/seedu/address/ui/IconImage.java
``` java
package seedu.address.ui;

import javafx.scene.image.Image;

/**
 * Contains the image used in group and person card
 * All image are stored in the memory for this class to present java heap exhausted
 */
public class IconImage {
    private static final String ICON = "/images/heart.png";
    private static final String ICON_OUTLINE = "/images/heartOutline.png";
    private static final String DEFAULT = "/images/default.png";
    private static final String DEFAULT_GROUP = "/images/group.png";
    private static final String FACEBOOK = "/images/facebook.png";

    private final Image heart;
    private final Image heartOutline;
    private final Image fbicon;
    private final Image circlePerson;
    private final Image circleGroup;

    public IconImage() {
        circlePerson = new Image(getClass().getResourceAsStream(DEFAULT));
        circleGroup = new Image(getClass().getResourceAsStream(DEFAULT_GROUP));
        heart = new Image(getClass().getResourceAsStream(ICON), 25, 25, false, false);
        heartOutline = new Image(getClass().getResourceAsStream(ICON_OUTLINE), 20, 20, false, false);
        fbicon = new Image(getClass().getResourceAsStream(FACEBOOK), 25, 25, false, false);
    }

    /**
     * Return a heart shape image
     * @return heart
     */
    public Image getHeart() {
        return heart;
    }

    /**
     * Return a facebook icon
     * @return fbicon
     */
    public Image getFbicon() {
        return fbicon;
    }

    /**
     * @return a heart outline shape
     */
    public Image getHeartOutline() {
        return heartOutline;
    }

    /**
     * @return group circle
     */
    public Image getCircleGroup() {
        return circleGroup;
    }

    /**
     * @return person circle
     */
    public Image getCirclePerson() {
        return circlePerson;
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
            String enteredText = commandTextField.getText();
            //always hide suggestion if nothing has been entered (only "spacebars"
            // are dissalowed in TextFieldWithLengthLimit)
            if (enteredText == null || enteredText.isEmpty()) {
                commandContextMenu.hide();
            } else {
                //filter all possible suggestions depends on "Text", case insensitive
                List<String> filteredEntries = entries.stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .collect(Collectors.toList());
                //some suggestions are found
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    // check if the context menu contains only 1 recommend and its the same as command text input
                    if (filteredEntries.get(0).equalsIgnoreCase(enteredText)) {
                        commandContextMenu.hide();
                    } else {
                        populatePopup(filteredEntries, enteredText);
                        if (!commandContextMenu.isShowing()) { //optional
                            commandContextMenu.show(commandTextField, Side.BOTTOM, 0, 0); //position of popup
                        }
                    }
                    //no suggestions -> hide
                } else {
                    commandContextMenu.hide();
                }
            }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Fill up the command word from the characters
     * if there exists a command word with the same starting chars
     */
    private void fillInRecommendedCommand() {
        ArrayList<String> commandListString = new CommandList().getCommandList();
        for (String command : commandListString) {
            if (command.startsWith(commandTextField.getText())) {
                replaceText(command);
                return;
            }
        }
    }

    /**
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult, String searchRequest) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        //List size - 10 or founded suggestions count
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        //Build list as set of labels
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            //label with graphic (text flow) to highlight founded subtext in suggestions
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            //if any suggestion is select set it into text and close popup
            item.setOnAction(actionEvent -> {
                item.setText(result);
                commandTextField.positionCaret(result.length());
                commandContextMenu.hide();
            });
        }

        //"Refresh" context menu
        commandContextMenu.getItems().clear();
        commandContextMenu.getItems().addAll(menuItems);
    }


    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Build TextFlow with selected text. Return "case" dependent.
     *
     * @param text - string with text
     * @param filter - string to select in text
     * @return - TextFlow
     */
    public static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,
                filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12.00));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java

    private void loadFacebookPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_PREFIX + person.getFacebook().value);
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    public void googleSearch(String url) {
        loadPage(GOOGLE_URL_PREFIX + url + GOOGLE_URL_SUFFIX);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonFacebookOpenEvent(PersonFacebookOpenEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookPage(event.getNewSelection());
    }

    @Subscribe
    private void handleSearchNameEvent(SearchNameEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleSearch(event.getName());
    }

    @Subscribe
    private void handleSearchMajorEvent(SearchMajorEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        googleSearch("NUS " + event.getMajor());
    }
```
###### /java/seedu/address/ui/GroupCard.java
``` java
    /**
     * Instantiate image of a person.
     */
    private void initImage(IconImage image) {
        picture.setGraphic(new Circle(25, new ImagePattern(image.getCircleGroup())));
    }
}
```
###### /java/seedu/address/ui/StatusBarFooter.java
``` java
        setTotalPersons(totalNumber);
        setTotalGroups(totalGroup);
```
###### /java/seedu/address/ui/StatusBarFooter.java
``` java
    private void setTotalPersons(int numberOfPeople) {
        Platform.runLater(() -> this.totalPersons.setText(numberOfPeople + " person(s) total"));
    }

    private void setTotalGroups(int numberOfGroup) {
        Platform.runLater(() -> this.totalGroups.setText(numberOfGroup + " group(s) total"));
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        major.textProperty().bind(Bindings.convert(person.majorProperty()));
        person.favoriteProperty().addListener((observable, oldValue, newValue) -> initFavorite(person, image));
        person.facebookProperty().addListener((observable, oldValue, newValue) -> initFbIcon(person, image));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Prepare a HashMap of some default colors to link {@code tagName} with a color
     */
    private void initLabelColor() {
        labelColor.put("colleagues", "red");
        labelColor.put("friends", "blue");
        labelColor.put("family", "brown");
        labelColor.put("neighbours", "purple");
        labelColor.put("classmates", "green");
    }

    /**
     * Instantiate tags
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            String color = getColor(tag.tagName);
            label.setPrefHeight(23);
            label.setStyle("-fx-background-color: transparent; "
                    + "-fx-font-size: 10px;"
                    + "-fx-font-family: Segoe UI;"
                    + "-fx-text-fill: #010504;"
                    + "-fx-border-color: " + color + "; "
                    + "-fx-border-width: 2;"
                    + "-fx-padding: 3 5 3 5;"
                    + "-fx-border-radius: 15 15 15 15; "
                    + "-fx-background-radius: 15 15 15 15;");
            tags.getChildren().add(label);
        });
    }

    /**
     * Instantiate favorite label
     */
    private void initFavorite(ReadOnlyPerson person, IconImage image) {
        favorite.setGraphic(person.getFavorite().favorite
                ? new ImageView(image.getHeart())
                : new ImageView(image.getHeartOutline()));
    }

    /**
     * Instantiate the facebook icon if a facebook account is linked with the person
     */
    private void initFbIcon(ReadOnlyPerson person, IconImage image) {
        facebookPage.setGraphic(new ImageView(image.getFbicon()));
        facebookPage.setVisible(!person.getFacebook().value.equals(""));
    }


    /**
     * Get color from the hashmap. If not found, generate a new index and a random color
     * @param tagName specify the index
     * @return a color string
     */
    private String getColor(String tagName) {
        if (labelColor.containsKey(tagName)) {
            return labelColor.get(tagName);
        } else {
            Random random = new Random();
            // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
            int nextInt = random.nextInt(256 * 256 * 256);
            // format it as hexadecimal string (with hashtag and leading zeros)
            String colorCode = String.format("#%06x", nextInt);
            labelColor.put(tagName, colorCode);
            return labelColor.get(tagName);
        }
    }

    /**
     * Handles press on the facebook icon
     */
    @FXML
    private void openFacebookPage() {
        raise(new PersonFacebookOpenEvent(person));
    }


    /**
     * Handles toggling the favorite attribute of a person
     */
    @FXML
    private void toggleFavorite() {
        raise(new ToggleFavoritePersonEvent(id.getText().substring(0, 1)));
    }

    /**
     * Handles google searching for person's name
     */
    @FXML
    private void searchName() {
        raise(new SearchNameEvent(name.getText()));
    }

    /**
     * Handles google searching for person's major
     */
    @FXML
    private void searchMajor() {
        raise(new SearchMajorEvent(major.getText()));
    }


```
###### /java/seedu/address/commons/util/StringUtil.java
``` java
    /**
     * check if a string contains another substring, ignoring case
     * @param str String to be checked
     * @param searchStr Substring
     * @return true if {@code str} contains {@code searchStr}, false otherwise
     */
    public static boolean containsIgnoreCase(String str, String searchStr) {
        final int length = searchStr.length();
        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length)) {
                return true;
            }
        }
        return false;
    }
```
###### /java/seedu/address/commons/util/LanguageUtil.java
``` java
package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.commands.CommandList;
import seedu.address.logic.util.CommandWord;

/**
 * Helper function to enable auto-completition for command box
 */
public class LanguageUtil {
    /**
     * Return a correct command word that is the closet to the one provided by user.
     * @param commandWord by the user
     * @return the closest correct command
     */
    public static String getClosestCommand(String commandWord) {
        commandWord = commandWord.toLowerCase();
        ArrayList<String> commandListString = new CommandList().getCommandList();
        List<CommandWord> commandListCommand = new ArrayList<>();

        for (String command : commandListString) {
            commandListCommand.add(new CommandWord(command,
                    levenshteinDistance(commandWord, commandWord.length(), command, command.length())));
        }
        commandListCommand.sort(Comparator.comparing(CommandWord::getDistance));
        return commandListCommand.get(0).commandWord;
    }

    /**
     * Compare the distance between two string
     * @param s first string
     * @param lenS length of first string
     * @param t second string
     * @param lenT length of second string
     * @return levenshtein distance between 2 strings
     */
    public static Integer levenshteinDistance(String s, Integer lenS, String t, Integer lenT) {
        Integer cost;
        // base case: empty strings
        if (lenS == 0) {
            return lenT;
        }
        if (lenT == 0) {
            return lenS;
        }
        /* test if last characters of the strings match */
        if (s.charAt(lenS - 1) == t.charAt(lenT - 1)) {
            cost = 0;
        } else {
            cost = 1;
        }
        /* return minimum of delete char from s, delete char from t, and delete char from both */
        return Math.min(Math.min(levenshteinDistance(s, lenS - 1, t, lenT) + 1,
                levenshteinDistance(s, lenS, t, lenT - 1) + 1),
                levenshteinDistance(s, lenS - 1, t, lenT - 1) + cost);
    }
}
```
###### /java/seedu/address/commons/util/CollectionUtil.java
``` java
    /**
     * Return true if two collections has mutual member or 1 member of the first collections contains
     * the second member of the 2nd set
     */
    public static boolean mutualOrContains(Set<Tag> s1, Set<Tag> s2) {
        if (!Collections.disjoint(s1, s2)) {
            return true;
        }
        for (Tag t1 : s1) {
            for (Tag t2 : s2) {
                if (t1.tagName.contains(t2.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }
    //author

}
```
###### /java/seedu/address/commons/events/ui/SearchNameEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class SearchNameEvent extends BaseEvent {


    private final String name;

    public SearchNameEvent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonFacebookOpenEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a facebook open request
 */
public class PersonFacebookOpenEvent extends BaseEvent {


    private final ReadOnlyPerson newSelection;

    public PersonFacebookOpenEvent(ReadOnlyPerson newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/commons/events/ui/SearchMajorEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class SearchMajorEvent extends BaseEvent {


    private final String major;

    public SearchMajorEvent(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getMajor() {
        return major;
    }
}
```
###### /java/seedu/address/commons/events/ui/ToggleFavoritePersonEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a facebook open request
 */
public class ToggleFavoritePersonEvent extends BaseEvent {

    private final String id;

    public ToggleFavoritePersonEvent(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getId() {
        return id;
    }
}
```
###### /java/seedu/address/logic/util/CommandWord.java
``` java
package seedu.address.logic.util;

import seedu.address.commons.util.CollectionUtil;

/**
 * a Command Word class with the command word and distance calculated using levenshtein algorithm.
 */
public class CommandWord {
    public final String commandWord;
    public final Integer distance;

    public CommandWord (String commandWord, Integer distance) {
        CollectionUtil.requireAllNonNull(commandWord, distance);
        this.commandWord = commandWord;
        this.distance = distance;
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public Integer getDistance() {
        return this.distance;
    }
}
```
###### /java/seedu/address/logic/parser/FindCommandParser.java
``` java
        try {
            Optional<String> name = argMultimap.getValue(PREFIX_NAME);
            Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
            Optional<String> address = argMultimap.getValue(PREFIX_ADDRESS);
            Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
            Optional<String> remark = argMultimap.getValue(PREFIX_REMARK);
            Optional<String> major = argMultimap.getValue(PREFIX_MAJOR);
            Optional<String> facebook = argMultimap.getValue(PREFIX_FACEBOOK);
            Optional<Set<Tag>> tags = parseTagsForFind(argMultimap.getAllValues(PREFIX_TAG));

            if (name.isPresent()) {
                keywords = name.get().trim().split("\\s+");
                return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (phone.isPresent()) {
                keywords = phone.get().trim().split("\\s+");
                return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (address.isPresent()) {
                keywords = address.get().trim().split("\\s+");
                return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (email.isPresent()) {
                keywords = email.get().trim().split("\\s+");
                return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (remark.isPresent()) {
                keywords = remark.get().trim().split("\\s+");
                return new FindCommand(new RemarkContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (major.isPresent()) {
                keywords = major.get().trim().split("\\s+");
                return new FindCommand(new MajorContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (facebook.isPresent()) {
                keywords = facebook.get().trim().split("\\s+");
                return new FindCommand(new FacebookContainsKeywordsPredicate(Arrays.asList(keywords)));
            } else if (tags.isPresent()) {
                return new FindCommand(new TagContainsKeywordsPredicate(tags.get()));
            } else if ("favorite".equals(trimmedArgs) || "unfavorite".equals(trimmedArgs)) {
                return new FindCommand(new FavoritePersonsPredicate(trimmedArgs));
            } else {
                keywords = trimmedArgs.split("\\s+");
                return new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList(keywords)));
            }
```
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an Command object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        }


        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");


        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> rmajor} into an {@code Optional<Major>} if {@code major} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Major> parseMajor(Optional<String> major) {
        requireNonNull(major);
        return major.isPresent() ? Optional.of(new Major(major.get())) : Optional.empty();
    }


    /**
     * Parses a {@code Optional<String> facebook} into an {@code Optional<Facebook>} if {@code facebook} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Facebook> parseFacebook(Optional<String> facebook) {
        requireNonNull(facebook);
        return facebook.isPresent() ? Optional.of(new Facebook(facebook.get())) : Optional.empty();
    }
}
```
###### /java/seedu/address/logic/parser/FavoriteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavoriteCommand object
 */
public class FavoriteCommandParser implements Parser<FavoriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavoriteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavoriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavoriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE);
        }
    }

}
```
###### /java/seedu/address/logic/commands/FavoriteCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Favorite a person identified using it's last displayed index from the address book.
 */
public class FavoriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favorite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favorites the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVORITE_PERSON_SUCCESS = "Favorited Person: %1$s";

    private final Index targetIndex;

    public FavoriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavorite = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.favoritePerson(personToFavorite);
            model.propagateToGroup(personToFavorite, null, this.getClass());
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        Person favoritedPerson = new Person(personToFavorite);
        Favorite newFavorite = new Favorite(!personToFavorite.getFavorite().favorite);
        favoritedPerson.setFavorite(newFavorite);

        return new CommandResult(String.format(MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson));


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavoriteCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Add a remark for a specific person
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person to the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARK "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Best friends ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Remark remark;
    private final Index targetIndex;

    public RemarkCommand(Index index, Remark remark) {
        this.targetIndex = index;
        this.remark = remark;
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToRemark = lastShownList.get(targetIndex.getZeroBased());
        Person remarkedPerson = addOrChangeRemark(personToRemark, this.remark);
        remarkedPerson.setFavorite(personToRemark.getFavorite());

        try {
            model.updatePerson(personToRemark, remarkedPerson);
            model.propagateToGroup(personToRemark, remarkedPerson, this.getClass());
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, remarkedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemarkCommand) other).targetIndex)); // state check
    }

    /**
     * Generate a new person with all attributes from the readonly person, but add or change remark
     * @param person Readonly person to be remarked/editted
     * @param remark new Remark object to be insert
     * @return a new Readonly person with the remark object
     */
    public static Person addOrChangeRemark(ReadOnlyPerson person, Remark remark) {
        Name updatedName = person.getName();
        Phone updatedPhone = person.getPhone();
        Email updatedEmail = person.getEmail();
        Address updatedAddress = person.getAddress();
        Birthday updatedBirthday = person.getBirthday();
        Major updatedMajor = person.getMajor();
        Facebook updatedFacebook = person.getFacebook();
        Set<Tag> updatedTags = person.getTags();

        return new Person(updatedName, updatedPhone,
                updatedEmail, updatedAddress, updatedBirthday, remark,
                updatedMajor, updatedFacebook, updatedTags);
    }

}
```
###### /java/seedu/address/logic/commands/CommandList.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;

/**
 * Compile a list of command words and return an array list of {@COMMAND_WORD}
 */
public class CommandList {
    private final ArrayList<String> commandList = new ArrayList<>();

    public CommandList() {
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteTagCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FavoriteCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(ListAlphabetCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RemarkCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(GroupingCommand.COMMAND_WORD);
        commandList.add(ListGroupsCommand.COMMAND_WORD);
        commandList.add(DeleteGroupCommand.COMMAND_WORD);
        commandList.add(ViewGroupCommand.COMMAND_WORD);
        commandList.add(EditGroupCommand.COMMAND_WORD);
    }

    public ArrayList<String> getCommandList() {
        return this.commandList;
    }
}
```
###### /java/seedu/address/model/person/predicates/AnyContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s any detail of <br>
 * {@code Address}, {@code Phone}, {@code Email}, {@code Name} and {@code Tag} <br>
 *  matches any of the keywords given.
 */
public class AnyContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AnyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getRemark().remark, keyword)
                || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getMajor().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getFacebook().value, keyword)
                || !Collections.disjoint(person.getTagsString(), keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnyContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AnyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
//author
```
###### /java/seedu/address/model/person/predicates/TagContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final Set<Tag> tags;

    public TagContainsKeywordsPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return CollectionUtil.mutualOrContains(person.getTags(), tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.tags.equals(((TagContainsKeywordsPredicate) other).tags)); // state check
    }

}
//author
```
###### /java/seedu/address/model/person/predicates/AddressContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/predicates/FacebookContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Facebook} matches any of the keywords given.
 */
public class FacebookContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FacebookContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getFacebook().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FacebookContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/predicates/RemarkContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Remark} matches any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getRemark().remark, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((RemarkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/predicates/MajorContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Major} matches any of the keywords given.
 */
public class MajorContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public MajorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMajor().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MajorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/predicates/PhoneContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/predicates/EmailContainsKeywordsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/Favorite.java
``` java
package seedu.address.model.person;

/**
 * Represents whether a Person is favorited in the address book.
 */
public class Favorite {
    public final boolean favorite;

    public Favorite() {
        this.favorite = false;
    }

    public Favorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getValue() {
        return this.favorite ? 1 : 0;
    }
    @Override
    public String toString() {
        return favorite ? "true" : "false";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.favorite == (((Favorite) other).favorite)); // state check
    }
}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Favorites the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void favorite(ReadOnlyPerson toFavorite) throws PersonNotFoundException {
        requireNonNull(toFavorite);
        int index = internalList.indexOf(toFavorite);

        if (index == -1) {
            throw new PersonNotFoundException();
        }

        Person newPerson = new Person(toFavorite);
        newPerson.setFavorite(new Favorite(!toFavorite.getFavorite().favorite));

        internalList.set(index, newPerson);
        sort();
    }
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * sort the list in default sorting order: Favorite > non-Favorite; then alphabetical order
     */
    public void sort() {
        internalList.sort((ReadOnlyPerson p1, ReadOnlyPerson p2) -> {
            if (!p1.getFavorite().equals(p2.getFavorite())) {
                return p2.getFavorite().getValue() - p1.getFavorite().getValue();
            } else {
                switch (attribute) {
                case 1: return p1.getPhone().value.compareTo(p2.getPhone().value);
                case 2: return p1.getEmail().value.compareTo(p2.getEmail().value);
                case 3: return p1.getAddress().value.compareTo(p2.getAddress().value);
                default: return p1.getName().fullName.compareTo(p2.getName().fullName);
                }
            }
        });
    }

```
###### /java/seedu/address/model/person/Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark {
    public final String remark;


    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark.trim();
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }

    public void setFavorite(Favorite favorite) {
        this.favorite.set(requireNonNull(favorite));
    }

    @Override
    public ObjectProperty<Favorite> favoriteProperty() {
        return favorite;
    }

    @Override
    public Favorite getFavorite() {
        return favorite.get();
    }

    public void setMajor(Major major) {
        this.major.set(requireNonNull(major));
    }

    @Override
    public ObjectProperty<Major> majorProperty() {
        return major;
    }

    @Override
    public Major getMajor() {
        return major.get();
    }

    public void setFacebook(Facebook facebook) {
        this.facebook.set(requireNonNull(facebook));
    }

    @Override
    public ObjectProperty<Facebook> facebookProperty() {
        return facebook;
    }

    @Override
    public Facebook getFacebook() {
        return facebook.get();
    }
```
###### /java/seedu/address/model/person/Major.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent a student's major in the address book.
 * User might also add year of study as an additional information (optional)
 */
public class Major {
    public final String value;


    public Major(String major) {
        requireNonNull(major);
        this.value = major.trim();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && this.value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
