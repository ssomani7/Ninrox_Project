package com.example.game;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.database.DBAdapter;

//import android.media.MediaPlayer;

public class GameView extends SurfaceView implements Runnable,
		SurfaceHolder.Callback {

	Thread t = null;
	SurfaceHolder holder;
	boolean running = false, touched = false,backgroundDay = true;
	Bitmap ninja, obstacle, leftWall, background, fall, gameover, powerup, background2;
	Bitmap movingObs;
	Wall left;
	Ninja ninjas;
	int screenW, activityTime = 0;
	DBAdapter db = new DBAdapter(getContext());
	MediaPlayer mPlayer;
	MainView mview;

	// Initialization of bitmaps
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		mPlayer = MediaPlayer.create(context, R.raw.shippuden);
		ninja = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
		obstacle = BitmapFactory.decodeResource(getResources(),
				R.drawable.obstacles);
		gameover = BitmapFactory.decodeResource(getResources(),
				R.drawable.gameover);
		leftWall = BitmapFactory.decodeResource(getResources(),
				R.drawable.wall2);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.wallpaper);
		fall = BitmapFactory.decodeResource(getResources(), R.drawable.fall);
		powerup = BitmapFactory.decodeResource(getResources(),
				R.drawable.powerup);
		movingObs = BitmapFactory.decodeResource(getResources(),
				R.drawable.movingobs);
		background2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.night4);
		mPlayer.setLooping(true);
	}


	
	@Override
	public void run() {
		left = new Wall(this, leftWall);
		ninjas = new Ninja(this, ninja, obstacle, left, fall, powerup,movingObs);
		mPlayer.start();
		while (running) {
			if (!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			screenW = c.getWidth();
			onDraw(c);
			holder.unlockCanvasAndPost(c);
			
			
		}

	}

	protected void onDraw(Canvas c) {
		
		if (MainView.soundOn) {
			mPlayer.setVolume(100, 100);
		}
		else
			mPlayer.setVolume(0, 0);

		if (ninjas.dead && ninjas.time == 5) {

			if (touched) {
				
				activityTime = 0;
				db.open();
				if (Integer.parseInt(db.getHighScore().getString(1)) < ninjas.score) {
					db.updateScore(ninjas.score);
				}
				db.close();
				try {
					mPlayer.prepare();
					
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				mPlayer.seekTo(0);
				mPlayer.start();

				ninjas.reset(c);
				touched = false;

			} else {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				c.drawBitmap(gameover,
						((c.getWidth() / 2) - (gameover.getWidth() / 2)),
						((c.getHeight() / 2) - (gameover.getHeight() / 2)),
						null);
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				paint.setTextSize(25);
				db.open();
				if (Integer.parseInt(db.getHighScore().getString(1)) < ninjas.score) {
					c.drawText("HighScore",
							((c.getWidth() / 2) - (gameover.getWidth() / 2)),
							((c.getHeight() / 2) - (gameover.getHeight() / 2))
									+ gameover.getHeight() + 200, paint);
				}
				c.drawText("Your Score is :" + ninjas.score,
						((c.getWidth() / 2) - (gameover.getWidth() / 2)),
						((c.getHeight() / 2) - (gameover.getHeight() / 2))
								+ gameover.getHeight() + 100, paint);
				db.close();
			}
		} else {
			
			activityTime++;
			if(activityTime>300)
			c.drawBitmap(background2, 0, 0, null);
			else
				c.drawBitmap(background, 0, 0, null);
			if(activityTime > 600)
				activityTime = 0;
			left.onDraw(c);
			ninjas.onDraw(c);
		}
	}

	public void onPause() {

		running = false;
		while (true) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
		mPlayer.release();
	}

	public void onResume() {

		running = true;
		t = new Thread(this);
		t.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			{
				if (!ninjas.dead)
					ninjas.isJumping = true;
				else
					touched = true;
			}
		}
		invalidate();
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		onResume();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		onPause();
	}

}
