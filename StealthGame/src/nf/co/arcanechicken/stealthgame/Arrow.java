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

public class Arrow extends Image{
	private Body body;
	private float width, height;
	
	private Arrow(AtlasRegion region, World world, float x, float y, float angle){
		super(region);
		
		width = getDrawable().getMinWidth() * Constants.scale;
		height = getDrawable().getMinHeight() * Constants.scale;
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(width / 2, height / 2);
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		bd.bullet = true;
		body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.shape = box;
		fd.friction = 100000.0f;
		fd.filter.categoryBits = Bits.ARROW;
		fd.filter.maskBits = Bits.OBSTACLE;
		body.createFixture(fd);
		setSize(width, height);
		setScaling(Scaling.stretch);
		angle += 3 * MathUtils.PI / 2;
		body.setTransform(x, y, angle);
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		setRotation(MathUtils.radiansToDegrees * angle);
		setOrigin(width / 2, height / 2);
		
	}
	
	public Body getBody(){
		return body;
	}
	
	public static Arrow create(TextureAtlas atlas, World world, float x, float y, float angle, String name){
		AtlasRegion region = null;
		for(int i = 0; i < atlas.getRegions().size; i++){
			if(atlas.getRegions().get(i).name.contains(name)){
				region = atlas.getRegions().get(i);
			} 
		}
		return new Arrow(region, world, x, y, angle);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		setOrigin(width / 2, height / 2);
		
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		setRotation(MathUtils.radiansToDegrees * body.getTransform().getRotation());
	}
}
