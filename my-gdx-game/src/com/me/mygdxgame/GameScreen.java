package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.me.mygdxgame.item.Self;
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
		checkButtons();
		wc.update(delta);
		wr.draw();
		Gdx.gl.glViewport(0, 0, GL_WIDTH, GL_HEIGHT);
		inputStage.act(delta);
		inputStage.draw();
	}
	
	private void checkButtons() {
		// TODO add slow and cross check
		if (up.isPressed()) {
			wc.setSelfVerticalDirection(Self.UP);
			wc.setSelfMoving(true);
		} else if (down.isPressed()) {
			wc.setSelfVerticalDirection(Self.DOWN);
			wc.setSelfMoving(true);
		} else if (left.isPressed()) {
			wc.setSelfHorizentalDirection(Self.LEFT);
			wc.setSelfMoving(true);
		} else if (right.isPressed()) {
			wc.setSelfHorizentalDirection(Self.RIGHT);
			wc.setSelfMoving(true);
		} else {
			wc.setSelfHorizentalDirection(Self.STOP);
			wc.setSelfVerticalDirection(Self.STOP);
			wc.setSelfMoving(false);
		}
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
		loadButtons();
		Gdx.input.setInputProcessor(inputStage);
	}
	
	private void loadButtons() {
		// TODO
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/button.pack"));
		final float WH = (QUARTER_GL_WIDTH - 10f) / 3f;
		final float MARGIN = 5f;
		
		up = new ImageButton(new TextureRegionDrawable(atlas.findRegion("u")));
		up.setSize(WH, WH);
		up.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH, MARGIN + WH * 2);
		inputStage.addActor(up);
		
		down = new ImageButton(new TextureRegionDrawable(atlas.findRegion("d")));
		down.setSize(WH, WH);
		down.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH, MARGIN);
		inputStage.addActor(down);
		
		left = new ImageButton(new TextureRegionDrawable(atlas.findRegion("l")));
		left.setSize(WH, WH);
		left.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN, MARGIN + WH);
		inputStage.addActor(left);
		
		right = new ImageButton(new TextureRegionDrawable(atlas.findRegion("r")));
		right.setSize(WH, WH);
		right.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH * 2, MARGIN + WH);
		inputStage.addActor(right);
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
