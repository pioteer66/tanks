package model;


import java.awt.*;


public class Blok extends Rectangle{
    protected int wytrzymalosc;
    protected char symbol;

    public Blok(int polozenieX, int polozenieY){
        super(polozenieX, polozenieY, 25, 25);
    }


    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getWytrzymalosc() {
        return wytrzymalosc;
    }

    public void setWytrzymalosc(int wytrzymalosc) {
        this.wytrzymalosc = wytrzymalosc;
    }


}
