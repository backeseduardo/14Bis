package br.upf.pos.jogosandroid.a14bis;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import br.upf.pos.jogosandroid.a14bis.scene.MenuScene;
import br.upf.pos.jogosandroid.a14bis.util.Sound;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar().hide();

        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);

        CCDirector.sharedDirector().attachInView(surfaceView);
        CCDirector.sharedDirector().setScreenSize(320, 480);

        CCDirector.sharedDirector().runWithScene(new MenuScene());

//        SharedPreferences prefs = CCDirector.sharedDirector().getActivity().getSharedPreferences("14BisPrefs", Context.MODE_PRIVATE);
//        prefs.edit().putInt("score", 0).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Sound.stopBackgroundMusic();
    }
}
