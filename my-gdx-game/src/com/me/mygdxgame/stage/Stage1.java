package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.PowerElf;

public class Stage1 extends AbstractStage {

	public Stage1(World world, AssetManager resources) {
		super(world, resources);
	}

	@Override
	public synchronized void run() {
		TextureRegion bimg = new TextureRegion(resources.get(
				"images/bullet1.jpg", Texture.class), 112, 48, 16, 16);
		while (true) {
			try {
				wait(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Enemy e = PowerElf.newPowerElf(0, World.CAMERA_HEIGHT, resources,
					world, 20);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(40, -20, 3)));
			Bullet b = Bullet.newEnemyBullet(0, 0, 0.5f, 0.5f, 0.2f, bimg,
					world);
			world.addEnemy(e);
			try {
				wait(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// e.aimShoot(0, b, 15);
			b.setPosition(e.getCheckX() - b.getWidth(),
					e.getCheckY() - b.getHeight());
			b.addAction(Actions.moveBy(0, -58, 4));
			world.addEnemyBullet(b);
		}
	}

}
