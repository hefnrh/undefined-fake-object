package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

public class Reimu extends Self {

	public static final float NORMAL_BULLET_WIDTH = Self.IMG_WIDTH / 2f;
	public static final float NORMAL_BULLET_HEIGHT = Self.IMG_WIDTH / 2f;
	public static final float NORMAL_BULLET_RADIUS = Self.RADIUS;

	public Reimu(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, resources);
		normalBulletImg = new TextureRegion(resources.get("images/self1.jpg",
				Texture.class), 0, 144, 16, 16);
		// TODO load special bullet image
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture self1 = resources.get("images/self1.jpg", Texture.class);
		TextureRegion[] frames = new TextureRegion(self1, 0, 0, 256, 48).split(
				32, 48)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(self1, 128, 48, 128, 48).split(32, 48)[0];
		moveLeft = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(self1, 128, 96, 128, 48).split(32, 48)[0];
		moveRight = new Animation(RUNNING_FRAME_DURATION, frames);
		img = selfIdle.getKeyFrame(0, true);
	}

	@Override
	protected void loadSupport(AssetManager resources) {
		Texture self1 = resources.get("images/self1.jpg", Texture.class);
		supImg = new Support[4];
		TextureRegion supImgTesture = new TextureRegion(self1, 64, 144, 16, 16);
		supImg[0] = new ReimuSupport(-ReimuSupport.WIDTH / 2f, IMG_WIDTH,
				supImgTesture);
		supImg[1] = new ReimuSupport(IMG_WIDTH, -ReimuSupport.HEIGHT / 2f,
				supImgTesture);
		supImg[2] = new ReimuSupport(-ReimuSupport.WIDTH / 2f, -IMG_WIDTH - ReimuSupport.HEIGHT,
				supImgTesture);
		supImg[3] = new ReimuSupport(-IMG_WIDTH - ReimuSupport.WIDTH, -ReimuSupport.HEIGHT / 2f, supImgTesture);
	}

	@Override
	public void setLowSpeed(boolean b) {
		if (b == lowSpeed)
			return;
		float dis = IMG_WIDTH / 4f;
		if (b) {
			supImg[0].addAction(Actions.moveBy(0, -dis, 0.1f));
			supImg[1].addAction(Actions.moveBy(-dis, 0, 0.1f));
			supImg[2].addAction(Actions.moveBy(0, dis, 0.1f));
			supImg[3].addAction(Actions.moveBy(dis, 0, 0.1f));
		} else {
			supImg[0].addAction(Actions.moveBy(0, dis, 0.1f));
			supImg[1].addAction(Actions.moveBy(dis, 0, 0.1f));
			supImg[2].addAction(Actions.moveBy(0, -dis, 0.1f));
			supImg[3].addAction(Actions.moveBy(-dis, 0, 0.1f));
		}
		lowSpeed = b;
	}

	@Override
	protected void upgradeSupport(int i) {
		supGroup.addActor(supImg[i]);
		supGroup.addAction(Actions.rotateBy(45, 0.125f));
	}

	@Override
	protected void initSupport() {
		supGroup.setRotation(0f);
		supGroup.addActor(supImg[0]);
	}
	
	@Override
	public Bullet newNormalBullet(float x, float y) {
		Bullet b;
		if (uselessNormalBullet.isEmpty()) {
			b = new Bullet(x, y, NORMAL_BULLET_WIDTH, NORMAL_BULLET_HEIGHT,
					NORMAL_BULLET_RADIUS, normalBulletImg);
			b.setRotation(90);
			b.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.moveBy(0, 6, 0.1f)));
		} else {
			b = uselessNormalBullet.removeFirst();
			b.init(x, y, NORMAL_BULLET_WIDTH, NORMAL_BULLET_HEIGHT,
					NORMAL_BULLET_RADIUS, normalBulletImg);
		}
		return b;
	}

	@Override
	public Bullet newSpecialBullet(float x, float y) {
		// TODO
		return null;
	}
	
	@Override
	public float getSupportCenterX(Support s) {
		float deg = supGroup.getRotation();
		float x = supGroup.getX() + s.getX() + s.getWidth();
		float y = supGroup.getY() + s.getY() + s.getHeight();
		return 0;
	}
	
	@Override
	public float getSupportCenterY(Support s) {
		return 0;
	}

	class ReimuSupport extends Support {

		ReimuSupport(float x, float y, TextureRegion img) {
			super(x, y, img);
		}

		@Override
		public void setAction() {
			addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(DEG_PER_SEC, 1f)));
		}
	}
}
