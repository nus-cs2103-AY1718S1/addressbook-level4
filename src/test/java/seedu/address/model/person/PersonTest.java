//@@author A0143832J
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class PersonTest {
    public static final UniqueTagList TAG_LIST = new UniqueTagList();
    private Person person = new Person(AMY);

    @Test(expected = IllegalValueException.class)
    public void setName() throws Exception {
        person.setName(new Name("Akatsuki_wtf@12"));
    }

    @Test
    public void nameProperty() throws Exception {
        assertEquals(person.nameProperty().get(), new Name(VALID_NAME_AMY));
    }

    @Test
    public void getName() throws Exception {
        assertEquals(person.getName(), new Name(VALID_NAME_AMY));
    }

    @Test(expected = IllegalValueException.class)
    public void setPhone() throws Exception {
        person.setPhone(new Phone("asd1213f_2"));
    }

    @Test
    public void phoneProperty() throws Exception {
        assertEquals(person.phoneProperty().get(), new Phone(VALID_PHONE_AMY));
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals(person.getPhone(), new Phone(VALID_PHONE_AMY));
    }

    @Test(expected = IllegalValueException.class)
    public void setEmail() throws Exception {
        person.setEmail(new Email("notValidEmail"));
    }

    @Test
    public void emailProperty() throws Exception {
        assertEquals(person.emailProperty().get(), new Email(VALID_EMAIL_AMY));
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(person.getEmail(), new Email(VALID_EMAIL_AMY));
    }

    @Test(expected = NullPointerException.class)
    public void setAddress() throws Exception {
        person.setAddress(null);
    }

    @Test
    public void addressProperty() throws Exception {
        assertEquals(person.addressProperty().get(), new Address(VALID_ADDRESS_AMY));
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals(person.getAddress(), new Address(VALID_ADDRESS_AMY));
    }

    @Test(expected = NullPointerException.class)
    public void setRemark() throws Exception {
        person.setRemark(null);
    }

    @Test
    public void remarkProperty() throws Exception {
        assertEquals(person.remarkProperty().get(), new Remark(""));
    }

    @Test
    public void getRemark() throws Exception {
        assertEquals(person.getRemark(), new Remark(""));
    }

    @Test(expected = NullPointerException.class)
    public void setFavorite() throws Exception {
        person.setFavorite(null);
    }

    @Test
    public void favoriteProperty() throws Exception {
        assertEquals(person.favoriteProperty().get(), new Favorite());
    }

    @Test
    public void getFavorite() throws Exception {
        assertEquals(person.getFavorite(), new Favorite());
    }

    @Test
    public void getTags() throws Exception {
        TAG_LIST.add(new Tag("friend"));
        assertEquals(person.getTags(), TAG_LIST.toSet());
    }

    @Test
    public void getTagsString() throws Exception {
        assertEquals(person.getTagsString(), Collections.singletonList("friend"));
    }

    @Test
    public void tagProperty() throws Exception {
        assertEquals(person.tagProperty().get(), TAG_LIST);
    }

    @Test(expected = NullPointerException.class)
    public void setTags() throws Exception {
        person.setTags(null);
    }

    @Test
    public void equals() throws Exception {
        assertTrue(person.equals(person));
    }

}
//@@author
