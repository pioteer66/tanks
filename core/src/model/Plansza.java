package model;

import java.io.*;
import java.util.ArrayList;
import com.mygdx.tanks.Stale;

public class Plansza {
    public ArrayList <Blok> listaObiektow;
    public ArrayList <Pocisk> listaPociskow;

    public Plansza(String path) {
        listaObiektow = new ArrayList<Blok>();
        listaPociskow = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader("plansza.txt"));
            br.read();
            for (int i = 1; i <= 32; i++) {
                String linia = br.readLine();
                for (int j = 0; j < 32; j++) {
                    char znak = linia.charAt(j * 2);
                    switch (znak) {
                        case 'C': {
                            this.listaObiektow.add(new Cegly(j*25, Stale.WYSOKOSC -i*25));
                            break;
                        }
                        case 'K': {
                            this.listaObiektow.add(new Kamien(j*25, Stale.WYSOKOSC-i*25));
                            break;
                        }
                        case 'Z': {
                            this.listaObiektow.add(new Zarosla(j*25, Stale.WYSOKOSC-i*25));
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Nie znaleziono pliku!");
        } catch (IOException ex) {
            ex.getMessage();
        }

    }

}
