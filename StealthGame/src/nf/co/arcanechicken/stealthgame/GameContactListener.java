package nf.co.arcanechicken.stealthgame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {
	Enemy e;

	public GameContactListener(Enemy e) {
		super();
		this.e = e;
	}

	public boolean compareEquality(String a, String b, String da, String db) {
		return (a.equals(da) && b.equals(db)) || (a.equals(db) && b.equals(da));
	}

	@Override
	public void beginContact(Contact contact) {

		Box2DUserData dataA;
		Box2DUserData dataB;
		Fixture a = contact.getFixtureA();
		Body bodyA = a.getBody();
		Fixture b = contact.getFixtureB();
		Body bodyB = b.getBody();
		if (bodyA.getUserData() != null && bodyB.getUserData() != null) {
			dataA = (Box2DUserData) bodyA.getUserData();
			dataB = (Box2DUserData) bodyB.getUserData();

			if (compareEquality(dataA.type, dataB.type, "enemy", "obstacle")) {
				if (dataA.type.equals("enemy")) {
					Vector2 distance = bodyB.getPosition().sub(bodyA.getPosition()).scl(-1);
					bodyA.applyForceToCenter(distance, true);
				} else {
					Vector2 distance = bodyB.getPosition().sub(bodyA.getPosition()).nor().scl(1000);
					bodyB.applyForceToCenter(distance, true);
				}
			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		/*
		 * String dataA;
		 * String dataB;
		 * Fixture a = contact.getFixtureA();
		 * Body bodyA = a.getBody();
		 * Fixture b = contact.getFixtureB();
		 * Body bodyB = b.getBody();
		 * if (bodyA.getUserData() != null && bodyB.getUserData() != null) {
		 * dataA = (String) bodyA.getUserData();
		 * dataB = (String) bodyB.getUserData();
		 * if ((dataA.equals("foot") && dataB.equals("ground")) ||
		 * (dataA.equals("ground") && dataB.equals("foot"))) {
		 * player.canJump = false;
		 * }
		 * }
		 */

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
