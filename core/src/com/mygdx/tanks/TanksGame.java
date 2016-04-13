package com.mygdx.tanks;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.*;

import static model.Kierunek.LEWO;

public class TanksGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture czolg_ziel,czolg_czer, czolg_nieb, czolg_pom;
	Texture  zarosla, cegly, kamien, pocisk;
    Czolg czolg = new Czolg(1,5, Stale.CZOLG_START_X*Stale.ROZMIAR_CZOLGU, Stale.CZOLG_START_Y*Stale.ROZMIAR_CZOLGU );
    Plansza plansza;

	@Override
	public void create () {
        plansza = new Plansza("plansza.txt");
        batch = new SpriteBatch();
        kamien= new Texture("niezniszczalny.png");
        czolg_czer = new Texture("czerwonyCzolg.png");
        czolg_nieb = new Texture("niebieskiCzolg.png");
        czolg_pom = new Texture("zoltyCzolg.png");
        czolg_ziel = new Texture("zielonyCzolg.png");
        zarosla = new Texture("krzak.png");
        cegly = new Texture("cegla.png");
        pocisk = new Texture("pocisk.png");
        czolg.setKierunek(LEWO);
	}

	@Override
	public void render () {
        update();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(cegly, 775,775);
        double x = czolg.getX();
        double y = czolg.getY();
        batch.draw(new TextureRegion(czolg_czer), (float)x, (float)y,
                (float)czolg.getCenterX()-(float)x, (float)czolg.getCenterY()-(float)y,
                (float)czolg.getWidth(), (float)czolg.getHeight(), 1f, 1f, (float)czolg.getKierunek().getValue()*90);
        drawMissiles();
        drawBoard();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

    public void drawBoard(){
        for (Blok obiekt:plansza.listaObiektow){
            switch (obiekt.getSymbol()){
                case 'C':{
                    batch.draw(cegly, (int)obiekt.getX(), (int)obiekt.getY());
                    break;
                }
                case 'K':{
                    batch.draw(kamien, (int)obiekt.getX(), (int)obiekt.getY());
                    break;
                }
                case 'Z':{
                    batch.draw(zarosla, (int)obiekt.getX(), (int)obiekt.getY());
                    break;
                }
            }
        }
    }

    public void drawMissiles(){
        for (Pocisk obiekt:plansza.listaPociskow){
            batch.draw(new TextureRegion(pocisk), (float)obiekt.getX(), (float)obiekt.getY(),
                    (float)obiekt.getCenterX()-(float)obiekt.getX(), (float)obiekt.getCenterY()-(float)obiekt.getY(),
                    (float)obiekt.getWidth(), (float)obiekt.getHeight(), 1f, 1f, (float)obiekt.getKierunek().getValue()*90);
            switch (obiekt.getKierunek()){
                case LEWO: {
                    obiekt.x--;
                    break;
                }
                case PRAWO: {
                    obiekt.x++;
                    break;
                }
                case GORA: {
                    obiekt.y++;
                    break;
                }
                case DOL: {
                    obiekt.y--;
                    break;
                }
            }
        }
    }

    public void update(){
        int x = (int)czolg.getX();
        int y = (int)czolg.getY();
        Kierunek k = czolg.getKierunek();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                czolg.x-=Stale.PREDKOSC_CZOLGU ;
                czolg.setKierunek(LEWO);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                czolg.x+=Stale.PREDKOSC_CZOLGU;
                czolg.setKierunek(Kierunek.PRAWO);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                czolg.y+=Stale.PREDKOSC_CZOLGU;
                czolg.setKierunek(Kierunek.GORA);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                czolg.y-=Stale.PREDKOSC_CZOLGU;
                czolg.setKierunek(Kierunek.DOL);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                int start_x = 0;
                int start_y = 0;
                //TODO
                //NIECH POCISKI WYLATUJĄ Z LUFY!
                switch(czolg.getKierunek()){
                    case LEWO:{
                        start_x = (int)(czolg.getX());
                        start_y = (int) (czolg.getY() + czolg.height/2);
                    }
                    case PRAWO:{
                        start_x = (int)(czolg.getX()+czolg.width);
                        start_y = (int) (czolg.getY() + czolg.height/2);
                    }
                    case GORA:{
                        start_x = (int)(czolg.getX()+ czolg.width/2);
                        start_y = (int)(czolg.getY() + czolg.height);
                    }
                    case DOL:{
                        start_x = (int)(czolg.getX()+ czolg.width/2);
                        start_y = (int)(czolg.getY());
                    }

                }

                Pocisk nowy = new Pocisk(czolg, czolg.getKierunek());
                nowy.x = start_x;
                nowy.y = start_y;
                plansza.listaPociskow.add(nowy);
            }
        if (czolg.getX() >= Stale.SZEROKOSC-50 || czolg.getX() <=0 ||
                czolg.getY() <= 0 || czolg.getY() >= Stale.WYSOKOSC-50){
            czolg.x = x;
            czolg.y = y;
        }
        else {
            boolean jest_kolizja = false;
            for (Blok obiekt : plansza.listaObiektow) {
                if (obiekt.intersection(czolg).width >2 && obiekt.intersection(czolg).height >2 && obiekt.getSymbol() != 'Z') {
                    jest_kolizja = true;
                    break;
                }
            }
            //Usuwanie pocisków po wylocie z planszy
            for (int i=0; i<plansza.listaPociskow.size(); i++){
                Pocisk pocisk = plansza.listaPociskow.get(i);
                switch(pocisk.getKierunek()){
                    case LEWO: {
                        if (pocisk.getX() <= 0) plansza.listaPociskow.remove(i);
                        break;
                    }
                    case PRAWO: {
                        if (pocisk.getX() >= Stale.SZEROKOSC) plansza.listaPociskow.remove(i);
                        break;
                    }
                    case GORA: {
                        if (pocisk.getY() >= Stale.WYSOKOSC) plansza.listaPociskow.remove(i);
                        break;
                    }
                    case DOL: {
                        if (pocisk.getY() <= 0) plansza.listaPociskow.remove(i);
                        break;
                    }
                }
            }
            if (jest_kolizja) {
                czolg.x = x;
                czolg.y = y;
            }
        }
    }

	@Override
	public void dispose() {
		batch.dispose();
		kamien.dispose();
		czolg_czer.dispose();
		czolg_nieb.dispose();
		czolg_pom.dispose();
		czolg_ziel.dispose();
		zarosla.dispose();
        pocisk.dispose();
		cegly.dispose();
	}

	public TanksGame() {
		super();
	}
}
