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
import com.me.mygdxgame.item.PowerYinyangyu;
import com.me.mygdxgame.stage.pattern.AimShoot;
import com.me.mygdxgame.stage.pattern.AllRangeShoot;
import com.me.mygdxgame.stage.pattern.OneShoot;

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
		checkTime(6500);
		bimg = new TextureRegion(rawImg, 64, 208, 32, 32);
		b = Bullet.newEnemyBullet(0, 0, 2f, 2f, 0.8f, bimg, world);
		b.setRotation(-90);
		for (int i = 0; i < 5; ++i) {
			checkTime(500);
			e = PointYinyangyu.newPointYinyangyu(0, World.CAMERA_HEIGHT * 0.9f,
					resources, world, 50);
			e.setOrigin(0, -10);
			e.addAction(Actions.sequence(Actions.moveBy(15, 0, 1),
					Actions.repeat(5, Actions.rotateBy(-360, 4f)),
					Actions.moveBy(30, 0, 2)));
			e.setShootPattern(new AimShoot(300, 0.5f, 2, e, b, 1, 9, 0.4f));
			world.addEnemy(e);
			checkTime(500);
			e = PowerYinyangyu.newPowerYinyangyu(World.CAMERA_WIDTH,
					World.CAMERA_HEIGHT * 0.9f, resources, world, 50);
			e.setOrigin(0, -10);
			e.addAction(Actions.sequence(Actions.moveBy(-15, 0, 1),
					Actions.repeat(4, Actions.rotateBy(360, 4f)),
					Actions.moveBy(-30, 0, 2)));
			e.setShootPattern(new AimShoot(300, 0.5f, 2, e, b, 1, 9, 0.4f));
			world.addEnemy(e);
		}

		// elf
		checkTime(5500);
		bimg = new TextureRegion(rawImg, 208, 64, 16, 16);
		b = Bullet.newEnemyBullet(0, 0, 1, 1, 0.25f, bimg, world);
		b.setRotation(-90);
		for (int i = 0; i < 6; ++i) {
			checkTime(2000);
			for (int j = 0; j < 6; ++j) {
				e = PowerElf.newPowerElf(j * PowerElf.WIDTH * 2,
						World.CAMERA_HEIGHT, resources, world, 40);
				e.addAction(Actions.repeat(
						RepeatAction.FOREVER,
						Actions.sequence(Actions.moveBy(0, -2, 0.5f),
								Actions.moveBy(2, 0, 0.5f),
								Actions.moveBy(0, -2, 0.5f),
								Actions.moveBy(-2, 0, 0.5f))));
				e.setShootPattern(new OneShoot(100, 0, 1, e, b, 0.3f, -90));
				world.addEnemy(e);
			}
			checkTime(2000);
			for (int j = 1; j < 7; ++j) {
				e = PointElf.newPointElf(World.CAMERA_WIDTH - (j + 1) * PowerElf.WIDTH * 2,
						World.CAMERA_HEIGHT, resources, world, 40);
				e.addAction(Actions.repeat(
						RepeatAction.FOREVER,
						Actions.sequence(Actions.moveBy(0, -2, 0.5f),
								Actions.moveBy(-2, 0, 0.5f),
								Actions.moveBy(0, -2, 0.5f),
								Actions.moveBy(2, 0, 0.5f))));
				e.setShootPattern(new OneShoot(100, 0, 1, e, b, 0.3f, -90));
				world.addEnemy(e);
			}
		}
	}

}
