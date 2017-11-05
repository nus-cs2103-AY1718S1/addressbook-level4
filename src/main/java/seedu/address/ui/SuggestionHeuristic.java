package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.commands.CommandWordList;
import seedu.address.model.person.ReadOnlyPerson;

//@@author newalter
public class SuggestionHeuristic {

    private final String[] sortFieldsList = {"name", "phone", "email", "address", "tag", "meeting"};
    private String firstWord;
    private String prefixWords;
    private String lastWord;

    SortedSet<String> commands = new TreeSet<>();
    SortedSet<String> sortFields = new TreeSet<>();
    SortedSet<String> tags = new TreeSet<>();
    SortedSet<String> names = new TreeSet<>();
    SortedSet<String> phones = new TreeSet<>();
    SortedSet<String> emails = new TreeSet<>();

    public void initialise(List<ReadOnlyPerson> persons) {
        commands.addAll(CommandWordList.COMMAND_WORD_LIST);
        sortFields.addAll(Arrays.asList(sortFieldsList));
        for (ReadOnlyPerson person : persons) {
            names.addAll(Arrays.asList(person.getName().fullName.toLowerCase().split("\\s+")));
            person.getTags().stream().map(tag -> tag.tagName.toLowerCase()).forEachOrdered(tags::add);
            phones.add(person.getPhone().value);
            emails.add(person.getEmail().value);
        }
    }

    public void update(String command) {
        String[] args = command.toLowerCase().split("(\\s+|[a-z]/)");
        for (int i = 1; i < args.length; i++) {
            if (args[i].matches("[a-z]+")) {
                tags.add(args[i]);
            }
        }
    }

    public SortedSet<String> getSuggestions(String text) {
        parse(text);
        SortedSet<String> matchedWords;
        if (prefixWords.equals("")) {
            matchedWords = commands.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE);
        } else {
            matchedWords = tags.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE);
        }
        return matchedWords;
    }

    /**
     * Splits the command in the command box into
     * two parts by the last occurrence of space.
     * Store them into prefixWords and lastWord respectively.
     */
    private void parse(String text) {

        int lastSpace = text.lastIndexOf(" ");
        int lastSlash = text.lastIndexOf("/");
        int splitingPosition = Integer.max(lastSlash, lastSpace);
        prefixWords = text.substring(0, splitingPosition + 1);
        lastWord = text.substring(splitingPosition + 1).toLowerCase();
    }

}
