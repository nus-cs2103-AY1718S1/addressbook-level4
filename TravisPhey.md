# TravisPhey
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteMultipleCommand.java.html
``` html
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

<span class="nc bnc" id="L21" title="All 2 branches missed.">public class DeleteMultipleCommand extends UndoableCommand {</span>
    public static final String COMMAND_WORD = &quot;deleteMul&quot;;
    public static final String COMMAND_ALIAS = &quot;delM&quot;;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + &quot;: Can be used to delete multiple contacts at one go.\n&quot;
            + &quot;: Indexes that are to be deleted must be listed in ascending order.\n&quot;
            + &quot;: Deletes the contacts identified by the index numbers used in the last person listing.\n&quot;
            + &quot;Parameters: INDEX (must be a positive integer)\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; 1&quot; + &quot; 2&quot; + &quot; 3&quot;;

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = &quot;Deleted Person: %1$s&quot;;

    private final ArrayList&lt;Index&gt; arrayOfIndex;

<span class="nc" id="L35">    public DeleteMultipleCommand(ArrayList&lt;Index&gt; arrayOfIndex) {</span>
<span class="nc" id="L36">        this.arrayOfIndex = arrayOfIndex;</span>
<span class="nc" id="L37">    }</span>


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
<span class="nc" id="L42">        String listOfDeletedContacts = &quot;&quot;;</span>

<span class="nc bnc" id="L44" title="All 2 branches missed.">        for (int n = 0; n &lt; arrayOfIndex.size(); n++) {</span>

<span class="nc" id="L46">            Index targetIndex = arrayOfIndex.get(n);</span>
<span class="nc" id="L47">            List&lt;ReadOnlyPerson&gt; lastShownList = model.getFilteredPersonList();</span>

<span class="nc bnc" id="L49" title="All 2 branches missed.">            if (targetIndex.getZeroBased() &gt;= lastShownList.size()) {</span>
<span class="nc" id="L50">                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);</span>
            }

<span class="nc" id="L53">            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());</span>
<span class="nc bnc" id="L54" title="All 2 branches missed.">            if (n == 0) {</span>
<span class="nc" id="L55">                listOfDeletedContacts = listOfDeletedContacts + personToDelete.getName();</span>
            } else {
<span class="nc" id="L57">                listOfDeletedContacts = listOfDeletedContacts + &quot;, &quot; + personToDelete.getName();</span>
            }

            try {
<span class="nc" id="L61">                model.deletePerson(personToDelete);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteMultipleCommand.java.html
``` html
<span class="nc" id="L65">            } catch (PersonNotFoundException pnfe) {</span>
<span class="nc bnc" id="L66" title="All 2 branches missed.">                assert false : &quot;The target person cannot be missing&quot;;</span>
<span class="nc" id="L67">            }</span>
        }

<span class="nc" id="L70">        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listOfDeletedContacts));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="nc bnc" id="L75" title="All 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
<span class="nc bnc" id="L77" title="All 2 branches missed.">                &amp;&amp; this.arrayOfIndex.equals(((DeleteMultipleCommand) other).arrayOfIndex)); // state check</span>
    }

```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\FindCommand.java.html
``` html
package seedu.address.logic.commands;

import seedu.address.model.person.FindCommandPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = &quot;find&quot;;
    public static final String COMMAND_ALIAS = &quot;fi&quot;;
    public static final String MESSAGE_USAGE = COMMAND_WORD + &quot;: Finds all persons whose names contain any of &quot;
            + &quot;the specified keywords (case-sensitive) and displays them as a list with index numbers.\n&quot;
            + &quot;Parameters: KEYWORD [MORE_KEYWORDS]...\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; alice bob charlie&quot;;

    private final FindCommandPredicate predicate;

