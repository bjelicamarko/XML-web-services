package com.imunizacija.ImunizacijaApp.transformers;


import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Component
public class XSLFOTransformer {

    private final FopFactory fopFactory;

    private final TransformerFactory transformerFactory;

    public XSLFOTransformer() throws SAXException, IOException {

        // Initialize FOP factory object
        fopFactory = FopFactory.newInstance(new File(FOP_CONFIG));

        // Setup the XSLT transformer factory
        transformerFactory = new TransformerFactoryImpl();
    }

    public byte[] generatePDF(Node xmlAsDOMNode, String xslFilePath, String resourceUrl) throws Exception {

        // Point to the XSL-FO file
        File xslFile = new File(xslFilePath);

        // Create transformation source
        StreamSource transformSource = new StreamSource(xslFile);

        // Node to String
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(xmlAsDOMNode), new StreamResult(writer));
        String xmlString = writer.toString();

        // Create qr code
        if(resourceUrl != null)
            Util.createQrCode(resourceUrl);

        // String to byte[] in UTF8
        String text = new String(xmlString.getBytes(), StandardCharsets.UTF_8);
        byte[] xmlEncodedUTF8 = text.getBytes(StandardCharsets.UTF_8);

        // byte[] to InputStream
        InputStream inputStream = new ByteArrayInputStream(xmlEncodedUTF8);

        // Initialize the transformation subject
        StreamSource source = new StreamSource(inputStream);

        // Initialize user agent needed for the transformation
        FOUserAgent userAgent = fopFactory.newFOUserAgent();

        // Create the output stream to store the results
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        // Initialize the XSL-FO transformer object
        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);

        // Construct FOP instance with desired output format
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

        // Resulting SAX events
        Result res = new SAXResult(fop.getDefaultHandler());

        // Start XSLT transformation and FOP processing
        xslFoTransformer.transform(source, res);

        // Delete temp qr code
        if(resourceUrl != null)
            Util.deleteQrCode();

        return outStream.toByteArray();
    }
}

