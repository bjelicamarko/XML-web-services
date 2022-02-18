package com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception;

public class DzsException extends Exception{
    private String message;

    public DzsException(String s){
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