<span class="fc" id="L21">    public FindCommand(FindCommandPredicate predicate) {</span>
<span class="fc" id="L22">        this.predicate = predicate;</span>
<span class="fc" id="L23">    }</span>

    @Override
    public CommandResult execute() {
<span class="fc" id="L27">        model.updateFilteredPersonList(predicate);</span>
<span class="fc" id="L28">        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="fc bfc" id="L33" title="All 4 branches covered.">        return other == this // short circuit if same object</span>
                || (other instanceof FindCommand // instanceof handles nulls
<span class="fc bfc" id="L35" title="All 2 branches covered.">                &amp;&amp; this.predicate.equals(((FindCommand) other).predicate)); // state check</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\FindNumberCommand.java.html
``` html
package seedu.address.logic.commands;

import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose number contains any of the argument keywords.
 */

public class FindNumberCommand extends Command {

    public static final String COMMAND_WORD = &quot;findnum&quot;;
    public static final String COMMAND_ALIAS = &quot;fin&quot;;
    public static final String MESSAGE_USAGE = COMMAND_WORD + &quot;: Finds all persons whose numbers contain any of &quot;
            + &quot;the specified keywords (case-sensitive) and displays them as a list with index numbers.\n&quot;
            + &quot;Parameters: KEYWORD [MORE_KEYWORDS]...\n&quot;
            + &quot;Example: &quot; + COMMAND_WORD + &quot; 98765432 12345678 61772655&quot;;

    private final NumberContainsKeywordsPredicate predicate;

<span class="fc" id="L21">    public FindNumberCommand(NumberContainsKeywordsPredicate predicate) {</span>
<span class="fc" id="L22">        this.predicate = predicate;</span>
<span class="fc" id="L23">    }</span>

    @Override
    public CommandResult execute() {
<span class="fc" id="L27">        model.updateFilteredPersonList(predicate);</span>
<span class="fc" id="L28">        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="fc bfc" id="L33" title="All 4 branches covered.">        return other == this // short circuit if same object</span>
                || (other instanceof FindNumberCommand // instanceof handles nulls
<span class="fc bfc" id="L35" title="All 2 branches covered.">                &amp;&amp; this.predicate.equals(((FindNumberCommand) other).predicate)); // state check</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\AddressBookParser.java.html
``` html
        case DeleteMultipleCommand.COMMAND_WORD:
        case DeleteMultipleCommand.COMMAND_ALIAS:
<span class="nc" id="L81">            return new DeleteMultipleCommandParser().parse(arguments);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\AddressBookParser.java.html
``` html
        case FindNumberCommand.COMMAND_WORD:
        case FindNumberCommand.COMMAND_ALIAS:
<span class="nc" id="L128">            return new FindNumberCommandParser().parse(arguments);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\DeleteMultipleCommandParser.java.html
``` html
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMultipleCommand object
 */

<span class="nc" id="L18">public class DeleteMultipleCommandParser implements Parser&lt;DeleteMultipleCommand&gt; {</span>
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMultipleCommand parse(String args) throws ParseException {
<span class="nc" id="L25">        String trimmedArgs = args.trim();</span>
<span class="nc bnc" id="L26" title="All 2 branches missed.">        if (trimmedArgs.isEmpty()) {</span>
<span class="nc" id="L27">            throw new ParseException(</span>
<span class="nc" id="L28">                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE));</span>
        }

<span class="nc" id="L31">        String[] listOfIndex = trimmedArgs.split(&quot;\\s+&quot;);</span>
<span class="nc" id="L32">        ArrayList&lt;String&gt; list = new ArrayList&lt;String&gt;(Arrays.asList(listOfIndex));</span>
<span class="nc" id="L33">        Collections.reverse(list);</span>
<span class="nc" id="L34">        ArrayList&lt;Index&gt; arrayOfIndex = new ArrayList&lt;Index&gt;();</span>
<span class="nc bnc" id="L35" title="All 2 branches missed.">        for (int n = 0; n &lt; list.size(); n++) {</span>
<span class="nc" id="L36">            String indexString = list.get(n);</span>
<span class="nc" id="L37">            int foo = Integer.parseInt(indexString) - 1;</span>
<span class="nc" id="L38">            Index index = new Index(foo);</span>
<span class="nc" id="L39">            arrayOfIndex.add(index);</span>
        }

<span class="nc" id="L42">        return new DeleteMultipleCommand(arrayOfIndex);</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\FindCommandParser.java.html
``` html
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindCommandPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
<span class="fc" id="L15">public class FindCommandParser implements Parser&lt;FindCommand&gt; {</span>

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
<span class="fc" id="L23">        String trimmedArgs = args.trim();</span>
<span class="fc bfc" id="L24" title="All 2 branches covered.">        if (trimmedArgs.isEmpty()) {</span>
<span class="fc" id="L25">            throw new ParseException(</span>
<span class="fc" id="L26">                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));</span>
        }

<span class="fc" id="L29">        String[] nameKeywords = trimmedArgs.split(&quot;\\s+&quot;);</span>
        //String[] nameKeywords = trimmedArgs;

<span class="fc" id="L32">        return new FindCommand(new FindCommandPredicate(Arrays.asList(nameKeywords)));</span>
    }

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.parser\FindNumberCommandParser.java.html
``` html
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNumberCommand object
 */

<span class="nc" id="L16">public class FindNumberCommandParser implements Parser&lt;FindNumberCommand&gt; {</span>
    /**
     * Parses the given {@code String} of arguments in the context of the FindNumberCommand
     * and returns an FindNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNumberCommand parse(String args) throws ParseException {
<span class="nc" id="L23">        String trimmedArgs = args.trim();</span>
<span class="nc bnc" id="L24" title="All 2 branches missed.">        if (trimmedArgs.isEmpty()) {</span>
<span class="nc" id="L25">            throw new ParseException(</span>
<span class="nc" id="L26">                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNumberCommand.MESSAGE_USAGE));</span>
        }

<span class="nc" id="L29">        String[] nameKeywords = trimmedArgs.split(&quot;\\s+&quot;);</span>

<span class="nc" id="L31">        return new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.model.person\NumberContainsKeywordsPredicate.java.html
``` html
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Number} matches any of the keywords given.
 */

public class NumberContainsKeywordsPredicate implements Predicate&lt;ReadOnlyPerson&gt; {
    private final List&lt;String&gt; keywords;

<span class="fc" id="L16">    public NumberContainsKeywordsPredicate(List&lt;String&gt; keywords) {</span>
<span class="fc" id="L17">        this.keywords = keywords;</span>
<span class="fc" id="L18">    }</span>

    @Override
    public boolean test(ReadOnlyPerson person) {
<span class="fc" id="L22">        return keywords.stream()</span>
<span class="fc" id="L23">                .anyMatch(keyword -&gt; StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));</span>
    }

    @Override
    public boolean equals(Object other) {
<span class="pc bpc" id="L28" title="1 of 4 branches missed.">        return other == this // short circuit if same object</span>
                || (other instanceof NumberContainsKeywordsPredicate // instanceof handles nulls
<span class="pc bpc" id="L30" title="1 of 2 branches missed.">                &amp;&amp; this.keywords.equals(((NumberContainsKeywordsPredicate) other).keywords)); // state check</span>
    }
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

public class DeleteMultipleCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteMul";
    public static final String COMMAND_ALIAS = "delM";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Can be used to delete multiple contacts at one go.\n"
            + ": Indexes that are to be deleted must be listed in ascending order.\n"
            + ": Deletes the contacts identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " 2" + " 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final ArrayList<Index> arrayOfIndex;

    public DeleteMultipleCommand(ArrayList<Index> arrayOfIndex) {
        this.arrayOfIndex = arrayOfIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String listOfDeletedContacts = "";

        for (int n = 0; n < arrayOfIndex.size(); n++) {

            Index targetIndex = arrayOfIndex.get(n);
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            if (n == 0) {
                listOfDeletedContacts = listOfDeletedContacts + personToDelete.getName();
            } else {
                listOfDeletedContacts = listOfDeletedContacts + ", " + personToDelete.getName();
            }

            try {
                model.deletePerson(personToDelete);
```
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listOfDeletedContacts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
                && this.arrayOfIndex.equals(((DeleteMultipleCommand) other).arrayOfIndex)); // state check
    }

```
###### \src\main\java\seedu\address\logic\commands\FindCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.FindCommandPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "fi";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FindCommandPredicate predicate;

    public FindCommand(FindCommandPredicate predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### \src\main\java\seedu\address\logic\commands\FindNumberCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose number contains any of the argument keywords.
 */

public class FindNumberCommand extends Command {

    public static final String COMMAND_WORD = "findnum";
    public static final String COMMAND_ALIAS = "fin";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 98765432 12345678 61772655";

    private final NumberContainsKeywordsPredicate predicate;

    public FindNumberCommand(NumberContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindNumberCommand // instanceof handles nulls
                && this.predicate.equals(((FindNumberCommand) other).predicate)); // state check
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteMultipleCommand.COMMAND_WORD:
        case DeleteMultipleCommand.COMMAND_ALIAS:
            return new DeleteMultipleCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindNumberCommand.COMMAND_WORD:
        case FindNumberCommand.COMMAND_ALIAS:
            return new FindNumberCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\DeleteMultipleCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMultipleCommand object
 */

public class DeleteMultipleCommandParser implements Parser<DeleteMultipleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMultipleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE));
        }

        String[] listOfIndex = trimmedArgs.split("\\s+");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(listOfIndex));
        Collections.reverse(list);
        ArrayList<Index> arrayOfIndex = new ArrayList<Index>();
        for (int n = 0; n < list.size(); n++) {
            String indexString = list.get(n);
            int foo = Integer.parseInt(indexString) - 1;
            Index index = new Index(foo);
            arrayOfIndex.add(index);
        }

        return new DeleteMultipleCommand(arrayOfIndex);
    }
}
```
###### \src\main\java\seedu\address\logic\parser\FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindCommandPredicate;

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

        String[] nameKeywords = trimmedArgs.split("\\s+");
        //String[] nameKeywords = trimmedArgs;

        return new FindCommand(new FindCommandPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \src\main\java\seedu\address\logic\parser\FindNumberCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNumberCommand object
 */

public class FindNumberCommandParser implements Parser<FindNumberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindNumberCommand
     * and returns an FindNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNumberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\main\java\seedu\address\model\person\NumberContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Number} matches any of the keywords given.
 */

public class NumberContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NumberContainsKeywordsPredicate(List<String> keywords) {
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
                || (other instanceof NumberContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NumberContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindByNumberTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
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
import seedu.address.model.person.NumberContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FindByNumberTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NumberContainsKeywordsPredicate firstPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("first"));
        NumberContainsKeywordsPredicate secondPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNumberCommand findFirstCommand = new FindNumberCommand(firstPredicate);
        FindNumberCommand findSecondCommand = new FindNumberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindNumberCommand findFirstCommandCopy = new FindNumberCommand(firstPredicate);
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
        FindNumberCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindNumberCommand command = prepareCommand("95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindNumberCommand}.
     */
    private FindNumberCommand prepareCommand(String userInput) {
        FindNumberCommand command =
            new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindNumberCommand command, String eMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(eMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
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
import seedu.address.model.person.FindCommandPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FindCommandPredicate firstPredicate =
                new FindCommandPredicate(Collections.singletonList("first"));
        FindCommandPredicate secondPredicate =
                new FindCommandPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
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
        FindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command =
                new FindCommand(new FindCommandPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
