package seedu.address.logic;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class ContactTsvReader {
    private String contactTsvFilePath;

    public ContactTsvReader(String contactTsvFilePath) {
        this.contactTsvFilePath = contactTsvFilePath;
    }

    public ArrayList<ReadOnlyPerson> readContactsFromFile() throws ParseException {

        BufferedReader bufferedReader = null;
        ArrayList<ReadOnlyPerson> toAddPeople = new ArrayList<ReadOnlyPerson>();

        try {
            String line;
            bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));
            int i=0;

            // How to read file in java line by line?
            while ((line = bufferedReader.readLine()) != null) {
                if (i!=0) {
                    try {
                        ArrayList<String> columns = csvLinetoArrayList(line);
                        Name name = ParserUtil.parseName(checkEmptyAndReturn(columns.get(0))).get();
                        Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(columns.get(1))).get();
                        Email email = ParserUtil.parseEmail(checkEmptyAndReturn(columns.get(2))).get();
                        Address address = ParserUtil.parseAddress(checkEmptyAndReturn(columns.get(3))).get();
                        Set<Tag> tagList = ParserUtil.parseTags(new ArrayList<String>(
                                Arrays.asList(columns.get(4)
                                        .replaceAll("^[,\"\\s]+", "")
                                        .replace("\"", "")
                                        .split("[,\\s]+"))));
                        ReadOnlyPerson toAddPerson = new Person(name, phone, email, address, tagList);
                        toAddPeople.add(toAddPerson);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(ive.getMessage(), ive);
                    }
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return toAddPeople;
    }

    public Optional<String> checkEmptyAndReturn(String valueStr) {
        return valueStr.length() < 1? Optional.empty() : Optional.of(valueStr);
    }

    // Utility which converts CSV to ArrayList using Split Operation
    private static ArrayList<String> csvLinetoArrayList(String line) {
        ArrayList<String> result = new ArrayList<String>();

        if (line != null) {
            String[] splitData = line.split("\\s*\t\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                }
            }
        }

        return result;
    }
}
