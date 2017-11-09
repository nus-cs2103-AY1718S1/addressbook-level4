package seedu.address.bot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static seedu.address.bot.ArkBot.BOT_MESSAGE_SUCCESS;

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
import org.telegram.telegrambots.api.objects.Update;
import seedu.address.TestApp;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.Model;
import systemtests.SystemTestSetupHelper;

public class ArkBotTest {
    private static final String BOT_DEMO_JOHN = "#/RR000000000SG n/John Doe p/98765432 e/johnd@example.com "
            + "a/John street, block 123, #01-01 S123121 d/01-01-2001 s/DELIVERING";
    private static final String SAMPLE_ADD_COMMAND = BOT_DEMO_JOHN;

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
    }

    @Test
    public void canSayHelloWorld() {
        Update mockedUpdate = mock(Update.class);
        EndUser endUser = EndUser.endUser(USER_ID, "Abbas", "Abou Daya", "addo37");
        MessageContext context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID);

        bot.saysHelloWorld().action().accept(context);

        // We verify that the sender was called only ONCE and sent Hello World to CHAT_ID
        Mockito.verify(sender, times(1)).send("Hello World!", CHAT_ID);
    }


    @Test
    public void canAddCommand() {
        Model model = testApp.getModel();
        Update mockedUpdate = mock(Update.class);
        EndUser endUser = EndUser.endUser(USER_ID, "Abbas", "Abou Daya", "addo37");
        MessageContext context = MessageContext.newContext(mockedUpdate, endUser, CHAT_ID, SAMPLE_ADD_COMMAND);

        bot.addCommand().action().accept(context);

        // We verify that the sender was called only ONCE and sent Hello World to CHAT_ID
        Mockito.verify(sender, times(1)).send(String.format(BOT_MESSAGE_SUCCESS,
                AddCommand.COMMAND_WORD), CHAT_ID);
    }

    @After
    public void tearDown() {
        db.clear();
    }
}