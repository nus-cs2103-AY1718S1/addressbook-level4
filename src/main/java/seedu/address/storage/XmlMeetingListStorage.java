package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyMeetingList;
//@@author liuhang0213
/**
 * A class to access Meeting data stored as an xml file on the hard disk.
 */
public class XmlMeetingListStorage implements MeetingListStorage {

    // Creates a new folder for all backup data
    private static final Logger logger = LogsCenter.getLogger(XmlMeetingListStorage.class);

    private String filePath;

    public XmlMeetingListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getMeetingsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList() throws DataConversionException, IOException {
        return readMeetingList(filePath);
    }

    /**
     * Returns a ReadOnlyMeetingList from a given file
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File meetingListFile = new File(filePath);

        if (!meetingListFile.exists()) {
            logger.info("MeetingList file "  + meetingListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyMeetingList meetingListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath),
                XmlSerializableMeetingList.class);

        return Optional.of(meetingListOptional);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException {
        saveMeetingList(meetingList, filePath);
    }

    /**
     * Similar to {@link #saveMeetingList(ReadOnlyMeetingList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) throws IOException {
        requireNonNull(meetingList);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMeetingList(meetingList));
    }

}
