package nf.co.arcanechicken.stealthgame;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Box2DMapParser {

	public Box2DMapParser(FileHandle file, World world, Map map) {
		String fileS = file.readString();
		String[] lines = null;

		lines = fileS.split("\\r?\\n");

		TiledMapTileLayer tmtl = (TiledMapTileLayer) map.getMap().getLayers().get(0);
		
		HashMap<Integer, ArrayList<Line2D>> tileCollisionJoints = new HashMap<Integer, ArrayList<Line2D>>();

		for (int n = 0; n < lines.length; n++) {
			String cols[] = lines[n].split(" ");
			int tileNo = Integer.parseInt(cols[0]);

			ArrayList<Line2D> tmp = new ArrayList<Line2D>();

			for (int m = 1; m < cols.length; m++) {
				String coords[] = cols[m].split(",");

				String start[] = coords[0].split("x");
				for(int d = 0; d < start.length; d++){
				}
				String end[] = coords[1].split("x");
				for(int e = 0; e < end.length; e++){
				}
				tmp.add(new Line2D.Float(Integer.parseInt(start[0]), 64 - Integer.parseInt(start[1]),
						Integer.parseInt(end[0]), 64 - Integer.parseInt(end[1])));
			}

			tileCollisionJoints.put(Integer.valueOf(tileNo), tmp);

		}
		Array<Line2D> lines2d = new Array<Line2D>();
		for (int i = 0; i < map.getMap().getProperties().get("width", Integer.class); i++) {
			for (int j = 0; j < map.getMap().getProperties().get("height", Integer.class); j++) {
				Cell cell = tmtl.getCell(i, j);
				try {
					int tileType = cell.getTile().getId();
					

					for (int k = 0; k < tileCollisionJoints.get(tileType).size(); k++) {
						Line2D line = new Line2D.Float(i * 64f
								+ (float) tileCollisionJoints.get(tileType).get(k).getX1(), j * 64f
								+ (float) tileCollisionJoints.get(tileType).get(k).getY1(), i * 64f
								+ (float) tileCollisionJoints.get(tileType).get(k).getX2(), j * 64f
								+ (float) tileCollisionJoints.get(tileType).get(k).getY2());
						lines2d.add(line);
						if(tileType == 5 || tileType == 10){
						System.err.println(line.getP1() + " " + line.getP2());
						}
					}

					

				} catch (NullPointerException npe) {

				}

			}

		}

		
		for (int i = 0; i < lines2d.size; i++) {
			EdgeShape shape = new EdgeShape();
			lines2d.get(i).setLine((float) lines2d.get(i).getX1() / 80f, (float) lines2d.get(i).getY1() / 80f,
					(float) lines2d.get(i).getX2() / 80f, (float) lines2d.get(i).getY2() / 80f);
			shape.set((float) lines2d.get(i).getX1(), (float) lines2d.get(i).getY1(),
					(float) lines2d.get(i).getX2(), (float) lines2d.get(i).getY2());
			System.out.println(lines2d.get(i).getP1() + " " + lines2d.get(i).getP2());
		
		BodyDef edgeDef = new BodyDef();
		edgeDef.type = BodyType.StaticBody;
		Body edge = world.createBody(edgeDef);
		FixtureDef edgeFixtureDef = new FixtureDef();
		edgeFixtureDef.shape = shape;
		edge.createFixture(edgeFixtureDef);
		shape.dispose();
		}
	}
}
