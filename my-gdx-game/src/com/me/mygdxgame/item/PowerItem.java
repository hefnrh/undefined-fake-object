package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class PowerItem extends PItem {

	public static final LinkedList<PowerItem> uselessPowerItem = new LinkedList<PowerItem>();
	public static final float WIDTH = 0.5f;
	public static final float HEIGHT = 0.5f;
	public static final float RADIUS = 0.25f;

	protected PowerItem(float x, float y, World world) {
		super(x, y, WIDTH, HEIGHT, RADIUS, null, world);
	}

	public static PowerItem newPowerItem(float x, float y,
			AssetManager resources, World world) {
		PowerItem p;
		if (uselessPowerItem.isEmpty()) {
			p = new PowerItem(x, y, world);
			p.img = new TextureRegion(resources.get("images/item.jpg",
					Texture.class), 0, 208, 16, 16);
		} else {
			p = uselessPowerItem.removeFirst();
			p.setPosition(x, y);
		}
		return p;
	}

	@Override
	public void hit(Self s) {
		s.addPower(1);
	}

	@Override
	public void recycle() {
		uselessPowerItem.add(this);
		this.setToTrace(null, 0);
	}

}
