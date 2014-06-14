package nf.co.arcanechicken.stealthgame;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Top Down Game";
		cfg.useGL20 = true;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.x = 0;
		cfg.y = 0;
		cfg.width = d.width - 20;
		cfg.height = d.height - 100;
		
		TexturePacker2.process("C:/Users/Austin/repos/stealth-game/StealthGame-android/assets/images", "C:/Users/Austin/repos/stealth-game/StealthGame-android/assets/image-atlases", "pack");

		new LwjglApplication(new StealthGame(), cfg);
	}
}
