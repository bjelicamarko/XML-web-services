package com.imunizacija.ImunizacijaApp.transformers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

public class Util {

    public static final String PATH = "data/xsl_documents/qr-code.png";
    public static final String CHARSET = "UTF-8";
    public static final int H = 200;
    public static final int W = 200;

    public static void createQrCode(String data) throws IOException, WriterException {
        QRCodeWriter qcwobj = new QRCodeWriter();
        BitMatrix bmobj = qcwobj.encode(data, BarcodeFormat.QR_CODE, W, H);
        Path pobj = FileSystems.getDefault().getPath(PATH);
        MatrixToImageWriter.writeToPath(bmobj, "PNG", pobj);
    }

    public static void deleteQrCode() {
        File file = new File(PATH);

        if (file.delete()) {
            System.out.println("Temp qr code deleted successfully");
        }
        else {
            System.out.println("Failed to delete temp qr code");
        }
    }

    public static String createAndEmbedQrCodeInHtmlString(String html, String data) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, W, H);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png", outputStream);

        String base64 = new String(Base64.getEncoder().encode(outputStream.toByteArray()));
        String imgTag = "<img alt='Qr Code' src='data:image/png;base64," + base64 + "' />";

        StringBuilder sb = new StringBuilder();
        int idx = html.indexOf("</body>");
        sb.append(html, 0, idx);
        sb.append(imgTag);
        sb.append(html.substring(idx));

        return sb.toString();
    }

    public static String replaceCharacters(String xml) {

        return null;
    }


}
