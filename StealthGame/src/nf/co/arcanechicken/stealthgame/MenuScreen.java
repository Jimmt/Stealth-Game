package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen{

	public MenuScreen(StealthGame sg) {
		super(sg);
		// TODO Auto-generated constructor stub
	}
	public void show(){
		super.show();
		Table table = super.getTable();
		table.setTransform(true);
		table.setFillParent(true);
		table.debug();
		
		table.add("Game");
		
		table.row();
		
		TextButton startButton = new TextButton("Start", getSkin());
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//load savegames
				sg.setScreen(new LevelScreen(sg));
			}
		});
		table.add(startButton).fill().prefWidth(Constants.WIDTH / 3).prefHeight(Constants.HEIGHT / 10);
		TextButton optionsButton = new TextButton("Options", getSkin());
		table.row();
		table.add(optionsButton).fill().prefWidth(Constants.WIDTH / 3).prefHeight(Constants.HEIGHT / 10);
	}

}
