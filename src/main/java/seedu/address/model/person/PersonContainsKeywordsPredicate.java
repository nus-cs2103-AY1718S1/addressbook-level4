package seedu.address.model.person;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.customfields.CustomField;


/**
 * Tests that any of {@code ReadOnlyPerson}'s Name, Phone, Email or Address
 * partially matches any of the keywords identified by prefixes.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final FindFields findFields;

    public PersonContainsKeywordsPredicate(FindFields findFields) {
        this.findFields = new FindFields(findFields);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return ((findFields.getNameKeywordsStream().anyMatch(keyword
            -> person.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase())))
                || (findFields.getPhoneKeywordsStream().anyMatch(keyword
                    -> person.getPhone().toString().toLowerCase().contains(keyword.toString().toLowerCase())))
                || (findFields.getEmailKeywordsStream().anyMatch(keyword
                    -> person.getEmail().toString().toLowerCase().contains(keyword.toString().toLowerCase())))
                || (findFields.getAddressKeywordsStream().anyMatch(keyword
                    -> person.getAddress().toString().toLowerCase().contains(keyword.toString().toLowerCase()))));
    }

    private boolean customFieldContainsWordIgnoreCase(Set<CustomField> fields, String keyword) {
        return fields.stream().anyMatch(field -> StringUtil.containsWordIgnoreCase(field.asData(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.findFields.getNameKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getNameKeywords())
                && this.findFields.getPhoneKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getPhoneKeywords())
                && this.findFields.getEmailKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getEmailKeywords())
                && this.findFields.getAddressKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getAddressKeywords())); // state check
    }

    /**
     * Stores the fields to search with.
     */
    public static class FindFields {
        private List<Name> nameKeywords;
        private List<Phone> phoneKeywords;
        private List<Email> emailKeywords;
        private List<Address> addressKeywords;

        public FindFields() {}

        public FindFields(FindFields findFields) {
            this.nameKeywords = findFields.nameKeywords;
            this.phoneKeywords = findFields.phoneKeywords;
            this.emailKeywords = findFields.emailKeywords;
            this.addressKeywords = findFields.addressKeywords;
        }

        public void setNameKeywords(List<Name> names) {
            this.nameKeywords = names;
        }

        public Optional<List<Name>> getNameKeywords() {
            return Optional.ofNullable(nameKeywords);
        }

        public Stream<Name> getNameKeywordsStream() {
            return this.getNameKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setPhoneKeywords(List<Phone> phones) {
            this.phoneKeywords = phones;
        }

        public Optional<List<Phone>> getPhoneKeywords() {
            return Optional.ofNullable(phoneKeywords);
        }

        public Stream<Phone> getPhoneKeywordsStream() {
            return this.getPhoneKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setEmailKeywords(List<Email> emails) {
            this.emailKeywords = emails;
        }

        public Optional<List<Email>> getEmailKeywords() {
            return Optional.ofNullable(emailKeywords);
        }

        public Stream<Email> getEmailKeywordsStream() {
            return this.getEmailKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setAddressKeywords(List<Address> addresses) {
            this.addressKeywords = addresses;
        }

        public Optional<List<Address>> getAddressKeywords() {
            return Optional.ofNullable(addressKeywords);
        }

        public Stream<Address> getAddressKeywordsStream() {
            return this.getAddressKeywords().map(List::stream).orElseGet(Stream::empty);
        }
    }
}
