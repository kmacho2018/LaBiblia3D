package com.kmacho.pageflip;

/**
 * Created by kmachoLaptop on 9/28/2017.
 */

public final class GLPoint {
    // 3D coordinate
    float x;
    float y;
    float z;

    // texture coordinate
    float texX;
    float texY;

    /**
     * Set GLPoint with given values
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @param tX x coordinate of texture
     * @param tY y coordinate of texture
     */
    public void set(float x, float y, float z, float tX, float tY) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.texX = tX;
        this.texY = tY;
    }
}