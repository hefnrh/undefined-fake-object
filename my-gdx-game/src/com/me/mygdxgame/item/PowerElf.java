package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class PowerElf extends Enemy {

	public static final LinkedList<PowerElf> uselessPowerElf = new LinkedList<PowerElf>();
	
	protected PowerElf(float x, float y, float width, float height,
			float checkRadius, AssetManager resources, World world, int hp) {
		super(x, y, width, height, checkRadius, resources, world, hp);
	}

	@Override
	public void dead() {
		PowerItem power = powerItems.get(0);
		power.setPosition(getCheckX(), getCheckY());
		world.addItem(power);
		setInUse(false);
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture pic = resources.get("images/enemy2.jpg", Texture.class);
		TextureRegion[] frames = new TextureRegion(pic, 0, 32, 128, 32).split(32, 32)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(pic, 256, 32, 128, 32).split(32, 32)[0];
		moveRight = new Animation(RUNNING_FRAME_DURATION, frames);
		frames =  new TextureRegion(pic, 256, 32, 128, 32).split(32, 32)[0];
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
		moveLeft = new Animation(RUNNING_FRAME_DURATION, frames);
		currentState = selfIdle;
	}

	@Override
	public void shoot(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public static PowerElf newPowerElf(float x, float y, int hp) {
		if (uselessPowerElf.isEmpty()) {
		}
		// TODO
		return null;
	}

}
