package com.example.flow.tetrisgame;

import com.example.flow.player.PlayerUI;
import com.example.flow.player.TetrisButton;
import com.example.flow.R;
import com.example.flow.tetris.Tetris;
import com.example.flow.tetris.Tetrominos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class PlayerDrawImpl extends PlayerUI {
    private Context mContext;
    private int BLOCK_IMAGE_SIZE = 60;
    public int BOARD_WIDTH = 10;
    public int BOARD_HEIGHT = 20;

    Paint mPaint;

    Bitmap mGameBack;
    Bitmap mGameStart;
    Bitmap mGameOver;

    Bitmap leftArrow;
    Bitmap rightArrow;
    Bitmap downArrow;
    Bitmap bottomArrow;
    Bitmap rotateArrow;
    Bitmap playBtn;
    Bitmap pauseBtn;

    Bitmap[] mTile = new Bitmap[10];

    boolean isLoadedImage = false;
    private BoardProfile profile;

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

    public PlayerDrawImpl(Context context, BoardProfile profile) {
        mContext = context;
        this.profile = profile;
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isLoadedImage) {
            return;
        }

        int i = 0;
        int j = 0;
        int startX = profile.startX;
        int startY = profile.startY;

        int width = player.getWidth();
        int height = player.getHeight();

        canvas.drawBitmap(mGameBack, null, new Rect(0, 0, profile.screenWidth(), profile.screenHeight()), null);

        Paint paint = new Paint();
        paint.setAlpha(128);

        int[][] m_Board = player.getBoard();

        // Draw board
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {

                if (Tetris.EMPTY == m_Board[j][i]) {
                    canvas.drawBitmap(mTile[m_Board[j][i]], null,
                            new Rect(i * BLOCK_IMAGE_SIZE + startX,
                                    j * BLOCK_IMAGE_SIZE + startY,
                                    i * BLOCK_IMAGE_SIZE + startX + BLOCK_IMAGE_SIZE,
                                    j * BLOCK_IMAGE_SIZE + startY + BLOCK_IMAGE_SIZE), paint);
                } else {
                    canvas.drawBitmap(mTile[m_Board[j][i]], null,
                            new Rect(i * BLOCK_IMAGE_SIZE + startX,
                                    j * BLOCK_IMAGE_SIZE + startY,
                                    i * BLOCK_IMAGE_SIZE + startX + BLOCK_IMAGE_SIZE,
                                    j * BLOCK_IMAGE_SIZE + startY + BLOCK_IMAGE_SIZE), null);
                }
            }
        }

        mPaint.setTextSize(BLOCK_IMAGE_SIZE);
        canvas.drawText("Score", startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-2)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize((int)(BLOCK_IMAGE_SIZE * 0.8));
        canvas.drawText(Integer.toString(player.getScore()),startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-1)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize(BLOCK_IMAGE_SIZE);
        canvas.drawText("High", startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-4)*BLOCK_IMAGE_SIZE, mPaint);
        mPaint.setTextSize((int)(BLOCK_IMAGE_SIZE * 0.8));
        canvas.drawText(Integer.toString(player.getHighScore()), startX + (BOARD_WIDTH+1)*BLOCK_IMAGE_SIZE, startY + (BOARD_HEIGHT-3)*BLOCK_IMAGE_SIZE, mPaint);

        canvas.drawBitmap(bottomArrow, null, bottomArrowBtn.toRect(), null);

        canvas.drawBitmap(leftArrow, null, leftArrowBtn.toRect(), null);
        canvas.drawBitmap(downArrow, null, downArrowBtn.toRect(), null);
        canvas.drawBitmap(rotateArrow, null, rotateArrowBtn.toRect() , null);
        canvas.drawBitmap(rightArrow, null, rightArrowBtn.toRect(), null);


        if (player == null) {
            Log.d("Tetris", "Tetris is null");
            return;
        }

        if (player.isIdleState()) {
            canvas.drawBitmap(mGameStart, null, startButton.toRect(), null);
            canvas.drawBitmap(playBtn, null, playArrowBtn.toRect(), null);
        } else if (player.isGameOverState()) {
            canvas.drawBitmap(mGameOver, null, gameoverButton.toRect(), null);
            canvas.drawBitmap(playBtn, null, playArrowBtn.toRect(), null);
        } else if (player.isPlayState()) {

            if (player.isEnableShadow()) {
                Tetrominos sblock = player.getShadowBlock();
                onDrawBlock(canvas, sblock, startX, startY, paint);
            }

            Tetrominos block = player.getCurrentBlock();
            onDrawBlock(canvas, block, startX, startY);

            Tetrominos nextTetrominos = player.getNextBlock();
            int nStartX = startX + profile.blockSize() * (BOARD_WIDTH - 2);
            int nStartY = startY + 5 * BLOCK_IMAGE_SIZE;
            onDrawBlock(canvas, nextTetrominos, nStartX, nStartY);

            canvas.drawBitmap(pauseBtn, null, pauseArrowBtn.toRect(), null);
        } else if (player.isPauseState()) {
            canvas.drawBitmap(mGameStart, null, startButton.toRect(), null);
            canvas.drawBitmap(playBtn, null, playArrowBtn.toRect(), null);
            mPaint.setTextSize(30);
            canvas.drawText("[" + screenWidth + "x" + screenHeight + "]", startX, (int)(startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1)), mPaint);
        }
    }

    private void init() {
        loadImage();

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);

        isLoadedImage = true;

        BLOCK_IMAGE_SIZE = profile.blockSize();
        BOARD_WIDTH = profile.boardWidth;
        BOARD_HEIGHT = profile.boardHeight;

        initButton();
    }

    private void initButton() {
        int startX = profile.startX;
        int startY = profile.startY;

        bottomArrowBtn = new TetrisButton("BottomArrow", 4, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());

        leftArrowBtn = new TetrisButton("LeftArrow", 0, startX, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        downArrowBtn = new TetrisButton("DownArrow", 1, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        rotateArrowBtn = new TetrisButton("RotateArrow", 2, startX + BLOCK_IMAGE_SIZE * 8, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());
        rightArrowBtn = new TetrisButton("RightArrow", 3, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 5),  profile.buttonSize(), profile.buttonSize());

        playArrowBtn = new TetrisButton("PlayArrow", 5, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());
        pauseArrowBtn = new TetrisButton("PlayArrow", 6, startX + BLOCK_IMAGE_SIZE * 12, startY + BLOCK_IMAGE_SIZE * (BOARD_HEIGHT + 1),  profile.buttonSize(), profile.buttonSize());

        startButton = new TetrisButton("StartButton", 7, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
        playButton = new TetrisButton("PlayButton", 8, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
        gameoverButton = new TetrisButton("GameoverButton", 9, startX + BLOCK_IMAGE_SIZE * 4, startY + BLOCK_IMAGE_SIZE * 9,  BLOCK_IMAGE_SIZE * 6, BLOCK_IMAGE_SIZE * 3);
    }

    private void loadImage() {
        mGameBack = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.backimage);
        mGameStart = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.start);
        mGameOver = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.gameover);

        mTile[0] =  BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.black);
        mTile[1] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.yellow);
        mTile[2] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.blue);
        mTile[3] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.red);
        mTile[4] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.gray);
        mTile[5] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.green);
        mTile[6] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.magenta);
        mTile[7] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.orange);
        mTile[8] = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.cyan);

        leftArrow = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.left);
        rightArrow = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.right);
        downArrow  = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.down);
        rotateArrow = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.rotate);
        playBtn = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.play);
        pauseBtn = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pause);
        bottomArrow =  BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bottom);
    }

    public void onDrawBlock(Canvas canvas, Tetrominos block, int startX, int startY) {
        onDrawBlock(canvas, block, startX, startY, null);
    }

    public void onDrawBlock(Canvas canvas, Tetrominos block, int startX, int startY, Paint paint) {
        int [][]m_block = block.getBlock();

        int i = 0, j = 0;
        int w = block.getWidth();
        int h = block.getHeight();
        int x = block.getX();
        int y = block.getY();
        int type = block.getType();

        for (i = 0; i < w; i++) {
            for (j = 0; j < h; j++) {
                if (m_block[j][i] != Tetris.EMPTY) {
                    canvas.drawBitmap(mTile[type], null,
                            new Rect((x + i) * BLOCK_IMAGE_SIZE + startX,
                                    (y + j) * BLOCK_IMAGE_SIZE + startY,
                                    (x + i + 1) * BLOCK_IMAGE_SIZE + startX,
                                    (y + j+ 1) * BLOCK_IMAGE_SIZE + startY),
                            paint);
                }
            }
        }
    }

}
