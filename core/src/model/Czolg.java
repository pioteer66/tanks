package model;


import java.awt.*;

/**
 * Created by Dawid on 2016-04-12.
 */
public class Czolg extends Rectangle{
    private int idGracza;
    private int zycia;
    private Kierunek kierunek;
    private boolean czyWRuchu;

    public Kierunek getKierunek() {
        return kierunek;
    }

    public void setKierunek(Kierunek kierunek) {
        this.kierunek = kierunek;
    }

    public int getZycia() {
        return zycia;
    }

    public void setZycia(int zycia) {
        this.zycia = zycia;
    }

    public int getIdGracza() {
        return idGracza;
    }

    public void setIdGracza(int idGracza) {
        this.idGracza = idGracza;
    }

    public boolean isCzyWRuchu() {
        return czyWRuchu;
    }

    public void setCzyWRuchu(boolean czyWRuchu) {
        this.czyWRuchu = czyWRuchu;
    }





    public Czolg(int idGracza,int iloscZyc, int polozenieX, int polozenieY){
        //czy to nie jest szerokoscia i wysokoscia?
        super(polozenieX,polozenieY,50,50);
        this.idGracza=idGracza;
        this.zycia=iloscZyc;
        this.czyWRuchu=false;
        this.kierunek=Kierunek.LEWO;
    }
}
