package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	MainView myView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_view);
		myView = (MainView) findViewById(R.id.view);
		myView.setKeepScreenOn(true);
	
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
