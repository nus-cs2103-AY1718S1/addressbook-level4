# nguyenvanhoang7398
###### \build\reports\jacoco\coverage\html\seedu.address\MainApp.java.html
``` html
    /**
     * Initialize empty storage folder to store profile picture
     */
    private void initEmptyStorage() {
<span class="fc" id="L119">        boolean success = (new File(StorageManager.EMPTY_STORAGE_DEFAULT_PATH)).mkdirs();</span>
<span class="pc bpc" id="L120" title="1 of 2 branches missed.">        if (!success) {</span>
<span class="fc" id="L121">            logger.warning(&quot;Problem while initializing empty storage.&quot;);</span>
        }
<span class="fc" id="L123">    }</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic\ContactTsvReader.java.html
``` html
package seedu.address.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;

/**
 * Reads tsv file
 */
public class ContactTsvReader {
    private String contactTsvFilePath;
    private ArrayList&lt;ReadOnlyPerson&gt; toAddPeople;
    private ArrayList&lt;Integer&gt; failedEntries;

<span class="fc" id="L35">    public ContactTsvReader(String contactTsvFilePath) {</span>
<span class="fc" id="L36">        this.contactTsvFilePath = contactTsvFilePath;</span>
<span class="fc" id="L37">    }</span>

    public ArrayList&lt;ReadOnlyPerson&gt; getToAddPeople() {
<span class="fc" id="L40">        return toAddPeople;</span>
    }

    public ArrayList&lt;Integer&gt; getFailedEntries() {
<span class="fc" id="L44">        return failedEntries;</span>
    }

    /**
     * Read contacts from the given file path and update toAddPeople and failedEntries
     * @throws ParseException
     * @throws IOException
     */
    public void readContactFromFile() throws ParseException, IOException {

        BufferedReader bufferedReader;
<span class="fc" id="L55">        toAddPeople = new ArrayList&lt;ReadOnlyPerson&gt;();</span>
<span class="fc" id="L56">        failedEntries = new ArrayList&lt;&gt;();</span>

        String line;
<span class="fc" id="L59">        bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));</span>
<span class="fc" id="L60">        int i = 0;</span>

        // How to read file in java line by line?
<span class="fc bfc" id="L63" title="All 2 branches covered.">        while ((line = bufferedReader.readLine()) != null) {</span>
<span class="fc bfc" id="L64" title="All 2 branches covered.">            if (i != 0) {</span>
                try {
<span class="fc" id="L66">                    ArrayList&lt;String&gt; columns = tsvLinetoArrayList(line);</span>
<span class="fc" id="L67">                    Name name = ParserUtil.parseName(checkEmptyAndReturn(retrieveColumnField(columns, 0)))</span>
<span class="fc" id="L68">                            .get();</span>
<span class="fc" id="L69">                    Occupation occupation = ParserUtil.parseOccupation(checkEmptyAndReturn(retrieveColumnField(columns,</span>
<span class="fc" id="L70">                            1))).get();</span>
<span class="fc" id="L71">                    Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(retrieveColumnField(columns, 2)))</span>
<span class="fc" id="L72">                            .get();</span>
<span class="fc" id="L73">                    Email email = ParserUtil.parseEmail(checkEmptyAndReturn(retrieveColumnField(columns, 3)))</span>
<span class="fc" id="L74">                            .get();</span>
<span class="fc" id="L75">                    Address address = ParserUtil.parseAddress(checkEmptyAndReturn(retrieveColumnField(columns, 4)))</span>
<span class="fc" id="L76">                            .get();</span>
<span class="fc" id="L77">                    Website website = ParserUtil.parseWebsite(checkEmptyAndReturn(retrieveColumnField(columns, 5)))</span>
<span class="fc" id="L78">                            .get();</span>
<span class="fc" id="L79">                    Set&lt;Tag&gt; tagList = ParserUtil.parseTags(new ArrayList&lt;String&gt;(</span>
<span class="fc" id="L80">                            Arrays.asList(retrieveColumnField(columns, 6)</span>
<span class="fc" id="L81">                                    .replaceAll(&quot;^[,\&quot;\\s]+&quot;, &quot;&quot;)</span>
<span class="fc" id="L82">                                    .replace(&quot;\&quot;&quot;, &quot;&quot;)</span>
<span class="fc" id="L83">                                    .split(&quot;[,\\s]+&quot;))));</span>
<span class="fc" id="L84">                    Remark remark = new Remark(&quot;&quot;);</span>
<span class="fc" id="L85">                    ReadOnlyPerson toAddPerson = new Person(name, occupation, phone, email, address, remark, website,</span>
                            tagList);
<span class="fc" id="L87">                    toAddPeople.add(toAddPerson);</span>
<span class="nc" id="L88">                } catch (IllegalValueException ive) {</span>
<span class="nc" id="L89">                    throw new ParseException(ive.getMessage(), ive);</span>
<span class="nc" id="L90">                } catch (NoSuchElementException nsee) {</span>
<span class="nc" id="L91">                    failedEntries.add(i);</span>
<span class="fc" id="L92">                }</span>
            }
<span class="fc" id="L94">            i++;</span>
        }

