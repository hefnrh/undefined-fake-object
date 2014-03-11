package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.PointYinyangyu;
import com.me.mygdxgame.stage.pattern.AimShoot;

public class Stage1 extends AbstractStage {

	public Stage1(World world, AssetManager resources) {
		super(world, resources);
	}

	@Override
	public synchronized void run() {
		TextureRegion bimg = new TextureRegion(resources.get(
				"images/bullet1.jpg", Texture.class), 112, 80, 16, 16);
		while (true) {
			checkTime(500);
			Enemy e = PointYinyangyu.newPointYinyangyu(0, World.CAMERA_HEIGHT, resources,
					world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(40, -20, 3)));
			Bullet b = Bullet.newEnemyBullet(0, 0, 0.5f, 0.5f, 0.125f, bimg,
					world);
			b.setRotation(-90);
			e.setShootPattern(new AimShoot(3, 0.5f, 15, e, b, 3, 9, 0.2f));
			world.addEnemy(e);
		}
	}

}