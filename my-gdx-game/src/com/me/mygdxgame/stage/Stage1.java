package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Butterfly;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.PointElf;
import com.me.mygdxgame.item.PointYinyangyu;
import com.me.mygdxgame.item.PowerElf;
import com.me.mygdxgame.stage.pattern.AimShoot;
import com.me.mygdxgame.stage.pattern.AllRangeShoot;

public class Stage1 extends AbstractStage {

	public Stage1(World world, AssetManager resources) {
		super(world, resources);
	}

	@Override
	public synchronized void run() {
		Texture rawImg = resources.get("images/bullet1.jpg", Texture.class);
		TextureRegion bimg;
		Enemy e;
		Bullet b;
		checkTime(1000);
		// first five
		e = PowerElf.newPowerElf((World.CAMERA_WIDTH - Butterfly.WIDTH) / 2f
				- PowerElf.WIDTH, World.CAMERA_HEIGHT, resources, world, 60);
		e.addAction(Actions.sequence(Actions.moveBy(0, -16f, 1),
				Actions.moveBy(0, 0, 3f), Actions.moveBy(0, 32f, 2)));
		bimg = new TextureRegion(rawImg, 32, 248, 8, 8);
		b = Bullet.newEnemyBullet(0, 0, 0.5f, 0.5f, 0.25f, bimg, world);
		e.setShootPattern(new AllRangeShoot(6, 1, 2f, e, b, 0.2f, 12));
		world.addEnemy(e);
		e = PointElf.newPointElf((World.CAMERA_WIDTH + Butterfly.WIDTH) / 2f,
				World.CAMERA_HEIGHT, resources, world, 60);
		e.addAction(Actions.sequence(Actions.moveBy(0, -16f, 1),
				Actions.moveBy(0, 0, 3f), Actions.moveBy(0, 32f, 2)));
		e.setShootPattern(new AllRangeShoot(6, 1, 2f, e, b, 0.2f, 12));
		world.addEnemy(e);
		e = Butterfly.newButterfly((World.CAMERA_WIDTH - Butterfly.WIDTH) / 2f,
				World.CAMERA_HEIGHT, resources, world, 100);
		e.addAction(Actions.sequence(Actions.moveBy(0, -16f, 1),
				Actions.moveBy(0, 0, 3f), Actions.moveBy(0, 32f, 2)));
		bimg = new TextureRegion(rawImg, 32, 16, 16, 16);
		b = Bullet.newEnemyBullet(0, 0, 1f, 1f, 0.4f, bimg, world);
		b.setRotation(-90);
		b.setOrigin(b.getWidth() / 2f, b.getHeight() / 2f);
		e.setShootPattern(new AllRangeShoot(6, 1, 2, e, b, 0.2f, 36));
		world.addEnemy(e);
		checkTime(1000 >> 3);
		e = PowerElf.newPowerElf((World.CAMERA_WIDTH - Butterfly.WIDTH) / 2f
				- PowerElf.WIDTH, World.CAMERA_HEIGHT, resources, world, 60);
		e.addAction(Actions.sequence(Actions.moveBy(0, -14f, 14f / 16f),
				Actions.moveBy(0, 0, 3f), Actions.moveBy(0, 32f, 2)));
		bimg = new TextureRegion(rawImg, 32, 248, 8, 8);
		b = Bullet.newEnemyBullet(0, 0, 0.5f, 0.5f, 0.25f, bimg, world);
		e.setShootPattern(new AllRangeShoot(6, 1, 2f, e, b, 0.2f, 12));
		world.addEnemy(e);
		e = PointElf.newPointElf((World.CAMERA_WIDTH + Butterfly.WIDTH) / 2f,
				World.CAMERA_HEIGHT, resources, world, 60);
		e.addAction(Actions.sequence(Actions.moveBy(0, -14f, 14f / 16f),
				Actions.moveBy(0, 0, 3f), Actions.moveBy(0, 32f, 2)));
		e.setShootPattern(new AllRangeShoot(6, 1, 2f, e, b, 0.2f, 12));
		world.addEnemy(e);

		// yinyangyu
		checkTime(7000);
		bimg = new TextureRegion(rawImg, 64, 208, 32, 32);
		b = Bullet.newEnemyBullet(0, 0, 2f, 2f, 0.8f, bimg, world);
		while (true) {
			checkTime(500);
			e = PointYinyangyu.newPointYinyangyu(0, World.CAMERA_HEIGHT,
					resources, world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(40, -20, 3)));
			b.setRotation(-90);
			e.setShootPattern(new AimShoot(3, 0.5f, 2, e, b, 1, 9, 0.2f));
			world.addEnemy(e);
		}
	}

}
