package com.sluzbenik.SluzbenikApp.utils;

public class BridgeControllerUtil {

    public static String makeUrlSearch(String userId, String searchText, String documentName) {
        return String.format("http://localhost:9001/api/%s/search?userId=%s&searchText=%s",
                documentName, userId, searchText);
    }

    public static String makeUrlGenerateDocument(String type, String documentId, String documentName) {
        return String.format("http://localhost:9001/api/%s/generate%s/%s",
                documentName, type, documentId);
    }

}
