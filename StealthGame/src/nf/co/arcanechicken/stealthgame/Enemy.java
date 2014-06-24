package nf.co.arcanechicken.stealthgame;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class Enemy extends Image {
	private Map<TextureRegion, Drawable> drawables;
	private Body body;
	private Vector2 loc, dir;
	private float angle;
	private Array<AtlasRegion> regions;
	private Array<Item> equipped;
	private Array<Item> inventory;
	protected float width, height;
	protected World world;

	public Enemy(Array<AtlasRegion> regions, TextureAtlas atlas, World world) {
		super(regions.get(0));
		width = getDrawable().getMinWidth() * Constants.scale * 2;
		height = getDrawable().getMinHeight() * Constants.scale * 2;
		this.world = world;
		initBody();
	}

	public Body getBody() {
		return body;
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
		fixtureDef.filter.categoryBits = Bits.ENEMY;
		fixtureDef.filter.maskBits = (short) (Bits.PLAYER | Bits.ENEMY | Bits.OBSTACLE);

		body.createFixture(fixtureDef);

		body.setUserData(new Box2DUserData("enemy", this));

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		setSize(width, height);
		setScaling(Scaling.fit);
		setAlign(Align.center);
	}

	public void setInitialPosition(float x, float y) {
		body.setTransform(x, y, 0.0f);

	}
	
	public void rotateTowards(Vector2 vec){
		rotateTowards(vec.x, vec.y);
	}

	public void rotateTowards(float x, float y) {

		loc = new Vector2(x, y);

		dir = loc.sub(getBody().getPosition());
		
		angle = dir.angle();

	}

	public void act(float delta) {
		super.act(delta);
		
		setOrigin(width / 2, height / 2);
		
		body.setTransform(body.getTransform().getPosition().x, body.getTransform().getPosition().y, MathUtils.degreesToRadians * angle - MathUtils.PI / 2);
		
		setRotation(body.getTransform().getRotation() * MathUtils.radiansToDegrees);
		
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		
		

	}

	public static Enemy create(TextureAtlas atlas, World world) {
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for (int i = 0; i < atlas.getRegions().size; i++) {
			System.out.println(atlas.getRegions().get(i).name);
			if (atlas.getRegions().get(i).name.contains("enemy")) {
				regions.add(atlas.getRegions().get(i));
			}
		}

		return new Enemy(regions, atlas, world);
	}

}
