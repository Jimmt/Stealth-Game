package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class Bullet extends Image{
	private Body body;
	private float width = 0.2f, height = 0.7f;
	
	private Bullet(AtlasRegion region, World world, float x, float y, float angle){
		super(region);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(width, height);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.shape = box;
		fd.isSensor = true;
		body.createFixture(fd);
		setSize(width, height);
		setScaling(Scaling.stretch);
		body.setTransform(x - width, y - height, angle);
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		setRotation(MathUtils.radiansToDegrees * angle);
		setOrigin(width / 2, height / 2);
		
	}
	
	public Body getBody(){
		return body;
	}
	
	public static Bullet create(TextureAtlas atlas, World world, float x, float y, float angle, String name){
		AtlasRegion region = null;
		for(int i = 0; i < atlas.getRegions().size; i++){
			if(atlas.getRegions().get(i).name.contains(name)){
				region = atlas.getRegions().get(i);
			} 
		}
		return new Bullet(region, world, x, y, angle);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		setOrigin(width / 2, height / 2);
		
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		setRotation(MathUtils.radiansToDegrees * body.getTransform().getRotation());
	}
}
