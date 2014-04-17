package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class Enemy extends NPC {
	private Body body;
	
	
	public Enemy(Array<AtlasRegion> regions, TextureAtlas atlas, World world) {
		super(regions, atlas, world);
		width = getDrawable().getMinWidth() / 40f;
		height = getDrawable().getMinHeight() / 40f;
		initBody();
	}

	public Body getBody(){
		return body;
	}
	
	
	public void act(float delta){
		super.act(delta);
		
		
	}
	
	public static Enemy create(TextureAtlas atlas, World world) {
		Array<AtlasRegion> regions = new Array<AtlasRegion>();
		for (int i = 0; i < atlas.getRegions().size; i++) {
			System.out.println(atlas.getRegions().get(i).name);
			if (atlas.getRegions().get(i).name.contains("char")) {
				regions.add(atlas.getRegions().get(i));
			}
		}

		return new Enemy(regions, atlas, world);
	}

	
}
