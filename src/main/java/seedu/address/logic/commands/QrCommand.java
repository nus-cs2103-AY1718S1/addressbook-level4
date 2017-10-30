package seedu.address.logic.commands;

/**
 * Method for application to call out for different QR Functions
 */
public class QrCommand {
    /**
     * Method to Generate PhoneCall QRCode
     */
    public String qrCall(String phoneNum) {
        String qrCodeA = "http://api.qrserver.com/v1/create-qr-code/?color=000000&bgcolor=FFFFFF&data=tel%3A";
        String qrCodeB = "&qzone=1&margin=0&size=150x150&ecc=L";
        String fullQr = qrCodeA + phoneNum + qrCodeB;
        return fullQr;
    }
}
