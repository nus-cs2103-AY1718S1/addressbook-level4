package seedu.address.email.message;

import javax.mail.internet.InternetAddress;

//@@author awarenessxz
/**
 * Represents a Email Message Draft in addressbook.
 */
public class MessageDraft implements ReadOnlyMessageDraft {

    private String message;
    private String subject;
    private InternetAddress [] recipientsEmail;

    public MessageDraft() {
        message = "";
        subject = "";
        recipientsEmail = new InternetAddress[0];
    }

    public MessageDraft(String message, String subject) {
        this.message = message;
        this.subject = subject;
        recipientsEmail = new InternetAddress[0];
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public InternetAddress[] getRecipientsEmails() {
        return recipientsEmail;
    }

    public void setRecipientsEmail(InternetAddress[] recipientsEmail) {
        this.recipientsEmail = new InternetAddress[recipientsEmail.length];
        System.arraycopy(recipientsEmail, 0, this.recipientsEmail, 0, recipientsEmail.length);
    }

    @Override
    public String getRecipientsEmailtoString() {
        String receipients = "";
        for (int i = 0; i < recipientsEmail.length; i++) {
            receipients += recipientsEmail[i].getAddress();
            if (i != recipientsEmail.length - 1) {
                receipients += ", ";
            }
        }
        return receipients;
    }

    @Override
    public boolean containsContent() {
        if (message.isEmpty() || subject.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMessageDraft // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMessageDraft) other));
    }

    @Override
    public boolean recipientsEquals(InternetAddress [] other) {
        if (other.length == recipientsEmail.length) {
            for (int i = 0; i < recipientsEmail.length; i++) {
                if (other[i] != recipientsEmail[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
