package com.me.mygdxgame.stage;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Item;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	private List<Bullet> uselessSelfBullet;
	private List<Bullet> uselessEnemyBullet;
	
	public WorldController(World world) {
		this.world = world;
		world.setSelf(Self
				.getInstance(World.CAMERA_WIDTH / 2, 10f));
		self = world.getSelf();
		uselessSelfBullet = new LinkedList<Bullet>();
		uselessEnemyBullet = new LinkedList<Bullet>();
		addDebugBullets();
	}

	private void addDebugBullets() {
		TextureRegion img = new TextureRegion(new Texture(Gdx.files.internal("images/bullet2.jpg")), 0, 0, 32, 32);
		Bullet b;
		for (int i = 0; i < 10; ++i) {
			b = new Bullet((i * 1.5f + 5), 30, 1.2f, 1.2f, 0.4f, img);
			b.setOrigin(b.getWidth() * 4, b.getHeight() * 4);
			b.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(MathUtils.random(-180, 180), 0.5f)));
			world.addEnemyBullet(b);
		}
		for (int i = 0; i < 10; ++i) {
			// TODO add self bullet
		}
	}

	public void update(float delta) {
		world.getSelf().act(delta);
		for (Bullet b : world.getEnemyBullets()) {
			b.act(delta);
		}
		for (Bullet b : world.getSelfBullets()) {
			b.act(delta);
		}
		// TODO update enemies
		detectCollision();
		detectOutOfWorld();
		recycleUselessBullets();
	}
	
	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				// TODO do sth
				uselessEnemyBullet.add(b);
				self.hitBy(b);
				break;
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
	
	private void recycleUselessBullets() {
		for (Bullet b: uselessEnemyBullet) {
			world.removeEnemyBullet(b);
		}
		for (Bullet b: uselessSelfBullet) {
			world.removeSelfBullet(b);
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
	
	public void setSelfSlow(boolean b) {
		self.setLowSpeed(b);
	}
	
	public World getWorld() {
		return world;
	}
}
