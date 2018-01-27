package com.example.game;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	private Bitmap playUp, playDown, instructionsUp, instructionsDown, creditsUp, creditsDown;
	private Bitmap soundOnUp, soundOnDown, soundOffUp, soundOffDown, background;
	private int screenW;
	private int screenH;
	private boolean playPressed = false,instructionsPressed = false, creditsPressed = false, soundPressed = false;
	private Paint paint = new Paint();
	public static boolean soundOn=true;
	Rect src,dst;
	public MainView(Context context,AttributeSet attrs) {
		super(context,attrs);
		playUp = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn1);
		playDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn2);
		instructionsUp = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn3);
		instructionsDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn4);
		creditsUp = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn5);
		creditsDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn6);
		soundOnUp = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn7);
		soundOnDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn8);
		soundOffUp = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn9);
		soundOffDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn10);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawBitmap(background,0,0, paint);
		
		if (!playPressed) {
			canvas.drawBitmap(playUp,
					(screenW - playUp.getWidth()) / 2,
					(int) (screenH * 0.5), null);
		} else {
			canvas.drawBitmap(playDown,
					(screenW - playDown.getWidth()) / 2,
					(int) (screenH * 0.5), null);
		}
		
		if (!instructionsPressed) {
			canvas.drawBitmap(instructionsUp,
					(screenW - playUp.getWidth()) / 2,
					(int) (screenH * 0.6), null);
		} else {
			canvas.drawBitmap(instructionsDown,
					(screenW - playDown.getWidth()) / 2,
					(int) (screenH * 0.6), null);
		}
		
		if (soundOn) {
			if(!soundPressed){
			canvas.drawBitmap(soundOnUp,
					(screenW - playUp.getWidth()) / 2,
					(int) (screenH * 0.7), null);
			} else {
			canvas.drawBitmap(soundOnDown,
					(screenW - playDown.getWidth()) / 2,
					(int) (screenH * 0.7), null);
					soundOn = false;
			}
		}
		
		else{
			if(!soundPressed){
				canvas.drawBitmap(soundOffUp,
						(screenW - playUp.getWidth()) / 2,
						(int) (screenH * 0.7), null);
				} else {
				canvas.drawBitmap(soundOffDown,
						(screenW - playDown.getWidth()) / 2,
						(int) (screenH * 0.7), null);
						soundOn = true;
				}
		}
		
		
		if (!creditsPressed) {
			canvas.drawBitmap(creditsUp,
					(screenW - playUp.getWidth()) / 2,
					(int) (screenH * 0.8), null);
		} else {
			canvas.drawBitmap(creditsDown,
					(screenW - playDown.getWidth()) / 2,
					(int) (screenH * 0.8), null);
		}
		
	
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			if ((X > (screenW - playUp.getWidth()) / 2 && X < ((screenW - playUp
					.getWidth()) / 2) + playUp.getWidth())
					&& Y > (int) (screenH * 0.5)
					&& Y < (int) (screenH * 0.5) + playUp.getHeight()) {
				playPressed = true;
			}
			
			if ((X > (screenW - playUp.getWidth()) / 2 && X < ((screenW - playUp
					.getWidth()) / 2) + playUp.getWidth())
					&& Y > (int) (screenH * 0.6)
					&& Y < (int) (screenH * 0.6) + playUp.getHeight()) {
				instructionsPressed = true;
			}
			
			if ((X > (screenW - playUp.getWidth()) / 2 && X < ((screenW - playUp
					.getWidth()) / 2) + playUp.getWidth())
					&& Y > (int) (screenH * 0.8)
					&& Y < (int) (screenH * 0.8) + playUp.getHeight()) {
				creditsPressed = true;
			}
			if ((X > (screenW - playUp.getWidth()) / 2 && X < ((screenW - playUp
					.getWidth()) / 2) + playUp.getWidth())
					&& Y > (int) (screenH * 0.7)
					&& Y < (int) (screenH * 0.7) + playUp.getHeight()) {
				soundPressed = true;
			}
			break;
			
			
			
		case MotionEvent.ACTION_UP:
			if (playPressed) {
				Intent gameIntent = new Intent(getContext(), GameActivity.class);
				getContext().startActivity(gameIntent);
				playPressed = false;
			}
			
			
			if (instructionsPressed) {
				Intent instructionsIntent = new Intent(getContext(), InstructActivity.class);
				((MainActivity) getContext()).startActivityForResult(instructionsIntent,0);
				instructionsPressed = false;
			}
			
			
			if (creditsPressed) {
				Intent creditsIntent = new Intent(getContext(), CreditsActivity.class);
				((MainActivity) getContext()).startActivityForResult(creditsIntent,0);
				creditsPressed = false;
			}
			
			if (soundPressed) 
			soundPressed = false;
		
			break;

		}
		invalidate();
		return true;
	}
}
