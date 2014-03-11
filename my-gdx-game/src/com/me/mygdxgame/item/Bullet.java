package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class Bullet extends Item {
	
	public static final LinkedList<Bullet> uselessEnemyBullet;
	
	static {
		uselessEnemyBullet = new LinkedList<Bullet>();
		for (int i = 0; i < 1000; ++i) {
			uselessEnemyBullet.add(new Bullet(0, 0, 0, 0, 0, null, null));
		}
	}
	
	public Bullet(float x, float y, float width, float height,
			float checkRadius, TextureRegion img, World world) {
		super(x, y, width, height, checkRadius, img, world);
	}
	
	public synchronized static Bullet newEnemyBullet(float x, float y, float width, float height,
			float checkRadius, TextureRegion img, World world) {
		if (uselessEnemyBullet.isEmpty()) {
			return new Bullet(x, y, width, height, checkRadius, img, world);
		} else {
			Bullet b = uselessEnemyBullet.poll();
			b.world = world;
			b.init(x, y, width, height, checkRadius, img);
			return b;
		}
	}
	
	public synchronized static void recycleEnemyBullet(Bullet b) {
		b.clearActions();
		uselessEnemyBullet.add(b);
	}
	
}
