package seedu.address.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Read csv file from the directory.
 */

public class ExportCsvFile {

    private static final String heading = "First Name,Last Name,Company,Home Street,Other Phone,"
            + "Birthday,E-mail Address,Notes";

    private String fileLocation;

    public ExportCsvFile(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    //@@author stan789
    /**
     * Create a csv file to the directory.
     */
    public void createCsvFile(ObservableList<ReadOnlyPerson> person) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(heading);
        bufferedWriter.newLine();

        for (ReadOnlyPerson p : person) {

            writeCsvName(bufferedWriter, p);
            writeCsvOrganisation(bufferedWriter, p);
            writeCsvAddress(bufferedWriter, p);
            writeCsvPhone(bufferedWriter, p);
            writeCsvBirthday(bufferedWriter, p);
            writeCsvEmail(bufferedWriter, p);
            writeCsvRemark(bufferedWriter, p);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

    //@@author stan789
    /**
     * Writes a person's name to csv file
     */
    private void writeCsvName(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws IOException {
        String[] name = person.getName().toString().split(" ", 2);
        bufferedWriter.write(name[0]);
        bufferedWriter.write(",");

        if (name.length == 2) {
            bufferedWriter.write(name[1]);
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's organisation to csv file
     */
    private void writeCsvOrganisation(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getOrganisation().toString().equals("~")) {
            bufferedWriter.write(person.getOrganisation().toString());
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's address to csv file
     */
    private void writeCsvAddress(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getAddress().toString().equals("~")) {
            String address = person.getAddress().toString();
            address = address.replaceAll(",", "");
            bufferedWriter.write(address);
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's phone to csv file
     */
    private void writeCsvPhone(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getPhone().toString().equals("~")) {
            bufferedWriter.write(person.getPhone().toString());
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's birthday to csv file
     */
    private void writeCsvBirthday(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getBirthday().toString().equals("~")) {
            String birthday = person.getBirthday().toString();
            birthday = birthday.replaceAll("-", "/");
            bufferedWriter.write(birthday);
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's email to csv file
     */
    private void writeCsvEmail(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getEmail().toString().equals("~")) {
            bufferedWriter.write(person.getEmail().toString());
        }
        bufferedWriter.write(",");
    }

    //@@author stan789
    /**
     * Writes a person's remarks to csv file
     */
    private void writeCsvRemark(BufferedWriter bufferedWriter, ReadOnlyPerson person) throws  IOException {
        if (!person.getRemark().toString().equals("~")) {
            bufferedWriter.write(person.getRemark().toString());
        }
    }

}
