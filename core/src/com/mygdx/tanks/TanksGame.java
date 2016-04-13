package com.mygdx.tanks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.Blok;
import model.Czolg;
import model.Kierunek;
import model.Plansza;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;

public class TanksGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture czolg_ziel,czolg_czer_L, czolg_nieb, czolg_pom;
    Texture czolg_czer_P, czolg_czer_G, czolg_czer_D;
	Texture  zarosla, cegly, kamien;
    Czolg czolg = new Czolg(1,5,new Point(10,10));
    Plansza plansza;
	
	@Override
	public void create () {
        plansza = new Plansza("plansza.txt");
		batch = new SpriteBatch();
		kamien= new Texture("kamien.png");
		czolg_czer_L = new Texture("czolg_czerL.png");
        czolg_czer_P = new Texture("czolg_czerP.png");
        czolg_czer_G = new Texture("czolg_czerG.png");
        czolg_czer_D = new Texture("czolg_czerD.png");
		czolg_nieb = new Texture("czolg_nieb.png");
		czolg_pom = new Texture("czolg_pom.png");
		czolg_ziel = new Texture("czolg_ziel.png");
		zarosla = new Texture("zarosla.png");
		cegly = new Texture("mur.png");
        czolg.setKierunek(Kierunek.LEWO);
        czolg.setPolozenieNaPlanszy(new Point(10*25, 800-10*25));
	}

	@Override
	public void render () {
        update();
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawBoard();
        batch.draw(, czolg.getPolozenie().x, czolg.getPolozenie().y);
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
            switch (obiekt.symbol){
                case 'C':{
                    batch.draw(cegly, 25*obiekt.getPolozenie().x, 800-25*obiekt.getPolozenie().y);
                    break;
                }
                case 'K':{
                    batch.draw(kamien, 25*obiekt.getPolozenie().x, 800-25*obiekt.getPolozenie().y);
                    break;
                }
                case 'Z':{
                    batch.draw(zarosla, 25*obiekt.getPolozenie().x, 800-25*obiekt.getPolozenie().y);
                    break;
                }
            }
        }
    }

    public void update(){
        int x = czolg.getPolozenieNaPlanszy().x;
        int y = czolg.getPolozenieNaPlanszy().y;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                x--;
                czolg.setKierunek(Kierunek.LEWO);
                czolg.setTekstura(czolg_czer_L);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                x++;
                czolg.setKierunek(Kierunek.PRAWO);
                czolg.setTekstura(czolg_czer_P);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                y++;
                czolg.setKierunek(Kierunek.GORA);
                czolg.setTekstura(czolg_czer_G);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                y--;
                czolg.setKierunek(Kierunek.DOL);
                czolg.setTekstura(czolg_czer_D);
            }

        czolg.setPolozenieNaPlanszy(new Point(x,y));
    }

	@Override
	public void dispose() {
		batch.dispose();
		kamien.dispose();
		czolg_czer_L.dispose();
		czolg_nieb.dispose();
		czolg_pom.dispose();
		czolg_ziel.dispose();
		zarosla.dispose();
		cegly.dispose();

	}

	public TanksGame() {
		super();
	}
}
