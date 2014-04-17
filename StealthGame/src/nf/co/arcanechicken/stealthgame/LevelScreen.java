package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class LevelScreen extends AbstractScreen implements InputProcessor {
	private Player player;
	private Array<NPC> npcs;
	private Map map;
	private Enemy enemy;

	public LevelScreen(StealthGame sg) {
		super(sg);
		
		npcs = new Array<NPC>();
		// TODO Auto-generated constructor stub
	}

	public void show() {
		super.show();

		stage.setViewport(Constants.UNIT_WIDTH, Constants.UNIT_HEIGHT, false);
		
		map = new Map("map.tmx", cam);
		stage.addActor(map);
		
		player = Player.create(getAtlas(), getWorld());
		player.setInitialPosition(5f, 7f);
		

		stage.addActor(player);
		
		enemy = Enemy.create(getAtlas(), getWorld());
		//amt controls distance
		stage.addActor(enemy);

		
		
	}

	public void render(float delta) {
		super.render(delta);


		for(int i = 0; i < npcs.size; i++){
			if(!stage.getActors().contains(npcs.get(i), true)){
				stage.addActor(npcs.get(i));
			}
		}
		

		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.moveUp(delta);
			enemy.moveUp(delta);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.moveLeft(delta);

		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.moveDown(delta);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.moveRight(delta);
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
