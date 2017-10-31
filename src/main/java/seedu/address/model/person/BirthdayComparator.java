//@@author zengfengw
package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares two birthday string according to lexicographical order.
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
            String dayString = person.getBirthday().toString().substring(0, 2);
            String monthString = person.getBirthday().toString().substring(3, 5);
            return monthString + dayString; // Return String format mmdd
        }
    }
}
