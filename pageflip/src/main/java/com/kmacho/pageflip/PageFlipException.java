package com.kmacho.pageflip;

/**
 * Created by kmachoLaptop on 9/28/2017.
 */

public class PageFlipException extends Exception {

    public PageFlipException() {
        super();
    }

    public PageFlipException(String message) {
        super(message);
    }

    public PageFlipException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageFlipException(Throwable cause) {
        super(cause);
    }
}
