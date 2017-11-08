# vmlimshimin
###### \build\reports\jacoco\coverage\html\seedu.address.logic\LogicManager.java.html
``` html
    private final RecentlyDeletedQueue queue;

```
###### \build\reports\jacoco\coverage\html\seedu.address.logic\LogicManager.java.html
``` html
<span class="fc" id="L36">        this.queue = new RecentlyDeletedQueue();</span>
<span class="fc" id="L37">    }</span>

```
###### \build\reports\jacoco\coverage\html\seedu.address.logic\LogicManager.java.html
``` html
<span class="fc" id="L46">            command.setData(model, history, undoRedoStack, queue);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic\RecentlyDeletedQueue.java.html
``` html
package seedu.address.logic;

import java.util.LinkedList;
import java.util.Queue;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Maintains the Recently Deleted Queue (the list of contacts that were recently deleted,
 * for up to 30 contacts).
 */
public class RecentlyDeletedQueue {
    private Queue&lt;ReadOnlyPerson&gt; recentlyDeletedQueue;
    private int count;

<span class="fc" id="L17">    public RecentlyDeletedQueue() {</span>
<span class="fc" id="L18">        recentlyDeletedQueue = new LinkedList&lt;&gt;();</span>
<span class="fc" id="L19">        count = 0;</span>
<span class="fc" id="L20">    }</span>

<span class="fc" id="L22">    public RecentlyDeletedQueue(LinkedList&lt;ReadOnlyPerson&gt; newQueue) {</span>
<span class="fc" id="L23">        recentlyDeletedQueue = newQueue;</span>
<span class="fc" id="L24">        count = newQueue.size();</span>
<span class="fc" id="L25">    }</span>

    /**
     * Offers the @param person in the queue when the @param person is deleted by
     * {@code DeleteCommand} or {@code DeleterMultipleCommand}.
     */
    public void offer(ReadOnlyPerson person) {
<span class="pc bpc" id="L32" title="1 of 2 branches missed.">        if (count &lt; 30) {</span>
<span class="fc" id="L33">            recentlyDeletedQueue.offer(person);</span>
<span class="fc" id="L34">            count++;</span>
        } else {
<span class="nc" id="L36">            recentlyDeletedQueue.poll();</span>
<span class="nc" id="L37">            recentlyDeletedQueue.offer(person);</span>
        }
<span class="fc" id="L39">    }</span>

    /**
     * Returns the list of people in the queue.
     */
    public LinkedList&lt;ReadOnlyPerson&gt; getQueue() {
<span class="fc" id="L45">        return new LinkedList&lt;&gt;(recentlyDeletedQueue);</span>
    }

    public void setQueue(LinkedList&lt;ReadOnlyPerson&gt; newQueue) {
<span class="fc" id="L49">        recentlyDeletedQueue = newQueue;</span>
<span class="fc" id="L50">    }</span>

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteCommand.java.html
``` html
<span class="fc" id="L50">            queue.offer(personToDelete);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteCommand.java.html
``` html
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
<span class="fc" id="L71">        this.model = model;</span>
<span class="fc" id="L72">        this.queue = queue;</span>
<span class="fc" id="L73">    }</span>
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteMultipleCommand.java.html
``` html
<span class="nc" id="L63">                queue.offer(personToDelete);</span>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\DeleteMultipleCommand.java.html
``` html
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
<span class="nc" id="L84">        this.model = model;</span>
<span class="nc" id="L85">        this.queue = queue;</span>
<span class="nc" id="L86">    }</span>
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\RecentlyDeletedCommand.java.html
``` html
package seedu.address.logic.commands;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Shows a list of recently deleted contacts and their details, up to the last 30 contacts deleted.
 */
<span class="nc" id="L17">public class RecentlyDeletedCommand extends Command {</span>
    public static final String COMMAND_WORD = &quot;recentlyDel&quot;;
    public static final String COMMAND_ALIAS = &quot;recentD&quot;;
    public static final String MESSAGE_SUCCESS = &quot;Listed all recently deleted:\n%1$s&quot;;
    public static final String MESSAGE_NO_RECENTLY_DELETED = &quot;You have not yet deleted any contacts.&quot;;

    @Override
    public CommandResult execute() {
        LinkedList&lt;ReadOnlyPerson&gt; previouslyDeleted;
<span class="nc" id="L26">        previouslyDeleted = queue.getQueue();</span>

<span class="nc bnc" id="L28" title="All 2 branches missed.">        if (previouslyDeleted.isEmpty()) {</span>
<span class="nc" id="L29">            return new CommandResult(MESSAGE_NO_RECENTLY_DELETED);</span>
        }

<span class="nc" id="L32">        Collections.reverse(previouslyDeleted);</span>
<span class="nc" id="L33">        LinkedList&lt;String&gt; deletedAsText = new LinkedList&lt;&gt;();</span>
<span class="nc" id="L34">        Iterator list = previouslyDeleted.listIterator(0);</span>
        //System.out.println(previouslyDeleted.size());
<span class="nc bnc" id="L36" title="All 2 branches missed.">        while (list.hasNext()) {</span>
<span class="nc" id="L37">            String personAsText = (list.next()).toString();</span>
<span class="nc" id="L38">            deletedAsText.add(personAsText);</span>
<span class="nc" id="L39">        }</span>
        //System.out.println(deletedAsText.size());
<span class="nc" id="L41">        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join(&quot;\n&quot;, deletedAsText)));</span>
    }

    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
<span class="nc" id="L45">        this.queue = queue;</span>
<span class="nc" id="L46">    }</span>
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\UndoableCommand.java.html
``` html
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
<span class="fc" id="L26">        requireNonNull(model);</span>
<span class="fc" id="L27">        this.previousAddressBook = new AddressBook(model.getAddressBook());</span>
<span class="fc" id="L28">        this.previousQueue = new RecentlyDeletedQueue(queue.getQueue());</span>
<span class="fc" id="L29">    }</span>

