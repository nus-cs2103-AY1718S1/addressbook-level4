package seedu.address.logic;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.io.*;
import java.util.*;

public class ContactTsvReader {
    private String contactTsvFilePath;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public ContactTsvReader(String contactTsvFilePath) {
        this.contactTsvFilePath = contactTsvFilePath;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }

    public void readContactsFromFile() throws ParseException, IOException {

        BufferedReader bufferedReader;
        toAddPeople = new ArrayList<ReadOnlyPerson>();
        failedEntries = new ArrayList<>();

        String line;
        bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));
        int i=0;

        // How to read file in java line by line?
        while ((line = bufferedReader.readLine()) != null) {
            if (i!=0) {
                try {
                    ArrayList<String> columns = csvLinetoArrayList(line);
                    Name name = ParserUtil.parseName(checkEmptyAndReturn(retrieveColumnField(columns, 0))).get();
                    Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(retrieveColumnField(columns, 1))).get();
                    Email email = ParserUtil.parseEmail(checkEmptyAndReturn(retrieveColumnField(columns, 2))).get();
                    Address address = ParserUtil.parseAddress(checkEmptyAndReturn(retrieveColumnField(columns, 3))).get();
                    Set<Tag> tagList = ParserUtil.parseTags(new ArrayList<String>(
                            Arrays.asList(retrieveColumnField(columns, 4)
                                    .replaceAll("^[,\"\\s]+", "")
                                    .replace("\"", "")
                                    .split("[,\\s]+"))));
                    ReadOnlyPerson toAddPerson = new Person(name, phone, email, address, tagList);
                    toAddPeople.add(toAddPerson);
                } catch (IllegalValueException ive) {
                    throw new ParseException(ive.getMessage(), ive);
                } catch (NoSuchElementException nsee) {
                    failedEntries.add(i);
                }
            }
            i++;
        }

        bufferedReader.close();
    }

    private static Optional<String> checkEmptyAndReturn(String valueStr) {
        Optional<String> result = valueStr.length() < 1? Optional.empty() : Optional.of(valueStr);
        System.out.println(result);
        return result;
    }

    // Utility which converts CSV to ArrayList using Split Operation
    private static ArrayList<String> csvLinetoArrayList(String line) {
        final String EMPTY_FIELD_VALUE = "";
        ArrayList<String> result = new ArrayList<String>();

        if (line != null) {
            String[] splitData = line.split("\t");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                } else {
                    result.add(EMPTY_FIELD_VALUE);
                }
            }
        }

        return result;
    }

    private static String retrieveColumnField(ArrayList<String> columns, int index) {
        final String OUT_OF_BOUND_VALUE = "";

        try {
            return columns.get(index).replace("\"", "");
        } catch (IndexOutOfBoundsException e) {
            return OUT_OF_BOUND_VALUE;
        }
    }
}
