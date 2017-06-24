package br.upf.pos.jogosandroid.a14bis.util;

/**
 * Created by backes on 6/17/17.
 */

public class GameStatus {

    private boolean isPlaying;
    private int score;

    private static GameStatus instance = null;

    private GameStatus() {
        score = 0;
    }

    public static GameStatus getInstance() {
        if (instance == null)
            instance = new GameStatus();

        return instance;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
