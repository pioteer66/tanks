package com.mygdx.tanks;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Blok;
import model.Czolg;
import model.Kierunek;
import model.Plansza;

public class TanksGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture czolg_ziel,czolg_czer_L, czolg_nieb, czolg_pom;
    Texture czolg_czer_P, czolg_czer_G, czolg_czer_D;
	Texture  zarosla, cegly, kamien;
    Czolg czolg = new Czolg(1,5, Stale.CZOLG_START_X, Stale.CZOLG_START_Y );
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
	}

	@Override
	public void render () {
        update();
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawBoard();
        batch.draw(new TextureRegion(czolg_czer_L), (float)czolg.getX(), (float)czolg.getY(), (float)czolg.getCenterX(), (float)czolg.getCenterY(),
                (float)czolg.getWidth(), (float)czolg.getHeight(), 0.5f, 0.5f, (float)czolg.getKierunek().getValue()*90);
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
                    batch.draw(cegly, (int)obiekt.getX(), Stale.WYSOKOSC*(int)obiekt.getY());
                    break;
                }
                case 'K':{
                    batch.draw(kamien, (int)obiekt.getX(), Stale.WYSOKOSC*(int)obiekt.getY());
                    break;
                }
                case 'Z':{
                    batch.draw(zarosla, (int)obiekt.getX(), Stale.WYSOKOSC-25*(int)obiekt.getY());
                    break;
                }
            }
        }
    }

    public void update(){
        int x = (int)czolg.getX();
        int y = (int)czolg.getY();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                czolg.x--;
                czolg.setKierunek(Kierunek.LEWO);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                czolg.x++;
                czolg.setKierunek(Kierunek.PRAWO);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                czolg.y++;
                czolg.setKierunek(Kierunek.GORA);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                czolg.y--;
                czolg.setKierunek(Kierunek.DOL);
            }
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