```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\UndoableCommand.java.html
``` html
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
<span class="fc" id="L38">        requireAllNonNull(model, previousAddressBook);</span>
<span class="fc" id="L39">        model.resetData(previousAddressBook);</span>
<span class="fc" id="L40">        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);</span>
<span class="fc" id="L41">        queue.setQueue(previousQueue.getQueue());</span>
<span class="fc" id="L42">    }</span>

```
###### \build\reports\jacoco\coverage\html\seedu.address.logic.commands\UndoCommand.java.html
``` html
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
<span class="fc" id="L37">        this.model = model;</span>
<span class="fc" id="L38">        this.undoRedoStack = undoRedoStack;</span>
<span class="fc" id="L39">        this.queue = queue;</span>
<span class="fc" id="L40">    }</span>
}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \build\reports\jacoco\coverage\html\seedu.address.storage\StorageManager.java.html
``` html
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
<span class="nc" id="L85">        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + &quot;-backup.xml&quot;);</span>
<span class="nc" id="L86">    }</span>

```
###### \build\reports\jacoco\coverage\html\seedu.address.storage\XmlAddressBookStorage.java.html
``` html
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
<span class="nc" id="L80">        saveAddressBook(addressBook, filePath + &quot;-backup.xml&quot;);</span>
<span class="nc" id="L81">    }</span>

}
</pre><div class="footer"><span class="right">Created with <a href="http://www.eclemma.org/jacoco">JaCoCo</a> 0.7.1.201405082137</span></div></body></html>
```
###### \src\main\java\seedu\address\logic\commands\DeleteCommand.java
``` java
            queue.offer(personToDelete);
```
###### \src\main\java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.model = model;
        this.queue = queue;
    }
}
```
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
                queue.offer(personToDelete);
```
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.model = model;
        this.queue = queue;
    }
}
```
###### \src\main\java\seedu\address\logic\commands\RecentlyDeletedCommand.java
``` java
package seedu.address.logic.commands;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Shows a list of recently deleted contacts and their details, up to the last 30 contacts deleted.
 */
public class RecentlyDeletedCommand extends Command {
    public static final String COMMAND_WORD = "recentlyDel";
    public static final String COMMAND_ALIAS = "recentD";
    public static final String MESSAGE_SUCCESS = "Listed all recently deleted:\n%1$s";
    public static final String MESSAGE_NO_RECENTLY_DELETED = "You have not yet deleted any contacts.";

    @Override
    public CommandResult execute() {
        LinkedList<ReadOnlyPerson> previouslyDeleted;
        previouslyDeleted = queue.getQueue();

        if (previouslyDeleted.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENTLY_DELETED);
        }

        Collections.reverse(previouslyDeleted);
        LinkedList<String> deletedAsText = new LinkedList<>();
        Iterator list = previouslyDeleted.listIterator(0);
        //System.out.println(previouslyDeleted.size());
        while (list.hasNext()) {
            String personAsText = (list.next()).toString();
            deletedAsText.add(personAsText);
        }
        //System.out.println(deletedAsText.size());
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", deletedAsText)));
    }

    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.queue = queue;
    }
}
```
###### \src\main\java\seedu\address\logic\commands\UndoableCommand.java
``` java
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousQueue = new RecentlyDeletedQueue(queue.getQueue());
    }

```
###### \src\main\java\seedu\address\logic\commands\UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        queue.setQueue(previousQueue.getQueue());
    }

```
###### \src\main\java\seedu\address\logic\commands\UndoCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
        this.queue = queue;
    }
}
```
###### \src\main\java\seedu\address\logic\LogicManager.java
``` java
    private final RecentlyDeletedQueue queue;

```
###### \src\main\java\seedu\address\logic\LogicManager.java
``` java
        this.queue = new RecentlyDeletedQueue();
    }

```
###### \src\main\java\seedu\address\logic\LogicManager.java
``` java
            command.setData(model, history, undoRedoStack, queue);
```
###### \src\main\java\seedu\address\logic\RecentlyDeletedQueue.java
``` java
package seedu.address.logic;

import java.util.LinkedList;
import java.util.Queue;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Maintains the Recently Deleted Queue (the list of contacts that were recently deleted,
 * for up to 30 contacts).
 */
public class RecentlyDeletedQueue {
    private Queue<ReadOnlyPerson> recentlyDeletedQueue;
    private int count;

    public RecentlyDeletedQueue() {
        recentlyDeletedQueue = new LinkedList<>();
        count = 0;
    }

    public RecentlyDeletedQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
        count = newQueue.size();
    }

    /**
     * Offers the @param person in the queue when the @param person is deleted by
     * {@code DeleteCommand} or {@code DeleterMultipleCommand}.
     */
    public void offer(ReadOnlyPerson person) {
        if (count < 30) {
            recentlyDeletedQueue.offer(person);
            count++;
        } else {
            recentlyDeletedQueue.poll();
            recentlyDeletedQueue.offer(person);
        }
    }

    /**
     * Returns the list of people in the queue.
     */
    public LinkedList<ReadOnlyPerson> getQueue() {
        return new LinkedList<>(recentlyDeletedQueue);
    }

    public void setQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
    }

}
```
###### \src\main\java\seedu\address\storage\AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
```
###### \src\main\java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }

```
###### \src\main\java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }

}
```
