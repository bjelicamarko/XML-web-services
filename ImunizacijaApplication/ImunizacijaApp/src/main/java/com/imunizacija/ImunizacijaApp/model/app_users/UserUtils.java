package com.imunizacija.ImunizacijaApp.model.app_users;

import java.util.regex.Pattern;

public class UserUtils {
    public static void CheckUserInfo(RegistrationDTO user) throws UserException {
        if (user == null) {
            throw new UserException("Invalid user sent from front!");
        }else if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null
                || user.getUserId() == null || user.getPassword() == null){
            throw new UserException("Podaci o korisniku ne smeju biti prazni!");
        }

        if (!user.getNationalityType().equalsIgnoreCase("RS") &&
                !user.getNationalityType().equalsIgnoreCase("FOREIGN_W_PASSPORT")
                && !user.getNationalityType().equalsIgnoreCase("FOREIGN_W_STAY") ){
            throw new UserException("Nevalidan tip korisnika!");
        }

        if (user.getFirstName().isEmpty()) {
            throw new UserException("Ime ne sme biti prazno!");
        }

        if (user.getLastName().isEmpty()) {
            throw new UserException("Prezime ne sme biti prazno!");
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 10){
            throw new UserException("Lozinka mora imati izmedju 8 i 10 karaktera!");
        }

        String jmbgPattern = "(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])\\d{3}\\d{2}\\d{4}";

        String passportPattern = "^[A-Z0-9&lt;]{9}[0-9]{1}[A-Z]{3}[0-9]{7}[A-Z]{1}[0-9]{7}[A-Z0-9&lt;]{14}[0-9]{2}$";  //pattern za mail

        if (user.getNationalityType().equalsIgnoreCase("RS") && !Pattern.compile(jmbgPattern).matcher(user.getUserId()).matches()){
            throw new UserException("Nevalidan JMBG!");
        }

        if (user.getNationalityType().equalsIgnoreCase("FOREIGN_W_PASSPORT") && !Pattern.compile(passportPattern).matcher(user.getUserId()).matches()){
            throw new UserException("Nevalidan broj pasosa!");
        }

        if (user.getNationalityType().equalsIgnoreCase("FOREIGN_W_STAY") && (user.getUserId().length() > 8 || user.getUserId().length() < 2)){
            throw new UserException("Nevalidan evidencioni broj stranca!");
        }


        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";  //pattern za mail

        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches()){
            throw new UserException("Email adresa nije validna!");
        }

    }
}
