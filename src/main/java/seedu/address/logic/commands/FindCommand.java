package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ClearPersonListEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_2 = "search";
    public static final String COMMAND_WORD_3 = "get";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice john "
            + PREFIX_PHONE + "91234567 98765432 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author dalessr
    @Override
    public CommandResult execute() {
        String[] parameters = (String[]) predicate.getKeywords().toArray();
        //Create list to store the keywords of different keywords
        ArrayList<String> nameKeywords = new ArrayList<>();
        ArrayList<String> phoneKeywords = new ArrayList<>();
        ArrayList<String> emailKeywords = new ArrayList<>();
        ArrayList<String> addressKeywords = new ArrayList<>();
        ArrayList<String> currentKeywords = nameKeywords;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].equals(PREFIX_NAME.getPrefix())) {
                currentKeywords = nameKeywords;
            } else if (parameters[i].equals(PREFIX_PHONE.getPrefix())) {
                currentKeywords = phoneKeywords;
            } else if (parameters[i].equals(PREFIX_EMAIL.getPrefix())) {
                currentKeywords = emailKeywords;
            } else if (parameters[i].equals(PREFIX_ADDRESS.getPrefix())) {
                currentKeywords = addressKeywords;
            } else if (!parameters[i].equals("")) {
                currentKeywords.add(parameters[i]);
            }
        }
        //Go through each keywords list and get the names to search in the storage
        List<String> namesToSearch = new ArrayList<>();
        if (nameKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromNameKeywords(nameKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (phoneKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromPhoneKeywords(phoneKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (emailKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromEmailKeywords(emailKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (addressKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromAddressKeywords(addressKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }

        NameContainsKeywordsPredicate updatedPredicate = new NameContainsKeywordsPredicate(namesToSearch);
        model.updateFilteredPersonList(updatedPredicate);
        Index defaultIndex = new Index(0);
        if (model.getFilteredPersonList().size() > 0) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));
        } else {
            EventsCenter.getInstance().post(new ClearPersonListEvent());
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * Get a list of names whose contact details contain all the address keywords provided by user.
     * @param addressKeywords a list of address keywords to search for
     * Returns a list of names found by searching the corresponding address keywords
     */
    private ArrayList<String> getNamesFromAddressKeywords(ArrayList<String> addressKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> addressList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            addressList.add(model.getAddressBook().getPersonList().get(i).getAddress().toString().toLowerCase());
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < addressKeywords.size(); i++) {
            stringBuffer.append(addressKeywords.get(i) + " ");
        }
        String addressValue = stringBuffer.toString().toLowerCase().trim();
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).contains(addressValue)) {
                matchedNames.add(model.getAddressBook().getPersonList().get(i).getName().toString());
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the email keywords provided by user.
     * @param emailKeywords a list of email keywords to search for
     * Returns a list of names found by searching the corresponding email keywords
     */
    private ArrayList<String> getNamesFromEmailKeywords(ArrayList<String> emailKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            emailList.add(model.getAddressBook().getPersonList().get(i).getEmail().toString().toLowerCase());
        }
        for (int i = 0; i < emailKeywords.size(); i++) {
            for (int j = 0; j < emailList.size(); j++) {
                if (emailList.get(j).contains(emailKeywords.get(i).toLowerCase())) {
                    matchedNames.add(model.getAddressBook().getPersonList().get(j).getName().toString());
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the phone keywords provided by user.
     * @param phoneKeywords a list of phone keywords to search for
     * Returns a list of names found by searching the corresponding phone keywords
     */
    private ArrayList<String> getNamesFromPhoneKeywords(ArrayList<String> phoneKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            phoneList.add(model.getAddressBook().getPersonList().get(i).getPhone().toString());
        }
        for (int i = 0; i < phoneKeywords.size(); i++) {
            for (int j = 0; j < phoneList.size(); j++) {
                if (phoneList.get(j).contains(phoneKeywords.get(i))) {
                    matchedNames.add(model.getAddressBook().getPersonList().get(j).getName().toString());
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the name keywords provided by user.
     * @param nameKeywords a list of name keywords to search for
     * Returns a list of names found by searching the corresponding name keywords
     */
    private ArrayList<String> getNamesFromNameKeywords(ArrayList<String> nameKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            nameList.add(model.getAddressBook().getPersonList().get(i).getName().toString().toLowerCase());
        }
        for (int i = 0; i < nameKeywords.size(); i++) {
            for (int j = 0; j < nameList.size(); j++) {
                if (nameList.get(j).contains(nameKeywords.get(i).toLowerCase())) {
                    matchedNames.add(nameList.get(j));
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
