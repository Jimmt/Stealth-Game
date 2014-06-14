package nf.co.arcanechicken.stealthgame;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

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
	protected RayHandler rh;
	private FPSLogger logger;
	
	

	public AbstractScreen(StealthGame sg) {
		this.sg = sg;
		stage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		cam = (OrthographicCamera) stage.getCamera();

		world = new World(new Vector2(0, 0f), false);
		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();

		
		logger = new FPSLogger();
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

	protected World getWorld() {
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


		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		stage.draw();
		
		if(rh != null)
		rh.updateAndRender();
		
		world.step(1 / 60f, 3, 3); 
		

		debugRenderer.render(world, cam.combined);
		Table.drawDebug(stage);

		getBatch().setProjectionMatrix(cam.combined);
		
		logger.log();
		
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
