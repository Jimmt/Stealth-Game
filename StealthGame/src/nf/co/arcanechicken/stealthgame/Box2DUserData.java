package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.physics.box2d.Body;

public class Box2DUserData {
	public String type;
	public Enemy enemy;
	
	public Box2DUserData(String type, Enemy e){
		this.type = type;
		this.enemy = e;
	}
}
