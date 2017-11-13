package seedu.address.testutil;

import seedu.address.logic.commands.NusmodsCommand.NusmodsDescriptor;

//@@author ritchielq-reuse
/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class NusmodsDescriptorBuilder {

    private NusmodsDescriptor descriptor;

    public NusmodsDescriptorBuilder() {
        descriptor = new NusmodsDescriptor();
    }

    /**
     * Sets the {@code type} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withType(String type) {
        descriptor.setType(type);
        return this;
    }

    /**
     * Sets the {@code moduleCode} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code designLecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withDesignLecture(String designLecture) {
        descriptor.setDesignLecture(designLecture);
        return this;
    }

    /**
     * Sets the {@code laboratory} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withLaboratory(String laboratory) {
        descriptor.setLaboratory(laboratory);
        return this;
    }

    /**
     * Sets the {@code lecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withLecture(String lecture) {
        descriptor.setLecture(lecture);
        return this;
    }

    /**
     * Sets the {@code packagedLecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withPackagedLecture(String packagedLecture) {
        descriptor.setPackagedLecture(packagedLecture);
        return this;
    }

    /**
     * Sets the {@code packagedTutorial} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withPackagedTutorial(String packagedTutorial) {
        descriptor.setPackagedTutorial(packagedTutorial);
        return this;
    }

    /**
     * Sets the {@code recitation} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withRecitation(String recitation) {
        descriptor.setRecitation(recitation);
        return this;
    }

    /**
     * Sets the {@code sectionalTeaching} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withSectionalTeaching(String sectionalTeaching) {
        descriptor.setSectionalTeaching(sectionalTeaching);
        return this;
    }

    /**
     * Sets the {@code seminar} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withSeminar(String seminar) {
        descriptor.setSeminar(seminar);
        return this;
    }

    /**
     * Sets the {@code tutorial} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial(String tutorial) {
        descriptor.setTutorial(tutorial);
        return this;
    }

    /**
     * Sets the {@code tutorial2} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial2(String tutorial2) {
        descriptor.setTutorial2(tutorial2);
        return this;
    }

    /**
     * Sets the {@code tutorial3} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial3(String tutorial3) {
        descriptor.setTutorial3(tutorial3);
        return this;
    }

    public NusmodsDescriptor build() {
        return descriptor;
    }
}
