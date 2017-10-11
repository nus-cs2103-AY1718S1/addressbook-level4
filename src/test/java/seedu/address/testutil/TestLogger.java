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

public class TestLogger {

	private static OutputStream logCapturingStream;
	private static StreamHandler customLogHandler;
	private static Logger log;

	public TestLogger(Class<?> classType) {
		log = LogsCenter.getLogger(classType);
		attachLogCapturer();
	}

    public void attachLogCapturer() {
        logCapturingStream = new ByteArrayOutputStream();
        customLogHandler = new StreamHandler(logCapturingStream, new testLogFormatter());
        log.addHandler(customLogHandler);
    }

    public String getTestCapturedLog() throws IOException {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    public void close() {
		detachLogCapturer();
	}

	public void detachLogCapturer() {
		log.removeHandler(customLogHandler);
	}

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
