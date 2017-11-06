package seedu.address.logic.commands;

/**
 * Method for application to call out for different QR Functions
 */
//@@author danielweide
public class QrGenSaveContactCommand {
    /**
     * Method to Generate SaveContact for Phone QRCode
     */
    public String qrSaveContact(String phoneNum, String contactName, String contactEmail) {
        String qrA = "http://";
        String qrB = "api.qrserver.com/";
        String qrC = "v1/";
        String qrD = "create-qr-code/";
        String qrE = "?color=000000";
        String qrF = "&bgcolor=FFFFFF";
        String qrG = "&data=BEGIN";
        String qrH = "%3AVCARD";
        String qrI = "%0AVERSION";
        String qrJ = "%3A2.1%0";
        String qrK = "AFN%3A";
        String lineA = qrA + qrB + qrC + qrD + qrE + qrF + qrG + qrH + qrI
                + qrJ + qrK;
        String newName = contactName.replace(' ', '+');
        String lineB = "%0AN%3A%3B";
        String qrL = "%0ATEL";
        String qrM = "%3BWORK";
        String qrN = "%3BVOICE%3A";
        String lineC = qrL + qrM + qrN;
        String qrO = "%0AEMAIL";
        String qrP = "%3BWORK";
        String qrQ = "%3BINTERNET%3A";
        String lineD = qrO + qrP + qrQ;
        String qrCodeA = "%0AEND";
        String qrCodeB = "%3AVCARD";
        String qrCodeC = "%0A&qzone=1";
        String qrCodeD = "&margin=0";
        String qrCodeE = "&size=500x500";
        String qrCodeF = "&ecc=L";
        String lineE = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF;
        String fullQr = lineA + newName + lineB + newName + lineC + phoneNum + lineD + contactEmail + lineE;
        System.out.println(fullQr);
        return fullQr;
    }
}
