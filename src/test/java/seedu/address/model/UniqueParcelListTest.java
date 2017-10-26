package seedu.address.model;

import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.UniqueParcelList;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;
import seedu.address.testutil.ParcelBuilder;

public class UniqueParcelListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueParcelList uniqueParcelList = new UniqueParcelList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueParcelList.asObservableList().remove(0);
    }

    @Test
    public void setParcel_targetIsNull_throwsParcelNotFoundException() throws DuplicateParcelException,
            ParcelNotFoundException {
        UniqueParcelList uniqueParcelList = new UniqueParcelList();
        Parcel toAdd = new ParcelBuilder().build();
        UniqueParcelList uniqueParcelListOther = new UniqueParcelList();
        uniqueParcelListOther.add(toAdd);
        uniqueParcelList.add(toAdd);
        thrown.expect(ParcelNotFoundException.class);
        uniqueParcelList.setParcel(null, toAdd);

        assertNotEquals(uniqueParcelList.hashCode(), uniqueParcelListOther.hashCode());
    }

    @Test
    public void removeParcel_targetIsNull_throwsParcelNotFoundException() throws ParcelNotFoundException {
        UniqueParcelList uniqueParcelList = new UniqueParcelList();
        Parcel toRemove = new ParcelBuilder().build();
        thrown.expect(ParcelNotFoundException.class);
        uniqueParcelList.remove(toRemove);
    }

}
