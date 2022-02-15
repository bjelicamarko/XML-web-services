package com.imunizacija.ImunizacijaApp.transformers;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

@Component
public class XML2HTMLTransformer {

    private static final TransformerFactory transformerFactory;

    static {
        transformerFactory = TransformerFactory.newInstance();
    }

    public String generateHTML(Node xmlAsDOMNode, String xslPath, String resourceUrl) throws TransformerException, IOException, WriterException {
        // Initialize Transformer instance
        StreamSource transformSource = new StreamSource(new File(xslPath));
        Transformer transformer = transformerFactory.newTransformer(transformSource);
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Generate XHTML
        transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");

        // generate StringWriter
        StringWriter sw = new StringWriter();

        // Transform DOM to HTML
        DOMSource source = new DOMSource(xmlAsDOMNode);
        StreamResult result = new StreamResult(sw);

        transformer.transform(source, result);
        String ret = sw.toString();

        if(resourceUrl != null)
            ret = Util.createAndEmbedQrCodeInHtmlString(sw.toString(), resourceUrl);

        return ret;
    }
}
