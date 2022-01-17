package com.north.light.libdatesel.v2.widget;

import java.io.Serializable;

/**
 * @Author: lzt
 * @Date: 2022/1/10 18:13
 * @Description:
 */
public class LibDrawTxInfo implements Serializable {
    private String tx;
    private float posX;
    private float poxY;
    private int color;
    private int size ;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPoxY() {
        return poxY;
    }

    public void setPoxY(float poxY) {
        this.poxY = poxY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
