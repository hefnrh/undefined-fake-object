package com.me.mygdxgame.stage;

import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractStage implements Runnable {

	protected World world;
	protected AssetManager resources;
	protected boolean paused;
	protected boolean ready;
	protected volatile long time;
	protected long pauseStartTime;
	protected long readyTime;
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
		if (ready) {
			time += readyTime - pauseStartTime;
			synchronized (this) {
				notifyAll();
			}
		} else {
			time += System.currentTimeMillis() - pauseStartTime;
		}
	}

	protected void checkTime(long waitTime) {
		ready = false;
		try {
			wait(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ready = true;
		if (paused) {
			readyTime = System.currentTimeMillis();
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (time > 0) {
			long tmp = time;
			time = 0;
			checkTime(tmp);
		}
	}

}
