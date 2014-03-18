package com.me.mygdxgame.stage.pattern;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;

public class OneShoot extends ShootPattern {

	private float direction;

	public OneShoot(int magazine, float delay, float rps, Enemy parent,
			Bullet b, float speed, float direction) {
		super(magazine, delay, rps, parent, b, speed);
		this.direction = direction;
	}

	@Override
	public void shoot() {
		Bullet b = getBullet();
		b.rotate(direction);
		b.setOrigin(b.getWidth() / 2f, b.getHeight() / 2f);
		b.setPosition(parent.getCheckX() - b.getWidth() / 2f,
				parent.getCheckY() - b.getHeight() / 2f);
		b.addAction(Actions.repeat(
				RepeatAction.FOREVER,
				Actions.moveBy(MathUtils.cosDeg(direction) * speed,
						MathUtils.sinDeg(direction) * speed)));
		parent.getWorld().addEnemyBullet(b);
	}

}
