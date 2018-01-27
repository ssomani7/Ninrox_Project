package com.example.game;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ninja {

	int x, y, speedX, speedY, oX[], oY[], ospeedX, ospeedY, pY;
	int oWidth, oHeight, obs[];
	boolean isJumping = false, isRight = false, dead = false, present = false,
			isInvisible = false, powerPresent = false, moPresent = false;
	int height, width, wallwidth ;
	Bitmap n, o, f, p, movO;
	GameView gView;
	int currentFrame = 0, gravity = 7, counter = 0;
	Rect src, dst, osrc[], odst[];
	int srcX, srcY, frameH, score, time, scoreInc, movoX, movoY,
			mspeedY;
	Wall w;
	Random r1;

	public Ninja(GameView gameView, Bitmap ninja, Bitmap obstacle, Wall side,
			Bitmap fall, Bitmap powerup, Bitmap movingObs) {
		w = side;
		n = ninja;
		gView = gameView;
		height = (n.getHeight() / 6) + 2;
		width = n.getWidth() / 5;
		movO = movingObs;
		x = 0;
		score = 5;
		scoreInc = 5;
		y = 850;
		time = 0;
		speedX = 0;
		speedY = 15;
		o = obstacle;
		mspeedY = 50;
		p = powerup;
		oX = new int[2];
		oY = new int[2];
		oY[1] = oY[0] - 300;
		obs = new int[2];
		osrc = new Rect[2];
		odst = new Rect[2];
		oWidth = o.getWidth() / 2;
		oHeight = o.getHeight() / 3;
		f = fall;
		r1 = new Random();
		wallwidth = 45;
	}

	void update(Canvas c) {
		if (y <= 500) {
			speedY = 0;
			if (w.wallSpeedY == 0)
				w.wallSpeedY = 15;
			else {
				if (score % 1000 == 0) {
					w.wallSpeedY += 5;
					scoreInc += 5;
				}
			}

		} else {
			speedY = 15;
			w.wallSpeedY = 0;

		}

		ospeedY = -2 * w.wallSpeedY;
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentFrame = ++currentFrame % 6;
		x += speedX;
		y -= speedY;
		score += scoreInc;
	}

	void obstacles(Canvas c) {

		if (!moPresent) {
			if (!present) {
				obs[0] = r1.nextInt(3);
				present = true;
			}

			switch (obs[0]) {

			case 0:
				osrc[0] = new Rect(0, 0, oWidth, oHeight);
				oX[0] = 0;
				osrc[1] = new Rect(oWidth, 0, 2 * oWidth, oHeight);
				oX[1] = c.getWidth() - oWidth;
				break;

			case 1:
				osrc[0] = new Rect(0, oHeight, oWidth, 2 * oHeight);
				oX[0] = 0;
				osrc[1] = new Rect(oWidth, oHeight, 2 * oWidth, 2 * oHeight);
				oX[1] = c.getWidth() - oWidth;
				break;

			case 2:
				osrc[0] = new Rect(0, 2 * oHeight, oWidth / 2, 3 * oHeight);
				oX[0] = 0;
				osrc[1] = new Rect(oWidth + (oWidth / 2), 2 * oHeight,
						2 * oWidth, 3 * oHeight);
				oX[1] = c.getWidth() - oWidth / 2;
				break;

			}
			if (score % 1000 == 0 && !powerPresent) {
				powerPresent = true;
				pY = y - 200;
			}

			if (powerPresent) {

				c.drawBitmap(p, 32, pY, null);

				if (x >= 32 && x <= p.getWidth() && y >= pY
						&& y <= pY + p.getHeight()) {
					isInvisible = true;
					powerPresent = false;
				}
				if (pY > c.getHeight() && !isInvisible)
					powerPresent = false;
			}

			if (isInvisible) {
				counter += 1;
				if (counter == 100) {
					isInvisible = false;
					counter = 0;
				}
			}

			for (byte i = 0; i < 2; i++) {
				if (obs[0] == 2) {
					odst[0] = new Rect(oX[0], oY[0], oX[0] + oWidth / 2, oY[0]
							+ oHeight);
					odst[1] = new Rect(oX[1], oY[1], oX[1] + oWidth / 2, oY[1]
							+ oHeight);
				} else
					odst[i] = new Rect(oX[i], oY[i], oX[i] + oWidth, oY[i]
							+ oHeight);

				if (obs[i] < 2) {
					if (x >= oX[i] && x <= oX[i] + oWidth && y <= oY[i]
							&& y >= oY[i] - oHeight && !isInvisible) {
						dead = true;
					}
				} else {
					if (x >= oX[i] && x <= oX[i] + oWidth / 2 && y <= oY[i]
							&& y >= oY[i] - oHeight && !isInvisible) {
						dead = true;
					}
				}

				c.drawBitmap(o, osrc[i], odst[i], null);
			}
			if (oY[1] > c.getHeight()) {
				oY[0] = -50;
				oY[1] = -350;
				present = false;
				if (r1.nextInt(2) == 1) {
					movoX = c.getWidth();
					movoY = y - c.getWidth();
					moPresent = true;
				}
			}

			if (speedY == 0) {
				oY[0] -= ospeedY;
				oY[1] -= ospeedY;
				pY -= ospeedY;
			}
		} else {

			int tx, ty;
			tx = x + width / 2;
			ty = y + height / 2;
			
			if (Math.sqrt(Math.pow(tx - movoX, 2) + Math.pow(ty - movoY, 2)) < (width
				+ movO.getWidth()) / 2 && !isJumping && !isInvisible)
				dead = true;
			else {
				c.drawBitmap(movO, movoX + movO.getWidth() / 2,
						movoY - movO.getHeight() / 2, null);

				movoY += mspeedY;
				movoX -= (mspeedY - w.wallSpeedY/2);
				if (movoX < 0) {
					moPresent = false;
				}

			}

		}

	}

	void reset(Canvas c) {

		x = 0;
		score = 5;
		y = 850;
		speedX = 0;
		oY[0] = 0;
		oY[1] = -300;
		oX[0] = oX[1] = 0;
		time = 0;
		speedY = 5;
		currentFrame = 0;
		time = 0;
		counter = 0;
		gravity = 7;
		isInvisible = false;
		powerPresent = false;
		dead = false;
		isJumping = false;
		ospeedX = ospeedY = 0;
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas c) {
		frameH = c.getHeight();
		update(c);
		obstacles(c);
		if (!dead) {
			if (!isJumping) {
				if (x >= c.getWidth() - width) {
					if (isInvisible)
						srcX = 4 * width;
					else
						srcX = width;
					x = c.getWidth() - wallwidth;
					dst = new Rect(x - width, y, x, y + height);
				} else {
					if (isInvisible)
						srcX = 3 * width;
					else
						srcX = 0;
					x = wallwidth;
					dst = new Rect(x, y, x + width, y + height);
				}
			} else {
				srcX = 2 * width;
				if (isRight)
					speedX = -100;
				else
					speedX = 100;
				speedY = 0;
				if (x >= c.getWidth() - width  && isRight == false) {
					isJumping = false;
					isRight = true;
					speedX = 0;
					speedY = 0;
				}
				if (x <= width && isRight == true) {
					isJumping = false;
					isRight = false;
					speedX = 0;
					speedY = 0;
				}
				if (!isRight)
					dst = new Rect(x, y, x + width, y + height);
				else
					dst = new Rect(x - width, y, x, y + height);
			}
			srcY = currentFrame * height;
			src = new Rect(srcX, (5 * height) - srcY, srcX + width,
					(5 * height) - srcY + height);
			c.drawBitmap(n, src, dst, null);
		} else {
			y += gravity;
			gravity += 3;
			w.wallSpeedY = 0;
			if(time <= 5)
			c.drawBitmap(f, x, y, null);
			time += 1;
		}

	}

}
