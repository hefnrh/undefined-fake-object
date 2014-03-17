package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.stage.World;

public class Reimu extends Self {

	public static final float NORMAL_BULLET_WIDTH = Self.IMG_WIDTH / 2f;
	public static final float NORMAL_BULLET_HEIGHT = NORMAL_BULLET_WIDTH;
	public static final float NORMAL_BULLET_RADIUS = Self.RADIUS;
	public static final float SPECIAL_BULLET_WIDTH = NORMAL_BULLET_WIDTH;
	public static final float SPECIAL_BULLET_HEIGHT = NORMAL_BULLET_HEIGHT;
	public static final float SPECIAL_BULLET_RADIUS = NORMAL_BULLET_RADIUS;
	public static final float ITEM_RANGE = 3f;

	private float normalTime;
	private float specialTime;

	protected Reimu(float x, float y, float width, float height,
			float checkRadius, AssetManager resources, World parent) {
		super(x, y, width, height, checkRadius, resources, parent);
		Texture self1 = resources.get("images/self1.jpg", Texture.class);
		normalBulletImg = new TextureRegion(self1, 0, 144, 16, 16);
		specialBulletImg = new TextureRegion(self1, 0, 160, 16, 16);
		normalTime = specialTime = 0;
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
		supImg[2] = new ReimuSupport(-ReimuSupport.WIDTH / 2f, -IMG_WIDTH
				- ReimuSupport.HEIGHT, supImgTesture);
		supImg[3] = new ReimuSupport(-IMG_WIDTH - ReimuSupport.WIDTH,
				-ReimuSupport.HEIGHT / 2f, supImgTesture);
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
	public synchronized Bullet newNormalBullet(float x, float y) {
		Bullet b;
		synchronized (Self.class) {
			if (uselessNormalBullet.isEmpty()) {
				b = new Bullet(x, y, NORMAL_BULLET_WIDTH, NORMAL_BULLET_HEIGHT,
						NORMAL_BULLET_RADIUS, normalBulletImg, world);
				b.setRotation(90);
				b.addAction(Actions.repeat(RepeatAction.FOREVER,
						Actions.moveBy(0, 0.7f)));
			} else {
				b = uselessNormalBullet.poll();
				b.init(x, y, NORMAL_BULLET_WIDTH, NORMAL_BULLET_HEIGHT,
						NORMAL_BULLET_RADIUS, normalBulletImg);
			}
		}
		return b;
	}

	@Override
	public Bullet newSpecialBullet(float x, float y) {
		Bullet b;
		synchronized (Self.class) {
			if (uselessSpecialBullet.isEmpty()) {
				b = new Bullet(x, y, SPECIAL_BULLET_WIDTH,
						SPECIAL_BULLET_HEIGHT, SPECIAL_BULLET_RADIUS,
						specialBulletImg, world);
				b.setOrigin(ReimuSupport.WIDTH / 2f, ReimuSupport.HEIGHT / 2f);
			} else {
				b = uselessSpecialBullet.poll();
				b.init(x, y, SPECIAL_BULLET_WIDTH, SPECIAL_BULLET_HEIGHT,
						SPECIAL_BULLET_RADIUS, specialBulletImg);
			}
			b.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(30, 0.1f)));
			LinkedList<Enemy> enemies = world.getEnemies();
			if (enemies.isEmpty()) {
				b.setAction(Actions.repeat(RepeatAction.FOREVER,
						Actions.moveBy(0, 4, 0.1f)));
			} else {
				b.setToTrace(enemies.peekFirst(), 40f);
			}
		}
		return b;
	}

	@Override
	public float getSupportCenterX(int index) {
		float deg = supGroup.getRotation();
		float sin = MathUtils.sinDeg(deg), cos = MathUtils.cosDeg(deg);
		float x = supImg[index].getX() + ReimuSupport.WIDTH / 2f, y = supImg[index]
				.getY() + ReimuSupport.HEIGHT / 2f;
		return supGroup.getX() + cos * x - sin * y;
	}

	@Override
	public float getSupportCenterY(int index) {
		float deg = supGroup.getRotation();
		float sin = MathUtils.sinDeg(deg), cos = MathUtils.cosDeg(deg);
		float x = supImg[index].getX() + ReimuSupport.WIDTH / 2f, y = supImg[index]
				.getY() + ReimuSupport.HEIGHT / 2f;
		return supGroup.getY() + cos * y + sin * x;
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

	@Override
	public float getNormalBulletWidth() {
		return NORMAL_BULLET_WIDTH;
	}

	@Override
	public float getNormalBulletHeight() {
		return NORMAL_BULLET_HEIGHT;
	}

	@Override
	public float getSpecialBulletWidth() {
		return SPECIAL_BULLET_WIDTH;
	}

	@Override
	public float getSpecialBulletHeight() {
		return SPECIAL_BULLET_HEIGHT;
	}

	@Override
	protected void normalShoot(float delta) {
		normalTime += delta;
		float tmp = normalTime * 15f;
		if (tmp < 1)
			return;
		normalTime -= 1f / 15f;
		Bullet myBullet;
		// self bullet will be rotated before shooting, so set X + height
		myBullet = newNormalBullet(getX() + getNormalBulletHeight(), getY()
				+ Self.IMG_HEIGHT);
		world.addSelfNormalBullet(myBullet);
		myBullet = newNormalBullet(getX() + Self.IMG_WIDTH, getY()
				+ Self.IMG_HEIGHT);
		world.addSelfNormalBullet(myBullet);
	}

	@Override
	protected void specialShoot(float delta) {
		specialTime += delta;
		float tmp = specialTime * 5f;
		if (tmp < 1)
			return;
		specialTime -= 0.2f;
		Bullet myBullet;
		for (int i = 0; i < powerLevel; ++i) {
			myBullet = newSpecialBullet(getSupportCenterX(i)
					- getSpecialBulletHeight() / 2f, getSupportCenterY(i));
			world.addSelfSpecialBullet(myBullet);
		}
	}

	@Override
	public boolean itemInRange(PItem p) {
		float dx = getCheckX() - p.getCheckX();
		float dy = getCheckY() - p.getCheckY();
		return dx * dx + dy * dy < ITEM_RANGE * ITEM_RANGE;
	}
}
