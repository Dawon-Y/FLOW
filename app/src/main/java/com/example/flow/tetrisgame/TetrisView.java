package com.example.flow.tetrisgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.flow.player.Player;
import com.example.flow.player.PlayerInput;
import com.example.flow.player.PlayerObserver;
import com.example.flow.player.PlayerUI;
import com.example.flow.tetris.Score;

public class TetrisView extends View implements PlayerObserver {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Player player;
    private PlayerInput playerInput;
    private PlayerUI playerUI;
    private Score playerScore;

    private HandlerThread playerHandlerThread;
    private Handler playerHandler;

    private int highScore = 0;
    private static final int EMPTY_MESSAGE = 0;

    private BoardProfile profile;

    public TetrisView(Context context, Player player, BoardProfile profile) {
        super(context);
        init(context, player, profile);
    }

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TetrisView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, Player player, BoardProfile profile) {
        this.mContext = context;
        this.profile = profile;
        this.player = player;

        loadHighScore();

        this.playerInput = new PlayerInputImpl(profile);
        this.playerUI = new PlayerDrawImpl(context, profile);
        this.playerScore = new PlayerScoreImpl();
        this.playerScore.setHighScore(highScore);

        player.setInputDevice(playerInput);
        player.setView(playerUI);
        player.setSCore(playerScore);
        player.register(this);
        player.init();

        setScreenSize(profile.screenWidth(), profile.screenHeight());

        createPlayerThread();
    }

    private void createPlayerThread() {
        Log.d(LOG_TAG, "createPlayerThread()");
        playerHandlerThread = new HandlerThread("TetrisThread");
        playerHandlerThread.start();

        playerHandler = new Handler(playerHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (player != null && player.isPlayState()) {
                    player.MoveDown();
                    int gameSpeed = 700 - (player.getScore() / 10000);
                    if (playerHandler.hasMessages(EMPTY_MESSAGE)) {
                        playerHandler.removeMessages(EMPTY_MESSAGE);
                    }
                    playerHandler.sendEmptyMessageDelayed(EMPTY_MESSAGE, gameSpeed);
                }
            }
        };
    }

    public void startGame() {
        if (playerHandler != null) {
            playerHandler.sendEmptyMessage(EMPTY_MESSAGE);
        }
    }

    public void pauseGame() {
        Log.d(LOG_TAG, "pauseGame()");
        if (playerHandler != null) {
            playerHandler.removeMessages(EMPTY_MESSAGE);
        }

        if (player != null) {
            player.pause();
        }

        if (playerHandlerThread != null) {
            playerHandlerThread.quitSafely();
            try {
                playerHandlerThread.join();
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Thread join interrupted", e);
            }
            playerHandlerThread = null;
            playerHandler = null;
        }

        saveScore();
    }

    public void resumeGame() {
        Log.d(LOG_TAG, "resumeGame()");
        if (playerHandler == null) {
            createPlayerThread();
            startGame();
        }
    }

    public void setScreenSize(int width, int height) {
        if (playerUI != null) {
            playerUI.setScreenSize(width, height);
        }
    }

    @Override
    public void update() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (playerUI != null) {
            playerUI.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (playerInput == null || event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        return playerInput.touch(x, y);
    }

    private void loadHighScore() {
        SharedPreferences pref = mContext.getSharedPreferences("choboTetris", Context.MODE_PRIVATE);
        this.highScore = pref.getInt("highscore", 0);
    }

    private void saveScore() {
        if (player == null || highScore > player.getHighScore()) return;

        SharedPreferences pref = mContext.getSharedPreferences("choboTetris", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("highscore", player.getHighScore());
        edit.apply();
    }
}