<span class="fc" id="L97">        bufferedReader.close();</span>
<span class="fc" id="L98">    }</span>

    /**
     * Check if a given string is empty and return Optional object accordingly
     * @param valueStr
     * @return
     */
    private static Optional&lt;String&gt; checkEmptyAndReturn(String valueStr) {
<span class="pc bpc" id="L106" title="1 of 2 branches missed.">        Optional&lt;String&gt; result = valueStr.length() &lt; 1 ? Optional.empty() : Optional.of(valueStr);</span>
<span class="fc" id="L107">        System.out.println(result);</span>
<span class="fc" id="L108">        return result;</span>
    }

    /**
     * Convert a line in tsv file to list of string values of fields
     * @param line
     * @return
     */
    private static ArrayList&lt;String&gt; tsvLinetoArrayList(String line) {
<span class="fc" id="L117">        final String emptyFieldValue = &quot;&quot;;</span>
<span class="fc" id="L118">        ArrayList&lt;String&gt; result = new ArrayList&lt;String&gt;();</span>

<span class="pc bpc" id="L120" title="1 of 2 branches missed.">        if (line != null) {</span>
<span class="fc" id="L121">            String[] splitData = line.split(&quot;\t&quot;);</span>
<span class="fc bfc" id="L122" title="All 2 branches covered.">            for (int i = 0; i &lt; splitData.length; i++) {</span>
<span class="pc bpc" id="L123" title="3 of 4 branches missed.">                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {</span>
<span class="fc" id="L124">                    result.add(splitData[i].trim());</span>
                } else {
<span class="nc" id="L126">                    result.add(emptyFieldValue);</span>
                }
            }
        }

<span class="fc" id="L131">        return result;</span>
    }

    /**
     * Retrieve string value of a field given the columns and its index
     * @param columns
     * @param index
     * @return
     */
    private static String retrieveColumnField(ArrayList&lt;String&gt; columns, int index) {
<span class="fc" id="L141">        final String outOfBoundValue = &quot;&quot;;</span>

        try {
<span class="fc" id="L144">            return columns.get(index).replace(&quot;\&quot;&quot;, &quot;&quot;);</span>
<span class="nc" id="L145">        } catch (IndexOutOfBoundsException e) {</span>
<span class="nc" id="L146">            return outOfBoundValue;</span>
        }
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\AddMultipleByTsvCommand.java.html
``` html
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.StringJoiner;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to add multiple contacts at once
 */
public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = &quot;addMulTsv&quot;;
    public static final String COMMAND_ALIAS = &quot;addMT&quot;;
    public static final String MESSAGE_USAGE = COMMAND_WORD + &quot;: Adds multiple people to the address book &quot;
            + &quot;given a tsv (tab separated value) txt file containing their contact information. &quot;
            + &quot;Parameters: TSV_PATH\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; D:/Contacts.txt&quot;;

    public static final String MESSAGE_SUCCESS = &quot;%d new person (people) added&quot;;
    public static final String MESSAGE_DUPLICATE_PERSON = &quot;%d new person (people) duplicated&quot;;
    public static final String MESSAGE_NUMBER_OF_ENTRIES_FAILED = &quot;%d entry (entries) failed: &quot;;
    public static final String MESSAGE_FILE_NOT_FOUND = &quot;The system cannot find the file specified&quot;;

    private final ArrayList&lt;Person&gt; toAdd;
    private final ArrayList&lt;Integer&gt; failedEntries;
    private final boolean isFileFound;

    public AddMultipleByTsvCommand(ArrayList&lt;ReadOnlyPerson&gt; toAddPeople, ArrayList&lt;Integer&gt; failedEntries,
<span class="fc" id="L36">                                   boolean isFileFound) {</span>
<span class="fc" id="L37">        this.toAdd = new ArrayList&lt;Person&gt;();</span>
<span class="fc" id="L38">        this.failedEntries = failedEntries;</span>
<span class="fc" id="L39">        this.isFileFound = isFileFound;</span>
<span class="fc bfc" id="L40" title="All 2 branches covered.">        for (ReadOnlyPerson person: toAddPeople) {</span>
<span class="fc" id="L41">            toAdd.add(new Person(person));</span>
<span class="fc" id="L42">        }</span>
<span class="fc" id="L43">    }</span>

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
<span class="fc" id="L47">        requireNonNull(model);</span>
<span class="pc bpc" id="L48" title="1 of 2 branches missed.">        if (!isFileFound) {</span>
<span class="nc" id="L49">            return new CommandResult(MESSAGE_FILE_NOT_FOUND);</span>
        }
<span class="fc" id="L51">        int numAdded = 0;</span>
<span class="fc" id="L52">        int numDuplicated = 0;</span>

<span class="fc bfc" id="L54" title="All 2 branches covered.">        for (Person person: toAdd) {</span>
            try {
<span class="fc" id="L56">                model.addPerson(person);</span>
<span class="fc" id="L57">                numAdded++;</span>
<span class="fc" id="L58">            } catch (DuplicatePersonException e) {</span>
<span class="fc" id="L59">                numDuplicated++;</span>
<span class="fc" id="L60">            }</span>
<span class="fc" id="L61">        }</span>

<span class="fc" id="L63">        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + &quot;, &quot;</span>
<span class="fc" id="L64">                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + &quot;, &quot;</span>
<span class="fc" id="L65">                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())</span>
<span class="fc" id="L66">                + joinFailedEntries(failedEntries));</span>
    }

    /**
     * Join the list of integers of failed entries into string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList&lt;Integer&gt; failedEntries) {
<span class="fc" id="L75">        StringJoiner joiner = new StringJoiner(&quot;, &quot;);</span>
<span class="pc bpc" id="L76" title="1 of 2 branches missed.">        for (Integer entry: failedEntries) {</span>
<span class="nc" id="L77">            joiner.add(entry.toString());</span>
<span class="nc" id="L78">        }</span>
<span class="fc" id="L79">        return joiner.toString();</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="nc bnc" id="L84" title="All 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
<span class="nc bnc" id="L86" title="All 2 branches missed.">                &amp;&amp; toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));</span>
    }

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\FindTagCommand.java.html
``` html
package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = &quot;findtag&quot;;
    public static final String COMMAND_ALIAS = &quot;fitg&quot;;
    public static final String MESSAGE_USAGE = COMMAND_WORD + &quot;: Finds all persons whose tags contain any of &quot;
            + &quot;the specified tags (not case-sensitive) and displays them as a list with index numbers.\n&quot;
            + &quot;Parameters: TAG [MORE_TAGS]...\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; friends family\n&quot;;

    private final TagsContainKeywordsPredicate predicate;

<span class="fc" id="L22">    public FindTagCommand(TagsContainKeywordsPredicate predicate) {</span>
<span class="fc" id="L23">        this.predicate = predicate;</span>
<span class="fc" id="L24">    }</span>

    @Override
    public CommandResult execute() {
<span class="fc" id="L28">        model.updateFilteredPersonList(predicate);</span>
<span class="fc" id="L29">        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="fc bfc" id="L34" title="All 4 branches covered.">        return other == this // short circuit if same object</span>
                || (other instanceof FindTagCommand // instanceof handles nulls
<span class="fc bfc" id="L36" title="All 2 branches covered.">                &amp;&amp; this.predicate.equals(((FindTagCommand) other).predicate)); // state check</span>
    }

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\AddMultipleByTsvCommandParser.java.html
``` html
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parser for AddMultipleByTsvCommand
 */
<span class="fc" id="L17">public class AddMultipleByTsvCommandParser implements Parser&lt;AddMultipleByTsvCommand&gt; {</span>

    /**
     * Parse arguments given by AddressBookParser
     * @param args
     * @return
     * @throws ParseException
     */
    public AddMultipleByTsvCommand parse(String args) throws ParseException {
<span class="fc" id="L26">        String trimmedArgs = args.trim();</span>
<span class="pc bpc" id="L27" title="1 of 2 branches missed.">        if (trimmedArgs.isEmpty()) {</span>
<span class="nc" id="L28">            throw new ParseException(</span>
<span class="nc" id="L29">                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByTsvCommand.MESSAGE_USAGE));</span>
        }

<span class="fc" id="L32">        String[] nameKeywords = trimmedArgs.split(&quot;\\s+&quot;);</span>
<span class="fc" id="L33">        String contactTsvFilePath = nameKeywords[0];</span>
<span class="fc" id="L34">        ContactTsvReader contactTsvReader = new ContactTsvReader(contactTsvFilePath);</span>
        boolean isFileFound;
<span class="fc" id="L36">        ArrayList&lt;ReadOnlyPerson&gt; toAddPeople = new ArrayList&lt;ReadOnlyPerson&gt;();</span>
<span class="fc" id="L37">        ArrayList&lt;Integer&gt; failedEntries = contactTsvReader.getFailedEntries();</span>

        try {
<span class="fc" id="L40">            contactTsvReader.readContactFromFile();</span>
<span class="fc" id="L41">            toAddPeople = contactTsvReader.getToAddPeople();</span>
<span class="fc" id="L42">            failedEntries = contactTsvReader.getFailedEntries();</span>
<span class="fc" id="L43">            isFileFound = true;</span>
<span class="nc" id="L44">        } catch (IOException e) {</span>
<span class="nc" id="L45">            isFileFound = false;</span>
<span class="fc" id="L46">        }</span>

<span class="fc" id="L48">        return new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\AddressBookParser.java.html
``` html
        case AddMultipleByTsvCommand.COMMAND_WORD:
        case AddMultipleByTsvCommand.COMMAND_ALIAS:
<span class="fc" id="L64">            return new AddMultipleByTsvCommandParser().parse(arguments);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\FindTagCommandParser.java.html
``` html
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */

<span class="fc" id="L16">public class FindTagCommandParser implements Parser&lt;FindTagCommand&gt; {</span>

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindTagCommand parse(String args) throws ParseException {
<span class="fc" id="L25">        String trimmedArgs = args.trim();</span>
<span class="pc bpc" id="L26" title="1 of 2 branches missed.">        if (trimmedArgs.isEmpty()) {</span>
<span class="nc" id="L27">            throw new ParseException(</span>
<span class="nc" id="L28">                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));</span>
        }

<span class="fc" id="L31">        String[] nameKeywords = trimmedArgs.split(&quot;\\s+&quot;);</span>

<span class="fc" id="L33">        return new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(nameKeywords)));</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Person.java.html
``` html
    private static final String DEFAULT_NAME = &quot;Full Name&quot;;
    private static final String DEFAULT_OCCUPATION = &quot;Google, Software engineer&quot;;
    private static final String DEFAULT_PHONE = &quot;123456&quot;;
    private static final String DEFAULT_EMAIL = &quot;fullname@gmail.com&quot;;
    private static final String DEFAULT_ADDRESS = &quot;Singapore&quot;;
    private static final String DEFAULT_REMARK = &quot;funny&quot;;
    private static final String DEFAULT_WEBSITE = &quot;https://www.google.com&quot;;
    private static final String DEFAULT_TAG = &quot;me&quot;;
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Person.java.html
``` html
<span class="fc" id="L63">    public Person() {</span>
        try {
<span class="fc" id="L65">            this.name = new SimpleObjectProperty&lt;&gt;(new Name(DEFAULT_NAME));</span>
<span class="fc" id="L66">            this.occupation = new SimpleObjectProperty&lt;&gt;(new Occupation(DEFAULT_OCCUPATION));</span>
<span class="fc" id="L67">            this.phone = new SimpleObjectProperty&lt;&gt;(new Phone(DEFAULT_PHONE));</span>
<span class="fc" id="L68">            this.email = new SimpleObjectProperty&lt;&gt;(new Email(DEFAULT_EMAIL));</span>
<span class="fc" id="L69">            this.address = new SimpleObjectProperty&lt;&gt;(new Address(DEFAULT_ADDRESS));</span>
<span class="fc" id="L70">            this.remark = new SimpleObjectProperty&lt;&gt;(new Remark(DEFAULT_REMARK));</span>
<span class="fc" id="L71">            this.website = new SimpleObjectProperty&lt;&gt;(new Website(DEFAULT_WEBSITE));</span>
<span class="fc" id="L72">            List&lt;Tag&gt; tagList = new ArrayList&lt;&gt;();</span>
<span class="fc" id="L73">            tagList.add(new Tag(DEFAULT_TAG));</span>
<span class="fc" id="L74">            this.tags = new SimpleObjectProperty&lt;&gt;(new UniqueTagList(new HashSet&lt;&gt;(tagList)));</span>
<span class="nc" id="L75">        } catch (IllegalValueException ive) {</span>
<span class="nc" id="L76">            ive.printStackTrace();</span>
<span class="fc" id="L77">        }</span>
<span class="fc" id="L78">    }</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\TagsContainKeywordsPredicate.java.html
``` html
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */

public class TagsContainKeywordsPredicate implements Predicate&lt;ReadOnlyPerson&gt; {
    private final List&lt;String&gt; keywords;

<span class="fc" id="L19">    public TagsContainKeywordsPredicate(List&lt;String&gt; keywords) {</span>
<span class="fc" id="L20">        this.keywords = keywords;</span>
<span class="fc" id="L21">    }</span>

    /**
     * @param setTags
     * @return
     */

    private String stringifySetTags(Set&lt;Tag&gt; setTags) {
<span class="fc" id="L29">        StringBuilder setTagsString = new StringBuilder();</span>
<span class="fc" id="L30">        final String delimiter = &quot; &quot;;</span>

<span class="fc" id="L32">        Iterator&lt;Tag&gt; tagIterator = setTags.iterator();</span>
<span class="fc bfc" id="L33" title="All 2 branches covered.">        while (tagIterator.hasNext()) {</span>
<span class="fc" id="L34">            Tag checkingTag = tagIterator.next();</span>
<span class="fc" id="L35">            setTagsString.append(checkingTag.tagName);</span>
<span class="fc" id="L36">            setTagsString.append(delimiter);</span>
<span class="fc" id="L37">        }</span>

<span class="fc" id="L39">        return setTagsString.toString();</span>
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
<span class="fc" id="L44">        return keywords.stream()</span>
<span class="fc" id="L45">                .anyMatch(keyword -&gt; StringUtil.containsWordIgnoreCase(stringifySetTags(person.getTags()), keyword));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="pc bpc" id="L50" title="1 of 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
<span class="pc bpc" id="L52" title="1 of 2 branches missed.">                &amp;&amp; this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.ui\MainWindow.java.html
``` html
    @FXML
    private StackPane profilePlaceholder;
```
###### \build\reports\jacoco\coverage\html\seedu.address.ui\MainWindow.java.html
``` html
<span class="fc" id="L139">        profilePanel = new ProfilePanel(primaryStage);</span>
<span class="fc" id="L140">        profilePlaceholder.getChildren().add(profilePanel.getRoot());</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.ui\ProfilePanel.java.html
``` html
package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the app
 */
public class ProfilePanel extends UiPart&lt;Region&gt; {

    private static final String FXML = &quot;ProfilePanel.fxml&quot;;
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = &quot;data/&quot;;
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = &quot;.png&quot;;
    private static final String DEFAULT_PROFILE_PICTURE_PATH = &quot;/images/default_profile_picture.png&quot;;
<span class="fc" id="L42">    private static String[] colors = { &quot;red&quot;, &quot;yellow&quot;, &quot;blue&quot;, &quot;orange&quot;, &quot;indigo&quot;, &quot;green&quot;, &quot;violet&quot;, &quot;black&quot; };</span>
<span class="fc" id="L43">    private static HashMap&lt;String, String&gt; tagColors = new HashMap&lt;String, String&gt;();</span>
<span class="fc" id="L44">    private static Random random = new Random();</span>

<span class="fc" id="L46">    private final Logger logger = LogsCenter.getLogger(this.getClass());</span>
<span class="fc" id="L47">    private final FileChooser fileChooser = new FileChooser();</span>
    private ReadOnlyPerson person;
    private Stage primaryStage;

    @FXML
    private HBox profilePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label occupation;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label website;
    @FXML
    private FlowPane tags;
    @FXML
    private javafx.scene.image.ImageView profilePicture;
    @FXML
    private Button changePictureButton;

    public ProfilePanel(Stage primaryStage) {
<span class="fc" id="L77">        super(FXML);</span>
<span class="fc" id="L78">        this.person = new Person();</span>
<span class="fc" id="L79">        this.primaryStage = primaryStage;</span>
<span class="fc" id="L80">        initChangePictureButton();</span>
<span class="fc" id="L81">        initStyle();</span>
<span class="fc" id="L82">        refreshState();</span>
<span class="fc" id="L83">        registerAsAnEventHandler(this);</span>
<span class="fc" id="L84">    }</span>

    private void refreshState() {
<span class="fc" id="L87">        bindListeners();</span>
<span class="fc" id="L88">        initPicture();</span>
<span class="fc" id="L89">        initTags();</span>
<span class="fc" id="L90">    }</span>

    /**
     * Initialize change picture button
     */
    private void initChangePictureButton() {
<span class="fc" id="L96">        changePictureButton.setOnAction(</span>
<span class="fc" id="L97">                new EventHandler&lt;ActionEvent&gt;() {</span>
                    @Override
                    public void handle(ActionEvent event) {
<span class="nc" id="L100">                        setExtFilters(fileChooser);</span>
<span class="nc" id="L101">                        File file = fileChooser.showOpenDialog(primaryStage);</span>
<span class="nc bnc" id="L102" title="All 2 branches missed.">                        if (file != null) {</span>
<span class="nc" id="L103">                            saveImageToStorage(file);</span>
<span class="nc" id="L104">                            refreshState();</span>
                        }
<span class="nc" id="L106">                    }</span>
                }
        );
<span class="fc" id="L109">    }</span>

    private void setExtFilters(FileChooser chooser) {
<span class="nc" id="L112">        chooser.getExtensionFilters().addAll(</span>
                new FileChooser.ExtensionFilter(&quot;All Images&quot;, &quot;*.*&quot;),
                new FileChooser.ExtensionFilter(&quot;PNG&quot;, &quot;*.png&quot;)
        );
<span class="nc" id="L116">    }</span>

    /**
     * Save a given image file to storage
     * @param file
     */
    private void saveImageToStorage(File file) {
<span class="nc" id="L123">        Image image = new Image(file.toURI().toString());</span>
<span class="nc" id="L124">        String phoneNum = person.getPhone().value;</span>

        try {
<span class="nc" id="L127">            ImageIO.write(SwingFXUtils.fromFXImage(image, null), &quot;png&quot;,</span>
                    new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum + DEFAULT_IMAGE_STORAGE_SUFFIX));
<span class="nc" id="L129">        } catch (IOException ioe) {</span>
<span class="nc" id="L130">            ioe.printStackTrace();</span>
<span class="nc" id="L131">        }</span>
<span class="nc" id="L132">    }</span>

    /**
     * Initialize profile picture
     */
    private void initPicture() {
        BufferedImage bufferedImage;
<span class="fc" id="L139">        String phoneNum = person.getPhone().value;</span>

        try {
<span class="nc" id="L142">            bufferedImage = ImageIO.read(new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum</span>
                    + DEFAULT_IMAGE_STORAGE_SUFFIX));
<span class="nc" id="L144">            Image image = SwingFXUtils.toFXImage(bufferedImage, null);</span>
<span class="nc" id="L145">            profilePicture.setImage(image);</span>
<span class="fc" id="L146">        } catch (IOException ioe1) {</span>
<span class="fc" id="L147">            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));</span>
<span class="fc" id="L148">            profilePicture.setImage(image);</span>
<span class="nc" id="L149">        }</span>
<span class="fc" id="L150">    }</span>

    /**
     * Initialize panel's style such as color
     */
    private void initStyle() {
<span class="fc" id="L156">        profilePane.setStyle(&quot;-fx-background-color: #FFFFFF;&quot;);</span>
<span class="fc" id="L157">    }</span>

    /**
     * Change current displayed profile
     * @param person
     */
    public void changeProfile(ReadOnlyPerson person) {
<span class="fc" id="L164">        this.person = person;</span>
<span class="fc" id="L165">        refreshState();</span>
<span class="fc" id="L166">    }</span>

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners() {
<span class="fc" id="L173">        name.textProperty().bind(Bindings.convert(person.nameProperty()));</span>
<span class="fc" id="L174">        occupation.textProperty().bind(Bindings.convert(person.occupationProperty()));</span>
<span class="fc" id="L175">        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));</span>
<span class="fc" id="L176">        address.textProperty().bind(Bindings.convert(person.addressProperty()));</span>
<span class="fc" id="L177">        email.textProperty().bind(Bindings.convert(person.emailProperty()));</span>
<span class="fc" id="L178">        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));</span>
<span class="fc" id="L179">        website.textProperty().bind(Bindings.convert(person.websiteProperty()));</span>
<span class="fc" id="L180">        person.tagProperty().addListener((observable, oldValue, newValue) -&gt; {</span>
            tags.getChildren().clear();
            //person.getTags().forEach(tag -&gt; tags.getChildren().add(new Label(tag.tagName)));
            initTags();
        });
