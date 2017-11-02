# NabeelZaheer
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
/**
 * Add the specified tag to the address book
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add the tag to a person by the index number used "
            + "in the last person listing.\n"
            + "Parameters: TAG... INDEX...(INDEX must be positive integer)\n"
            + "[INDEX] can be set as a range."
            + "Example: " + COMMAND_WORD + " friends 1"
            + "Example: " + COMMAND_WORD + " friends 1-4";

    public static final String MESSAGE_ADDED_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag: %1$s already exist in";

    private final Set<Tag> tag;
    private final Set<Index> index;
    private final String indexDisplay;

    /**
     *
     * @param tag to be added to address book
     * @param index of the person in the filtered list to remove tag
     */
    public AddTagCommand(Set<Tag> tag, Set<Index> index, String indexDisplay)  {
        this.tag = tag;
        this.index = index;
        this.indexDisplay = indexDisplay;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String duplicate;

        for (Index i : index) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        successMessage = String.format(MESSAGE_ADDED_SUCCESS + " to index " + indexDisplay + ".", tag);
        duplicate = String.format(MESSAGE_DUPLICATE_TAG + " index: " + indexDisplay + ".", tag);

        try {
            model.addTag(tag, index);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(
                    String.format
                            (MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(duplicate);
        }
        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }
        // state check
        AddTagCommand e = (AddTagCommand) other;

        boolean check1 = checkEqual(tag, e.tag);
        boolean check2 = checkEqual(index, e.index);
        return check1 && check2;
    }

    /**
     *
     * @param set1
     * @param set2
     * @param <T>
     * @return true if set1 and set2 are identical
     */
    public <T> boolean checkEqual(Set<T> set1, Set<T> set2) {
        Iterator<T> it1 = set1.iterator();
        Iterator<T> it2 = set2.iterator();

        Boolean check = false;

        while (it1.hasNext()) {
            T item = it1.next();
            while (it2.hasNext()) {
                T item2 = it2.next();
                if (item.equals(item2)) {
                    check = true;
                }
            }
        }
        return check;
    }
}

