package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class PowerItem extends PItem {

	public static final LinkedList<PowerItem> uselessPowerItem = new LinkedList<PowerItem>();

	protected PowerItem(float x, float y, World world) {
		super(x, y, null, world);
	}

	public synchronized static PowerItem newPowerItem(float x, float y,
			AssetManager resources, World world) {
		PowerItem p;
		if (uselessPowerItem.isEmpty()) {
			p = new PowerItem(x, y, world);
			p.img = new TextureRegion(resources.get("images/item.jpg",
					Texture.class), 0, 208, 16, 16);
		} else {
			p = uselessPowerItem.poll();
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
		synchronized (PowerItem.class) {
			this.setToTrace(null, 0);
			if (action != null) {
				removeAction(action);
			}
			uselessPowerItem.add(this);
		}
	}

}
