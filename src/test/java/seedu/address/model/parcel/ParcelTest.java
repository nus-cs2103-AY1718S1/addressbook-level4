package seedu.address.model.parcel;


import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.ParcelBuilder;

public class ParcelTest {

	@Test
	public void equals() throws IllegalValueException {
		Parcel parcel = new Parcel(new ParcelBuilder().build());
		Parcel sameParcel = new Parcel(new ArticleNumber(ParcelBuilder.DEFAULT_ARTICLE_NUMBER),
				new Name(ParcelBuilder.DEFAULT_NAME), new Phone(ParcelBuilder.DEFAULT_PHONE),
				new Email(ParcelBuilder.DEFAULT_EMAIL), new Address(ParcelBuilder.DEFAULT_ADDRESS),
				SampleDataUtil.getTagSet(ParcelBuilder.DEFAULT_TAGS));
		Parcel differentParcel = new Parcel(new ArticleNumber(ParcelBuilder.DEFAULT_ARTICLE_NUMBER),
				new Name(ParcelBuilder.DEFAULT_NAME), new Phone(ParcelBuilder.DEFAULT_PHONE),
				new Email(ParcelBuilder.DEFAULT_EMAIL), new Address(ParcelBuilder.DEFAULT_ADDRESS),
				SampleDataUtil.getTagSet("different"));

		Parcel differentParcelArticleNumber = new Parcel(parcel);
		differentParcelArticleNumber.setArticleNumber(new ArticleNumber("RR123661123SG"));

		// check state equality
		assertEquals(parcel.getArticleNumber(), new ArticleNumber(ParcelBuilder.DEFAULT_ARTICLE_NUMBER));
		assertEquals(parcel.getName(), new Name(ParcelBuilder.DEFAULT_NAME));
		assertEquals(parcel.getPhone(), new Phone(ParcelBuilder.DEFAULT_PHONE));
		assertEquals(parcel.getEmail(), new Email(ParcelBuilder.DEFAULT_EMAIL));
		assertEquals(parcel.getAddress(), new Address(ParcelBuilder.DEFAULT_ADDRESS));
		assertEquals(parcel.getTags(), SampleDataUtil.getTagSet(ParcelBuilder.DEFAULT_TAGS));

		// parcel equality
		assertEquals(parcel, sameParcel);

		// parcel inequality
		assertFalse(parcel.equals(differentParcelArticleNumber));
	}
}