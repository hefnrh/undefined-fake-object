package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.me.mygdxgame.stage.World;
import com.me.mygdxgame.stage.WorldController;
import com.me.mygdxgame.stage.WorldRenderer;

public class GameScreen implements Screen {

	private World world;
	private WorldController wc;
	private WorldRenderer wr;
	private Stage inputStage;
	private final int GL_WIDTH;
	private final int QUARTER_GL_WIDTH;
	private final int GL_HEIGHT;

	private ImageButton up;
	private ImageButton down;
	private ImageButton left;
	private ImageButton right;
	private ImageButton upLeft;
	private ImageButton upRight;
	private ImageButton downLeft;
	private ImageButton downRight;

	public GameScreen(Game parent) {
		GL_WIDTH = Gdx.graphics.getWidth();
		QUARTER_GL_WIDTH = GL_WIDTH >> 2;
		GL_HEIGHT = Gdx.graphics.getHeight();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(QUARTER_GL_WIDTH, 0, QUARTER_GL_WIDTH << 1, GL_HEIGHT);
		wc.update(delta);
		wr.draw();
		Gdx.gl.glViewport(0, 0, GL_WIDTH, GL_HEIGHT);
		inputStage.act(delta);
		inputStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		world = new World();
		wc = new WorldController(world);
		wr = new WorldRenderer(world);
		inputStage = new Stage();
		Gdx.input.setInputProcessor(inputStage);
		loadButtons();
	}
	
	private void loadButtons() {
		// TODO
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		wr.dispose();
		inputStage.dispose();
	}

}
