package com.example.flow.tetris;

public class TetrisPauseState extends TetrisGameState {

    /**
     * Default constructor
     */
    public TetrisPauseState(Tetris tetris) {
        this.tetris = tetris;
    }

    public boolean isPauseState() { return true; }
}