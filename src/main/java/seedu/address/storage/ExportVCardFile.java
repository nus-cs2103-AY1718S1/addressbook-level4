package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * write vCard file from the directory. supports up to VCard Version 3.0
 */

public class ExportVCardFile {


    private static final Integer INDEX_ZERO = 0;
    private static final Integer INDEX_ONE = 1;
    private static final Integer INDEX_TWO = 2;
    private String fileLocation;
    private VCardFileFormat vcf;

    public ExportVCardFile(String fileLocation) {
        this.fileLocation = fileLocation;
        vcf = new VCardFileFormat();
    }

    //@@author stan789
    /**
     * write vCard file from the directory. supports up to VCard Version 3.0
     */
    public void createVCardFile(ObservableList<ReadOnlyPerson> person) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for (ReadOnlyPerson p : person) {
            bufferedWriter.write(vcf.getBegin());
            bufferedWriter.newLine();

            bufferedWriter.write(vcf.getVersion());
            bufferedWriter.newLine();

            writeVCardName(bufferedWriter, p);
            writeVCardEmail(bufferedWriter, p);
            writeVCardPhone(bufferedWriter, p);
            writeVCardTag(bufferedWriter, p);
            writeVCardBirthday(bufferedWriter, p);
            writeVCardAddress(bufferedWriter, p);
            writeVCardOrganisation(bufferedWriter, p);
            writeVCardRemark(bufferedWriter, p);

            bufferedWriter.write(vcf.getEnd());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    //@@author stan789
    /**
     * write a person's name and full name to VCard file.
     */
    private void writeVCardName(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {

        bufferedWriter.write(vcf.getFullName() + ":" + person.getName());
        bufferedWriter.newLine();

        String[] arrayName = person.getName().toString().split(" ", 2);

        bufferedWriter.write(vcf.getName() + ":" + arrayName[INDEX_ZERO] + ";");
        if (arrayName.length == INDEX_TWO) {
            bufferedWriter.write(arrayName[INDEX_ONE]);
        }
        bufferedWriter.newLine();
    }

    //@@author stan789
    /**
     * write a person's email to VCard file.
     */
    private void writeVCardEmail(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getEmail().toString().equals("~")) {
            bufferedWriter.write(vcf.getEmail() + ":" + person.getEmail());
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's phone to VCard file.
     */
    private void writeVCardPhone(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getPhone().toString().equals("~")) {
            bufferedWriter.write(vcf.getPhoneFormat() + ":" + person.getPhone());
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's tag/tags to VCard file.
     */
    private void writeVCardTag(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getTags().isEmpty()) {
            String tag = person.getTags().toString();
            if (tag.contains("[")) {
                tag = tag.replaceAll("[\\[\\]]", "");
            }
            bufferedWriter.write(vcf.getLabel() + ":" + tag);
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's birthday to VCard file.
     */
    private void writeVCardBirthday(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getBirthday().toString().equals("~")) {
            String birthday = person.getBirthday().toString();
            String[] birthdayArray = birthday.split("-");
            birthday = birthdayArray[INDEX_TWO] + "-" + birthdayArray[INDEX_ONE] + "-" + birthdayArray[INDEX_ZERO];
            bufferedWriter.write(vcf.getBirthday() + ":" + birthday);
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's address to VCard file.
     */
    private void writeVCardAddress(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getAddress().toString().equals("~")) {
            String address = person.getAddress().toString();
            for (int count = 0; count < 4; count++) {
                if (address.contains(",")) {
                    address = address.replace(",", ";");
                } else {
                    address = address.concat(";");
                }
            }
            bufferedWriter.write(vcf.getAddressFormat1() + ":;;" + address);
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's organisation to VCard file.
     */
    private void writeVCardOrganisation(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getOrganisation().toString().equals("~")) {
            String org = person.getOrganisation().toString();
            bufferedWriter.write(vcf.getOrganization() + ":" + org);
            bufferedWriter.newLine();
        }
    }

    //@@author stan789
    /**
     * write a person's reamark to VCard file.
     */
    private void writeVCardRemark(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        if (!person.getRemark().toString().equals("~")) {
            String remark = person.getRemark().toString();
            bufferedWriter.write(vcf.getNotes() + ":" + remark);
            bufferedWriter.newLine();
        }

    }
}
