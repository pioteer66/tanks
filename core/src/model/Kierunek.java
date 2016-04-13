package model;

/**
 * Created by Dawid on 2016-04-12.
 */
public enum Kierunek {
    LEWO(0), DOL(1), PRAWO(2), GORA(3);
    private final int wartosc;
    private Kierunek(int wartosc) {
        this.wartosc = wartosc;
    }

    public int getValue() {
        return wartosc;
    }
}
