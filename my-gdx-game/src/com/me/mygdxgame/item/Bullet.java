package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class Bullet extends Item {
	
	public static final LinkedList<Bullet> uselessEnemyBullet = new LinkedList<Bullet>();
	
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
			b.init(x, y, width, height, checkRadius, img);
			return b;
		}
	}
	
	public synchronized static void recycleEnemyBullet(Bullet b) {
		b.clearActions();
		uselessEnemyBullet.add(b);
	}
	
}
