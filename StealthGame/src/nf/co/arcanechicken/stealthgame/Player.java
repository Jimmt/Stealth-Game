package nf.co.arcanechicken.stealthgame;

import java.util.HashMap;
import java.util.Map;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class Player extends Image {
	private Map<TextureRegion, Drawable> walkDrawables;
	private Array<Arrow> arrows;
	private Arrow arrow;
	private TextureAtlas atlas;
	private World world;
	private Body body;
	public Vector2 temp, centerPosition, mouse, dir, dir1, ext, sclDir;
	private float width, height, ry, lastShotTime = 999f, shotCap = 0.5f;
	private float angle;
	private ConeLight cl;
	private PointLight pl;

	public Player(Array<AtlasRegion> regions, TextureAtlas atlas, World world, RayHandler rh) {
		super(regions.get(0));

		this.atlas = atlas;
		this.world = world;


		arrows = new Array<Arrow>();

		walkDrawables = new HashMap<TextureRegion, Drawable>();


		for (AtlasRegion region : regions) {
			walkDrawables.put(region, new TextureRegionDrawable(region));
		}

		temp = new Vector2(0, 0);
		

//		cl = new ConeLight(rh, 32, new Color(0f, 0.0f, 0.0f, 1.0f), 3, 0, 0, 0, 1);
		pl = new PointLight(rh, 32, new Color(0f, 0.0f, 0.0f, 0.99f), 4, 0, 0);
		
		width = getDrawable().getMinWidth() * Constants.scale * 2;
		height = getDrawable().getMinHeight() * Constants.scale * 2;
		initBody();
	}
	
	public void checkMouseRotation() {
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		mouse = new Vector2(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());

		dir = mouse.sub(centerPosition);
		angle = dir.angle();

	}
	public float getAngle(){
		return angle;
	}


	public void act(float delta) {
		super.act(delta);

		checkMouseRotation();

		if (Gdx.input.isButtonPressed(Buttons.LEFT) && lastShotTime > shotCap) {
			fire();
			lastShotTime = 0f;
		} else {
			lastShotTime += delta;
		}

		getStage().getCamera().position.set(Math.round(body.getPosition().x * 80f) / 80f, Math.round(body.getPosition().y * 80f) / 80f, 0);

		setOrigin(width / 2, height / 2);

		body.setTransform(body.getPosition().x, body.getPosition().y, MathUtils.degreesToRadians
				* angle - MathUtils.PI / 2);
		setRotation(body.getTransform().getRotation() * MathUtils.radiansToDegrees);

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

	}
	

	public void fire() {
		float startX = body.getPosition().x + MathUtils.cos(MathUtils.degreesToRadians * angle) / 4;
		float startY = body.getPosition().y + MathUtils.sin(MathUtils.degreesToRadians * angle) / 4;

		arrow = Arrow.create(atlas, world, startX, startY, MathUtils.degreesToRadians * angle,
				"arrow");

		getStage().addActor(arrow);

		arrow.getBody().setLinearVelocity(dir.nor().scl(20f));
		arrows.add(arrow);

		
		// bullet type class, NEED DESIGN
	}

	public void setFriction(float friction) {
		body.getFixtureList().get(0).setFriction(friction);
	}

	public void setInitialPosition(float x, float y) {
		body.setTransform(x, y, 0.0f);

	}

	public void initBody() {
		PolygonShape box = new PolygonShape();
		box.setAsBox(width / 2, height / 2);

		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.linearDamping = 25f;
		bodyDef.type = BodyType.DynamicBody;
		body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 1.0f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.filter.categoryBits = Bits.PLAYER;
		fixtureDef.filter.maskBits = (short)(Bits.ENEMY | Bits.OBSTACLE);

		body.createFixture(fixtureDef);

		body.setUserData(new Box2DUserData("player", null));

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		
		setSize(width, height);
		setScaling(Scaling.stretch);
		setAlign(Align.center);

	}

	public void moveUp(float delta) {
		if(body.getPosition().y < 110f * 64f){
		body.setLinearVelocity(temp.set(body.getLinearVelocity().x, 200 * delta));
		}

	}

	public void moveDown(float delta) {
		if(body.getPosition().y > 0){
		body.setLinearVelocity(temp.set(body.getLinearVelocity().x, -200 * delta));
		}
	}

	public void moveRight(float delta) {
		if(body.getPosition().x < 100f * 64f){
		body.setLinearVelocity(temp.set(200 * delta, body.getLinearVelocity().y));
		}
	}

	public void moveLeft(float delta) {
		if(body.getPosition().x > 0){
		body.setLinearVelocity(temp.set(-200 * delta, body.getLinearVelocity().y));
		}
	}

	public Body getBody() {
		return body;
	}

	public static Player create(TextureAtlas atlas, World world, RayHandler rh) {
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for (int i = 0; i < atlas.getRegions().size; i++) {
			System.out.println(atlas.getRegions().get(i).name);
			if (atlas.getRegions().get(i).name.contains("crossbow")) {
				regions.add(atlas.getRegions().get(i));
			}
		}

		return new Player(regions, atlas, world, rh);
	}

}
