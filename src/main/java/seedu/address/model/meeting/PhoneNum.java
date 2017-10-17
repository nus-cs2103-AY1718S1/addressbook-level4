package seedu.address.model.meeting;

/**
 * Store phonenumber of Person so that user can easily contact him/her for meeting
 */
public class PhoneNum {
    public final String phone;

    public PhoneNum(String num) {
        this.phone = num;
    }

    @Override
    public String toString() {
        return phone;
    }

    @Override
    public int hashCode() {
        return phone.hashCode();
    }
}
