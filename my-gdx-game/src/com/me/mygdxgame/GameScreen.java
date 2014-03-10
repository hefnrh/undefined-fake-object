package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.me.mygdxgame.item.Self;
import com.me.mygdxgame.stage.AbstractStage;
import com.me.mygdxgame.stage.Stage1;
import com.me.mygdxgame.stage.World;
import com.me.mygdxgame.stage.WorldController;
import com.me.mygdxgame.stage.WorldRenderer;

public class GameScreen implements Screen {

	public static final float MARGIN = 5f;

	private World world;
	private WorldController wc;
	private WorldRenderer wr;
	private Stage inputStage;
	private AbstractStage stage;
	public final int GL_WIDTH;
	public final int QUARTER_GL_WIDTH;
	public final int GL_HEIGHT;
	public final float WH;
	private AssetManager resources;
	private BitmapFont font;
	private Label powerLabel;
	private Label pointLabel;

	private Button up;
	private Button down;
	private Button left;
	private Button right;
	private Button upLeft;
	private Button upRight;
	private Button downLeft;
	private Button downRight;
	private Button slow;
	private ClickListener listener;

	private Music bgm;

	public GameScreen(Game parent) {
		GL_WIDTH = Gdx.graphics.getWidth();
		QUARTER_GL_WIDTH = GL_WIDTH >> 2;
		GL_HEIGHT = Gdx.graphics.getHeight();
		WH = (QUARTER_GL_WIDTH - 10f) / 3f;
		loadRescources();
		world = new World();
		wc = new WorldController(world, resources, this);
		wr = new WorldRenderer(world);
		inputStage = new Stage();
		stage = new Stage1(world, resources);
		loadImage();
		loadButtons();
		Gdx.input.setInputProcessor(inputStage);
		bgm = resources.get("sound/bgm.mp3", Music.class);
		bgm.setLooping(true);
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
		new Thread(stage).start();
		bgm.play();
	}

	private void loadRescources() {
		resources = new AssetManager();
		resources.load("sound/bgm.mp3", Music.class);
		resources.load("images/textures/button.pack", TextureAtlas.class);
		resources.load("images/bullet2.jpg", Texture.class);
		resources.load("images/bullet1.jpg", Texture.class);
		resources.load("images/self1.jpg", Texture.class);
		resources.load("images/bg1.jpg", Texture.class);
		resources.load("images/item.jpg", Texture.class);
		resources.load("images/sidebar.jpg", Texture.class);
		resources.load("images/enemy2.jpg", Texture.class);
		// FIXME remove after debug
		while (!resources.update())
			;
	}

