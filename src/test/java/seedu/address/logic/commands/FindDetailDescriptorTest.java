package seedu.address.logic.commands;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindDetailDescriptorTest {

    @Test
    public void equals() {
        FindCommand.FindDetailDescriptor descriptor1 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor2 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor3 = new FindDetailDescriptorBuilder().withEmail("Bob").build();

        Assert.assertTrue(descriptor1.equals(descriptor2));

        Assert.assertTrue(descriptor1.equals(descriptor1));

        Assert.assertFalse(descriptor1.equals(descriptor3));
    }

    @Test
    public void test_toString() {
        FindCommand.FindDetailDescriptor descriptor1 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor2 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor3 = new FindDetailDescriptorBuilder().withEmail("Bob").build();

        Assert.assertTrue(descriptor1.toString().equals(descriptor2.toString()));

        Assert.assertFalse(descriptor1.toString().equals(descriptor3.toString()));
    }
}
