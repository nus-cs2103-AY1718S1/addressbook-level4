package seedu.address.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ImportAnalysis;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.storage.exceptions.EmptyFileException;
import seedu.address.storage.exceptions.WrongFormatInFileException;

/**
 * Read vCard file from the directory. supports up to VCard Version 3.0
 */

public class ImportVCardFile {

    private static final Integer BIRTHDAY_SIZE = 3;
    private static final Integer INDEX_ZERO = 0;
    private static final Integer INDEX_ONE = 1;
    private static final Integer INDEX_TWO = 2;
    private static final Integer EMPTY_SIZE = 0;
    private Path fileLocation;
    private ImportAnalysis importAnalysis;
    private ArrayList<Person> person = new ArrayList<>();
    private VCardFileFormat vcf;
    private VCard vCard;
    private boolean checkEnd = true;
    private boolean checkBegin = false;
    private String name;


    /**
     * Read vCard file from the directory.
     * return a list of persons
     */

    public ImportVCardFile(Path fileLocation, ImportAnalysis importAnalysis) {
        this.fileLocation = fileLocation;
        this.importAnalysis = importAnalysis;
        vcf = new VCardFileFormat();
        vCard = new VCard();
    }

    //@@author stan789
    /**
     * Read vCard file from the directory. Check format in file.
     * @throws IOException if not able to read from file
     */

    public ArrayList<Person> getPersonFromFile() throws IOException {

        File file = new File(fileLocation.toString());
        if (file.length() == EMPTY_SIZE) {
            throw new EmptyFileException();
        }
        BufferedReader br = new BufferedReader(new FileReader(fileLocation.toString()));
        String string = br.readLine();
        if (!string.equals(vcf.getBegin())) {
            throw new IOException();
        }

        for (String str : Files.readAllLines(fileLocation, Charset.defaultCharset())) {
            try {
                sendRequest(str);
            } catch (WrongFormatInFileException e) {
                throw new IOException();
            }
        }
        if (!checkEnd) {
            throw new IOException();
        }
        return person;
    }

    //@@author stan789
    /**
     * Check format in file for each line.
     * return a person
     */
    private void sendRequest(String line) throws WrongFormatInFileException {

        if (line.contains(vcf.getBegin())) {

            if (!checkEnd) {
                throw new WrongFormatInFileException();
            } else {
                vCard = new VCard();
                name = "";
            }
            checkEnd = false;
            checkBegin = true;
        } else if (line.contains(vcf.getEnd())) {
            endSection();
        } else {
            vCardFilePart(line);
        }
    }

    //@@author stan789
    /**
     * check the format of the different attributes of VCard object.
     */
    private void vCardFilePart(String line) {
        String[] contactArray = line.split(":");
        if (contactArray.length == INDEX_TWO) {
            if ((line.startsWith(vcf.getPhoneFormat2()) || line.startsWith(vcf.getPhoneFormat()))) {
                phoneSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getEmail())) {
                vCard.setEmail(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getFullName())) {
                vCard.setName(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getName())) {
                nameSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getAddressFormat1()) || line.startsWith(vcf.getAddressFormat2())) {
                addressSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getBirthday())) {
                birthdaySection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getLabel())) {
                tagSection(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getOrganization())) {
                vCard.setOrganisation(contactArray[INDEX_ONE].trim());
            }
            if (line.startsWith(vcf.getNotes())) {
                vCard.setRemark(contactArray[INDEX_ONE].trim());
            }
        }
    }

    //@@author stan789
    /**
     * change format of VCard name to OneBook phone format
     */
    private void nameSection(String contactArray) {
        String[] nameArray = contactArray.split(";");
        for (int i = nameArray.length - 1; i >= 0; i--) {
            if (!nameArray[i].equals("")) {
                name = name.concat(nameArray[i] + " ");
            }
        }
    }


    //@@author stan789
    /**
     * change format of VCard phone to OneBook phone format
     */
    private void phoneSection(String phone) {
        phone = phone.replaceAll("[^\\p{Graph}]", " ");
        if (vCard.getPhone() == null) {
            vCard.setPhone(phone);
        }
    }

    //@@author stan789
    /**
     * change format of VCard birthday to OneBook birthday format
     */
    private void birthdaySection(String birthday) {
        String[] array = birthday.split("-");
        if (array.length == BIRTHDAY_SIZE) {
            birthday = array[INDEX_TWO] + "-" + array[INDEX_ONE] + "-" + array[INDEX_ZERO];
        }
        vCard.setBirthday(birthday);
    }

    //@@author stan789
    /**
     * change format of VCard address to OneBook address format
     */
    private void addressSection(String spiltAddress) {
        String[] array = spiltAddress.split(";");
        String address = "";
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("")) {
                continue;
            }
            address = address.concat(array[i]);
            if (i != array.length - 1) {
                address = address.concat(",");
            }

        }
        vCard.setAddress(address);
    }

    //@@author stan789
    /**
     * create a list of tags from VCard file tags.
     */
    private void tagSection (String label) {
        List<String> tagList = new ArrayList<>();
        if (label.contains(",")) {
            tagList.addAll(Arrays.asList(label.split(",")));
        } else {
            tagList.add(label);
        }
        vCard.setTag(tagList);
    }

    //@@author stan789
    /**
     * add a new person to Arraylist if a person's format is correct and start with begin VCard and
     * end VCard statement.
     */
    private void endSection() throws WrongFormatInFileException {
        try {
            if (!checkBegin) {
                throw new WrongFormatInFileException();
            } else {
                checkEnd = true;
                checkBegin = false;
                if (vCard.getName().equals("")) {
                    vCard.setName(name.trim());
                }
                Set<Tag> tag = new HashSet<>();
                for (String string : vCard.getTag()) {
                    tag.add(new Tag(string));
                }
                person.add(new Person(new Name(vCard.getName()), new Phone(vCard.getPhone()),
                        new Birthday(vCard.getBirthday()), new Email(vCard.getEmail()),
                        new Address(vCard.getAddress()), new Organisation(vCard.getOrganisation()),
                        new Remark((vCard.getRemark())), tag));
            }
        } catch (IllegalValueException e) {
            importAnalysis.setIllegalValue(true);
        }
    }

}
