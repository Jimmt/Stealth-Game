package nf.co.arcanechicken.stealthgame;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class NPC extends Image {
	private Map<TextureRegion, Drawable> drawables;
	private Body body;
	private Vector2 temp;
	private Array<AtlasRegion> regions;
	private Array<Item> equipped;
	private Array<Item> inventory;
	protected float width, height;
	protected World world;

	public NPC(Array<AtlasRegion> regions, TextureAtlas atlas, World world) {
		super(regions.get(0));

		this.regions = regions;
		this.world = world;

		temp = new Vector2(0, 0);

		drawables = new HashMap<TextureRegion, Drawable>();

		for (AtlasRegion region : regions) {
			drawables.put(region, new TextureRegionDrawable(region));
		}

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
		setSize(width, height);
		setScaling(Scaling.fit);
		setAlign(Align.center);
	}

	
	@Override
	public void act(float delta) {

		width = getDrawable().getMinWidth() / 40f;
		height = getDrawable().getMinHeight() / 40f;
		if(body != null){
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		}
		setSize(width, height);

	}

	public void moveOperate(String regionName) {
		for (int i = 0; i < regions.size; i++) {
			if (regions.get(i).name.equals(regionName)) {
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

		for (int i = 0; i < regions.size; i++) {
			if (regions.get(i).name.equals("char-side")) {
				if (regions.get(i).isFlipX()) {
					regions.get(i).flip(true, false);
				}
				this.setDrawable(drawables.get(regions.get(i)));
			}
		}
		((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(width / 2, height / 2);
	}

	public void moveLeft(float delta) {
		body.setLinearVelocity(temp.set(-200 * delta, body.getLinearVelocity().y));

		for (int i = 0; i < regions.size; i++) {
			if (regions.get(i).name.equals("char-side")) {
				if (!regions.get(i).isFlipX()) {
					regions.get(i).flip(true, false);
				}
				this.setDrawable(drawables.get(regions.get(i)));
			}
		}
		((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(width / 2, height / 2);
	}

}
