package seedu.address.model.person;

import java.util.Calendar;

import static java.util.Objects.requireNonNull;

public class Age {

    public final String value;

    public Age(String birthday) {
        if (birthday.length() == 0) {
            this.value = "";
            return;
        }
        else {
            String result = birthday.substring(6);
            int birthYear = Integer.parseInt(result);


            int year = Calendar.getInstance().get(Calendar.YEAR);

            String howOld = Integer.toString(year - birthYear);

            if (year - birthYear == 1) {
                this.value = "(" + howOld + " year old" + ")";
            } else {
                this.value = "(" + howOld + " years old" + ")";
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Age //instanceof handles nulls
                && this.value.equals(((Age) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
