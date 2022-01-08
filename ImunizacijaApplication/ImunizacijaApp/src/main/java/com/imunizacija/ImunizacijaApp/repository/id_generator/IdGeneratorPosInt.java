package com.imunizacija.ImunizacijaApp.repository.id_generator;

public class IdGeneratorPosInt{

    public String generateId(String[] resourceList) {
        int genId = resourceList.length + 1;
        return Integer.toString(genId);
    }
}
