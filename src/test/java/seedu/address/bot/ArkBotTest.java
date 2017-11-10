package seedu.address.bot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static seedu.address.bot.ArkBot.BOT_MESSAGE_FAILURE;
import static seedu.address.bot.ArkBot.BOT_MESSAGE_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalParcels.BENSON;
import static seedu.address.testutil.TypicalParcels.DANIEL;
import static seedu.address.testutil.TypicalParcels.HOON;

import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.objects.EndUser;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import seedu.address.TestApp;
import seedu.address.bot.parcel.ParcelParser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import systemtests.ModelHelper;
import systemtests.SystemTestSetupHelper;

public class ArkBotTest {
    private static final String BOT_DEMO_JOHN = "#/RR000000000SG n/John Doe p/98765432 e/johnd@example.com "
            + "a/John street, block 123, #01-01 S123121 d/01-01-2001 s/DELIVERING";
    private static final String SAMPLE_ADD_COMMAND = BOT_DEMO_JOHN;
    private static final Logger logger = LogsCenter.getLogger(ArkBotTest.class);

    public static final int USER_ID = 1337;
    public static final long CHAT_ID = 1337L;

    private ArkBot bot;
    private DBContext db;
    private MessageSender sender;
    private EndUser endUser;
    private Model model;
    private Logic logic;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;
    private ParcelParser parcelParser;
    private Optional<Message> lastKnownMessage;
    private int numberOfFailures = 0;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initializeStage();
    }

    @Before
    public void setUp() {
        // Offline instance will get deleted at JVM shutdown
        db = MapDBContext.offlineInstance("test");
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication();
        logic = testApp.getLogic();
        model = testApp.getModel();
        bot = testApp.getBot();
        sender = mock(MessageSender.class);
        endUser = EndUser.endUser(USER_ID, "Abbas", "Abou Daya", "addo37");
        bot.setSender(sender);
        parcelParser = new ParcelParser();

    }

    @Test
    public void canSayHelloWorld() throws InterruptedException, ParseException, DuplicateParcelException {
        Update mockedUpdate = mock(Update.class);
        MessageContext context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.saysHelloWorld().action().accept(context);

        // We verify that the sender was called only ONCE and sent Hello World to CHAT_ID
        Mockito.verify(sender, times(1)).send("Hello World!", CHAT_ID);

        /*================================== UNDO COMMAND FAILURE TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.undoCommand().action().accept(context);
        String message = BOT_MESSAGE_FAILURE;
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(++numberOfFailures)).send(message, CHAT_ID);

        /*================================== REDO COMMAND FAILURE TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.redoCommand().action().accept(context);
        message = BOT_MESSAGE_FAILURE;
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(++numberOfFailures)).send(message, CHAT_ID);

        /*================================== ADD COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID, SAMPLE_ADD_COMMAND);

        bot.addCommand().action().accept(context);
        message = String.format(BOT_MESSAGE_SUCCESS, AddCommand.COMMAND_WORD);
        model.addParcelCommand(parcelParser.parse(BOT_DEMO_JOHN));
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== ADD COMMAND FAILURE TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID, SAMPLE_ADD_COMMAND);

        bot.addCommand().action().accept(context);
        message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command failure.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== LIST COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.listCommand().action().accept(context);
        ObservableList<ReadOnlyParcel> parcels = model.getUncompletedParcelList();
        message = bot.parseDisplayParcels(bot.formatParcelsForBot(parcels));
        logger.info("Listing parcels: \n" + message);
        waitForRunLater();

        // We verify that the sender was called only ONCE and listed parcels
        lastKnownMessage = Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== DELETE COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID, "1");

        bot.deleteCommand().action().accept(context);
        message = bot.parseDisplayParcels(bot.formatParcelsForBot(parcels));
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== UNDO COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.undoCommand().action().accept(context);
        message = String.format(BOT_MESSAGE_SUCCESS, UndoCommand.COMMAND_WORD);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== REDO COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.redoCommand().action().accept(context);
        message = String.format(BOT_MESSAGE_SUCCESS, RedoCommand.COMMAND_WORD);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== DELETE COMMAND FAILURE TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID,
                (model.getUncompletedParcelList().size() + 1) + "");

        bot.deleteCommand().action().accept(context);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(++numberOfFailures)).send(BOT_MESSAGE_FAILURE, CHAT_ID);

        /*================================== FIND COMMAND SUCCESS TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID, "Meier");
        ModelHelper.setFilteredList(model, BENSON, DANIEL, HOON);
        parcels = model.getUncompletedParcelList();
        message = bot.parseDisplayParcels(bot.formatParcelsForBot(parcels));
        bot.findCommand().action().accept(context);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

        /*================================== FIND COMMAND FAILURE TEST ====================================*/

        mockedUpdate = mock(Update.class);
        context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);
        message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        bot.findCommand().action().accept(context);
        waitForRunLater();

        // We verify that the sender was called only ONCE and sent add command success.
        Mockito.verify(sender, times(1)).send(message, CHAT_ID);

    }

    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        semaphore.acquire();

    }

    @After
    public void tearDown() {
        db.clear();
    }
}
