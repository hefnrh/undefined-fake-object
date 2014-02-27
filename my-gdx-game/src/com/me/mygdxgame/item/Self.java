package com.me.mygdxgame.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Self extends Aircraft {

	public static final float RADIUS = 0.375f;
	public static final float HALF_IMG_WIDTH = 1f;
	public static final float HALF_IMG_HEIGHT = 1.5f;
	public static final float HIGH_SPEED = 8f;
	public static final float LOW_SPEED = 4f;
	public static final float HIGH_CROSS_SPEED = 5.657f;
	public static final float LOW_CROSS_SPEED = 2.828f;
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int STOP = 0;

	private static Self self;

	private Animation selfIdle;
	private boolean lowSpeed;
	private int verticalDirection;
	private int horizentalDirection;

	private Self(Vector2 position) {
		super(position, RADIUS);
		moving = false;
		loadAtlas();
	}

	private void loadAtlas() {
		Texture origin = new Texture(Gdx.files.internal("images/self1.jpg"));
		TextureRegion[] frames = new TextureRegion(origin, 0, 0, 256, 48)
				.split(32, 48)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
	}

	public static Self getInstance(Vector2 position) {
		if (self != null)
			return self;
		self = new Self(position);
		self.imgBound.x = position.x - HALF_IMG_WIDTH;
		self.imgBound.y = position.y - HALF_IMG_HEIGHT;
		self.imgBound.width = 2 * HALF_IMG_WIDTH;
		self.imgBound.height = 2 * HALF_IMG_HEIGHT;
		return self;
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		imgBound.x = x - HALF_IMG_WIDTH;
		imgBound.y = y - HALF_IMG_HEIGHT;
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

	public void update(float delta) {
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
		}
		super.update(delta);
	}

	public void draw(SpriteBatch sb) {
		sb.draw(selfIdle.getKeyFrame(time, true), imgBound.x, imgBound.y,
				imgBound.width, imgBound.height);
	}

	@Override
	public void hitBy(Item i) {
		// TODO
	}

}
