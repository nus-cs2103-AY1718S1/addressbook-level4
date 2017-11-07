# JavynThun
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\RemarkCommand.java.html
``` html
/**
 *  Changes the remark of an existing person in the address book
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = &quot;remark&quot;;

<span class="fc" id="L25">    public static final String MESSAGE_USAGE = COMMAND_WORD + &quot;: Edits the remark of the person indentified &quot;</span>
            + &quot;by the index number used in the last person listing. &quot;
            + &quot;Existing remark will be overwritten by the input.\n&quot;
            + &quot;Parameters: INDEX (must be a positive integer) &quot;
            + PREFIX_REMARK + &quot; [REMARK]\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; 1 &quot;
            + PREFIX_REMARK + &quot;Likes to swim.&quot;;

    public static final String MESSAGE_ADD_REMARK_SUCCESS = &quot;Added remark to Person: %1$s&quot;;
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = &quot;Removed remark to Person: %1$s&quot;;
    public static final String MESSAGE_DUPLICATE_PERSON = &quot;This person already exists in the address book.&quot;;

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit remark
     * @param remark of the person
     * @return
     * @throws CommandException
     */
<span class="fc" id="L46">    public RemarkCommand(Index index, Remark remark) {</span>
<span class="fc" id="L47">        requireNonNull(index);</span>
<span class="fc" id="L48">        requireNonNull(remark);</span>

<span class="fc" id="L50">        this.index = index;</span>
<span class="fc" id="L51">        this.remark = remark;</span>
<span class="fc" id="L52">    }</span>

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
<span class="fc" id="L56">        List&lt;ReadOnlyPerson&gt; lastShownList = model.getFilteredPersonList();</span>

<span class="fc bfc" id="L58" title="All 2 branches covered.">        if (index.getZeroBased() &gt;= lastShownList.size()) {</span>
<span class="fc" id="L59">            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);</span>
        }

<span class="fc" id="L62">        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());</span>
<span class="fc" id="L63">        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getOccupation(), personToEdit.getPhone(),</span>
<span class="fc" id="L64">                personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getWebsite(),</span>
<span class="fc" id="L65">                personToEdit.getTags());</span>

        try {
<span class="fc" id="L68">            model.updatePerson(personToEdit, editedPerson);</span>
<span class="nc" id="L69">        } catch (DuplicatePersonException dpe) {</span>
<span class="nc" id="L70">            throw new CommandException(MESSAGE_DUPLICATE_PERSON);</span>
<span class="nc" id="L71">        } catch (PersonNotFoundException pnfe) {</span>
<span class="nc" id="L72">            throw new AssertionError(&quot;The target person cannot be missing&quot;);</span>
<span class="fc" id="L73">        }</span>

<span class="fc" id="L75">        return new CommandResult(generateSucessMessage(editedPerson));</span>
    }

    /**
     * Generate success message to users given the person to edit
     * @param persontoEdit
     * @return
     */
    private String generateSucessMessage(ReadOnlyPerson persontoEdit) {
<span class="fc bfc" id="L84" title="All 2 branches covered.">        if (!remark.value.isEmpty()) {</span>
<span class="fc" id="L85">            return  String.format(MESSAGE_ADD_REMARK_SUCCESS, persontoEdit);</span>
        } else {
<span class="fc" id="L87">            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, persontoEdit);</span>
        }
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
<span class="fc bfc" id="L96" title="All 2 branches covered.">        if (other == this) {</span>
<span class="fc" id="L97">            return true;</span>
        }

        // instanceof handles nulls
<span class="fc bfc" id="L101" title="All 2 branches covered.">        if (!(other instanceof RemarkCommand)) {</span>
<span class="fc" id="L102">            return false;</span>
        }

        // state check
<span class="fc" id="L106">        RemarkCommand e = (RemarkCommand) other;</span>
<span class="fc bfc" id="L107" title="All 4 branches covered.">        return index.equals(e.index) &amp;&amp; remark.equals(e.remark);</span>
    }

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\CliSyntax.java.html
``` html
<span class="fc" id="L11">    public static final Prefix PREFIX_OCCUPATION = new Prefix(&quot;o/&quot;);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\CliSyntax.java.html
``` html
<span class="fc" id="L17">    public static final Prefix PREFIX_REMARK = new Prefix(&quot;r/&quot;);</span>
<span class="fc" id="L18">    public static final Prefix PREFIX_WEBSITE = new Prefix(&quot;w/&quot;);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\ParserUtil.java.html
``` html
    /**
     * Parses a {@code Optional&lt;String&gt; occupation} into an {@code Optional&lt;Occupation&gt;} if {@code occupation} is
     * present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional&lt;Occupation&gt; parseOccupation(Optional&lt;String&gt; occupation) throws IllegalValueException {
<span class="fc" id="L65">        requireNonNull(occupation);</span>
<span class="fc bfc" id="L66" title="All 2 branches covered.">        return occupation.isPresent() ? Optional.of(new Occupation(occupation.get())) : Optional.empty();</span>
    }
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\ParserUtil.java.html
``` html
    /**
     * Parses a {@code Optional&lt;String&gt; remark} into an {@code Optional&lt;Remark&gt;} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional&lt;Remark&gt; parseRemark(Optional&lt;String&gt; remark) throws IllegalValueException {
<span class="nc" id="L103">        requireNonNull(remark);</span>
<span class="nc bnc" id="L104" title="All 2 branches missed.">        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();</span>
    }

    /**
     * Parses a {@code Optional&lt;String&gt; website} into an {@code Optional&lt;Website&gt;} if {@code website} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional&lt;Website&gt; parseWebsite(Optional&lt;String&gt; website) throws IllegalValueException {
<span class="fc" id="L112">        requireNonNull(website);</span>
<span class="fc bfc" id="L113" title="All 2 branches covered.">        return website.isPresent() ? Optional.of(new Website(website.get())) : Optional.empty();</span>
    }
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\RemarkCommandParser.java.html
``` html
/**
 * Parser for RemarkCommand
 */
