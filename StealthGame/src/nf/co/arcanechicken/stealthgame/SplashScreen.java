package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {

	private Image splashImage;

	public SplashScreen(StealthGame sg) {
		super(sg);

	}

	public void show() {
		super.show();
		
		

		TextureRegion splashRegion = getAtlas().findRegion("splash");

		
		splashImage = new Image(splashRegion);
		splashImage.setFillParent(true);

		Action switchScreenAction = new Action() {
			public boolean act(float delta) {
				sg.setScreen(new MenuScreen(sg));
				return true;
			}
		};

		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.1f), Actions.delay(0.1f),
				Actions.fadeOut(0.1f), switchScreenAction));
		stage.addActor(splashImage);
	}
}
