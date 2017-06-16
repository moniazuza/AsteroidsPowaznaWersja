package com.kozakteam.asteroids;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class AsteroidsActivity extends AppCompatActivity {

    private GLSurfaceView asteroidsView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        asteroidsView=new AsteroidsView(this, resolution.x, resolution.y);

        setContentView(asteroidsView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        asteroidsView.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        asteroidsView.onResume();
    }


}
