package com.me.mygdxgame.stage;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.PItem;
import com.me.mygdxgame.item.Self;

public class World {

	public static final float CAMERA_WIDTH = 40f;
	public static final float CAMERA_HEIGHT = 48f;

	private Self self;
	private LinkedList<Bullet> selfNormalBullets = new LinkedList<Bullet>();
	private LinkedList<Bullet> selfSpecialBullets = new LinkedList<Bullet>();
	private LinkedList<Bullet> enemyBullets = new LinkedList<Bullet>();
	private LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	private LinkedList<PItem> items = new LinkedList<PItem>();
	private Camera camera;
	private Texture bg;
	private float time;

	public World() {
		camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.position.set(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, 0);
		camera.update();
	}

	public Self getSelf() {
		return self;
	}

	public void setSelf(Self self) {
		this.self = self;
	}

	public Texture getBg() {
		return bg;
	}

	public void setBg(Texture bg) {
		this.bg = bg;
	}

	public void addSelfNormalBullet(Bullet b) {
		selfNormalBullets.add(b);
	}

	public void addSelfSpecialBullet(Bullet b) {
		selfSpecialBullets.add(b);
	}
	
	public void addEnemyBullet(Bullet b) {
		enemyBullets.add(b);
	}
	
	public void addItem(PItem p) {
		items.add(p);
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public void removeSelfNormalBullet(Bullet b) {
		selfNormalBullets.remove(b);
	}
	
	public void removeSelfSpecialBullet(Bullet b) {
		selfSpecialBullets.remove(b);
	}

	public void removeEnemyBullet(Bullet b) {
		enemyBullets.remove(b);
	}
	
	public void removeItem(PItem p) {
		items.remove(p);
	}
	
	public void removeEnemy(Enemy e) {
		enemies.remove(e);
	}

	public LinkedList<Bullet> getSelfNormalBullets() {
		return selfNormalBullets;
	}

	public LinkedList<Bullet> getSelfSpecialBullets() {
		return selfSpecialBullets;
	}
	
	public LinkedList<Bullet> getEnemyBullets() {
		return enemyBullets;
	}

	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}

	public LinkedList<PItem> getItems() {
		return items;
	}
	
	public Camera getCamera() {
		return camera;
	}

	public void updateTime(float delta) {
		time += delta;
	}

	public float getTime() {
		return time;
	}

}