<span class="fc" id="L17">public class RemarkCommandParser implements Parser&lt;RemarkCommand&gt; {</span>

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution
     * @throws ParseException if the user input does not conform with expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
<span class="fc" id="L25">        requireNonNull(args);</span>
<span class="fc" id="L26">        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);</span>

        Index index;
        try {
<span class="fc" id="L30">            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());</span>
<span class="fc" id="L31">        } catch (IllegalValueException ive) {</span>
<span class="fc" id="L32">            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));</span>
<span class="fc" id="L33">        }</span>

<span class="fc" id="L35">        String remark = argumentMultimap.getValue(PREFIX_REMARK).orElse(&quot;&quot;);</span>

<span class="fc" id="L37">        return new RemarkCommand(index, new Remark(remark));</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Occupation.java.html
``` html
/**
 * Represents a Person's occupation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccupation(String)}
 */
public class Occupation {

    public static final String MESSAGE_OCCUPATION_CONSTRAINTS =
            &quot;Person occupation should be 2 alphanumeric strings separated by ','&quot;
                    + &quot; in the form of [COMPANY NAME] , [JOB TITLE]&quot;;
    public static final String OCCUPATION_VALIDATION_REGEX = &quot;[\\w\\s]+\\,\\s[\\w\\s]+&quot;;

    public final String value;

    /**
     * Validates given occupation.
     *
     * @throws IllegalValueException if given occupation string is invalid.
     */
<span class="fc" id="L24">    public Occupation(String occupation) throws IllegalValueException {</span>
        //requireNonNull(occupation);
<span class="pc bpc" id="L26" title="1 of 2 branches missed.">        if (occupation == null) {</span>
<span class="nc" id="L27">            this.value = &quot;&quot;;</span>
        } else {
<span class="fc" id="L29">            String trimmedOccupation = occupation.trim();</span>
<span class="pc bpc" id="L30" title="1 of 4 branches missed.">            if (trimmedOccupation.length() &gt; 0 &amp;&amp; !isValidOccupation(trimmedOccupation)) {</span>
<span class="fc" id="L31">                throw new IllegalValueException(MESSAGE_OCCUPATION_CONSTRAINTS);</span>
            }
<span class="fc" id="L33">            this.value = trimmedOccupation;</span>
        }
<span class="fc" id="L35">    }</span>

    /**
     * Returns if a given string is a valid person occupation.
     */
    public static boolean isValidOccupation(String test) {
<span class="fc" id="L41">        return test.matches(OCCUPATION_VALIDATION_REGEX);</span>
    }

    @Override
    public String toString() {
<span class="fc" id="L46">        return value;</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="pc bpc" id="L51" title="1 of 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof Occupation // instanceof handles nulls
<span class="fc bfc" id="L53" title="All 2 branches covered.">                &amp;&amp; this.value.equals(((Occupation) other).value)); // state check</span>
    }

    @Override
    public int hashCode() {
<span class="nc" id="L58">        return value.hashCode();</span>
    }

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Person.java.html
``` html
    public void setOccupation(Occupation occupation) {
<span class="fc" id="L105">        this.occupation.set(requireNonNull(occupation));</span>
<span class="fc" id="L106">    }</span>

    @Override
    public ObjectProperty&lt;Occupation&gt; occupationProperty() {
<span class="fc" id="L110">        return occupation;</span>
    }

    @Override
    public Occupation getOccupation() {
<span class="fc" id="L115">        return occupation.get();</span>
    }
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Remark.java.html
``` html
/**
 *  Represents a Person's remark in the address book.
 *  Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS = &quot;Person remarks can take any values, can even be blank&quot;;

    public final String value;

<span class="fc" id="L16">    public Remark(String remark) {</span>
<span class="fc" id="L17">        requireNonNull(remark);</span>
<span class="fc" id="L18">        this.value = remark;</span>
<span class="fc" id="L19">    }</span>

    @Override
    public String toString() {
<span class="fc" id="L23">        return value;</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="fc bfc" id="L28" title="All 4 branches covered.">        return other == this // short circuit</span>
            || (other instanceof Remark //instance of nulls
<span class="fc bfc" id="L30" title="All 2 branches covered.">                &amp;&amp; this.value.equals(((Remark) other).value)); //state check</span>
    }

    @Override
    public int hashCode() {
<span class="nc" id="L35">        return value.hashCode();</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\Website.java.html
``` html
/**
 * Represents a Person's website in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            &quot;Person's website should end one or more top-level domain and include no special characters.&quot;;

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given website string is invalid.
     */
