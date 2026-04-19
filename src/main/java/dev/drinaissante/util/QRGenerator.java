package dev.drinaissante.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.drinaissante.Main;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRGenerator {
    public static final String qrCodesPath;
    public static final File parentFolder;

    static {
        try {
            parentFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        qrCodesPath = parentFolder.getAbsolutePath() + File.separator + "qrCodes";

        File qrCodesFile = new File(qrCodesPath);

        if (!qrCodesFile.exists()) {
            if (qrCodesFile.mkdirs())
                System.out.println("Successfully created " + qrCodesPath);
        }
    }

    public static void generateQR(String textToEncode, String filePath) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(textToEncode, BarcodeFormat.QR_CODE, 300, 300);

            String url = qrCodesPath + File.separator + filePath;
            Path path = Paths.get(url);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("QR Code saved at: " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
