package br.upf.pos.jogosandroid.a14bis.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.cocos2d.nodes.CCDirector;

/**
 * Created by backes on 6/23/17.
 */

public class ScoreUtil {

    private static ScoreUtil instance;
    private SharedPreferences prefs;

    private ScoreUtil() {
        prefs = CCDirector.sharedDirector().getActivity().getSharedPreferences("14BisPrefs", Context.MODE_PRIVATE);
    }

    public static ScoreUtil getInstance() {
        if (instance == null)
            instance = new ScoreUtil();

        return instance;
    }

    public int getHighScore() {
        return prefs.getInt("score", 0);
    }

    public void setHighScore(int score) {
        if (score > prefs.getInt("score", 0)) {
            prefs.edit().putInt("score", score).apply();
        }
    }

}
