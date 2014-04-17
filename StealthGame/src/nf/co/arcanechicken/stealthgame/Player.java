package nf.co.arcanechicken.stealthgame;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class Player extends Image {
	private Map<TextureRegion, Drawable> drawables;
	private Array<Item> inventory;
	private Array<Item> equipped;
	private TextureAtlas atlas;
	private Array<AtlasRegion> regions;
	private World world;
	private Body body;
	public Vector2 temp, centerPosition, mouse, dir;
	private float width = 1, height = 3f, ry, lastAttackTime = 999f, attackCap = 0.5f; //width/height should be same ratio as original pic
	private float dx, dy, angle;
	
	private Texture walkSheet;
	private Animation walkAnimation;
	private float walkAnimationStateTime; // do animation later, based on spine

	public Player(Array<AtlasRegion> regions, TextureAtlas atlas, World world) {
		super(regions.get(0));

		this.atlas = atlas;
		this.world = world;



		this.regions = regions;
		
		drawables = new HashMap<TextureRegion, Drawable>();

		walkAnimation = new Animation(0.2f, regions);

		for (AtlasRegion region : regions) {
			drawables.put(region, new TextureRegionDrawable(region));
		}

		equipped = new Array<Item>();
		temp = new Vector2(0, 0);
		initBody();

	}

	public void checkMouseRotation() {
		
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		dx = Gdx.input.getX();
		dy = Constants.HEIGHT - Gdx.input.getY();

		mouse = new Vector2(dx, dy);

		dir = mouse.sub(centerPosition);
		angle = dir.angle();
	}
	

	public void act(float delta) {
		super.act(delta);

		checkMouseRotation();
		
		width = getDrawable().getMinWidth() / 40f;
		height = getDrawable().getMinHeight() / 40f;
		
		getStage().getCamera().position.set(body.getPosition(), 0);

		if (Gdx.input.isButtonPressed(Buttons.LEFT) && lastAttackTime > attackCap) {
			attack();
			lastAttackTime = 0f;
		} else {
			lastAttackTime += delta;
		}

		setOrigin(width / 2, height / 2);

		body.setTransform(body.getPosition().x, body.getPosition().y, 0);

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

	}

	public void attack() {
		for(int i = 0; i < equipped.size; i++){
			if(equipped.get(i) instanceof Weapon){
				
			}
		}
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
		bodyDef.linearDamping = 25.0f;
		bodyDef.type = BodyType.DynamicBody;
		body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 10.0f;
		fixtureDef.restitution = 0.0f;

		body.createFixture(fixtureDef);

		body.setUserData("body");

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
//		setSize(width, height);
		setSize(this.getDrawable().getMinWidth() / 40f, this.getDrawable().getMinHeight() / 40f);
		setScaling(Scaling.fit);
		setAlign(Align.center);
	}
	
	public void moveOperate(String regionName){
		for(int i = 0; i < regions.size; i++){
			if(regions.get(i).name.equals(regionName)){
				this.setDrawable(drawables.get(regions.get(i)));
			}
		}
		((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(width / 2, height / 2);
	}

	public void moveUp(float delta) {
		body.setLinearVelocity(temp.set(body.getLinearVelocity().x, 200 * delta));
		moveOperate("char-back");

	}

	public void moveDown(float delta) {
		body.setLinearVelocity(temp.set(body.getLinearVelocity().x, -200 * delta));
		moveOperate("char-front");

	}

	public void moveRight(float delta) {
		body.setLinearVelocity(temp.set(200 * delta, body.getLinearVelocity().y));

		for(int i = 0; i < regions.size; i++){
			if(regions.get(i).name.equals("char-side")){
				if(regions.get(i).isFlipX()){
					regions.get(i).flip(true, false);
				}
				this.setDrawable(drawables.get(regions.get(i)));
			}
		}
		((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(width / 2, height / 2);
	}

	public void moveLeft(float delta) {
		body.setLinearVelocity(temp.set(-200 * delta, body.getLinearVelocity().y));

		for(int i = 0; i < regions.size; i++){
			if(regions.get(i).name.equals("char-side")){
				if(!regions.get(i).isFlipX()){
					regions.get(i).flip(true, false);
				}
				this.setDrawable(drawables.get(regions.get(i)));
			}
		}
		((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(width / 2, height / 2);
	}

	public Body getBody() {
		return body;
	}

	public static Player create(TextureAtlas atlas, World world) {
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for (int i = 0; i < atlas.getRegions().size; i++) {
			System.out.println(atlas.getRegions().get(i).name);
			if (atlas.getRegions().get(i).name.contains("char")) {
				regions.add(atlas.getRegions().get(i));
			}
		}
		System.out.println(regions.size);

		return new Player(regions, atlas, world);
	}

}
