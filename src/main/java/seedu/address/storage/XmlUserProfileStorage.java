package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.person.UserPerson;

/**
 * A class to access UserProfileManager stored in the hard disk as an xml file
 */
public class XmlUserProfileStorage implements UserProfileStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserProfileStorage.class);

    private String filePath;

    public XmlUserProfileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserProfileFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPerson> readUserProfile() throws DataConversionException,
            FileNotFoundException {

        requireNonNull(filePath);

        File userProfileFile = new File(filePath);

        if (!userProfileFile.exists()) {
            logger.info("AddressBook file "  + userProfileFile + " not found");
            return Optional.empty();
        }

        UserPerson userPersonOptional;

        try {
            userPersonOptional = XmlUtil.getDataFromFile(userProfileFile, UserPerson.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }

        return Optional.of(userPersonOptional);
    }

    /**
     * Similar to {@link #readUserProfile()}
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPerson> readUserProfile(String filePath) throws DataConversionException, IOException {
        return readUserProfile(filePath);
    }

    @Override
    public void saveUserPerson(UserPerson userPerson) throws IOException {
        saveUserPerson(userPerson, filePath);
    }

    /**
     * Similar to {@link #saveUserPerson(UserPerson userPerson)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserPerson(UserPerson userPerson, String filePath) throws IOException {
        requireNonNull(userPerson);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        saveDataToFile(file, new XmlUserPerson());
    }


    /**
     * Saves the given UserPerson data to the specified file.
     */
    public void saveDataToFile(File file, XmlUserPerson xmlUserPerson)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, xmlUserPerson);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }
}
