package com.me.mygdxgame.stage;

import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.CircleBullet;
import com.me.mygdxgame.item.Item;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	
	public WorldController(World world) {
		this.world = world;
		world.setSelf(Self
				.getInstance(new Vector2(World.CAMERA_WIDTH / 2, 10f)));
		self = world.getSelf();
		addDebugBullets();
	}

	private void addDebugBullets() {
		for (int i = 0; i < 10; ++i) {
			world.addEnemyBullet(new CircleBullet(1, 2, false, (i * 1.5f + 5), 30,
					0.6f));
		}
		for (int i = 0; i < 10; ++i) {
			world.addSelfBullet(new CircleBullet(2, 1, true, (i * 1.5f + 5), 20,
					0.6f));
		}
	}

	public void update(float delta) {
		world.getSelf().update(delta);
		for (Bullet b : world.getEnemyBullets()) {
			b.update(delta);
		}
		for (Bullet b : world.getSelfBullets()) {
			b.update(delta);
		}
		// TODO update enemies
		detectCollision();
		detectOutOfWorld();
	}
	
	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				// TODO do sth
			}
		}
		for (Bullet b : world.getSelfBullets()) {
			for (Item enemy : world.getEnemies()) {
				if (b.isHit(enemy)) {
					// TODO do sth
				}
			}
		}
	}
	
	private void detectOutOfWorld() {
		for (Bullet b : world.getEnemyBullets()) {
			// TODO do sth
		}
		for (Bullet b : world.getSelfBullets()) {
			// TODO do sth
		}
	}
	
	public void setSelfVerticalDirection(int i) {
		self.setVerticalDirection(i);
	}
	public void setSelfHorizentalDirection(int i) {
		self.setHorizentalDirection(i);
	}
	
	public void setSelfMoving(boolean b) {
		self.setMoving(b);
	}
	
	public World getWorld() {
		return world;
	}
}
