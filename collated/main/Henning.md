# Henning
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
    public static final String MESSAGE_SUCCESS_SCORE = "Sorted successfully by Group Scores, listing all persons below";
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
    private String sortFilter;

    public SortCommand (String sortFilter) {
        //Null sort Filter refers to default sort command i.e. sort by name
        this.sortFilter = sortFilter;
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (sortFilter) {
        case "name":
        default:
            model.sortFilteredPersonListName();
            return new CommandResult(MESSAGE_SUCCESS_NAME);

        case "birthday":
        case "b":
            model.sortFilteredPersonListBirthday();
            return new CommandResult(MESSAGE_SUCCESS_BIRTHDAY);

        //author Henning
        case "score":
        case "s":
            model.sortFilteredPersonListScore();
            return new CommandResult(MESSAGE_SUCCESS_SCORE);
            //author Henning
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // if not the same object, return false
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand s = (SortCommand) other;
        return sortFilter.equals(s.sortFilter);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    public static Optional<Score> parseScore(Optional<String> score) throws IllegalValueException {
        requireNonNull(score);
        return score.isPresent() ? Optional.of(new Score(score.get())) : Optional.of(new Score(""));
    }


```
###### \java\seedu\address\model\Model.java
``` java
    /** Sorts the list by groups score, in descending order*/
    void sortFilteredPersonListScore();
```
###### \java\seedu\address\model\Model.java
``` java

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersonListScore() {
        addressBook.sortPersonsByScore();
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setScore(Score score) {
        this.score.set(requireNonNull(score));
    }

    @Override
    public ObjectProperty<Score> scoreProperty() {
        return score;
    }

    @Override
    public Score getScore() {
        return score.get();
    }

    @Override
    public Avatar getAvatarPic() {
        return avatarPic.get();
    }

    public void setAvatarPic(Avatar avatar) {
        this.avatarPic.set(requireNonNull(avatar));
    }


```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" ")
                .append(getScore())
```
###### \java\seedu\address\model\person\Score.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a persons score in the address book.
 * Guarantees: immutable; is valid.
 */
public class Score {

    public static final String MESSAGE_SCORE_CONSTRAINTS =
            "The score should be a number between 0 and 9, with 9 being the best score and 0 the worst.";

    public static final String SCORE_VALIDATION_REGEX = "\\d";
    public final String value;

    public Score(String score) throws IllegalValueException {
        requireNonNull(score);
        if (score.equals("")) {
            this.value = score;
        } else {
            String filteredScore = score.replaceAll("[^\\d]", "");
            if (!isValidScore(filteredScore)) {
                throw new IllegalValueException(MESSAGE_SCORE_CONSTRAINTS);
            }
            this.value = "Group score: " + filteredScore;
        }
    }

    public boolean isValidScore(String value) {
        return value.matches(SCORE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Score && this.value.equals(((Score) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <Label fx:id="score" styleClass="cell_small_label"  text="\$score" />
```
