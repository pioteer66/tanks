package com.mygdx.tanks;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.*;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import static model.Direction.LEFT;

public class TanksGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture greenTankTexture, redTankTexture, blueTankTexture, orangeTankTexture;
	Texture shrubTexture, brickTexture, stoneTexture, missileTexture;
    Tank tank = new Tank(1,5, Constants.TANK_START_X * Constants.TANK_SIZE, Constants.TANK_START_Y * Constants.TANK_SIZE);
    Board board;
    OutputStream outToServer;
    DataOutputStream out;

    private Date date;
    private long timeStart;
    private long timeEnd;
    private long reloadTime; //milisekundy
    private double missileSpeed;     // jednostki odswiezen
    private Socket connectionSocket;
    private int playerId = -1;

	@Override
	public void create () {
        board = new Board("plansza.txt");
        batch = new SpriteBatch();
        stoneTexture = new Texture("niezniszczalny.png");
        redTankTexture = new Texture("czerwonyCzolg.png");
        blueTankTexture = new Texture("niebieskiCzolg.png");
        orangeTankTexture = new Texture("zoltyCzolg.png");
        greenTankTexture = new Texture("zielonyCzolg.png");
        shrubTexture = new Texture("krzak.png");
        brickTexture = new Texture("cegla.png");
        missileTexture = new Texture("pocisk.png");
        /*try{
            connectionSocket = new Socket(InetAddress.getByName("localhost"), Constants.SERVER_PORT);
            System.out.println("Połaczono z serwerem: "
                    + connectionSocket.getRemoteSocketAddress());
            outToServer  = connectionSocket.getOutputStream();
            out = new DataOutputStream(outToServer);
        }
        catch (UnknownHostException ex){
            System.out.println("Unknown host!");
        }
        catch (IOException ex){
            System.out.println("Problem with IO operation");
        }*/
        processServerResponse();

        tank.setDirection(LEFT);
        this.date = new Date();
        this.timeStart = date.getTime();
        this.timeEnd = date.getTime();
        this.reloadTime = 500; //ms
        this.missileSpeed = 600.0; // jednostek
	}

	@Override
	public void render () {
        update();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(brickTexture, 775,775);
        double x = tank.getX();
        double y = tank.getY();
        batch.draw(new TextureRegion(redTankTexture), (float)x, (float)y,
                (float) tank.getCenterX()-(float)x, (float) tank.getCenterY()-(float)y,
                (float) tank.getWidth(), (float) tank.getHeight(), 1f, 1f, (float) tank.getDirection().getValue()*90);
        drawMissiles();
        removeRedundantMissiles();
        drawBoard();
        batch.end();
        this.date = new Date(); // aktualizuje czas
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


    private void processServerResponse()
    {
        /*try{
            InputStream inFromServer = connectionSocket.getInputStream();
            DataInputStream in =
                    new DataInputStream(inFromServer);
            playerId = Integer.parseInt(in.readUTF());
        }catch (IOException ex){
            System.out.println("Problem ze strumieniem wejściowym");
        }
*/
    }

    private void checkForCollisions(Block object, int j)
    {
        if(object.getSymbol() != 'Z') {
            Rectangle rect = new Rectangle((int) object.getX(), (int) object.getY(), 25, 25);
            for (int i = 0; i < this.board.missilesList.size(); i++) {
                if (rect.contains(this.board.missilesList.get(i).getCenterX(), this.board.missilesList.get(i).getCenterY())) {
                    this.board.missilesList.remove(i);
                    board.objectsList.get(j).setStamina(board.objectsList.get(j).getStamina() -1);
                }
            }
        }
    }

    private void updateBoardState()
    {
        ArrayList<Block> temp = new ArrayList<Block>();
        for (Block object: board.objectsList){
            if (object.getStamina() != 0){
                temp.add(object);
            }
        }
        board.objectsList.clear();
        for (Block object:temp){
            board.objectsList.add(object);
        }
    }

    private void drawBoard(){
        int j=0;
        for (Block object: board.objectsList){
            switch (object.getSymbol()){
                case 'C':{
                    batch.draw(brickTexture, (int)object.getX(), (int)object.getY());
                    break;
                }
                case 'K':{
                    batch.draw(stoneTexture, (int)object.getX(), (int)object.getY());
                    break;
                }
                case 'Z':{
                    batch.draw(shrubTexture, (int)object.getX(), (int)object.getY());
                    break;
                }
            }
            checkForCollisions(object, j);
            // szybka kolizja pociskow -- potem zastapi ja serwer
            j++;
        }
        updateBoardState();
    }

    private void updateMissilePosition(Missile missile)
    {
        double missileStep = this.missileSpeed *( 1.0 / Gdx.graphics.getFramesPerSecond()); // predkosc = jednoski / ramke
        switch ( missile.getDirection()){
            case LEFT: {
                missile.x -= missileStep;
                break;
            }
            case RIGHT: {
                missile.x += missileStep;
                break;
            }
            case UP: {
                missile.y += missileStep;
                break;
            }
            case DOWN: {
                missile.y -= missileStep;
                break;
            }
        }
    }

    private void drawMissiles(){
        //Podobna funkcja jak dla rysowania czołgu
        for (Missile missile : board.missilesList){
                    batch.draw(new TextureRegion(missileTexture),
                            (float) missile.getX(), (float) missile.getY(),
                            (float) missile.getCenterX()-(float) missile.getX(), (float) missile.getCenterY()-(float) missile.getY(),
                            (float) missile.getWidth(), (float) missile.getHeight(),
                            1f, 1f,
                            (float) missile.getDirection().getValue()*90);
            updateMissilePosition(missile);
        }
    }

    private void removeRedundantMissiles(){
        //Usuwanie pocisków po wylocie z planszy
        for (int i = 0; i< board.missilesList.size(); i++){
            Missile missile = board.missilesList.get(i);
            switch(missile.getDirection()){
                case LEFT: {
                    if (missile.getX() <= 0) board.missilesList.remove(i);
                    break;
                }
                case RIGHT: {
                    if (missile.getX() >= Constants.WIDTH) board.missilesList.remove(i);
                    break;
                }
                case UP: {
                    if (missile.getY() >= Constants.HEIGHT) board.missilesList.remove(i);
                    break;
                }
                case DOWN: {
                    if (missile.getY() <= 0) board.missilesList.remove(i);
                    break;
                }
            }
        }
    }

    private Point getMissileStartingPosition()
    {
        Point start = new Point();
        start.x = 0;
        start.y = 0;
        switch (tank.getDirection()) {
            case LEFT: {
                start.x = (int) (tank.getX());
                start.y = (int) (tank.getY() + tank.height / 2 - 5);
                break;
            }
            case RIGHT: {
                start.x = (int) (tank.getX() + tank.width);
                start.y = (int) (tank.getY() + tank.height / 2 - 5);
                break;
            }
            case UP: {
                start.x = (int) (tank.getX() + tank.width / 2 - 5);
                start.y = (int) (tank.getY() + tank.height);
                break;
            }
            case DOWN: {
                start.x = (int) (tank.getX() + tank.width / 2 - 5);
                start.y = (int) (tank.getY());
                break;
            }

        }
        return start;
    }

    private void launchMissile(){
        if(this.timeStart >= timeEnd) { /* sprawdza czy upłyna rzadany czas przaładowania */
            Point start = getMissileStartingPosition();
            //start_x i start_y to początkowa pozycja pocisku
            Missile missile = new Missile(tank, tank.getDirection());
            missile.x = start.x;
            missile.y = start.y;
            board.missilesList.add(missile);
            timeEnd = this.date.getTime() + this.reloadTime;
        }
    }
    private void collisionDetector(int x, int y){
        //uniemożliwienie wyjechania poza planszę
        if (tank.getX() >= Constants.WIDTH - Constants.TANK_SIZE || tank.getX() <=0 ||
            tank.getY() <= 0 || tank.getY() >= Constants.HEIGHT - Constants.TANK_SIZE)
        {
            tank.x = x;
            tank.y = y;
        } else { /* //sprawdzenie kolizji, czyli dzięki temu czołg nie wjeżdża na bloki (chyba, że to zarośla) */
            boolean isCollision = false;
            for (Block object : board.objectsList)
            {
                if (object.intersection(tank).width >2 && object.intersection(tank).height >2 && object.getSymbol() != 'Z')
                {
                    isCollision = true;
                    break;
                }
            }
            if (isCollision) /*  //Jeśli wystąpiła kolizja z blokiem, to cofnij na pole sprzed zmiany */
            {
                tank.x = x;
                tank.y = y;
            }
        }
    }

    private void update(){
        this.timeStart = date.getTime();
        int x = (int) tank.getX();
        int y = (int) tank.getY();
            //Odczyt klawiszy
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                tank.x -= Constants.TANK_SPEED;
                //out.writeBytes(playerId + "," + Direction.LEFT);
                tank.setDirection(LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                tank.x += Constants.TANK_SPEED;
                tank.setDirection(Direction.RIGHT);
                //out.writeBytes(playerId + "," + Direction.LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                tank.y += Constants.TANK_SPEED;
                tank.setDirection(Direction.UP);
                //out.writeBytes(playerId + "," + Direction.LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                tank.y -= Constants.TANK_SPEED;
                tank.setDirection(Direction.DOWN);
                //out.writeBytes(playerId + "," + Direction.LEFT);
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                this.launchMissile();
                //out.writeBytes(playerId + "," + "");
            }
            collisionDetector(x, y);
        /*catch (IOException ex){
            System.out.println("Problem ze strumieniem wyjściowym");
        }*/

    }


	@Override
	public void dispose() {
		batch.dispose();
		stoneTexture.dispose();
		redTankTexture.dispose();
		blueTankTexture.dispose();
		orangeTankTexture.dispose();
		greenTankTexture.dispose();
		shrubTexture.dispose();
        missileTexture.dispose();
		brickTexture.dispose();
	}

	public TanksGame() {
		super();
	}
}
