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
import com.me.mygdxgame.item.PItem;
import com.me.mygdxgame.item.PointItem;
import com.me.mygdxgame.item.PowerElf;
import com.me.mygdxgame.item.PowerItem;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	private LinkedList<Enemy> enemyBuffer;
	private LinkedList<Bullet> enemyBulletBuffer;
	private LinkedList<Bullet> selfNormalBulletBuffer;
	private LinkedList<Bullet> selfSpecialBulletBuffer;
	private LinkedList<PItem> itemBuffer;
	private AssetManager resources;
	private GameScreen parent;
	private float time;

	public WorldController(World world, AssetManager resources,
			GameScreen parent) {
		this.world = world;
		this.resources = resources;
		this.parent = parent;
		world.setSelf(Self.getInstance(World.CAMERA_WIDTH / 2, 10f, resources,
				"reimu", world));
		self = world.getSelf();
		enemyBuffer = new LinkedList<Enemy>();
		enemyBulletBuffer = new LinkedList<Bullet>();
		selfNormalBulletBuffer = new LinkedList<Bullet>();
		selfSpecialBulletBuffer = new LinkedList<Bullet>();
		itemBuffer = new LinkedList<PItem>();
		world.setBg(resources.get("images/bg1.jpg", Texture.class));
		// addDebugBullets();
		// addDebugEnemies();
		// addDebugItem();
		time = 0;
	}

	private void addDebugBullets() {
		TextureRegion img = new TextureRegion(resources.get(
				"images/bullet2.jpg", Texture.class), 0, 0, 32, 32);
		Bullet b;
		for (int i = 0; i < 10; ++i) {
			b = Bullet.newEnemyBullet((i * 1.5f + 5), 30, 1.2f, 1.2f, 0.4f,
					img, world);
			b.setOrigin(b.getWidth() * 4, b.getHeight() * 4);
			b.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(MathUtils.random(-180, 180), 0.5f)));
			world.addEnemyBullet(b);
		}
	}

	private void addDebugItem() {
		for (int i = 0; i < 100; ++i) {
			world.addItem(PowerItem.newPowerItem(MathUtils.random(0, 40),
					MathUtils.random(36, 48), resources, world));
			world.addItem(PointItem.newPointItem(MathUtils.random(0, 40),
					MathUtils.random(36, 48), resources, world));
		}
	}

	private void addDebugEnemies() {
		Enemy e;
		for (int i = 0; i < 5; ++i) {
			e = PowerElf.newPowerElf(MathUtils.random(0, 40), 48, resources,
					world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(0, -4.8f, 1f)));
			world.addEnemy(e);
		}
		for (int i = 0; i < 5; ++i) {
			e = PowerElf.newPowerElf(-PowerElf.WIDTH, MathUtils.random(36, 48),
					resources, world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(4.8f, 0f, 1f)));
			world.addEnemy(e);
		}
		for (int i = 0; i < 5; ++i) {
			e = PowerElf.newPowerElf(40f, MathUtils.random(36, 48), resources,
					world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(-4.8f, 0f, 1f)));
			world.addEnemy(e);
		}
	}

	public void update(float delta) {
		world.updateTime(delta);
		self.act(delta);
		synchronized (world) {
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
			for (PItem p : world.getItems()) {
				p.act(delta);
			}
			detectCollision();
			detectOutOfWorld();
			recycleUselessBullets();
			recycleUselessEnemies();
			recycleUselessItems();
		}
	}

	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				recycleAllEnemyBullet();
				parent.updateLife(1);
				return;
			}
			if (self.isGraze(b) && !b.isGrazed()) {
				b.setGrazed(true);
				parent.updateGraze(1);
			}
		}
		for (Enemy enemy : world.getEnemies()) {
			for (Bullet b : world.getSelfNormalBullets()) {
				if (b.isHit(enemy)) {
					enemy.removeHp(1);
					if (enemy.getHp() == 0) {
						enemy.dead();
						enemyBuffer.add(enemy);
					}
					selfNormalBulletBuffer.add(b);
				}
			}
			for (Bullet b : world.getSelfSpecialBullets()) {
				if (b.isHit(enemy)) {
					enemy.removeHp(1);
					if (enemy.getHp() == 0) {
						enemy.dead();
						enemyBuffer.add(enemy);
					}
					selfSpecialBulletBuffer.add(b);
				}
			}
			if (self.isHit(enemy)) {
				recycleAllEnemyBullet();
				parent.updateLife(1);
				return;
			}
		}
		for (PItem p : world.getItems()) {
			if (self.itemInRange(p) && p.getTraceTarget() == null) {
				p.setToTrace(self, PItem.TRACE_SPEED);
			} else if (self.isHit(p)) {
				p.hit(self);
				parent.updatePoint(self.getPoint());
				parent.updatePower(self.getPower());
				itemBuffer.add(p);
			}
		}
	}

	private void recycleAllEnemyBullet() {
		for (Bullet b : world.getEnemyBullets()) {
			b.clearActions();
			enemyBulletBuffer.add(b);
		}
	}
	
	private void detectOutOfWorld() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isOutOfWorld() && !enemyBulletBuffer.contains(b)) {
				b.clearActions();
				enemyBulletBuffer.add(b);
			}
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			if (b.isOutOfWorld() && !selfNormalBulletBuffer.contains(b)) {
				selfNormalBulletBuffer.add(b);
			}
		}
		for (Bullet b : world.getSelfSpecialBullets()) {
			if (b.isOutOfWorld() && !selfSpecialBulletBuffer.contains(b)) {
				selfSpecialBulletBuffer.add(b);
			}
		}
		for (Enemy e : world.getEnemies()) {
			if (e.isOutOfWorld() && !enemyBuffer.contains(e)) {
				enemyBuffer.add(e);
			}
		}
		for (PItem p : world.getItems()) {
			if (p.isOutOfWorld() && !itemBuffer.contains(p)) {
				itemBuffer.add(p);
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
		Bullet b;
		while (!enemyBulletBuffer.isEmpty()) {
			b = enemyBulletBuffer.removeFirst();
			world.removeEnemyBullet(b);
			Bullet.recycleEnemyBullet(b);
		}
		while (!selfNormalBulletBuffer.isEmpty()) {
			b = selfNormalBulletBuffer.removeFirst();
			world.removeSelfNormalBullet(b);
			Self.recycleNormalBullet(b);
		}
		while (!selfSpecialBulletBuffer.isEmpty()) {
			b = selfSpecialBulletBuffer.removeFirst();
			world.removeSelfSpecialBullet(b);
			Self.recycleSpecialBullet(b);
		}
	}

	private void recycleUselessItems() {
		PItem p;
		while (!itemBuffer.isEmpty()) {
			p = itemBuffer.removeFirst();
			world.removeItem(p);
			p.recycle();
		}
	}

	private void recycleUselessEnemies() {
		Enemy e;
		while (!enemyBuffer.isEmpty()) {
			e = enemyBuffer.removeFirst();
			world.removeEnemy(e);
			e.recycle();
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
