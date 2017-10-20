package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares Age of ReadOnlyPerson
 */
public class AgeComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson firstNum, ReadOnlyPerson secondNum) {
        String newFirstNum = getNewStringAgeFormat(firstNum);
        String newSecondNum = getNewStringAgeFormat(secondNum);
        if (newFirstNum.equals("") || newSecondNum.equals("")) {
            return newSecondNum.compareTo(newFirstNum);
        } else {
            return newFirstNum.compareTo(newSecondNum);
        }
    }

    public static String getNewStringAgeFormat(ReadOnlyPerson person) {
        if (person.getBirthday().toString().equals("")) {
            return "";
        } else {
            String numInString = person.getBirthday().toString();    // Converts birthday to String type
            String dayForNum = numInString.substring(0, 2);        // Index of day in dd/mm/yyyy
            String monthForNum = numInString.substring(3, 5);      // Index of month in dd/mm/yyyy
            String yearForNum = numInString.substring(6, 10);      // Index of year in dd/mm/yyyy
            return yearForNum + monthForNum + dayForNum;           // Return string format yyyymmdd
        }
    }
}