```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Remove the specified tag from address book
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the tag from a person by the index number used "
            + "in the last person listing.\n"
            + "Remove the specified tag in the whole address book by excluding the [INDEX] parameter.\n"
            + "Parameters: TAG... INDEX...(INDEX must be positive integer)\n"
            + "[INDEX] can be set as a range."
            + "Example: " + COMMAND_WORD + " friends 1"
            + "Example: " + COMMAND_WORD + " friends 1-4";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag: %1$s does not exist in";

    private final Set<Tag> tag;
    private final Set<Index> index;
    private final List<String> indexDisplay;

    /**
     *
     * @param tag to be removed from address book
     * @param index of the person in the filtered list to remove tag
     */
    public RemoveTagCommand(Set<Tag> tag, Set<Index> index, List<String> indexDisplay)  {
        this.tag = tag;
        this.index = index;
        this.indexDisplay = indexDisplay;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String notFound;

        String indexInput = indexDisplay.stream().collect(Collectors.joining(", "));

        if (!index.isEmpty()) {

            for (Index i : index) {
                if (i.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }
            successMessage = String.format(MESSAGE_REMOVE_SUCCESS + " from index " + indexInput + ".", tag);
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " index: " + indexInput + ".", tag);
        } else {
            successMessage = String.format(MESSAGE_REMOVE_SUCCESS + " from address book.", tag);
            notFound = String.format(MESSAGE_TAG_NOT_FOUND + " the address book.", tag);
        }


        try {
            model.removeTag(tag, indexDisplay);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(
                    String.format
                            (MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(notFound);
        }
        return new CommandResult(successMessage);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }
        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        if ((index.size() == 0) && (e.index.size() == 0)) {
            return checkEqual(tag, e.tag);
        }

        boolean check1 = checkEqual(tag, e.tag);
        boolean check2 = checkEqual(index, e.index);
        return check1 && check2;
    }

    /**
     *
     * @param set1
     * @param set2
     * @param <T>
     * @return true if set1 and set2 are identical
     */
    public <T> boolean checkEqual(Set<T> set1, Set<T> set2) {
        Iterator<T> it1 = set1.iterator();
        Iterator<T> it2 = set2.iterator();

        Boolean check = false;

        while (it1.hasNext()) {
            T item = it1.next();
            while (it2.hasNext()) {
                T item2 = it2.next();
                if (item.equals(item2)) {
                    check = true;
                }
            }
        }

        return check;
    }
}

```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns a AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        Set<Tag> toAddSet = new HashSet<>();
        Set<Index> index = new HashSet<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args.trim(), " ");

        try {
            Tag toAdd = new Tag(st.nextToken());
            toAddSet.add(toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Boolean indexAdded = false;
        String indexInput;
        List<String> indexSet = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            Boolean isIndex = true;

            // check is newToken holds an non-integer value
            char[] charArray = newToken.toCharArray();
            String lowerLimit = "";
            String upperLimit = "";
            boolean isRange = false;
            if (newToken.contains("-")) {
                isRange = true;
            }
            boolean reach2nd = false;
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    if (c == '-') {
                        reach2nd = true;
                        continue;
                    }
                    isIndex = false;
                    break;
                } else {
                    if (isRange) {
                        if (!reach2nd) {
                            lowerLimit += c;
                        } else {
                            upperLimit += c;
                        }
                    }
                }
            }

            if (isRange) {
                boolean isLowerValid = lowerLimit.isEmpty();
                boolean isUpperValid = upperLimit.isEmpty();
                if (isLowerValid || isUpperValid) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }
                int lower = Integer.parseInt(lowerLimit);
                int upper = Integer.parseInt(upperLimit);
                if (lower > upper) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                }

                for (int i = lower; i <= upper; i++) {
                    String toAdd = String.valueOf(i);
                    indexSet.add(toAdd);
                    try {
                        Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
                        index.add(indexFromRangeToAdd);
                        indexAdded = true;
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                    }
                }
            } else {
                if (isIndex) {
                    indexSet.add(newToken);
                    try {
                        Index indexToAdd = ParserUtil.parseIndex(newToken);
                        index.add(indexToAdd);
                        indexAdded = true;
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                    }
                } else {
                    if (indexAdded) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                    } else {
                        try {
                            Tag toAdd = new Tag(newToken);
                            toAddSet.add(toAdd);
                        } catch (IllegalValueException ive) {
                            throw new ParseException(
                                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
                        }
                    }
                }
            }
        }

        if (index.isEmpty()) {
            throw new ParseException("Please provide at least one index.\n"
            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        indexInput = indexSet.stream().collect(Collectors.joining(", "));
        return new AddTagCommand(toAddSet, index, indexInput);
    }

}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(trimmedArgs, " ");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!((token.contains(PREFIX_NAME.getPrefix())) || (token.contains(PREFIX_PHONE.getPrefix()))
                || (token.contains(PREFIX_EMAIL.getPrefix())) || (token.contains(PREFIX_ADDRESS.getPrefix()))
                    || (token.contains(PREFIX_TAG.getPrefix())))) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_EMAIL, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_ADDRESS);

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME);
        List<String> phoneList = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> emailList = argMultimap.getAllValues(PREFIX_EMAIL);
        List<String> addressList = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        List<List<String>> keywords = new ArrayList<>();
        keywords.add(nameList);
        keywords.add(phoneList);
        keywords.add(emailList);
        keywords.add(addressList);
        keywords.add(tagList);

        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_NAME);
        prefixList.add(PREFIX_PHONE);
        prefixList.add(PREFIX_EMAIL);
        prefixList.add(PREFIX_ADDRESS);
        prefixList.add(PREFIX_TAG);

        String missingInput = "";
        Boolean checkMissingInput = false;
        for (int i = 0; i < keywords.size(); i++) {
            List<String> list = keywords.get(i);
            String prefix = prefixList.get(i).getPrefix();
            if (checkNoInput(list) && trimmedArgs.contains(prefix)) {
                missingInput += prefix + " ";
                checkMissingInput = true;
            }
        }
        if (checkMissingInput) {
            throw new ParseException("Missing input for field: " + missingInput + "\n"
                    + FindCommand.MESSAGE_USAGE);
        }

        Predicate<ReadOnlyPerson> predicate =
                new FieldContainsKeywordsPredicate(keywords);
        return new FindCommand(predicate);
    }
