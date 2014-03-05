package com.me.mygdxgame.stage;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.GameScreen;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	private LinkedList<Bullet> uselessEnemyBullet;
	private AssetManager resources;
	private GameScreen parent;
	private float normalTime;
	private float specialTime;

	public WorldController(World world, AssetManager resources,
			GameScreen parent) {
		this.world = world;
		this.resources = resources;
		this.parent = parent;
		world.setSelf(Self.getInstance(World.CAMERA_WIDTH / 2, 10f, resources,
				"reimu", world));
		self = world.getSelf();
		uselessEnemyBullet = new LinkedList<Bullet>();
		world.setBg(resources.get("images/bg1.jpg", Texture.class));
		normalTime = 0;
		specialTime = 0;
//		addDebugBullets();
	}

	private void addDebugBullets() {
		TextureRegion img = new TextureRegion(resources.get(
				"images/bullet2.jpg", Texture.class), 0, 0, 32, 32);
		Bullet b;
		for (int i = 0; i < 10; ++i) {
			b = newEnemyBullet((i * 1.5f + 5), 30, 1.2f, 1.2f, 0.4f, img);
			b.setOrigin(b.getWidth() * 4, b.getHeight() * 4);
			b.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(MathUtils.random(-180, 180), 0.5f)));
			world.addEnemyBullet(b);
		}
	}

	public void update(float delta) {
		normalTime += delta;
		specialTime += delta;
		world.updateTime(delta);
		world.getSelf().act(delta);
		for (Bullet b : world.getEnemyBullets()) {
			b.act(delta);
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			b.act(delta);
		}
		for (Bullet b : world.getSelfSpecialBullets()) {
			b.act(delta);
		}
		for (Enemy e : world.getEnemies()) {
			e.act(delta);
		}
		detectCollision();
		detectOutOfWorld();
		recycleUselessBullets();
		// TODO recycle enemies
		selfNormalShoot();
		selfSpecialShoot();
	}

	private void selfNormalShoot() {
		float tmp = normalTime * 15f;
		if (tmp < 1)
			return;
		normalTime -= 1f / 15f;
		Bullet myBullet;
		// self bullet will be rotated before shooting, so set X + height
		myBullet = self.newNormalBullet(
				self.getX() + self.getNormalBulletHeight(), self.getY()
						+ Self.IMG_HEIGHT);
		world.addSelfNormalBullet(myBullet);
		myBullet = self.newNormalBullet(self.getX() + Self.IMG_WIDTH,
				self.getY() + Self.IMG_HEIGHT);
		world.addSelfNormalBullet(myBullet);
	}
	
	private void selfSpecialShoot() {
		float tmp = specialTime * 4f;
		if (tmp < 1)
			return;
		specialTime -= 0.25f;
		Bullet myBullet;
		for (int i = 0, j = self.getPower() / 100; i < j; ++i) {
			myBullet = self.newSpecialBullet(
					self.getSupportCenterX(i) - self.getSpecialBulletHeight()
							/ 2f, self.getSupportCenterY(i));
			world.addSelfSpecialBullet(myBullet);
		}
	}

	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				// TODO do sth
				b.clearActions();
				b.setInUse(false);
				uselessEnemyBullet.add(b);
				self.hitBy(b);
				break;
			}
		}
		for (Enemy enemy : world.getEnemies()) {
			for (Bullet b : world.getSelfNormalBullets()) {
				if (b.isHit(enemy)) {
					// TODO do sth
					self.recycleNormalBullet(b);
					enemy.hitBy(b);
				}
			}
			for (Bullet b : world.getSelfSpecialBullets()) {
				if (b.isHit(enemy)) {
					// TODO do sth
					self.recycleSpecialBullet(b);
					enemy.hitBy(b);
				}
			}
		}
	}

	private void detectOutOfWorld() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isOutOfWorld()) {
				b.clearActions();
				b.setInUse(false);
				uselessEnemyBullet.add(b);
			}
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			if (b.isOutOfWorld()) {
				self.recycleNormalBullet(b);
			}
		}
		for (Bullet b : world.getSelfSpecialBullets()) {
			if (b.isOutOfWorld()) {
				self.recycleSpecialBullet(b);
			}
		}
		if (self.getX() < 0) {
			self.setX(0);
		} else if (self.getX() + Self.IMG_WIDTH > World.CAMERA_WIDTH) {
			self.setX(World.CAMERA_WIDTH - Self.IMG_WIDTH);
		}
		if (self.getY() < 0) {
			self.setY(0);
		} else if (self.getY() + Self.IMG_HEIGHT > World.CAMERA_HEIGHT) {
			self.setY(World.CAMERA_HEIGHT - Self.IMG_HEIGHT);
		}
	}

	private void recycleUselessBullets() {
		for (Bullet b : uselessEnemyBullet) {
			world.removeEnemyBullet(b);
		}
		for (Bullet b : self.getUselessNormalBullet()) {
			world.removeSelfNormalBullet(b);
		}
		for (Bullet b : self.getUselessSpecialBullet()) {
			world.removeSelfSpecialBullet(b);
		}
	}

	public Bullet newEnemyBullet(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		if (uselessEnemyBullet.isEmpty()) {
			return new Bullet(x, y, width, height, checkRadius, img, world);
		} else {
			Bullet b = uselessEnemyBullet.removeFirst();
			b.init(x, y, width, height, checkRadius, img);
			return b;
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
