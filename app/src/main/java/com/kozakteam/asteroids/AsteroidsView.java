package com.kozakteam.asteroids;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Klasa, w której dodajemy odpowiedni renderer, żeby później móc
 * nadpisać onTouchListener,
 * inicjalizujemy game managera i ustawiamy wersję openGL
 */

class AsteroidsView extends GLSurfaceView {

    GameManager gameManager;

    public AsteroidsView(Context ctx, int x, int y) {
        super(ctx);
        gameManager=new GameManager(x,y);
        setEGLContextClientVersion(2);
        setRenderer(new AsteroidsRenderer(gameManager));
    }
}
