package com.me.mygdxgame.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Self;

public class WorldRenderer {
	
	private static final Texture img = new Texture(Gdx.files.internal("images/bullet2.jpg"));
	
	private SpriteBatch batch;
	private ShapeRenderer debugRenderer;
	private World world;
	
	public WorldRenderer(World world) {
		this.world = world;
		batch = new SpriteBatch();
		debugRenderer = new ShapeRenderer();
		batch.setProjectionMatrix(world.getCamera().combined);
		debugRenderer.setProjectionMatrix(world.getCamera().combined);
	}
	
	public void draw() {
		Rectangle bound;
		batch.begin();
		for (Bullet b: world.getSelfBullets()) {
			b.draw(batch);
			bound = b.getImgBound();
			batch.draw(img, bound.x, bound.y, bound.width, bound.height, 0, 32, 32, 32, false, false);
		}
		world.getSelf().draw(batch);
		for (Bullet b: world.getEnemyBullets()) {
			b.draw(batch);
			bound = b.getImgBound();
			batch.draw(img, bound.x, bound.y, bound.width, bound.height, 0, 0, 32, 32, false, false);
		}
		batch.end();
		// TODO draw enemies
		drawDebug();
	}
	
	private void drawDebug() {
		Self self = world.getSelf();
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(Color.RED);
		for (Bullet b: world.getEnemyBullets()) {
			debugRenderer.circle(b.getPosition().x, b.getPosition().y, b.getCheckBound().radius,10);
		}
		debugRenderer.setColor(Color.GREEN);
		for (Bullet b: world.getSelfBullets()) {
			debugRenderer.circle(b.getPosition().x, b.getPosition().y, b.getCheckBound().radius, 10);
		}
		debugRenderer.setColor(Color.YELLOW);
		debugRenderer.circle(self.getPosition().x, self.getPosition().y, self.getCheckBound().radius, 10);
		debugRenderer.setColor(Color.WHITE);
		debugRenderer.rect(0.1f, 0, World.CAMERA_WIDTH - 0.1f, World.CAMERA_HEIGHT - 0.1f);
		debugRenderer.end();
	}
	
	public void dispose() {
		batch.dispose();
		debugRenderer.dispose();
	}
}
