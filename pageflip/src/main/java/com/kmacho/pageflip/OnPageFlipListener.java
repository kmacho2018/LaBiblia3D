package com.kmacho.pageflip;

/**
 * Created by kmachoLaptop on 9/28/2017.
 */

public interface OnPageFlipListener {

    /**
     * Can page flip forward?
     *
     * @return true if page can flip forward
     */
    boolean canFlipForward();

    /**
     * Can page flip backward?
     *
     * @return true if page can flip backward
     */
    boolean canFlipBackward();
}

