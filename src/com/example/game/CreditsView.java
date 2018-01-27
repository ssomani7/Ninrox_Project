package com.example.game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Intent;

//import android.media.MediaPlayer;

public class CreditsView extends SurfaceView implements Runnable,
		SurfaceHolder.Callback {

	Thread t = null;
	SurfaceHolder holder;
	boolean running = false;
	int screenW;
	Bitmap background;

	// Initialization of bitmaps
	public CreditsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.credits);
		
	}


public void run() {
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
	c.drawBitmap(background, 0, 0, null);
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
}

public void onResume() {
	running = true;
	t = new Thread(this);
	t.start();
}

@Override
public boolean onTouchEvent(MotionEvent event) {

	int eventaction = event.getAction();
	int X = (int) event.getX();
	int Y = (int) event.getY();
	switch (eventaction) {
	case MotionEvent.ACTION_UP:
		if(Y<273 || Y>751 || (X<49 && Y>275 && Y<751) || (X>426 &&  Y>275 && Y<751 )){
			 Intent data = new Intent();
			 data.putExtra("selected_states", "");                                       
			 ((CreditsActivity)getContext()).setResult(0,data);
		        ((CreditsActivity)getContext()).finish();
		       
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
