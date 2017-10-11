package seedu.address.testutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import seedu.address.commons.core.LogsCenter;

/**
 * Simple class to get logging messages
 * To Use:
 *   1. Instantiate logger with class of interest
 *   2. Retrieve message after each log generating event.
 *      @see TestLogger#getTestCapturedLog()
 *   3. Close logger.
 *   	@see TestLogger#close()
 */
public class TestLogger {

    private OutputStream logCapturingStream;
    private StreamHandler customLogHandler;
    private Logger logger;

    public TestLogger(Class<?> classType) {
        logger = LogsCenter.getLogger(classType);
        attachLogCapturer();
    }

    /**
     * Attaches Log Capturer to the logger.
     */
    public void attachLogCapturer() {
        logCapturingStream = new ByteArrayOutputStream();
        customLogHandler = new StreamHandler(logCapturingStream, new testLogFormatter());
        logger.addHandler(customLogHandler);
    }

    /**
     * @return captured log message in format "LEVEL - MESSAGE"
     */
    public String getTestCapturedLog() throws IOException {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    /**
     * Detaches the Log Capturer from logger
     */
    public void close() {
        detachLogCapturer();
    }

    public void detachLogCapturer() {
        logger.removeHandler(customLogHandler);
        logger = null;
    }

    /**
     * Custom Formatter for formatting log messages in test.
     */
    public class testLogFormatter extends Formatter {

        /**
         * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
         */
        @Override
        public String format(final LogRecord record) {
            return MessageFormat.format("{0} - {1}", record.getLevel(), record.getMessage(),
                    record.getParameters());
        }

    }
}