<span class="fc" id="L25">    public Website(String website) throws IllegalValueException {</span>
        //requireNonNull(website);
<span class="pc bpc" id="L27" title="1 of 2 branches missed.">        if (website == null) {</span>
<span class="nc" id="L28">            this.value = &quot;&quot;;</span>
        } else {
<span class="fc" id="L30">            String trimmedWebsite = website.trim();</span>
<span class="pc bpc" id="L31" title="1 of 4 branches missed.">            if (trimmedWebsite.length() &gt; 0 &amp;&amp; !isValidWebsite(trimmedWebsite)) {</span>
<span class="fc" id="L32">                throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);</span>
            }
<span class="fc" id="L34">            this.value = trimmedWebsite;</span>
        }
<span class="fc" id="L36">    }</span>

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidWebsite(String test) {
<span class="fc" id="L42">        Pattern p = Pattern.compile(&quot;(@)?(href=')?(HREF=')?(HREF=\&quot;)?(href=\&quot;)?(https://)?[a-zA-Z_0-9\\-]+&quot;</span>
                + &quot;(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&amp;\\n\\-=?\\+\\%/\\.\\w]+)?&quot;);

<span class="fc" id="L45">        Matcher m = p.matcher(test);</span>
<span class="fc" id="L46">        return m.matches();</span>
    }

    @Override
    public String toString() {
<span class="fc" id="L51">        return value;</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="pc bpc" id="L56" title="1 of 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof Website // instanceof handles nulls
<span class="fc bfc" id="L58" title="All 2 branches covered.">                &amp;&amp; this.value.equals(((Website) other).value)); // state check</span>
    }

    @Override
    public int hashCode() {
<span class="nc" id="L63">        return value.hashCode();</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \src\main\java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 *  Changes the remark of an existing person in the address book
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person indentified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + " [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit remark
     * @param remark of the person
     * @return
     * @throws CommandException
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getOccupation(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getWebsite(),
                personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSucessMessage(editedPerson));
    }

    /**
     * Generate success message to users given the person to edit
     * @param persontoEdit
     * @return
     */
    private String generateSucessMessage(ReadOnlyPerson persontoEdit) {
        if (!remark.value.isEmpty()) {
            return  String.format(MESSAGE_ADD_REMARK_SUCCESS, persontoEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, persontoEdit);
        }
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index) && remark.equals(e.remark);
    }

}
```
###### \src\main\java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_OCCUPATION = new Prefix("o/");
```
###### \src\main\java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_WEBSITE = new Prefix("w/");
```
###### \src\main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> occupation} into an {@code Optional<Occupation>} if {@code occupation} is
     * present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Occupation> parseOccupation(Optional<String> occupation) throws IllegalValueException {
        requireNonNull(occupation);
        return occupation.isPresent() ? Optional.of(new Occupation(occupation.get())) : Optional.empty();
    }
```
###### \src\main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> website} into an {@code Optional<Website>} if {@code website} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Website> parseWebsite(Optional<String> website) throws IllegalValueException {
        requireNonNull(website);
        return website.isPresent() ? Optional.of(new Website(website.get())) : Optional.empty();
    }
