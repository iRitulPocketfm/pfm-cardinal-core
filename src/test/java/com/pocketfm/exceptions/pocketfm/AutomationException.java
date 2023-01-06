package com.pocketfm.exceptions.pocketfm;

public class AutomationException
        extends RuntimeException {
    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Exception e) {
        super(message, e);
    }
}
