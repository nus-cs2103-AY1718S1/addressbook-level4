package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyEventBook;

//@@author kaiyu92

/**
 * A class to access TunedIn EventBook data stored as an xml file on the hard disk.
 */
public class XmlEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    private String filePath;
    private String exportedPath;
    private String header;


    public XmlEventBookStorage(String filePath, String exportedPath, String header) {
        this.filePath = filePath;
        this.exportedPath = exportedPath;
        this.header = header;
    }

    @Override
    public String getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(filePath);
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File eventBookFile = new File(filePath);

        if (!eventBookFile.exists()) {
            logger.info("EventBook file " + eventBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEventBook eventBookOptional = XmlFileStorage.loadEventDataFromSaveFile(new File(filePath));

        return Optional.of(eventBookOptional);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEventBook(eventBook));
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath.substring(0, filePath.indexOf('.')) + "_backup.xml");
    }

    @Override
    public void exportEventBook() throws ParserConfigurationException, IOException, SAXException {
        XmlFileStorage.exportEventbook(filePath, exportedPath, header);
    }
}
