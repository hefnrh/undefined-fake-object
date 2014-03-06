package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class PointItem extends PItem {

	public static final LinkedList<PointItem> uselessPointItem = new LinkedList<PointItem>();
	public static final float WIDTH = 0.5f;
	public static final float HEIGHT = 0.5f;
	public static final float RADIUS = 0.25f;

	public PointItem(float x, float y, World world) {
		super(x, y, WIDTH, HEIGHT, RADIUS, null, world);
	}

	public static PointItem newPointItem(float x, float y,
			AssetManager resources, World world) {
		PointItem p;
		if (uselessPointItem.isEmpty()) {
			p = new PointItem(x, y, world);
			p.img = new TextureRegion(resources.get("images/item.jpg",
					Texture.class), 16, 208, 16, 16);
		} else {
			p = uselessPointItem.removeFirst();
			p.setPosition(x, y);
		}
		return p;
	}

	@Override
	public void hit(Self s) {
		s.addPoint(1);
	}

	@Override
	public void recycle() {
		setToTrace(null, 0);
		uselessPointItem.add(this);
	}

}
