package com.example.flow.tetris;

public class TetrisIdleState extends TetrisGameState {

    /**
     * Default constructor
     */
    public TetrisIdleState(Tetris tetris) {
        this.tetris = tetris;
    }
    public boolean isIdleState() {
       return true;
    }
}