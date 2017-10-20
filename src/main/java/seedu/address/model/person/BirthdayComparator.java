package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares Birthday of ReadOnlyPerson
 */
public class BirthdayComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String newFirstNum = getNewStringBirthdayFormat(firstPerson);
        String newSecondNum = getNewStringBirthdayFormat(secondPerson);
        if (newFirstNum.equals("") || newSecondNum.equals("")) {
            return newSecondNum.compareTo(newFirstNum);
        } else {
            return newFirstNum.compareTo(newSecondNum);
        }
    }

    public static String getNewStringBirthdayFormat(ReadOnlyPerson person) {
        if (person.getBirthday().toString().equals("")) {
            return "";
        } else {
            String numInString = person.getBirthday().toString();  // Converts birthday to String type
            String dayForNum = numInString.substring(0, 2);        // Index of day in dd/mm/yyyy
            String monthForNum = numInString.substring(3, 5);      // Index of month in dd/mm/yyyy
            String yearForNum = numInString.substring(6, 10);      // Index of year in dd/mm/yyyy
            return monthForNum + dayForNum + yearForNum;           // Return String format mmddyyy
        }
    }
}
