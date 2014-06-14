package nf.co.arcanechicken.stealthgame;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class LevelScreen extends AbstractScreen implements InputProcessor {
	private Player player;
	private Array<NPC> npcs;
	private Map map;
	private Enemy enemy;
	private Box2DMapParser b2mp;
	private FirstPersonCameraController fpcc;
	private float velocity = 5.0f, degreesPerPixel = 1f, playerAngle, camAngle;

	public LevelScreen(StealthGame sg) {
		super(sg);

		npcs = new Array<NPC>();
		// TODO Auto-generated constructor stub
	}

	public void show() {
		super.show();

		fpcc = new FirstPersonCameraController(stage.getCamera());

		rh = new RayHandler(getWorld());
		rh.setCombinedMatrix(cam.combined);
// PointLight pl = new PointLight(rh, 32, new Color(0f, 0.0f, 0.0f, 1.0f), 3, 0,
// 0);

		stage.setViewport(Constants.UNIT_WIDTH, Constants.UNIT_HEIGHT, false);

		map = new Map("medmap.tmx", cam);
		stage.addActor(map);

		b2mp = new Box2DMapParser(Gdx.files.internal("boundaries.boxmap"), getWorld(), map);

		player = Player.create(getAtlas(), getWorld(), rh);
		player.setInitialPosition(5f, 7f);

		stage.addActor(player);

// enemy = Enemy.create(getAtlas(), getWorld());
// stage.addActor(enemy);

	}

	@Override
	public void dispose() {
		rh.dispose();
	}

	public void render(float delta) {
		super.render(delta);

		
		for (int i = 0; i < npcs.size; i++) {
			if (!stage.getActors().contains(npcs.get(i), true)) {
				stage.addActor(npcs.get(i));
			}
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.moveUp(delta);

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

	public float getCameraCurrentXYAngle(OrthographicCamera cam) {
		return (float) Math.atan2(cam.up.x, cam.up.y) * MathUtils.radiansToDegrees;
	}

	@Override
	public boolean keyDown(int keycode) {

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
