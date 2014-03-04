package com.me.mygdxgame.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Self;

public class WorldRenderer {

	private SpriteBatch batch;
	private ShapeRenderer debugRenderer;
	private World world;
	private BitmapFont fps;

	public WorldRenderer(World world) {
		this.world = world;
		batch = new SpriteBatch();
		debugRenderer = new ShapeRenderer();
		batch.setProjectionMatrix(world.getCamera().combined);
		debugRenderer.setProjectionMatrix(world.getCamera().combined);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font.ttf"));
		fps = generator.generateFont(32, "0123456789", false);
		fps.setColor(Color.WHITE);
		fps.setScale(World.CAMERA_HEIGHT / Gdx.graphics.getHeight());
		generator.dispose();
	}

	public void draw() {
		batch.begin();
		drawBg();
		for (Bullet b : world.getSelfNormalBullets()) {
			b.draw(batch, 1);
		}
		world.getSelf().draw(batch, 1);
		for (Bullet b : world.getEnemyBullets()) {
			b.draw(batch, 1);
		}
		// TODO draw enemies
		fps.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()),
				World.CAMERA_WIDTH - 3, fps.getCapHeight());
		batch.end();
		drawDebug();
	}

	private void drawBg() {
		batch.draw(world.getBg(), 0, (-world.getTime()) % World.CAMERA_HEIGHT,
				World.CAMERA_WIDTH, World.CAMERA_HEIGHT, 0, 1, 1, 0);
		batch.draw(world.getBg(), 0, World.CAMERA_HEIGHT
				- (world.getTime() % World.CAMERA_HEIGHT), World.CAMERA_WIDTH,
				World.CAMERA_HEIGHT, 0, 1, 1, 0);
	}

	private void drawDebug() {
		Self self = world.getSelf();
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(Color.RED);
		for (Bullet b : world.getEnemyBullets()) {
			debugRenderer.rect(b.getX(), b.getY(), b.getWidth(), b.getHeight(),
					b.getOriginX(), b.getOriginY(), b.getRotation());
			debugRenderer.circle(b.getCheckX(), b.getCheckY(),
					b.getCheckRadius(), 10);
		}
		debugRenderer.setColor(Color.GREEN);
		for (Bullet b : world.getSelfNormalBullets()) {
			debugRenderer.rect(b.getX(), b.getY(), b.getWidth(), b.getHeight(),
					b.getOriginX(), b.getOriginY(), b.getRotation());
			debugRenderer.circle(b.getCheckX(), b.getCheckY(),
					b.getCheckRadius(), 10);
		}
		debugRenderer.setColor(Color.YELLOW);
		debugRenderer.rect(self.getX(), self.getY(), self.getWidth(),
				self.getHeight(), self.getOriginX(), self.getOriginY(),
				self.getRotation());
		debugRenderer.circle(self.getCheckX(), self.getCheckY(),
				self.getCheckRadius(), 10);
		debugRenderer.setColor(Color.WHITE);
		debugRenderer.rect(0.1f, 0, World.CAMERA_WIDTH - 0.1f,
				World.CAMERA_HEIGHT - 0.1f);
		debugRenderer.end();
	}

	public void dispose() {
		batch.dispose();
		debugRenderer.dispose();
	}
}
