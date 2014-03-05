package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

public class Self extends Aircraft {

	public static final float RADIUS = 0.3f;
	public static final float IMG_WIDTH = 2f;
	public static final float IMG_HEIGHT = 3f;
	public static final float HIGH_SPEED = 12f;
	public static final float LOW_SPEED = 6f;
	public static final float HIGH_CROSS_SPEED = 8.487f;
	public static final float LOW_CROSS_SPEED = 4.243f;
	public static final float ROTATE_SPEED = 25f;
	public static final int START_POWER = 100;
	public static final int MAX_POWER = 400;
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
	private int power;
	private int powerLevel;
	private boolean moving;
	private TextureRegion slowImg;
	private Support[] supImg;
	private Group supGroup;

	protected Self(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, resources);
		supGroup = new Group();
		supGroup.setPosition(x, y);
		supGroup.setOrigin(width / 2f, height / 2f);
		Texture item = resources.get("images/item.jpg", Texture.class);
		Texture self1 = resources.get("images/self1.jpg", Texture.class);
		slowImg = new TextureRegion(item, 0, 16, 64, 64);
		supImg = new Support[4];
		TextureRegion supImgTesture = new TextureRegion(self1, 64, 144, 16, 16);
		supImg[0] = new Support(IMG_WIDTH / 4f, IMG_WIDTH * 2f, supImgTesture);
		supImg[1] = new Support(IMG_WIDTH * 1.5f, IMG_WIDTH / 2f, supImgTesture);
		supImg[2] = new Support(IMG_WIDTH / 4f, -IMG_WIDTH * 0.75f,
				supImgTesture);
		supImg[3] = new Support(-IMG_WIDTH, IMG_WIDTH / 2f, supImgTesture);
		setPower(START_POWER);
		lowSpeed = false;
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
	public void setPosition(float x, float y) {
		supGroup.setPosition(x, y);
		super.setPosition(x, y);
	}
	
	@Override
	public void setX(float x) {
		supGroup.setX(x);;
		super.setX(x);
	}
	
	@Override
	public void setY(float y) {
		supGroup.setY(y);
		super.setY(y);
	}
	
	@Override
	public void act(float delta) {
		lastX = getX();
		supGroup.act(delta);
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
			float dx = delta * speedx, dy = delta * speedy;
			translate(dx, dy);
			supGroup.translate(dx, dy);
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
		supGroup.draw(sb, alpha);
		if (lowSpeed) {
			sb.draw(slowImg, getX() - IMG_WIDTH / 2f, getY() - IMG_HEIGHT / 6f,
					IMG_WIDTH, IMG_WIDTH, IMG_WIDTH * 2, IMG_WIDTH * 2, 1, 1,
					ROTATE_SPEED * time);
		}
	}

	public void setMoving(boolean b) {
		moving = b;
	}

	public void setPower(int power) {
		this.power = power;
		for (int i = 0; i < powerLevel; ++i) {
			supGroup.removeActor(supImg[i]);
		}
		supGroup.setRotation(0);
		powerLevel = power / 100;
		for (int i = 0; i < powerLevel; ++i) {
			supGroup.addActor(supImg[i]);
		}
		supGroup.setRotation(45 * (powerLevel - 1));
	}

	public void addPower(int delta) {
		power += delta;
		if (power >= MAX_POWER) {
			power = MAX_POWER;
		}
		if (power >= (powerLevel + 1) * 100) {
			supGroup.addActor(supImg[powerLevel]);
			supGroup.addAction(Actions.rotateBy(45, 0.125f));
			powerLevel += 1;
		}
	}

	public float getPower() {
		return power;
	}

	class Support extends Actor {
		TextureRegion img;
		static final float WIDTH = 1f;
		static final float HEIGHT = 1f;
		static final float DEG_PER_SEC = 60f;

		Support(float x, float y, TextureRegion img) {
			setBounds(x, y, WIDTH, HEIGHT);
			setOrigin(WIDTH / 2f, HEIGHT / 2f);
			this.img = img;
			addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(DEG_PER_SEC, 1f)));
		}

		@Override
		public void draw(SpriteBatch sb, float alpha) {
			sb.draw(img, getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation());
		}
	}
}