<span class="fc" id="L185">    }</span>

    /**
     *javadoc comment
     */
    private void initTags() {
        //person.getTags().forEach(tag -&gt; tags.getChildren().add(new Label(tag.tagName)));
<span class="fc" id="L192">        tags.getChildren().clear();</span>
<span class="fc" id="L193">        person.getTags().forEach(tag -&gt; {</span>
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle(&quot;-fx-background-color: &quot; + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
<span class="fc" id="L198">    }</span>

    //The method below retrieves the color for the specific tag
    private static String getColorForTag(String tagValue) {
<span class="fc bfc" id="L202" title="All 2 branches covered.">        if (!tagColors.containsKey(tagValue)) {</span>
<span class="fc" id="L203">            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);</span>
        }
<span class="fc" id="L205">        return tagColors.get(tagValue);</span>
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
<span class="fc" id="L210">        logger.info(LogsCenter.getEventHandlingLogMessage(event));</span>
<span class="fc" id="L211">        changeProfile(event.getNewSelection().person);</span>
<span class="fc" id="L212">    }</span>
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\resources\main\view\MainWindow.fxml
``` fxml
    <StackPane fx:id="profilePlaceholder" minWidth="320" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>

    <StackPane fx:id="browserPlaceholder" minWidth="750" prefWidth="750" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \build\resources\main\view\ProfilePanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="profilePane" fx:id="profilePane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
         <ImageView fx:id="profilePicture" fitHeight="200.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
            <VBox.margin>
               <Insets bottom="15" top="15" />
            </VBox.margin>
         </ImageView>
            <HBox alignment="CENTER" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" alignment="CENTER" />
            <Label fx:id="occupation" styleClass="cell_small_label" text="\$occupation" />
            <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
            <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
            <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
            <Label fx:id="website" styleClass="cell_small_label" text="\$website" />
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
         <Button fx:id="changePictureButton" mnemonicParsing="false" prefWidth="119.0" text="Change" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \src\main\java\seedu\address\logic\commands\AddMultipleByTsvCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.StringJoiner;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to add multiple contacts at once
 */
public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulTsv";
    public static final String COMMAND_ALIAS = "addMT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a tsv (tab separated value) txt file containing their contact information. "
            + "Parameters: TSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.txt";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";
    public static final String MESSAGE_NUMBER_OF_ENTRIES_FAILED = "%d entry (entries) failed: ";
    public static final String MESSAGE_FILE_NOT_FOUND = "The system cannot find the file specified";

    private final ArrayList<Person> toAdd;
    private final ArrayList<Integer> failedEntries;
    private final boolean isFileFound;

    public AddMultipleByTsvCommand(ArrayList<ReadOnlyPerson> toAddPeople, ArrayList<Integer> failedEntries,
                                   boolean isFileFound) {
        this.toAdd = new ArrayList<Person>();
        this.failedEntries = failedEntries;
        this.isFileFound = isFileFound;
        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (!isFileFound) {
            return new CommandResult(MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;

        for (Person person: toAdd) {
            try {
                model.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries));
    }

    /**
     * Join the list of integers of failed entries into string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));
    }

}
```
###### \src\main\java\seedu\address\logic\commands\FindTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "fitg";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends family\n";

    private final TagsContainKeywordsPredicate predicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }

}
```
###### \src\main\java\seedu\address\logic\ContactTsvReader.java
``` java
package seedu.address.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;

