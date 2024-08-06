package org.example.com.excep;

public class NoSuchDataException extends RuntimeException {
    public NoSuchDataException(String msg){
        super(msg);
    }
}
