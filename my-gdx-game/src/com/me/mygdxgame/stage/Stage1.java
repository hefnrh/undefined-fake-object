package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Butterfly;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.stage.pattern.AllRangeShoot;

public class Stage1 extends AbstractStage {

	public Stage1(World world, AssetManager resources) {
		super(world, resources);
	}

	@Override
	public synchronized void run() {
		TextureRegion bimg = new TextureRegion(resources.get(
				"images/bullet1.jpg", Texture.class), 112, 48, 16, 16);
		while (true) {
			checkTime(500);
			Enemy e = Butterfly.newButterfly(0, World.CAMERA_HEIGHT, resources,
					world, 10);
			e.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(40, -20, 3)));
			Bullet b = Bullet.newEnemyBullet(0, 0, 0.5f, 0.5f, 0.2f, bimg,
					world);
			e.setShootPattern(new AllRangeShoot(5, 0.5f, 5, e, b, 0.3f, 18));
			world.addEnemy(e);
		}
	}

}
