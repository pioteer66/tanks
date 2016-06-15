package com.mygdx.tanks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.tanks.Magazine;
import com.mygdx.tanks.TanksGame;


public class DesktopLauncher {
	public static final String ip = "localhost";
	private static final Magazine magazine = new Magazine();

	public static void main (String[] arg) {
		try {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = 800;
			config.height= 800;
			config.resizable = false;
			config.backgroundFPS = 60;
			new LwjglApplication(new TanksGame(magazine), config);
			// trzeba jeszcze zamykać socket ale  nie wiem gdzie , na razie
		} catch (Exception ex){
			System.out.println(ex);
		}

	}
}