	private void loadImage() {
		Texture sidebar = resources.get("images/sidebar.jpg");
		Image bgLeft = new Image(new TextureRegionDrawable(new TextureRegion(
				sidebar, 0, 0, 256, 480)));
		bgLeft.setBounds(0, 0, QUARTER_GL_WIDTH, GL_HEIGHT);
		inputStage.addActor(bgLeft);
		Image bgRight = new Image(new TextureRegionDrawable(new TextureRegion(
				sidebar, 0, 0, 256, 480)));
		bgRight.setBounds(QUARTER_GL_WIDTH * 3, 0, QUARTER_GL_WIDTH, GL_HEIGHT);
		inputStage.addActor(bgRight);
		Image power = new Image(new TextureRegionDrawable(new TextureRegion(
				sidebar, 256, 54, 84, 18)));
		power.setSize(WH, WH * 9f / 38f);
		power.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN,
				GL_HEIGHT - power.getHeight() - MARGIN);
		inputStage.addActor(power);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/font.ttf"));
		font = generator.generateFont((int) power.getHeight(), "1234567890./",
				false);
		font.setColor(Color.WHITE);
		generator.dispose();
		Label.LabelStyle style = new Label.LabelStyle(font,
				Color.WHITE);
		powerLabel = new Label("1.00/4.00", style);
		powerLabel.setPosition(power.getX() + power.getWidth(), power.getY());
		inputStage.addActor(powerLabel);
		Image point = new Image(new TextureRegionDrawable(new TextureRegion(
				sidebar, 328, 36, 84, 18)));
		point.setSize(WH, WH * 9f / 38f);
		point.setPosition(power.getX(),
				power.getY() - MARGIN - point.getHeight());
		inputStage.addActor(point);
		pointLabel = new Label("0", style);
		pointLabel.setPosition(point.getX() + point.getWidth(), point.getY());
		inputStage.addActor(pointLabel);
	}

	private void loadButtons() {

		TextureAtlas atlas = resources.get("images/textures/button.pack",
				TextureAtlas.class);

		listener = new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchDragged(event, x, y, pointer);
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				x = event.getStageX();
				y = event.getStageY();
				if (isOverButton(up, x, y)) {
					wc.setSelfHorizentalDirection(Self.STOP);
					wc.setSelfVerticalDirection(Self.UP);
					wc.setSelfMoving(true);
				} else if (isOverButton(down, x, y)) {
					wc.setSelfHorizentalDirection(Self.STOP);
					wc.setSelfVerticalDirection(Self.DOWN);
					wc.setSelfMoving(true);
				} else if (isOverButton(left, x, y)) {
					wc.setSelfHorizentalDirection(Self.LEFT);
					wc.setSelfVerticalDirection(Self.STOP);
					wc.setSelfMoving(true);
				} else if (isOverButton(right, x, y)) {
					wc.setSelfHorizentalDirection(Self.RIGHT);
					wc.setSelfVerticalDirection(Self.STOP);
					wc.setSelfMoving(true);
				} else if (isOverButton(upLeft, x, y)) {
					wc.setSelfHorizentalDirection(Self.LEFT);
					wc.setSelfVerticalDirection(Self.UP);
					wc.setSelfMoving(true);
				} else if (isOverButton(upRight, x, y)) {
					wc.setSelfHorizentalDirection(Self.RIGHT);
					wc.setSelfVerticalDirection(Self.UP);
					wc.setSelfMoving(true);
				} else if (isOverButton(downLeft, x, y)) {
					wc.setSelfHorizentalDirection(Self.LEFT);
					wc.setSelfVerticalDirection(Self.DOWN);
					wc.setSelfMoving(true);
				} else if (isOverButton(downRight, x, y)) {
					wc.setSelfHorizentalDirection(Self.RIGHT);
					wc.setSelfVerticalDirection(Self.DOWN);
					wc.setSelfMoving(true);
				} else if (!isOverButton(slow, x, y)) {
					wc.setSelfHorizentalDirection(Self.STOP);
					wc.setSelfVerticalDirection(Self.STOP);
					wc.setSelfMoving(false);
				}
				if (isOverButton(slow, x, y) || slow.isOver()) {
					wc.setSelfSlow(true);
				} else {
					wc.setSelfSlow(false);
				}
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				wc.setSelfHorizentalDirection(Self.STOP);
				wc.setSelfVerticalDirection(Self.STOP);
				wc.setSelfSlow(false);
				wc.setSelfMoving(false);
			}

			private boolean isOverButton(Button b, float x, float y) {
				float bx = b.getX();
				float by = b.getY();
				return x > bx && x < bx + b.getWidth() && y > by
						&& y < by + b.getHeight();
			}
		};
		up = new Button(new TextureRegionDrawable(atlas.findRegion("u")));
		up.setSize(WH, WH);
		up.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH, MARGIN + WH * 2);
		up.addListener(listener);
		inputStage.addActor(up);

		down = new Button(new TextureRegionDrawable(atlas.findRegion("d")));
		down.setSize(WH, WH);
		down.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH, MARGIN);
		down.addListener(listener);
		inputStage.addActor(down);

		left = new Button(new TextureRegionDrawable(atlas.findRegion("l")));
		left.setSize(WH, WH);
		left.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN, MARGIN + WH);
		left.addListener(listener);
		inputStage.addActor(left);

		right = new Button(new TextureRegionDrawable(atlas.findRegion("r")));
		right.setSize(WH, WH);
		right.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH * 2, MARGIN + WH);
		right.addListener(listener);
		inputStage.addActor(right);

		upLeft = new Button(new TextureRegionDrawable(atlas.findRegion("ul")));
		upLeft.setSize(WH, WH);
		upLeft.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN, MARGIN + WH * 2);
		upLeft.addListener(listener);
		inputStage.addActor(upLeft);

		upRight = new Button(new TextureRegionDrawable(atlas.findRegion("ur")));
		upRight.setSize(WH, WH);
		upRight.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH * 2, MARGIN + WH
				* 2);
		upRight.addListener(listener);
		inputStage.addActor(upRight);

		downLeft = new Button(new TextureRegionDrawable(atlas.findRegion("dl")));
		downLeft.setSize(WH, WH);
		downLeft.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN, MARGIN);
		downLeft.addListener(listener);
		inputStage.addActor(downLeft);

		downRight = new Button(
				new TextureRegionDrawable(atlas.findRegion("dr")));
		downRight.setSize(WH, WH);
		downRight.setPosition(QUARTER_GL_WIDTH * 3 + MARGIN + WH * 2, MARGIN);
		downRight.addListener(listener);
		inputStage.addActor(downRight);

		slow = new Button(new TextureRegionDrawable(atlas.findRegion("slow")));
		slow.setSize(WH * 3, WH);
		slow.setPosition((QUARTER_GL_WIDTH - slow.getWidth()) / 2f + MARGIN,
				MARGIN);
		slow.addListener(listener);
		inputStage.addActor(slow);
		// TODO add bomb
	}

	public void updatePower(int power) {
		powerLabel.setText(String.format("%d.%02d/4.00", power / 100,
				power % 100));
	}

	public void updatePoint(int point) {
		pointLabel.setText(String.valueOf(point));
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
		resources.dispose();
		font.dispose();
	}

}
