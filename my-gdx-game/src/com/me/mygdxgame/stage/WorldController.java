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
import com.me.mygdxgame.item.PowerItem;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	private LinkedList<Enemy> uselessEnemies;
	private AssetManager resources;
	private GameScreen parent;

	public WorldController(World world, AssetManager resources,
			GameScreen parent) {
		this.world = world;
		this.resources = resources;
		this.parent = parent;
		world.setSelf(Self.getInstance(World.CAMERA_WIDTH / 2, 10f, resources,
				"reimu", world));
		self = world.getSelf();
		uselessEnemies = new LinkedList<Enemy>();
		world.setBg(resources.get("images/bg1.jpg", Texture.class));
		// addDebugBullets();
		addDebugEnemies();
		addDebugItem();
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
			world.addPowerItem(PowerItem.newPowerItem(MathUtils.random(0, 40),
					MathUtils.random(36, 48), resources, world));
		}
		for (int i = 0; i < 100; ++i) {
			world.addPointItem(PointItem.newPointItem(MathUtils.random(0, 40),
					MathUtils.random(36, 48), resources, world));
		}
	}

	private void addDebugEnemies() {
		// TODO
	}

	public void update(float delta) {
		world.updateTime(delta);
		self.act(delta);
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
		for (PowerItem p : world.getPowerItems()) {
			p.act(delta);
		}
		for (PointItem p : world.getPointItems()) {
			p.act(delta);
		}
		detectCollision();
		detectOutOfWorld();
		recycleUselessBullets();
		recycleUselessEnemies();
		recycleUselessItems();
	}

	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				// TODO do sth
				b.clearActions();
				b.setInUse(false);
				Bullet.uselessEnemyBullet.add(b);
				break;
			}
		}
		for (Enemy enemy : world.getEnemies()) {
			for (Bullet b : world.getSelfNormalBullets()) {
				if (b.isHit(enemy)) {
					// TODO do sth
					Self.recycleNormalBullet(b);
				}
			}
			for (Bullet b : world.getSelfSpecialBullets()) {
				if (b.isHit(enemy)) {
					// TODO do sth
					Self.recycleSpecialBullet(b);
				}
			}
		}
		for (PowerItem p : world.getPowerItems()) {
			if (self.itemInRange(p) && p.getTraceTarget() == null) {
				p.setToTrace(self, PItem.TRACE_SPEED);
			} else if (self.isHit(p)) {
				p.hit(self);
				parent.updatePower(self.getPower());
				p.recycle();
			}
		}
		for (PointItem p : world.getPointItems()) {
			if (self.itemInRange(p) && p.getTraceTarget() == null) {
				p.setToTrace(self, PItem.TRACE_SPEED);
			} else if (self.isHit(p)) {
				p.hit(self);
				parent.updatePoint(self.getPoint());
				p.recycle();
			}
		}
	}

	private void detectOutOfWorld() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isOutOfWorld()) {
				b.clearActions();
				Bullet.uselessEnemyBullet.add(b);
			}
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			if (b.isOutOfWorld()) {
				Self.recycleNormalBullet(b);
			}
		}
		for (Bullet b : world.getSelfSpecialBullets()) {
			if (b.isOutOfWorld()) {
				Self.recycleSpecialBullet(b);
			}
		}
		for (Enemy e : world.getEnemies()) {
			if (e.isOutOfWorld()) {
				e.clearActions();
				e.setInUse(false);
				uselessEnemies.add(e);
			}
		}
		for (PowerItem p : world.getPowerItems()) {
			if (p.isOutOfWorld()) {
				p.recycle();
			}
		}
		for (PointItem p : world.getPointItems()) {
			if (p.isOutOfWorld()) {
				p.recycle();
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
		for (Bullet b : Bullet.uselessEnemyBullet) {
			world.removeEnemyBullet(b);
		}
		for (Bullet b : Self.uselessNormalBullet) {
			world.removeSelfNormalBullet(b);
		}
		for (Bullet b : Self.uselessSpecialBullet) {
			world.removeSelfSpecialBullet(b);
		}
	}

	private void recycleUselessItems() {
		for (PowerItem p : PowerItem.uselessPowerItem) {
			world.removePowerItem(p);
		}
		for (PointItem p : PointItem.uselessPointItem) {
			world.removePointItem(p);
		}
	}

	private void recycleUselessEnemies() {
		for (Enemy e : uselessEnemies) {
			world.removeEnemy(e);
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
