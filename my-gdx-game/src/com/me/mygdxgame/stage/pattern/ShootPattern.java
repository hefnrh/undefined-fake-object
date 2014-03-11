package com.me.mygdxgame.stage.pattern;

import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;

public abstract class ShootPattern {

	protected float time;
	protected int count;
	protected int magazine;
	protected float rps;
	protected Enemy parent;
	protected Bullet example;
	protected float speed;
	
	protected ShootPattern(int magazine, float delay, float rps, Enemy parent, Bullet b, float speed) {
		time = -delay;
		count = 0;
		this.magazine = magazine;
		this.rps = rps;
		this.parent = parent;
		example = b;
		this.speed = speed;
	}

	public abstract void shoot();
	
	public void shootWithCheck(float delta) {
		time += delta;
		if (time * rps < 1) return;
		time -= 1f / rps;
		++count;
		if (count > magazine) return;
		shoot();
	}
}
