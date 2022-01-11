package com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator;

import java.util.Random;

public class IdGeneratorDZS implements IdGeneratorInterface{

    private final int idLength;

    public IdGeneratorDZS(){
        this.idLength = 7;
    }

    public IdGeneratorDZS(int idLength){
        this.idLength = idLength;
    }

    @Override
    public String generateId(String[] takenIds) {
        Random rand = new Random();
        String str = "1234567890";
        StringBuilder pw = new StringBuilder(idLength);
        boolean flag = true;

        while(flag) {
            for (int i = 0; i < idLength; i++) {
                pw.append(str.charAt(rand.nextInt(str.length())));
            }
            pw.append(".xml");
            String genId = pw.toString();
            flag = false;
            for (String takenId : takenIds) {
                if (takenId.equals(genId)) {
                    flag = true;
                    break;
                }
            }
        }

        return pw.toString().replace(".xml","");
    }
}
