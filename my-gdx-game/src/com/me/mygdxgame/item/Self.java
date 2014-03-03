package com.me.mygdxgame.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Self extends Aircraft {

	public static final float RADIUS = 0.375f;
	public static final float HALF_IMG_WIDTH = 1f;
	public static final float HALF_IMG_HEIGHT = 1.5f;
	public static final float HIGH_SPEED = 12f;
	public static final float LOW_SPEED = 6f;
	public static final float HIGH_CROSS_SPEED = 8.487f;
	public static final float LOW_CROSS_SPEED = 4.243f;
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

	protected Self(float x, float y, float width, float height,
			float checkRadius) {
		super(x, y, width, height, checkRadius);
	}

	@Override
	protected void loadAtlas() {
		Texture origin = new Texture(Gdx.files.internal("images/self1.jpg"));
		TextureRegion[] frames = new TextureRegion(origin, 0, 0, 256, 48)
				.split(32, 48)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		img = selfIdle.getKeyFrame(0, true);
	}

	public static Self getInstance(float x, float y) {
		if (self != null)
			return self;
		self = new Self(x, y, HALF_IMG_WIDTH * 2, HALF_IMG_HEIGHT * 2, RADIUS);
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
			// TODO draw check circle
		}
	}
	
	public void setMoving(boolean b) {
		moving = b;
	}
}
