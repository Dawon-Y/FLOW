package com.example.flow.tetrisgame;

import android.util.Log;

import com.example.flow.player.PlayerInput;
import com.example.flow.player.TetrisButton;

public class PlayerInputImpl extends PlayerInput {
    private final String TAG = this.getClass().getName();
    BoardProfile profile;

    TetrisButton bottomArrowBtn;
    TetrisButton leftArrowBtn;
    TetrisButton rightArrowBtn;
    TetrisButton rotateArrowBtn;
    TetrisButton downArrowBtn;
    TetrisButton playArrowBtn;
    TetrisButton pauseArrowBtn;

    TetrisButton startButton;
    TetrisButton playButton;
    TetrisButton gameoverButton;


    public PlayerInputImpl(BoardProfile profile) {
        this.profile = profile;
        startX = profile.startX;
        startY = profile.startY;

        initButton();
    }

    private void initButton() {
        int startX = profile.startX;
        int startY = profile.startY;
        int BOARD_HEIGHT = profile.boardHeight;
        int BLOCK_IMAGE_SIZE = profile.blockSize();


        bottomArrowBtn = new TetrisButton("BottomArrow", 4, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());

        leftArrowBtn = new TetrisButton("LeftArrow", 0, startX, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        downArrowBtn = new TetrisButton("DownArrow", 1, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        rotateArrowBtn = new TetrisButton("RotateArrow", 2, startX + BLOCK_IMAGE_SIZE * 8, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        rightArrowBtn = new TetrisButton("RightArrow", 3, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());

        playArrowBtn = new TetrisButton("PlayArrow", 5, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());
        pauseArrowBtn = new TetrisButton("PlayArrow", 5, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());

        startButton = new TetrisButton("StartButton", 7, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
        playButton = new TetrisButton("PlayButton", 8, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
        gameoverButton = new TetrisButton("GameoverButton", 9, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
    }

    public boolean touch(int touchX, int touchY) {
        if (player == null) {
            return true;
        }

        if (startButton.in(touchX, touchY)) {
            Log.d(TAG, "touch: ");
            clickStartButton();
            return true;
        }

        if (playArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "Round button: play()");
            play();
            return true;
        }

        if (rotateArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "rotate()");
            rotate();
            return true;
        }

        if (leftArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "left()");
            left();
            return true;
        }

        if (bottomArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "bottom() down()");
            bottom();
            down();
            return true;
        }

        if (rightArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "right()");
            right();
            return true;
        }

        if (downArrowBtn.in(touchX, touchY)) {
            Log.d(TAG, "down()");
            down();
            return true;
        }

        return false;
    }
}
