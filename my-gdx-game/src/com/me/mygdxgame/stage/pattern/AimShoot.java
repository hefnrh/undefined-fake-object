package com.me.mygdxgame.stage.pattern;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.Self;

public class AimShoot extends ShootPattern {

	private int n;
	private float degreeRange;
	private float degreePerBullet;

	public AimShoot(int magazine, float delay, float rps, Enemy parent,
			Bullet b, int n, float degreeRange, float speed) {
		super(magazine, delay, rps, parent, b, speed);
		this.n = n;
		if (n == 1) {
			this.degreeRange = 0;
			degreePerBullet = 0;
		} else {
			this.degreeRange = degreeRange;
			degreePerBullet = degreeRange / (n - 1);
		}
	}

	@Override
	public void shoot() {
		Bullet b;
		float startDeg = getDirectionDegree() - degreeRange / 2f;
		float deg;
		for (int i = 0; i < n; ++i) {
			deg = startDeg + i * degreePerBullet;
			b = getBullet();
			b.setOrigin(b.getWidth() / 2, b.getHeight() / 2);
			b.rotate(deg);
			b.addAction(Actions.repeat(
					RepeatAction.FOREVER,
					Actions.moveBy(speed * MathUtils.cosDeg(deg), speed
							* MathUtils.sinDeg(deg))));
			b.setPosition(parent.getCheckX() - b.getWidth() / 2,
					parent.getCheckY() - b.getHeight() / 2);
			parent.getWorld().addEnemyBullet(b);
		}
	}

	private float getDirectionDegree() {
		Self s = example.getWorld().getSelf();
		float dx = s.getCheckX() - parent.getCheckX(), dy = s.getCheckY()
				- parent.getCheckY();
		float d = (float) Math.sqrt(dx * dx + dy * dy);
		float cos = dx / d, sin = dy / d;
		if (sin >= 0) {
			return (float) Math.toDegrees(Math.acos(cos));
		} else if (cos >= 0) {
			return (float) Math.toDegrees(Math.asin(sin));
		} else {
			return 180f - (float) Math.toDegrees(Math.asin(sin));
		}
	}

}
