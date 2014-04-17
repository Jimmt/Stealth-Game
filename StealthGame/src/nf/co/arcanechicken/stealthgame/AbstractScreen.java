package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen {
	protected StealthGame sg;
	protected Stage stage;
	protected OrthographicCamera cam;
	private World world;
	private Skin skin;
	private BitmapFont font;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer;
	private Table table;
	protected ShapeRenderer sr;

	public AbstractScreen(StealthGame sg) {
		this.sg = sg;
		stage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		cam = (OrthographicCamera) stage.getCamera();
		world = new World(new Vector2(0, 0f), false);
		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();
	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	protected Table getTable() {
		if (table == null) {
			table = new Table(getSkin());
			table.setFillParent(true);

			stage.addActor(table);
		}
		return this.table;
	}
	protected World getWorld(){
		return world;
	}

	protected Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}

	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont();
		}
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) {
			batch = new SpriteBatch();
		}
		return batch;
	}

	public TextureAtlas getAtlas() {
		if (atlas == null) {
			atlas = new TextureAtlas(Gdx.files.internal("image-atlases/pack.atlas"));
		}

		return this.atlas;
	}

	public void resize(int width, int height) {

	}

	public void render(float delta) {

		stage.act(delta);
		
		

		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Gdx.gl.glClear(16384);
		
		stage.draw();
		
		
		
		world.step(1 / 60f, 3, 3); // error line

		debugRenderer.render(world, cam.combined);
		Table.drawDebug(stage);

		getBatch().setProjectionMatrix(cam.combined);
	}

	public void hide() {

		dispose();
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

}