```
###### \src\main\java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parser for RemarkCommand
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution
     * @throws ParseException if the user input does not conform with expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argumentMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### \src\main\java\seedu\address\model\person\Occupation.java
``` java
/**
 * Represents a Person's occupation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccupation(String)}
 */
public class Occupation {

    public static final String MESSAGE_OCCUPATION_CONSTRAINTS =
            "Person occupation should be 2 alphanumeric strings separated by ','"
                    + " in the form of [COMPANY NAME] , [JOB TITLE]";
    public static final String OCCUPATION_VALIDATION_REGEX = "[\\w\\s]+\\,\\s[\\w\\s]+";

    public final String value;

    /**
     * Validates given occupation.
     *
     * @throws IllegalValueException if given occupation string is invalid.
     */
    public Occupation(String occupation) throws IllegalValueException {
        //requireNonNull(occupation);
        if (occupation == null) {
            this.value = "";
        } else {
            String trimmedOccupation = occupation.trim();
            if (trimmedOccupation.length() > 0 && !isValidOccupation(trimmedOccupation)) {
                throw new IllegalValueException(MESSAGE_OCCUPATION_CONSTRAINTS);
            }
            this.value = trimmedOccupation;
        }
    }

    /**
     * Returns if a given string is a valid person occupation.
     */
    public static boolean isValidOccupation(String test) {
        return test.matches(OCCUPATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occupation // instanceof handles nulls
                && this.value.equals(((Occupation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \src\main\java\seedu\address\model\person\Person.java
``` java
    public void setOccupation(Occupation occupation) {
        this.occupation.set(requireNonNull(occupation));
    }

    @Override
    public ObjectProperty<Occupation> occupationProperty() {
        return occupation;
    }

    @Override
    public Occupation getOccupation() {
        return occupation.get();
    }
```
###### \src\main\java\seedu\address\model\person\Remark.java
``` java
/**
 *  Represents a Person's remark in the address book.
 *  Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS = "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit
            || (other instanceof Remark //instance of nulls
                && this.value.equals(((Remark) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \src\main\java\seedu\address\model\person\Website.java
``` java
/**
 * Represents a Person's website in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Person's website should end one or more top-level domain and include no special characters.";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given website string is invalid.
     */
    public Website(String website) throws IllegalValueException {
        //requireNonNull(website);
        if (website == null) {
            this.value = "";
        } else {
            String trimmedWebsite = website.trim();
            if (trimmedWebsite.length() > 0 && !isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);
            }
            this.value = trimmedWebsite;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidWebsite(String test) {
        Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(https://)?[a-zA-Z_0-9\\-]+"
                + "(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

        Matcher m = p.matcher(test);
        return m.matches();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Website // instanceof handles nulls
                && this.value.equals(((Website) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \src\test\java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getOccupation() {
        return occupationLabel.getText();
    }
```
###### \src\test\java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getRemark() {
        return remarkLabel.getText();
    }

    public String getWebsite() {
        return websiteLabel.getText();
    }
```
###### \src\test\java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return remarkCommand;
    }
}
```
###### \src\test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }
```
###### \src\test\java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseOccupation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseOccupation(null);
    }

    @Test
    public void parseOccupation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseOccupation(Optional.of(INVALID_OCCUPATION));
    }

    @Test
    public void parseOccupation_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseOccupation(Optional.empty()).isPresent());
    }

    @Test
    public void parseOccupation_validValue_returnsOccupation() throws Exception {
        Occupation expectedOccupation = new Occupation(VALID_OCCUPATION);
        Optional<Occupation> actualOccupation = ParserUtil.parseOccupation(Optional.of(VALID_OCCUPATION));
        assertEquals(expectedOccupation, actualOccupation.get());
    }
```
###### \src\test\java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseWebsite_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseWebsite(null);
    }

    @Test
    public void parseWebsite_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseWebsite(Optional.of(INVALID_WEBSITE));
    }

    @Test
    public void parseWebsite_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseWebsite(Optional.empty()).isPresent());
    }

    @Test
    public void parseWebsite_validValue_returnsWebsite() throws Exception {
        Website expectedWebsite = new Website(VALID_WEBSITE);
        Optional<Website> actualWebsite = ParserUtil.parseWebsite(Optional.of(VALID_WEBSITE));

        assertEquals(expectedWebsite, actualWebsite.get());
    }
```
###### \src\test\java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final  Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \src\test\java\seedu\address\model\person\OccupationTest.java
``` java
public class OccupationTest {

    @Test
    public void isValidOccupation() {
        // blank email
        assertFalse(Occupation.isValidOccupation("")); // empty string
        assertFalse(Occupation.isValidOccupation(" ")); // spaces only

        // invalid email
        assertFalse(Occupation.isValidOccupation("@pple, CEO")); // special character in the middle of two strings

        // missing parts
        assertFalse(Occupation.isValidOccupation("Google,Software Engineer")); // missing ' ' after ','
        assertFalse(Occupation.isValidOccupation("Microsoft CEO")); // missing ',' in the middle of two strings
        assertFalse(Occupation.isValidOccupation("Apple")); // missing second part (position)
        assertFalse(Occupation.isValidOccupation("Software Engineer")); // missing first part (company name)

        // valid email
        assertTrue(Occupation.isValidOccupation("Tan Tock Seng Hospital, Nurse"));
        assertTrue(Occupation.isValidOccupation("SMRT, Bus Driver"));
        assertTrue(Occupation.isValidOccupation("NUS, Student"));
    }


}
```
###### \src\test\java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        //different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### \src\test\java\seedu\address\model\person\WebsiteTest.java
``` java
public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        // blank email
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only

        // invalid email
        assertFalse(Website.isValidWebsite("https://example@example.com")); // special character in the middle

        // missing parts
        assertFalse(Website.isValidWebsite("https://example")); // missing top-level domain
        assertFalse(Website.isValidWebsite("http://example.com")); // missing 's' after http
        assertFalse(Website.isValidWebsite("https://.com")); // missing domain name
        assertFalse(Website.isValidWebsite("https//example.com")); // missing ':' after https

        // valid email
        assertTrue(Website.isValidWebsite("https://example.com"));
        assertTrue(Website.isValidWebsite("https://example.com.net"));  // multiple top-level domains
        assertTrue(Website.isValidWebsite("pexample.com"));
        assertTrue(Website.isValidWebsite("https://example.org"));
        assertTrue(Website.isValidWebsite("https://www.example.com"));
        assertTrue(Website.isValidWebsite("https://example.com/abcd"));
    }


}
```
###### \src\test\java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }
```
###### \src\test\java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Occupation} of the {@code Person} that we are building.
     */
    public PersonBuilder withOccupation(String occupation) {
        try {
            this.person.setOccupation(new Occupation(occupation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("occupation is expected to be unique.");
        }
        return this;
    }
```
###### \src\test\java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     *  Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    /**
     *  Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        try {
            this.person.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique");
        }
        return this;
    }
```