```
###### \java\seedu\address\logic\parser\RemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        Set<Tag> toRemoveSet = new HashSet<>();
        Set<Index> index = new HashSet<>();

        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(args, " ");

        try {
            Tag toRemove = new Tag(st.nextToken());
            toRemoveSet.add(toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        Boolean indexAdded = false;
        List<String> indexSet = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            Boolean isIndex = true;


            // check is newToken holds an non-integer value
            char[] charArray = newToken.toCharArray();
            String lowerLimit = "";
            String upperLimit = "";
            boolean isRange = false;
            if (newToken.contains("-")) {
                isRange = true;
            }
            boolean reach2nd = false;
            for (char c : charArray) {
                if (!Character.isDigit(c)) {
                    if (c == '-') {
                        reach2nd = true;
                        continue;
                    }
                    isIndex = false;
                    break;
                } else {
                    if (isRange) {
                        if (!reach2nd) {
                            lowerLimit += c;
                        } else {
                            upperLimit += c;
                        }
                    }
                }
            }

            if (isRange) {
                boolean isLowerValid = lowerLimit.isEmpty();
                boolean isUpperValid = upperLimit.isEmpty();
                if (isLowerValid || isUpperValid) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }
                int lower = Integer.parseInt(lowerLimit);
                int upper = Integer.parseInt(upperLimit);
                if (lower > upper) {
                    throw new ParseException("Invalid index range provided.\n"
                            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                }

                for (int i = lower; i <= upper; i++) {
                    String toAdd = String.valueOf(i);
                    indexSet.add(toAdd);
                    try {
                        Index indexFromRangeToAdd = ParserUtil.parseIndex(toAdd);
                        index.add(indexFromRangeToAdd);
                        indexAdded = true;
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    }
                }
            } else {
                if (isIndex) {
                    indexSet.add(newToken);
                    try {
                        Index indexToAdd = ParserUtil.parseIndex(newToken);
                        index.add(indexToAdd);
                        indexAdded = true;
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    }
                } else {
                    if (indexAdded) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                    } else {
                        try {
                            Tag toRemove = new Tag(newToken);
                            toRemoveSet.add(toRemove);
                        } catch (IllegalValueException ive) {
                            throw new ParseException(
                                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
                        }
                    }
                }
            }
        }
        return new RemoveTagCommand(toRemoveSet, index, indexSet);
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Set<Tag> tag, List<String> index) throws DuplicatePersonException,
            PersonNotFoundException {
        int totalSize = getFilteredPersonList().size();
        boolean tagExist = false;
        int decrease = 0;
        if (!index.isEmpty()) {
            boolean removed = false;
            for (int i = 0; i < index.size(); i++) {
                int currentSize = getFilteredPersonList().size();
                int indexToRemove = Integer.parseInt(index.get(i)) - 1;
                Person toDelete;
                /**
                 * Checks if tags have been removed before
                 * causing filtered list to change
                 */
                if (totalSize != currentSize) {
                    if (removed) {
                        indexToRemove -= decrease;
                    }
                    toDelete = new Person(getFilteredPersonList().get(indexToRemove));
                } else {
                    toDelete = new Person(getFilteredPersonList().get(indexToRemove));
                }
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTag(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    removed = true;
                    decrease++;
                    updatePerson(toDelete, toUpdate);
                }
            }
        } else {
            for (int i = 0; i < totalSize; i++) {
                Person toDelete = new Person(getFilteredPersonList().get(i));
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTag(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    updatePerson(toDelete, toUpdate);
                }
            }
        }

        if (!tagExist) {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public void addTag(Set<Tag> tag, Set<Index> index) throws PersonNotFoundException,
            DuplicatePersonException {

        Iterator<Index> indexIt = index.iterator();
        boolean added = false;

        while (indexIt.hasNext()) {
            int indexToAdd = indexIt.next().getZeroBased();
            Person toCheck = new Person(getFilteredPersonList().get(indexToAdd));
            Person toUpdate = new Person(toCheck);
            Set<Tag> current = toCheck.getTags();
            Set<Tag> updated = newTag(tag, current);
            if (!(current.size() == updated.size())) {
                toUpdate.setTags(updated);
                added = true;
                updatePerson(toCheck, toUpdate);
            }
        }

        if (!added) {
            throw new PersonNotFoundException();
        }
    }


    /**
     *
     * @param tag set of tags input by user
     * @param oldTags set of current tags
     * @return Set of Tags of new Person to be updated
     */
    private Set<Tag> deleteTag(Set<Tag> tag, Set<Tag> oldTags) {
        Set<Tag> newTags = new HashSet<>();

        Iterator<Tag> it = oldTags.iterator();
        while (it.hasNext()) {
            Tag checkTag = it.next();
            String current = checkTag.tagName;
            boolean toAdd = true;
            Iterator<Tag> it2 = tag.iterator();
            while (it2.hasNext()) {
                String toCheck = it2.next().tagName;
                if (current.equals(toCheck)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                newTags.add(checkTag);
            }
        }
        return newTags;
    }

    /**
     *
     * @param tag set of tags input by user
     * @param current set of current tags
     * @return Set of tags to be updated
     */
    private Set<Tag> newTag(Set<Tag> tag, Set<Tag> current) {
        Set<Tag> updated = new HashSet<>();
        Iterator<Tag> it = current.iterator();
        boolean exist = false;
        for (Tag t : tag) {
            Tag toAdd = t;
            while (it.hasNext()) {
                Tag toCheck = it.next();
                if (current.equals(t)) {
                    exist = true;
                }
                updated.add(toCheck);
            }
            if (!exist) {
                updated.add(toAdd);
                exist = false;
            }
        }
        return updated;
    }
```
###### \java\seedu\address\model\person\FieldContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag, Name, Email, Phone, Address} matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<List<String>> keywords;

    public FieldContainsKeywordsPredicate(List<List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tag = new HashSet<>(person.getTags());
        List<String> tagName = new ArrayList<>();
        Iterator<Tag> it = tag.iterator();
        while (it.hasNext()) {
            tagName.add(it.next().tagName);
        }
        String mergedNames = tagName.stream().collect(Collectors.joining(" "));
        return keywords.get(0).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                        (person.getName().fullName, keyword))
                || keywords.get(1).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getPhone().value, keyword))
                || keywords.get(2).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getEmail().value, keyword))
                || keywords.get(3).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (person.getAddress().value, keyword))
                || keywords.get(4).stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                                (mergedNames, keyword))
                || keywords.get(0).stream()
                        .anyMatch(keyword -> ((person.getName().fullName).toLowerCase()).contains(keyword))
                || keywords.get(1).stream()
                        .anyMatch(keyword -> ((person.getPhone().value).toLowerCase()).contains(keyword))
                || keywords.get(2).stream()
                        .anyMatch(keyword -> ((person.getEmail().value).toLowerCase()).contains(keyword))
                || keywords.get(3).stream()
                        .anyMatch(keyword -> ((person.getAddress().value).toLowerCase()).contains(keyword))
                || keywords.get(4).stream()
                        .anyMatch(keyword -> (mergedNames.toLowerCase()).contains(keyword));
    }
```
