package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Self extends Aircraft {

	public static final float RADIUS = 0.3f;
	public static final float IMG_WIDTH = 2f;
	public static final float IMG_HEIGHT = 3f;
	public static final float HIGH_SPEED = 12f;
	public static final float LOW_SPEED = 6f;
	public static final float HIGH_CROSS_SPEED = 8.487f;
	public static final float LOW_CROSS_SPEED = 4.243f;
	public static final float ROTATE_SPEED = 25f;
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int STOP = 0;

	private static Self self;

	private boolean lowSpeed;
	private int verticalDirection;
	private int horizentalDirection;
	private float speedx;
	private float speedy;
	private boolean moving;
	private TextureRegion slowImg;

	protected Self(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, resources);
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		TextureRegion[] frames = new TextureRegion(resources.get(
				"images/self1.jpg", Texture.class), 0, 0, 256, 48)
				.split(32, 48)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		img = selfIdle.getKeyFrame(0, true);
		slowImg = new TextureRegion(resources.get("images/item.jpg",
				Texture.class), 0, 16, 64, 64);
	}

	public static Self getInstance(float x, float y, AssetManager resources) {
		if (self != null)
			return self;
		self = new Self(x, y, IMG_WIDTH, IMG_HEIGHT, RADIUS, resources);
		return self;
	}

	public void setVerticalDirection(int i) {
		verticalDirection = i;
	}

	public void setHorizentalDirection(int i) {
		horizentalDirection = i;
	}

	public void setLowSpeed(boolean b) {
		lowSpeed = b;
	}

	@Override
	public void act(float delta) {
		if (moving) {
			float speedScale;
			if (lowSpeed) {
				speedScale = (horizentalDirection & verticalDirection) != 0 ? LOW_CROSS_SPEED
						: LOW_SPEED;
			} else {
				speedScale = (horizentalDirection & verticalDirection) != 0 ? HIGH_CROSS_SPEED
						: HIGH_SPEED;
			}
			speedx = speedScale * horizentalDirection;
			speedy = speedScale * verticalDirection;
			translate(delta * speedx, delta * speedy);
		}
		super.act(delta);
	}

	@Override
	public void hitBy(Item i) {
		// TODO
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		super.draw(sb, alpha);
		if (lowSpeed) {
			sb.draw(slowImg, getX() - IMG_WIDTH / 2f, getY() - IMG_HEIGHT / 6f,
					IMG_WIDTH, IMG_WIDTH, IMG_WIDTH * 2, IMG_WIDTH * 2, 1, 1,
					ROTATE_SPEED * time);
		}
	}

	public void setMoving(boolean b) {
		moving = b;
	}
}
