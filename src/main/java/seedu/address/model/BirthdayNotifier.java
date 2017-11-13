package seedu.address.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.BirthdayPopup;

//@@author liliwei25
/**
 * Checks current date against birthday of all persons
 */
public class BirthdayNotifier {
    public BirthdayNotifier(List<ReadOnlyPerson> list) {
        String[] birthdayNameList = getBirthdays(list);
        if (isNotEmpty(birthdayNameList)) {
            createPopup(birthdayNameList);
        }
    }

    /**
     * Checks if there are anyone celebrating birthday on current day
     *
     * @param people
     * @return
     */
    private boolean isNotEmpty(String[] people) {
        return people.length > 0;
    }

    /**
     * Get current date and Persons celebrating birthday
     *
     * @param list Current list of Persons in address book
     * @return List of names celebrating birthday
     */
    private String[] getBirthdays(List<ReadOnlyPerson> list) {
        LocalDate now = LocalDate.now();
        int date = now.getDayOfMonth();
        int month = now.getMonthValue();

        return getBirthdaysToday(list, date, month);
    }

    /**
     * Get a list of names celebrating birthdays on current day
     *
     * @param list List of Persons in address book
     * @param date Current day
     * @param month Current month
     * @return List of names celebrating birthday
     */
    private String[] getBirthdaysToday(List<ReadOnlyPerson> list, int date, int month) {
        ArrayList<String> people = new ArrayList<>();

        for (ReadOnlyPerson e: list) {
            if (e.getDay() == date && e.getMonth() == month) {
                people.add(e.getName().toString());
            }
        }
        return people.toArray(new String[people.size()]);
    }

    /**
     * Create Birthday Popup to show birthdays
     *
     * @param people List of names celebrating birthdays
     */
    private void createPopup(String[] people) {
        new BirthdayPopup(people);
    }
}
