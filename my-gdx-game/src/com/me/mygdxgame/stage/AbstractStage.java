package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractStage implements Runnable {
	
	protected World world;
	protected AssetManager resources;
	protected boolean paused;
	protected boolean ready;
	protected volatile long time;
	protected long pauseStartTime;
	protected long interval;
	
	public AbstractStage(World world, AssetManager resources) {
		this.world = world;
		this.resources = resources;
		paused = false;
	}
	
	public void pause() {
		paused = true;
		pauseStartTime = System.currentTimeMillis();
	}
	
	public void resume() {
		paused = false;
		time += System.currentTimeMillis() - pauseStartTime;
		if (ready) {
			synchronized (this) {
				notifyAll();
			}
		}
	}
	
	protected void checkTime() throws InterruptedException {
		while (time > 0) {
			long tmp = time;
			time = 0;
			wait(tmp);
		}
	}
	
}
