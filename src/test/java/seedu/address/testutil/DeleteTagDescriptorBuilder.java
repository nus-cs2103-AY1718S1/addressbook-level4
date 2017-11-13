package seedu.address.testutil;
//@@author pjunwei95
import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building DeleteTagDescriptor objects.
 */
public class DeleteTagDescriptorBuilder {

    private DeleteTagDescriptor descriptor;

    public DeleteTagDescriptorBuilder() {
        descriptor = new DeleteTagDescriptor();
    }

    public DeleteTagDescriptorBuilder(DeleteTagDescriptor descriptor) {
        this.descriptor = new DeleteTagDescriptor(descriptor);
    }

    /**
     * Returns an {@code DeleteTagDescriptor} with fields containing {@code person}'s details
     */
    public DeleteTagDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new DeleteTagDescriptor();
        descriptor.setTags(person.getTags());
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code DeleteTagDescriptor}
     * that we are building.
     */
    public DeleteTagDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public DeleteTagDescriptor build() {
        return descriptor;
    }
}
