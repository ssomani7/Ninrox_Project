package com.example.game;

import com.example.game.Ninja;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Wall {
	public int speed, height, width;
	Bitmap w;
	GameView gView;
	int total, iHeight, iWidth, wallCount = 0, srcY;
	int wallSpeedX, wallSpeedY;
	Ninja nin;
	Rect src, dst;

	public Wall(GameView gameView, Bitmap leftWall) {
		// TODO Auto-generated constructor stub

		w = leftWall;
		gView = gameView;
		iHeight = w.getHeight();
		iWidth = w.getWidth();

		wallSpeedY = 0;
	}

	void update() {

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		wallCount += wallSpeedY;
		srcY = 941 - ((wallCount) % 941);

		src = new Rect(0, srcY, iWidth, srcY + 400);
		dst = new Rect(0, 0, gView.getWidth(), gView.getHeight());

	}

	public void onDraw(Canvas c) {
		update();
		c.drawBitmap(w, src, dst, null);

	}

}
