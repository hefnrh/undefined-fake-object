package com.me.mygdxgame.stage.pattern;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;

public class AllRangeShoot extends ShootPattern {

	private int n;
	private float degPerBullet;

	public AllRangeShoot(int magazine, float delay, float rps, Enemy parent,
			Bullet b, float speed, int n) {
		super(magazine, delay, rps, parent, b, speed);
		this.n = n;
		degPerBullet = 360f / n;
	}

	@Override
	public void shoot() {
		Bullet b;
		float deg;
		for (int i = 0; i < n; ++i) {
			deg = i * degPerBullet;
			b = getBullet();
			b.setOrigin(b.getWidth() / 2, b.getHeight() / 2);
			b.rotate(deg);
			b.setPosition(parent.getCheckX() - b.getWidth() / 2,
					parent.getCheckY() - b.getHeight() / 2);
			b.addAction(Actions.repeat(
					RepeatAction.FOREVER,
					Actions.moveBy(speed * MathUtils.cosDeg(deg), speed
							* MathUtils.sinDeg(deg))));
			parent.getWorld().addEnemyBullet(b);
		}
	}

}
