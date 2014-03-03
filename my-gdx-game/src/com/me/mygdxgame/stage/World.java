package com.me.mygdxgame.stage;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.Self;

public class World {
	
	public static final float CAMERA_WIDTH = 40f;
	public static final float CAMERA_HEIGHT = 48f;
	
	private Self self;
	private List<Bullet> selfBullets = new LinkedList<Bullet>();
	private List<Bullet> enemyBullets = new LinkedList<Bullet>();
	private List<Enemy> enemies = new LinkedList<Enemy>();
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
	
	public void addSelfBullet(Bullet b) {
		selfBullets.add(b);
	}
	
	public void addEnemyBullet(Bullet b) {
		enemyBullets.add(b);
	}
	
	public void removeSelfBullet(Bullet b) {
		selfBullets.remove(b);
	}
	
	public void removeEnemyBullet(Bullet b) {
		enemyBullets.remove(b);
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public void romoveEnemy(Enemy e) {
		enemies.remove(e);
	}
	
	public List<Bullet> getSelfBullets() {
		return selfBullets;
	}
	
	public List<Bullet> getEnemyBullets() {
		return enemyBullets;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
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
