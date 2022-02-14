package com.imunizacija.ImunizacijaApp.template;

import org.exist.xupdate.XUpdateProcessor;

import static com.imunizacija.ImunizacijaApp.repository.Constants.ODGOVORI_NAMESPACE_PATH;

public class XUpdateTemplate {

    public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + ODGOVORI_NAMESPACE_PATH + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
            + "</xu:modifications>";

    public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + ODGOVORI_NAMESPACE_PATH + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";
}
