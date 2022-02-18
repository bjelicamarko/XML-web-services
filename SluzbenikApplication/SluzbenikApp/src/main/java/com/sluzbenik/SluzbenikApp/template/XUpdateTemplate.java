package com.sluzbenik.SluzbenikApp.template;

import org.exist.xupdate.XUpdateProcessor;


import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

public class XUpdateTemplate {

    public static final String UPDATE_SISTEMSKI_MAGACIN =
            "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
            + "\" xmlns=\"" + TERMIN_NAMESPACE_PATH + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
            + "</xu:modifications>";
}
