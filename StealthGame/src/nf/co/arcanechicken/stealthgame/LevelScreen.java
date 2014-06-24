package nf.co.arcanechicken.stealthgame;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.IntArray;

public class LevelScreen extends AbstractScreen implements InputProcessor {
	private PathFinder pathFinder;
	private AStar astar;
	private Player player;
	private Map map;
	private Enemy enemy;
	private Box2DMapParser b2mp;
	private float velocity = 5.0f, degreesPerPixel = 1f, playerAngle, current = 999f, cap = 1f;
	private int count, mapWidth, mapHeight;
	private boolean[] boxes;
	private ShapeRenderer sr;
	private IntArray path;
	private GameContactListener contactListener;

	public LevelScreen(StealthGame sg) {
		super(sg);

		// TODO Auto-generated constructor stub
	}

	public void show() {
		super.show();
		
		

		sr = new ShapeRenderer();

		rh = new RayHandler(getWorld());
		rh.setCombinedMatrix(cam.combined);

		stage.setViewport(Constants.UNIT_WIDTH, Constants.UNIT_HEIGHT, false);
		cam.position.set(0, 0, 0);

		map = new Map("medmap.tmx", cam);
		stage.addActor(map);
		mapWidth = map.getMap().getProperties().get("width", Integer.class);
		mapHeight = map.getMap().getProperties().get("height", Integer.class);

		b2mp = new Box2DMapParser(Gdx.files.internal("newboundaries.boxmap"), getWorld(), map);

		player = Player.create(getAtlas(), getWorld(), rh);
		player.setInitialPosition(35f, 15.6f);

		stage.addActor(player);

		enemy = Enemy.create(getAtlas(), getWorld());
		enemy.setInitialPosition(43, 9.6f);
		stage.addActor(enemy);
		
		contactListener = new GameContactListener(enemy);
		getWorld().setContactListener(contactListener);

		astar = new AStar(map.getMap().getProperties().get("width", Integer.class), map.getMap()
				.getProperties().get("height", Integer.class)) {
			protected boolean isValid(int x, int y) {
				return !map.getCollisions()[x][y];
			}
		};
		
		
		

	}

	@Override
	public void dispose() {
		rh.dispose();
	}

	public void render(float delta) {
		super.render(delta);

		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.BLUE);
		sr.begin(ShapeType.Filled);
		
		path = astar.getPath(
				(int) (player.getBody().getPosition().x / Constants.scale / Constants.TILE_SIZE),
				(int) (player.getBody().getPosition().y / Constants.scale / Constants.TILE_SIZE),
				(int) (enemy.getBody().getPosition().x / Constants.scale / Constants.TILE_SIZE),
				(int) (enemy.getBody().getPosition().y / Constants.scale / Constants.TILE_SIZE));
		

		for (int i = 0; i < path.size; i += 2) {
			int x = path.get(i);
			int y = path.get(i + 1);

			Vector2 local = new Vector2(x, y);
			sr.box(local.x * 64f / 80f, local.y * 64f / 80f, 0, 0.25f, 0.25f, 0);

		}
		
		if(path.size >= 4){
		
		float angle = (float) Math.atan2((path.get(3) * 64f / 80f)
				- (path.get(1) * 64f / 80f), (path.get(2) * 64f / 80f)
				- (path.get(0) * 64f / 80f));
		enemy.getBody().applyForceToCenter((float) Math.cos(angle) * 100, (float) Math.sin(angle) * 100, true);
		enemy.rotateTowards(player.getBody().getPosition());
		
		}

// if (current > cap && count < path.size) {
// enemy.getBody().setTransform(path.get(count) * 64f / 80f, path.get(count + 1)
// * 64f / 80f, 0);
//
//
// current = 0;
// count += 2;
// if (count >= path.size) {
// count = 0;
// }
//
// } else {
// current += delta;
//
// }

		sr.end();

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
