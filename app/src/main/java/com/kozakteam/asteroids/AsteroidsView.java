package com.kozakteam.asteroids;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.renderscript.ScriptGroup;
import android.view.MotionEvent;

/**
 * Klasa, w której dodajemy odpowiedni renderer, żeby później móc
 * nadpisać onTouchListener,
 * inicjalizujemy game managera i ustawiamy wersję openGL
 */

class AsteroidsView extends GLSurfaceView {

    GameManager gameManager;
    private InputController inputController;


    public AsteroidsView(Context ctx, int x, int y) {
        super(ctx);
        inputController = new InputController(x, y);
        gameManager = new GameManager(x, y);
        setEGLContextClientVersion(2);
        setRenderer(new AsteroidsRenderer(gameManager, inputController));
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        inputController.handleInput(motionEvent, gameManager);
        return true;
    }
}
