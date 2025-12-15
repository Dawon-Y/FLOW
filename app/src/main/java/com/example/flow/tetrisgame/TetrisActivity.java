package com.example.flow.tetrisgame;

import android.app.*;
import android.os.Bundle;
import android.util.Log;

import com.example.flow.player.Player;
import com.example.flow.player.PlayerImpl;

public class TetrisActivity extends Activity {
	TetrisView twN8;
	BoardProfile profile;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		profile = new BoardProfile(screenWidth, screenHeight);

		Player player = new PlayerImpl(profile);
		twN8 = new TetrisView(this, player, profile);
		setContentView(twN8);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (twN8 != null) {
			twN8.pauseGame();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (twN8 != null) {
			twN8.resumeGame();
		}
	}
}