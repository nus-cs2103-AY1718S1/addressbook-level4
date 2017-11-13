package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book whose information contains any of the argument keywords in the
 * specific field.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String COMMAND_SECONDARY = "search";

    //@@author CindyTsai1
    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Finds all persons whose information contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: ["
            + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + "ian zach\n"
            + "Returns any person with name including 'ian' and 'zach'.\n"
            + COMMAND_WORD + " "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney\n"
            + "Returns all persons in UniFy containing *both* 'JCfriends' and 'computing'.";

    public static final String MESSAGE_NOT_FOUND = "At least one field to find must be provided.";
    public static final String MESSAGE_BIRTHDAYKEYWORD_INVALID = "You should type 0%1$s instead of %1$s.";
    public static final String MESSAGE_BIRTHDAYKEYWORD_NONEXIST = "Month %1$s does not exist.";
    public static final String MESSAGE_BIRTHDAYKEYWORD_NONNUMBER = "Keyword input must be in integer.";
    private ArrayList<String> predicate;
    //to keep track of how many prefixes are input
    private int count = 0;
    private String birthdaySearch = new String();

    public FindCommand(ArrayList<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        List<String> predicateList = new ArrayList<>();

        //to keep track of the number of times a single person matches different fields of keywords
        HashMap<String, Integer> predicateMap = new HashMap<>();

        for (int i = 0; i < predicate.size() - 1; i++) {
            String predicates = predicate.get(i);

            if (predicates.equals(PREFIX_NAME.getPrefix())) {
                ArrayList<String> personsWithName = findPersonsWithName(predicate.get(i + 1));
                for (int j = 0; j < personsWithName.size(); j++) {
                    if (!predicateMap.containsKey(personsWithName.get(j))) {
                        predicateMap.put(personsWithName.get(j), 1);
                        predicateList.add(personsWithName.get(j));
                    } else {
                        predicateMap.put(personsWithName.get(j), predicateMap.remove(personsWithName.get(j)) + 1);
                    }
                }
            }

            if (predicates.equals(PREFIX_PHONE.getPrefix())) {
                ArrayList<String> personsWithPhone = findPersonsWithPhone(predicate.get(i + 1));
                for (int j = 0; j < personsWithPhone.size(); j++) {
                    if (!predicateMap.containsKey(personsWithPhone.get(j))) {
                        predicateMap.put(personsWithPhone.get(j), 1);
                        predicateList.add(personsWithPhone.get(j));
                    } else {
                        predicateMap.put(personsWithPhone.get(j), predicateMap.remove(personsWithPhone.get(j)) + 1);
                    }
                }
            }

            if (predicates.equals(PREFIX_ADDRESS.getPrefix())) {
                ArrayList<String> personsWithAddress = findPersonsWithAddress(predicate.get(i + 1));
                for (int j = 0; j < personsWithAddress.size(); j++) {
                    if (!predicateMap.containsKey(personsWithAddress.get(j))) {
                        predicateMap.put(personsWithAddress.get(j), 1);
                        predicateList.add(personsWithAddress.get(j));
                    } else {
                        predicateMap.put(personsWithAddress.get(j), predicateMap.remove(personsWithAddress.get(j)) + 1);
                    }
                }
            }

            if (predicates.equals(PREFIX_EMAIL.getPrefix())) {
                ArrayList<String> personsWithEmail = findPersonsWithEmail(predicate.get(i + 1));
                for (int j = 0; j < personsWithEmail.size(); j++) {
                    if (!predicateMap.containsKey(personsWithEmail.get(j))) {
                        predicateMap.put(personsWithEmail.get(j), 1);
                        predicateList.add(personsWithEmail.get(j));
                    } else {
                        predicateMap.put(personsWithEmail.get(j), predicateMap.remove(personsWithEmail.get(j)) + 1);
                    }
                }
            }

            if (predicates.equals(PREFIX_TAG.getPrefix())) {
                ArrayList<String> personsWithTags = findPersonsWithTags(predicate.get(i + 1));
                for (int j = 0; j < personsWithTags.size(); j++) {
                    if (!predicateMap.containsKey(personsWithTags.get(j))) {
                        predicateMap.put(personsWithTags.get(j), 1);
                        predicateList.add(personsWithTags.get(j));
                    } else {
                        predicateMap.put(personsWithTags.get(j), predicateMap.remove(personsWithTags.get(j)) + 1);
                    }
                }
            }

            if (predicates.equals(PREFIX_BIRTHDAY.getPrefix())) {
                birthdaySearch = predicate.get(i + 1).trim();
                ArrayList<String> personsWithBirthday = findPersonsWithBirthday(predicate.get(i + 1));
                for (int j = 0; j < personsWithBirthday.size(); j++) {
                    if (!predicateMap.containsKey(personsWithBirthday.get(j))) {
                        predicateMap.put(personsWithBirthday.get(j), 1);
                        predicateList.add(personsWithBirthday.get(j));
                    } else {
                        predicateMap.put(personsWithBirthday.get(j),
                                predicateMap.remove(personsWithBirthday.get(j)) + 1);
                    }
                }
            }
        }

        //to eliminate the persons who did not match all keywords
        ArrayList<String> predicatesList = new ArrayList<>();
        for (String word : predicateList) {
            if (predicateMap.get(word) == count) {
                predicatesList.add(word);
            }
        }

        NameContainsKeywordsPredicate predicates = new NameContainsKeywordsPredicate(predicatesList);
        model.updateFilteredPersonList(predicates);

        //Different display message for birthday
        if (!birthdaySearch.isEmpty()) {
            return new CommandResult(getMessageForPersonListShownSummary(
                    model.getFilteredPersonList().size())
            + getMessageForMonthSearch(birthdaySearch));
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons with the same
     * birthday month.
     * @param month used to generate Month
     * @return summary message for the birthday month of persons displayed
     */
    public static String getMessageForMonthSearch(String month) {
        switch (month) {
        case "01":
            month = "January";
            break;
        case "02":
            month = "February";
            break;
        case "03":
            month = "March";
            break;
        case "04":
            month = "April";
            break;
        case "05":
            month = "May";
            break;
        case "06":
            month = "June";
            break;
        case "07":
            month = "July";
            break;
        case "08":
            month = "August";
            break;
        case "09":
            month = "September";
            break;
        case "10":
            month = "October";
            break;
        case "11":
            month = "November";
            break;
        case "12":
            month = "December";
            break;
        default:
        }
        return String.format(Messages.MESSAGE_BIRTHDAY_MONTH_SEARCHED, month);
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

    //@@author CindyTsai1
    /**
     * Search for persons that contain the {@String keyword} in their name
     * @param name
     */
    public ArrayList<String> findPersonsWithName(String name) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] nameKeyword = name.split(" ");
        count += nameKeyword.length;
        ArrayList<String> nameList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : nameKeyword) {
                String names = person.getName().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (names.contains(keyword)) {
                    nameList.add(person.getName().toString());
                }
            }
        }
        return nameList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their phone number
     * @param phone
     */
    public ArrayList<String> findPersonsWithPhone(String phone) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] phoneKeyword = phone.split(" ");
        count += phoneKeyword.length;
        ArrayList<String> phoneList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : phoneKeyword) {
                String phones = person.getPhone().toString();
                if (phones.contains(keyword)) {
                    phoneList.add(person.getName().toString());
                }
            }
        }
        return phoneList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their email
     * @param email
     */
    public ArrayList<String> findPersonsWithEmail(String email) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] emailKeyword = email.split(" ");
        count += emailKeyword.length;
        ArrayList<String> emailList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : emailKeyword) {
                String emails = person.getEmail().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (emails.contains(keyword)) {
                    emailList.add(person.getName().toString());
                }
            }
        }
        return emailList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their address
     * @param address
     */
    public ArrayList<String> findPersonsWithAddress(String address) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] addressKeyword = address.split(" ");
        count += addressKeyword.length;
        ArrayList<String> addressList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : addressKeyword) {
                String addresses = person.getAddress().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (addresses.contains(keyword)) {
                    addressList.add(person.getName().toString());
                }
            }
        }
        return addressList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their tag
     * @param tags
     */
    public ArrayList<String> findPersonsWithTags(String tags) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] tagKeyword = tags.split(" ");
        count += tagKeyword.length;
        ArrayList<String> tagList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : tagKeyword) {
                //Used to keep track if a person's tags matches one keyword multiple times, if yes only record once
                ArrayList<String> tagsList = new ArrayList<>();
                for (Tag tagging : person.getTags()) {
                    //the tag name returned is in the format of [TAGNAME] so extract the name without []
                    String tag1 = tagging.toString().substring(1, tagging.toString().length()).toLowerCase();
                    keyword = keyword.toLowerCase();
                    if (tag1.contains(keyword) && !tagsList.contains(person.getName().toString())) {
                        tagsList.add(person.getName().toString());
                    }
                }
                tagList.addAll(tagsList);
            }
        }
        return tagList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their birthday
     * @param birthday
     */
    public ArrayList<String> findPersonsWithBirthday(String birthday) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] birthdayKeyword = birthday.split(" ");
        count += birthdayKeyword.length;
        ArrayList<String> birthdayList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : birthdayKeyword) {
                String birth = person.getBirthday().toString();
                String birthdays = (!birth.equals("")) ? birth.substring(3, 5) : "";
                if (birthdays.equals(keyword)) {
                    birthdayList.add(person.getName().toString());
                }
            }
        }
        return birthdayList;
    }
}
