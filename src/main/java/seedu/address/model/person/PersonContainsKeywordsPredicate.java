
package seedu.address.model.person;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.customfields.CustomField;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

//@@author sofarsophie
/**
 * Tests that any of {@code ReadOnlyPerson}'s fields
 * partially matches any of the keywords identified by the prefixes.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final FindFields findFields;

    public PersonContainsKeywordsPredicate(FindFields findFields) {
        this.findFields = new FindFields(findFields);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return ((findFields.getNameKeywordsStream().anyMatch(keyword
            -> StringUtil.containsPartialTextIgnoreCase(person.getName().fullName, keyword.fullName)))
                || (findFields.getPhoneKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getPhone().value, keyword.value)))
                || (findFields.getEmailKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getEmail().value, keyword.value)))
                || (findFields.getAddressKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getAddress().value, keyword.value)))
                || (findFields.getFieldsKeywordsStream().anyMatch(keyword
                    -> customFieldContainsWordIgnoreCase(person.getFields(), keyword)))
                || (findFields.getGroupsKeywordsStream().anyMatch(keyword
                    -> StringUtil.containsPartialTextIgnoreCase(person.getGroup().groupName, keyword.groupName)))
                || (findFields.getTagsKeywordsStream().anyMatch(keyword
                    -> tagContainsWordIgnoreCase(person.getTags(), keyword))));
    }

    private boolean customFieldContainsWordIgnoreCase(Set<CustomField> fields, String keyword) {
        return fields.stream().anyMatch(field -> StringUtil.containsPartialTextIgnoreCase(field.toString(), keyword));
    }

    private boolean tagContainsWordIgnoreCase(Set<Tag> tags, String keyword) {
        return tags.stream().anyMatch(tag -> StringUtil.containsPartialTextIgnoreCase(tag.toString(), keyword));
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
                        .findFields.getAddressKeywords())
                && this.findFields.getGroupsKeywords().equals(((PersonContainsKeywordsPredicate) other)
                        .findFields.getGroupsKeywords())); // state check
    }

    /**
     * Stores the list of fields to search with.
     * Supports operations to return a {@code Stream} or an {@code Optional} of the keywords.
     */
    public static class FindFields {
        private List<Name> nameKeywords;
        private List<Phone> phoneKeywords;
        private List<Email> emailKeywords;
        private List<Address> addressKeywords;
        private List<String> customFieldKeywords;
        private List<Group> groupKeywords;
        private List<String> tagKeywords;

        public FindFields() {}

        public FindFields(FindFields findFields) {
            this.nameKeywords = findFields.nameKeywords;
            this.phoneKeywords = findFields.phoneKeywords;
            this.emailKeywords = findFields.emailKeywords;
            this.addressKeywords = findFields.addressKeywords;
            this.customFieldKeywords = findFields.customFieldKeywords;
            this.groupKeywords = findFields.groupKeywords;
            this.tagKeywords = findFields.tagKeywords;
        }

        public void setNameKeywords(List<Name> names) {
            this.nameKeywords = names;
        }

        private Optional<List<Name>> getNameKeywords() {
            return Optional.ofNullable(nameKeywords);
        }

        private Stream<Name> getNameKeywordsStream() {
            return this.getNameKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setPhoneKeywords(List<Phone> phones) {
            this.phoneKeywords = phones;
        }

        private Optional<List<Phone>> getPhoneKeywords() {
            return Optional.ofNullable(phoneKeywords);
        }

        private Stream<Phone> getPhoneKeywordsStream() {
            return this.getPhoneKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setEmailKeywords(List<Email> emails) {
            this.emailKeywords = emails;
        }

        private Optional<List<Email>> getEmailKeywords() {
            return Optional.ofNullable(emailKeywords);
        }

        private Stream<Email> getEmailKeywordsStream() {
            return this.getEmailKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setGroupKeywords(List<Group> group) {
            this.groupKeywords = group;
        }

        private Optional<List<Group>> getGroupsKeywords() {
            return Optional.ofNullable(groupKeywords);
        }

        private Stream<Group> getGroupsKeywordsStream() {
            return this.getGroupsKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setTagKeywords(List<String> tags) {
            this.tagKeywords = tags;
        }

        private Optional<List<String>> getTagKeywords() {
            return Optional.ofNullable(tagKeywords);
        }

        private Stream<String> getTagsKeywordsStream() {
            return this.getTagKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setAddressKeywords(List<Address> addresses) {
            this.addressKeywords = addresses;
        }

        private Optional<List<Address>> getAddressKeywords() {
            return Optional.ofNullable(addressKeywords);
        }

        private Stream<Address> getAddressKeywordsStream() {
            return this.getAddressKeywords().map(List::stream).orElseGet(Stream::empty);
        }

        public void setFieldsKeywords(List<String> fields) {
            this.customFieldKeywords = fields;
        }

        private Optional<List<String>> getFieldKeywords() {
            return Optional.ofNullable(customFieldKeywords);
        }

        private Stream<String> getFieldsKeywordsStream() {
            return this.getFieldKeywords().map(List::stream).orElseGet(Stream::empty);
        }
    }
}
//@@author
