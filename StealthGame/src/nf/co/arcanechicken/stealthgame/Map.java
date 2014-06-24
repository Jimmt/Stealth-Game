package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Actor {
	private TiledMap map;
	private TmxMapLoader tmxL;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera oc;
	private boolean[][] collisions;

	public Map(String path, OrthographicCamera oc) {
		tmxL = new TmxMapLoader();
		map = tmxL.load(path);

		this.oc = oc;

		renderer = new OrthogonalTiledMapRenderer(map, Constants.scale);
		renderer.setMap(map);

		collisions = new boolean[map.getProperties().get("width", Integer.class)][map
				.getProperties().get("height", Integer.class)];

	}

	public boolean[][] getCollisions() {
		return collisions;
	}

	public TiledMap getMap() {
		return map;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		renderer.setView(oc);
		batch.end();
		renderer.render();
		batch.begin();

	}

}
