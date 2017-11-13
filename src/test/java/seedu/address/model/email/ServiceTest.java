//@@author tshradheya
package seedu.address.model.email;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceTest {
    private Service service = new Service("gmail");
    private Service sameService = new Service("gmail");
    private Service anotherService = new Service("outlook");

    @Test
    public void service_equals() {
        assertTrue(service.equals(sameService));

        assertFalse(service == null);

        assertTrue(service.equals(service));

        assertFalse(service.equals(anotherService));

        assertEquals(service.toString(), sameService.toString());
        assertEquals(service.hashCode(), sameService.hashCode());
    }
}
