package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class Self extends Aircraft {

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

	protected boolean lowSpeed;
	protected int verticalDirection;
	protected int horizentalDirection;
	protected float speedx;
	protected float speedy;
	protected int power;
	protected int powerLevel;
	protected int point;
	protected boolean moving;
	protected TextureRegion slowImg;
	protected TextureRegion normalBulletImg;
	protected TextureRegion specialBulletImg;
	protected Support[] supImg;
	protected Group supGroup;
	protected LinkedList<Bullet> uselessNormalBullet;
	protected LinkedList<Bullet> uselessSpecialBullet;
	

	protected Self(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, resources);
		Texture item = resources.get("images/item.jpg", Texture.class);
		slowImg = new TextureRegion(item, 0, 16, 64, 64);
		supGroup = new Group();
		supGroup.setPosition(x + IMG_WIDTH / 2f, y + IMG_HEIGHT / 2f);
		supGroup.setOrigin(0f, 0f);
		lowSpeed = false;
		uselessNormalBullet = new LinkedList<Bullet>();
		uselessSpecialBullet = new LinkedList<Bullet>();
		loadSupport(resources);
		initSupport();
	}

	protected abstract void loadSupport(AssetManager resources);
	
	public static Self getInstance(float x, float y, AssetManager resources, String name) {
		if (self != null)
			return self;
		if (self == null || self.equals("reimu"))
			self = new Reimu(x, y, IMG_WIDTH, IMG_HEIGHT, RADIUS, resources);
		return self;
	}

	public void setVerticalDirection(int i) {
		verticalDirection = i;
	}

	public void setHorizentalDirection(int i) {
		horizentalDirection = i;
	}

	public abstract void setLowSpeed(boolean b);

	@Override
	public void setPosition(float x, float y) {
		supGroup.setPosition(x, y);
		super.setPosition(x, y);
	}
	
	@Override
	public void setX(float x) {
		supGroup.setX(x);
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
		for (int i = 1; i < powerLevel; ++i) {
			supGroup.removeActor(supImg[i]);
		}
		initSupport();
		powerLevel = power / 100;
		for (int i = 1; i < powerLevel; ++i) {
			upgradeSupport(i);
		}
	}

	public void addPower(int delta) {
		power += delta;
		if (power >= MAX_POWER) {
			power = MAX_POWER;
		}
		if (power >= (powerLevel + 1) * 100) {
			upgradeSupport(powerLevel);
			powerLevel += 1;
		}
	}
	
	protected abstract void upgradeSupport(int i);
	
	protected abstract void initSupport();
	
	public int getPower() {
		return power;
	}
	
	public int getPoint() {
		return point;
	}
	
	abstract class Support extends Actor {
		TextureRegion img;
		static final float WIDTH = 1f;
		static final float HEIGHT = 1f;
		static final float DEG_PER_SEC = 60f;

		Support(float x, float y, TextureRegion img) {
			setBounds(x, y, WIDTH, HEIGHT);
			setOrigin(WIDTH / 2f, HEIGHT / 2f);
			this.img = img;
			setAction();
		}
		
		public abstract void setAction();
		
		@Override
		public void draw(SpriteBatch sb, float alpha) {
			sb.draw(img, getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation());
		}
	}
	
	public abstract Bullet newNormalBullet(float x, float y);
	
	public abstract Bullet newSpecialBullet(float x, float y);
	
	public abstract float getSupportCenterX(Support s);
	
	public abstract float getSupportCenterY(Support s);
	
	public void recycleNormalBullet(Bullet b) {
		b.setInUse(false);
		uselessNormalBullet.add(b);
	}
	
	public void recycleSpecialBullet(Bullet b) {
		b.setInUse(false);
		uselessSpecialBullet.add(b);
	}
	
	public LinkedList<Bullet> getUselessNormalBullet() {
		return uselessNormalBullet;
	}
	
	public LinkedList<Bullet> getUselessSpecialBullet() {
		return uselessSpecialBullet;
	}
}
