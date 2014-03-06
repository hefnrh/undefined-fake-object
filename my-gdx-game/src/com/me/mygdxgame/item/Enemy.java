package com.me.mygdxgame.item;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.me.mygdxgame.stage.World;


public abstract class Enemy extends Aircraft {
	
	protected int hp;
	protected ArrayList<PItem> pointItems;
	protected ArrayList<PowerItem> powerItems;
	
	protected Enemy(float x, float y, float width, float height,
			float checkRadius, AssetManager resources, World world, int hp) {
		super(x, y, width, height, checkRadius, resources, world);
		this.hp = hp;
		pointItems = new ArrayList<PItem>();
		powerItems = new ArrayList<PowerItem>();
	}
	
	public void addPoint(PItem point) {
		pointItems.add(point);
	}
	
	public void addPower(PowerItem power) {
		powerItems.add(power);
	}

	public abstract void dead();
	
	public int getHp() {
		return hp;
	}
}
