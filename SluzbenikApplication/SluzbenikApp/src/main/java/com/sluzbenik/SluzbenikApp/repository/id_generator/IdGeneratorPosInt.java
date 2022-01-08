package com.sluzbenik.SluzbenikApp.repository.id_generator;

public class IdGeneratorPosInt implements IdGeneratorInterface{
    @Override
    public String generateId(String[] resourceList) {
        int genId = resourceList.length + 1;
        return Integer.toString(genId);
    }
}