/**
 * Reads tsv file
 */
public class ContactTsvReader {
    private String contactTsvFilePath;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public ContactTsvReader(String contactTsvFilePath) {
        this.contactTsvFilePath = contactTsvFilePath;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }

    /**
     * Read contacts from the given file path and update toAddPeople and failedEntries
     * @throws ParseException
     * @throws IOException
     */
    public void readContactFromFile() throws ParseException, IOException {

        BufferedReader bufferedReader;
        toAddPeople = new ArrayList<ReadOnlyPerson>();
        failedEntries = new ArrayList<>();

        String line;
        bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));
        int i = 0;

        // How to read file in java line by line?
        while ((line = bufferedReader.readLine()) != null) {
            if (i != 0) {
                try {
                    ArrayList<String> columns = tsvLinetoArrayList(line);
                    Name name = ParserUtil.parseName(checkEmptyAndReturn(retrieveColumnField(columns, 0)))
                            .get();
                    Occupation occupation = ParserUtil.parseOccupation(checkEmptyAndReturn(retrieveColumnField(columns,
                            1))).get();
                    Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(retrieveColumnField(columns, 2)))
                            .get();
                    Email email = ParserUtil.parseEmail(checkEmptyAndReturn(retrieveColumnField(columns, 3)))
                            .get();
                    Address address = ParserUtil.parseAddress(checkEmptyAndReturn(retrieveColumnField(columns, 4)))
                            .get();
                    Website website = ParserUtil.parseWebsite(checkEmptyAndReturn(retrieveColumnField(columns, 5)))
                            .get();
                    Set<Tag> tagList = ParserUtil.parseTags(new ArrayList<String>(
                            Arrays.asList(retrieveColumnField(columns, 6)
                                    .replaceAll("^[,\"\\s]+", "")
                                    .replace("\"", "")
                                    .split("[,\\s]+"))));
                    Remark remark = new Remark("");
                    ReadOnlyPerson toAddPerson = new Person(name, occupation, phone, email, address, remark, website,
                            tagList);
                    toAddPeople.add(toAddPerson);
                } catch (IllegalValueException ive) {
                    throw new ParseException(ive.getMessage(), ive);
                } catch (NoSuchElementException nsee) {
                    failedEntries.add(i);
                }
            }
            i++;
        }

        bufferedReader.close();
    }

    /**
     * Check if a given string is empty and return Optional object accordingly
     * @param valueStr
     * @return
     */
    private static Optional<String> checkEmptyAndReturn(String valueStr) {
        Optional<String> result = valueStr.length() < 1 ? Optional.empty() : Optional.of(valueStr);
        System.out.println(result);
        return result;
    }

    /**
     * Convert a line in tsv file to list of string values of fields
     * @param line
     * @return
     */
    private static ArrayList<String> tsvLinetoArrayList(String line) {
        final String emptyFieldValue = "";
        ArrayList<String> result = new ArrayList<String>();

        if (line != null) {
            String[] splitData = line.split("\t");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                } else {
                    result.add(emptyFieldValue);
                }
            }
        }

        return result;
    }

    /**
     * Retrieve string value of a field given the columns and its index
     * @param columns
     * @param index
     * @return
     */
    private static String retrieveColumnField(ArrayList<String> columns, int index) {
        final String outOfBoundValue = "";

        try {
            return columns.get(index).replace("\"", "");
        } catch (IndexOutOfBoundsException e) {
            return outOfBoundValue;
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddMultipleByTsvCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parser for AddMultipleByTsvCommand
 */
public class AddMultipleByTsvCommandParser implements Parser<AddMultipleByTsvCommand> {

    /**
     * Parse arguments given by AddressBookParser
     * @param args
     * @return
     * @throws ParseException
     */
    public AddMultipleByTsvCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByTsvCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String contactTsvFilePath = nameKeywords[0];
        ContactTsvReader contactTsvReader = new ContactTsvReader(contactTsvFilePath);
        boolean isFileFound;
        ArrayList<ReadOnlyPerson> toAddPeople = new ArrayList<ReadOnlyPerson>();
        ArrayList<Integer> failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        }

        return new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddMultipleByTsvCommand.COMMAND_WORD:
        case AddMultipleByTsvCommand.COMMAND_ALIAS:
            return new AddMultipleByTsvCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */

public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\main\java\seedu\address\MainApp.java
``` java
    /**
     * Initialize empty storage folder to store profile picture
     */
    private void initEmptyStorage() {
        boolean success = (new File(StorageManager.EMPTY_STORAGE_DEFAULT_PATH)).mkdirs();
        if (!success) {
            logger.warning("Problem while initializing empty storage.");
        }
    }
```
###### \src\main\java\seedu\address\model\person\Person.java
``` java
    private static final String DEFAULT_NAME = "Full Name";
    private static final String DEFAULT_OCCUPATION = "Google, Software engineer";
    private static final String DEFAULT_PHONE = "123456";
    private static final String DEFAULT_EMAIL = "fullname@gmail.com";
    private static final String DEFAULT_ADDRESS = "Singapore";
    private static final String DEFAULT_REMARK = "funny";
    private static final String DEFAULT_WEBSITE = "https://www.google.com";
    private static final String DEFAULT_TAG = "me";
```
###### \src\main\java\seedu\address\model\person\Person.java
``` java
    public Person() {
        try {
            this.name = new SimpleObjectProperty<>(new Name(DEFAULT_NAME));
            this.occupation = new SimpleObjectProperty<>(new Occupation(DEFAULT_OCCUPATION));
            this.phone = new SimpleObjectProperty<>(new Phone(DEFAULT_PHONE));
            this.email = new SimpleObjectProperty<>(new Email(DEFAULT_EMAIL));
            this.address = new SimpleObjectProperty<>(new Address(DEFAULT_ADDRESS));
            this.remark = new SimpleObjectProperty<>(new Remark(DEFAULT_REMARK));
            this.website = new SimpleObjectProperty<>(new Website(DEFAULT_WEBSITE));
            List<Tag> tagList = new ArrayList<>();
            tagList.add(new Tag(DEFAULT_TAG));
            this.tags = new SimpleObjectProperty<>(new UniqueTagList(new HashSet<>(tagList)));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }
```
###### \src\main\java\seedu\address\model\person\TagsContainKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */

public class TagsContainKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @param setTags
     * @return
     */

    private String stringifySetTags(Set<Tag> setTags) {
        StringBuilder setTagsString = new StringBuilder();
        final String delimiter = " ";

        Iterator<Tag> tagIterator = setTags.iterator();
        while (tagIterator.hasNext()) {
            Tag checkingTag = tagIterator.next();
            setTagsString.append(checkingTag.tagName);
            setTagsString.append(delimiter);
        }

        return setTagsString.toString();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringifySetTags(person.getTags()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane profilePlaceholder;
```
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
        profilePanel = new ProfilePanel(primaryStage);
        profilePlaceholder.getChildren().add(profilePanel.getRoot());
```
###### \src\main\java\seedu\address\ui\ProfilePanel.java
``` java
package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the app
 */
public class ProfilePanel extends UiPart<Region> {

    private static final String FXML = "ProfilePanel.fxml";
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = "data/";
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = ".png";
    private static final String DEFAULT_PROFILE_PICTURE_PATH = "/images/default_profile_picture.png";
    private static String[] colors = { "red", "yellow", "blue", "orange", "indigo", "green", "violet", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final FileChooser fileChooser = new FileChooser();
    private ReadOnlyPerson person;
    private Stage primaryStage;

    @FXML
    private HBox profilePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label occupation;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label website;
    @FXML
    private FlowPane tags;
    @FXML
    private javafx.scene.image.ImageView profilePicture;
    @FXML
    private Button changePictureButton;

    public ProfilePanel(Stage primaryStage) {
        super(FXML);
        this.person = new Person();
        this.primaryStage = primaryStage;
        initChangePictureButton();
        initStyle();
        refreshState();
        registerAsAnEventHandler(this);
    }

    private void refreshState() {
        bindListeners();
        initPicture();
        initTags();
    }

    /**
     * Initialize change picture button
     */
    private void initChangePictureButton() {
        changePictureButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setExtFilters(fileChooser);
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            saveImageToStorage(file);
                            refreshState();
                        }
                    }
                }
        );
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    /**
     * Save a given image file to storage
     * @param file
     */
    private void saveImageToStorage(File file) {
        Image image = new Image(file.toURI().toString());
        String phoneNum = person.getPhone().value;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum + DEFAULT_IMAGE_STORAGE_SUFFIX));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Initialize profile picture
     */
    private void initPicture() {
        BufferedImage bufferedImage;
        String phoneNum = person.getPhone().value;

        try {
            bufferedImage = ImageIO.read(new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum
                    + DEFAULT_IMAGE_STORAGE_SUFFIX));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilePicture.setImage(image);
        } catch (IOException ioe1) {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));
            profilePicture.setImage(image);
        }
    }

    /**
     * Initialize panel's style such as color
     */
    private void initStyle() {
        profilePane.setStyle("-fx-background-color: #FFFFFF;");
    }

    /**
     * Change current displayed profile
     * @param person
     */
    public void changeProfile(ReadOnlyPerson person) {
        this.person = person;
        refreshState();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners() {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        occupation.textProperty().bind(Bindings.convert(person.occupationProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            initTags();
        });
    }

    /**
     *javadoc comment
     */
    private void initTags() {
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //The method below retrieves the color for the specific tag
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeProfile(event.getNewSelection().person);
    }
}
```
###### \src\main\resources\view\MainWindow.fxml
``` fxml
    <StackPane fx:id="profilePlaceholder" minWidth="320" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>

    <StackPane fx:id="browserPlaceholder" minWidth="750" prefWidth="750" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \src\main\resources\view\ProfilePanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="profilePane" fx:id="profilePane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
         <ImageView fx:id="profilePicture" fitHeight="200.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
            <VBox.margin>
               <Insets bottom="15" top="15" />
            </VBox.margin>
         </ImageView>
            <HBox alignment="CENTER" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" alignment="CENTER" />
            <Label fx:id="occupation" styleClass="cell_small_label" text="\$occupation" />
            <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
            <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
            <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
            <Label fx:id="website" styleClass="cell_small_label" text="\$website" />
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
         <Button fx:id="changePictureButton" mnemonicParsing="false" prefWidth="119.0" text="Change" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \src\test\java\seedu\address\logic\commands\AddMultipleByTsvCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;

public class AddMultipleByTsvCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTsvFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_tsvFileAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS), commandResult.feedbackToUser);
        assertEquals(toAddPeople, modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED),
                commandResult.feedbackToUser);
    }

    private AddMultipleByTsvCommand getAddMultipleByTsvCommandForPerson(ArrayList<ReadOnlyPerson> toAddPeople,
                                                                        ArrayList<Integer> failedEntries,
                                                                        boolean isFileFound, Model model) {
        AddMultipleByTsvCommand command = new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsContainKeywordsPredicate firstPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("first"));
        TagsContainKeywordsPredicate secondPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        FindTagCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE));
    }

    /**
     *
     * @param userInput
     * @return
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     *
     * @param command
     * @param expectMessage
     * @param expectedList
     */
    private void assertCommandSuccess(FindTagCommand command, String expectMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \src\test\java\seedu\address\testutil\TsvFileBuilder.java
``` java
package seedu.address.testutil;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building TsvFile objects.
 */
public class TsvFileBuilder {
    public static final String DEFAULT_TSV_FILE_PATH = TypicalTsvFiles.PERFECT_TSV_FILE_PATH;

    private boolean isFileFound;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public TsvFileBuilder() {
        ContactTsvReader contactTsvReader = new ContactTsvReader(DEFAULT_TSV_FILE_PATH);
        toAddPeople = new ArrayList<>();
        failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        } catch (ParseException pe) {
            throw new AssertionError("Default tsv file is invalid.");
        }
    }

    public boolean getIsFileFound() {
        return isFileFound;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }
}
```
###### \src\test\java\seedu\address\testutil\TypicalTsvFiles.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of TsvFile objects to be used in tests.
 */
public class TypicalTsvFiles {
    public static final String PERFECT_TSV_FILE_PATH = "src/test/resources/docs/perfectTsvFile.txt";
    public static final String PERFECT_TSV_FILE_MESSAGE_SUCCESS = "2 new person (people) added, "
            + "0 new person (people) duplicated, 0 entry (entries) failed: ";
    public static final String PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED = "0 new person (people) added, "
            + "2 new person (people) duplicated, 0 entry (entries) failed: ";
}
```
###### \src\test\java\systemtests\AddMultipleByTsvCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_NUMBER_OF_ENTRIES_FAILED;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_SUCCESS;

import java.util.ArrayList;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;


public class AddMultipleByTsvCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void addMultipleByTsv() {
        Model model = getModel();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        String command = AddMultipleByTsvCommand.COMMAND_WORD + " " + TypicalTsvFiles.PERFECT_TSV_FILE_PATH;
        String expectedResultMessage = TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS;
        assertCommandSuccess(command, validTsvFile);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, TsvFileBuilder)}. Executes {@code command}
     * instead.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, TsvFileBuilder)
     */
    private void assertCommandSuccess(String command, TsvFileBuilder tsvFile) {
        Model expectedModel = getModel();
        boolean isFileFound = tsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = tsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = tsvFile.getFailedEntries();

        if (!isFileFound) {
            throw new IllegalArgumentException(AddMultipleByTsvCommand.MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;
        ArrayList<Person> toAdd = new ArrayList<Person>();

        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
        for (Person person: toAdd) {
            try {
                expectedModel.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }
        String expectedResultMessage = String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Join list of failed entries as integers to a single string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }
}
```
###### \src\test\java\systemtests\AddressBookSystemTest.java
``` java
        String selectedCardWebsite = getPersonListPanel().getHandleToSelectedCard().getWebsite();
```
###### \src\test\java\systemtests\FindTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_COLLEAGUES;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FAMILY;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FRIENDS;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_OWESMONEY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findtag() {

        /* Case: find multiple persons in address book with a single tag
         * -> 1 persons found
         */
        String command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY;
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book with single tag, command with leading spaces and trailing spaces
         * -> 5 persons found
         */
        command = "   " + FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS + "   ";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY + " " + KEYWORD_MATCHING_FAMILY;
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords, in reverse order -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + KEYWORD_MATCHING_FAMILY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + "NoneMatchingKeyword";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find tag command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find tag command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 4 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 4 person found */
        command = FindTagCommand.COMMAND_WORD + " FriEnds";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " frie";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " friendss";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " siblings";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find occupation of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getOccupation().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find website of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getWebsite().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_COLLEAGUES;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdtAg" + " " + KEYWORD_MATCHING_FRIENDS;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
