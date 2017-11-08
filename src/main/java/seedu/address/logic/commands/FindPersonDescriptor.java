package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindPersonDescriptor {
    private List<String> name;
    private List<String> phone;
    private List<String> email;
    private List<String> address;
    private List<String> mrt;
    private List<String> tags;

    public FindPersonDescriptor() {
    }

    public FindPersonDescriptor(FindPersonDescriptor personDescriptor) {
        this.name = personDescriptor.name;
        this.phone = personDescriptor.phone;
        this.email = personDescriptor.email;
        this.address = personDescriptor.address;
        this.mrt = personDescriptor.mrt;
        this.tags = personDescriptor.tags;
    }

    public void setName(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] nameKeywords = trimmedArgs.split("\\s+");
            this.name = Arrays.asList(nameKeywords);
        }
    }

    public Optional<List<String>> getName() {
        return Optional.ofNullable(name);
    }

    public void setPhone(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] phoneKeywords = trimmedArgs.split("\\s+");
            this.phone = Arrays.asList(phoneKeywords);
        }
    }

    public Optional<List<String>> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setEmail(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] emailKeywords = trimmedArgs.split("\\s+");
            this.email = Arrays.asList(emailKeywords);
        }
    }

    public Optional<List<String>> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setAddress(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] addressKeywords = trimmedArgs.split("\\s+");
            this.address = Arrays.asList(addressKeywords);
        }
    }

    public Optional<List<String>> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setMrt(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] mrtKeywords = trimmedArgs.split("\\s+");
            this.mrt = Arrays.asList(mrtKeywords);
        }
    }

    public Optional<List<String>> getMrt() {
        return Optional.ofNullable(mrt);
    }

    public void setTag(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            String[] tagKeywords = trimmedArgs.split("\\s+");
            this.tags = Arrays.asList(tagKeywords);
        }
    }

    public Optional<List<String>> getTags() {
        return Optional.ofNullable(tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPersonDescriptor)) {
            return false;
        }

        // state check
        FindPersonDescriptor e = (FindPersonDescriptor) other;

        return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getMrt().equals(e.getMrt())
                && getTags().equals(e.getTags());
    }
}
