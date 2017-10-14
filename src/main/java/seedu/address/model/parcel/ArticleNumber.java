package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Article number of a package.
 * @link { https://en.wikipedia.org/wiki/Registered_mail}
 */
public class ArticleNumber {

	public static final String MESSAGE_ARTICLE_NUMBER_CONSTRAINTS =
			"Parcel article number should start with 'RR', followed by 9 digits, and ends with 'SG'";
	public static final String ARTICLE_NUMBER_VALIDATION_REGEX = "^[R]{2}[0-9]{9}[S]{1}[G]{1}$";

	public final String value;

	/**
	 * Validates given articleNumber.
	 *
	 * @throws IllegalValueException if given articleNumber string is invalid.
	 */
	public ArticleNumber(String articleNumber) throws IllegalValueException {
		requireNonNull(articleNumber);
		String trimmedArticleNumber = articleNumber.trim();
		if (!isValidArticleNumber(trimmedArticleNumber)) {
			throw new IllegalValueException(MESSAGE_ARTICLE_NUMBER_CONSTRAINTS);
		}
		this.value = trimmedArticleNumber;
	}

	/**
	 * Returns if a given string is a valid parcel email address.
	 */
	public static boolean isValidArticleNumber(String test) {
		return test.matches(ARTICLE_NUMBER_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ArticleNumber // instanceof handles nulls
				&& this.value.equals(((ArticleNumber) other).value)); // state check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
