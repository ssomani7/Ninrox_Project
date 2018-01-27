package com.example.game;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CreditsActivity extends Activity {

	CreditsView myView;
	int eventAction;
	//MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.credits_view);
		myView = (CreditsView) findViewById(R.id.cred);
		myView.setKeepScreenOn(true);
	

	}

	@Override
	protected void onPause() {
		super.onPause();
		
		myView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//mPlayer.start();
		myView.onResume();
	}

	public void onStart() {
		super.onStart();
	}

	public void onRestart() {
		super.onRestart();
	}
	
	public void onStop(){
		super.onStop();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
}

