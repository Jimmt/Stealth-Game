package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Heuristic {
	public float getCost(TiledMap map, int x, int y, int tx, int ty) {		
		float dx = tx - x;
		float dy = ty - y;
		
		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		return result;
	}
}
