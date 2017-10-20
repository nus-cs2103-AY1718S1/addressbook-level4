package seedu.address.model.parcel;

import seedu.address.commons.exceptions.IllegalValueException;

public enum Status {
	PENDING, DELIVERING, DELIVERED;

	public static final String MESSAGE_STATUS_CONSTRAINTS =
			"Status can only be PENDING, DELIVERED or DELIVERING";

	public static Status getStatusInstance(String status) throws IllegalValueException {
		String trimmedAndUpperCasedStatus = status.trim().toUpperCase();

		if(!isValidStatus(trimmedAndUpperCasedStatus)) {
			throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
		}

		switch (trimmedAndUpperCasedStatus) {
			case "PENDING":
				return PENDING;

			case "DELIVERING":
				return DELIVERING;

			case "DELIVERED":
				return DELIVERED;

			default:
				throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
		}
	}

	public static boolean isValidStatus(String status) {
		switch(status) {
			case "PENDING":  // fall through
			case "DELIVERING": // fall through
			case "DELIVERED":
				return true;

			default:
				return false;
		}
	}
}
