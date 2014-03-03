package com.me.mygdxgame.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Self;

public class WorldRenderer {
	
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
		batch.begin();
		for (Bullet b: world.getSelfBullets()) {
			b.draw(batch, 1);
		}
		world.getSelf().draw(batch, 1);
		for (Bullet b: world.getEnemyBullets()) {
			b.draw(batch, 1);
		}
		// TODO draw enemies
		batch.end();
		drawDebug();
	}
	
	private void drawDebug() {
		Self self = world.getSelf();
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(Color.RED);
		for (Bullet b: world.getEnemyBullets()) {
			debugRenderer.rect(b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getOriginX(), b.getOriginY(), b.getRotation());
			debugRenderer.circle(b.getCheckX(), b.getCheckY(), b.getCheckRadius(), 10);
		}
		debugRenderer.setColor(Color.GREEN);
		for (Bullet b: world.getSelfBullets()) {
			debugRenderer.rect(b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getOriginX(), b.getOriginY(), b.getRotation());
			debugRenderer.circle(b.getCheckX(), b.getCheckY(), b.getCheckRadius(), 10);
		}
		debugRenderer.setColor(Color.YELLOW);
		debugRenderer.rect(self.getX(), self.getY(), self.getWidth(), self.getHeight(), self.getOriginX(), self.getOriginY(), self.getRotation());
		debugRenderer.circle(self.getCheckX(), self.getCheckY(), self.getCheckRadius(), 10);
		debugRenderer.setColor(Color.WHITE);
		debugRenderer.rect(0.1f, 0, World.CAMERA_WIDTH - 0.1f, World.CAMERA_HEIGHT - 0.1f);
		debugRenderer.end();
	}
	
	public void dispose() {
		batch.dispose();
		debugRenderer.dispose();
	}
}
