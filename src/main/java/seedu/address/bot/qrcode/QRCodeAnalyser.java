package seedu.address.bot.qrcode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import seedu.address.commons.core.LogsCenter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class QRCodeAnalyser {

    private String decodedText;

    private static final Logger logger = LogsCenter.getLogger(QRCodeAnalyser.class);

    public QRCodeAnalyser(File file) {
        try {
            String decodedText = decodeQRCode(file);
            if(decodedText == null) {
                logger.info("No QR Code found in the image");
            } else {
                this.decodedText = decodedText;
                logger.info("Decoded text = " + decodedText);
            }
        } catch (IOException e) {
            logger.info("Could not decode QR Code, IOException :: " + e.getMessage());
        }
    }

    public String getDecodedText() {
        return this.decodedText;
    }

    private static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            logger.info("There is no QR code in the image");
            return null;
        }
    }
}