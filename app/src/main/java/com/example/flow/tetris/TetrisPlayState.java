package com.example.flow.tetris;

public class TetrisPlayState extends TetrisGameState {
    private Tetrominos currentTetrominos;
    private Tetrominos nextTetrominos;
    private Tetrominos shadowTetrominos;
    private TetrisBoard tetrisBoard;

    public TetrisPlayState(Tetris tetris, TetrisBoard board) {
        this.tetris = tetris;
        this.tetrisBoard = board;
        initTetrominos();
    }

    public void init() {
        this.tetrisBoard.init();
        initTetrominos();
    }

    private void initTetrominos() {
        currentTetrominos = TetrominosFactory.create();
        nextTetrominos = TetrominosFactory.create();
        shadowTetrominos = TetrominosFactory.clone(currentTetrominos);
    }

    public void moveLeft() {
        currentTetrominos.moveLeft();
        if (!tetrisBoard.isAcceptable(currentTetrominos)) {
            currentTetrominos.moveRight();
        }
    }

    public void moveRight() {
        currentTetrominos.moveRight();
        if (!tetrisBoard.isAcceptable(currentTetrominos)) {
            currentTetrominos.moveLeft();
        }
    }

    public void rotate() {
        currentTetrominos.rotate();
        if (!tetrisBoard.isAcceptable(currentTetrominos)) {
            currentTetrominos.preRotate();
        }
    }

    public void moveDown() {
        currentTetrominos.moveDown();
        if (!tetrisBoard.isAcceptable(currentTetrominos)) {
            currentTetrominos.moveUp();
            fixCurrentBlock();
            updateBoard();
            updateBlock();
        }
    }

    public void moveBottom() {
        while (tetrisBoard.isAcceptable(currentTetrominos)) {
            currentTetrominos.moveDown();
        }
        currentTetrominos.moveUp();
    }

    private void fixCurrentBlock() {
        tetrisBoard.addTetrominos(currentTetrominos);
    }

    private void updateBlock() {
        currentTetrominos = nextTetrominos;
        shadowTetrominos = TetrominosFactory.clone(currentTetrominos);
        nextTetrominos = TetrominosFactory.create();
    }

    public boolean gameOver() {
        return !tetrisBoard.isAcceptable(currentTetrominos);
    }

    private void updateScore(int removedLines) {
        tetris.addRemoveLineCount(removedLines);
        if (tetrisBoard.isClear()) {
            tetris.ClearBoard();
        }
        tetris.updateHighScore();
    }

    private void updateBoard() {
        int removedLine = tetrisBoard.arrange();
        updateScore(removedLine);
    }

    public Tetrominos getCurrentTetrominos() {
        return currentTetrominos;
    }

    public Tetrominos getNextTetrominos() {
        return nextTetrominos;
    }

    public Tetrominos getShodowTetrominos() {
        moveShadowBottom();
        return shadowTetrominos;
    }

    private void moveShadowBottom() {
        shadowTetrominos.clone(currentTetrominos);

        while (tetrisBoard.isAcceptable(shadowTetrominos)) {
            shadowTetrominos.moveDown();
        }
        shadowTetrominos.moveUp();
    }

    public boolean isPlayState() {
        return true;
    }
}