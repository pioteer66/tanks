package com.mygdx.tanks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.tanks.TanksGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height= 800;
		config.resizable = false;
		config.backgroundFPS = 60;
		new LwjglApplication(new TanksGame(), config);
	}
}
