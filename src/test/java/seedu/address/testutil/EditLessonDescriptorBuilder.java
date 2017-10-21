package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditLessonDescriptorBuilder(ReadOnlyLesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setCode(lesson.getCode());
        descriptor.setClassType(lesson.getClassType());
        descriptor.setLocation(lesson.getLocation());
        descriptor.setGroup(lesson.getGroup());
        descriptor.setTimeSlot(lesson.getTimeSlot());
        descriptor.setLecturer(lesson.getLecturers());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withCode(String name) {
        try {
            ParserUtil.parseCode(Optional.of(name)).ifPresent(descriptor::setCode);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withClassType(String phone) {
        try {
            ParserUtil.parseClassType(Optional.of(phone)).ifPresent(descriptor::setClassType);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withLocation(String email) {
        try {
            ParserUtil.parseLocation(Optional.of(email)).ifPresent(descriptor::setLocation);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withGroup(String address) {
        try {
            ParserUtil.parseGroup(Optional.of(address)).ifPresent(descriptor::setGroup);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withTimeSlot(String address) {
        try {
            ParserUtil.parseTimeSlot(Optional.of(address)).ifPresent(descriptor::setTimeSlot);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withLecturers(String... tags) {
        try {
            descriptor.setLecturer(ParserUtil.parseLecturer(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
