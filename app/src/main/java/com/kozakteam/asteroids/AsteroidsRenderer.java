package com.kozakteam.asteroids;

import android.graphics.PointF;
import android.opengl.GLSurfaceView.Renderer;
import android.provider.Settings;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.orthoM;

public class AsteroidsRenderer implements Renderer {

    boolean debbuging = true;

    //zmienne do kontrolowania FPS
    long frameCounter = 0;
    long averageFPS = 0;
    private long fps;

    //macierz, której openGL użyje, żeby policzyć sobie co tam chcemy rysować
    private final float[] viewportMatrix = new float[16];

    private GameManager gameManager;

    //żeby nie musieć tworzyć nowych obiektów w krytycznych miejscach
    PointF pointF;
    PointF pointF2;

    public AsteroidsRenderer(GameManager gameManager) {
        this.gameManager = gameManager;
        pointF = new PointF();
        pointF2 = new PointF();
    }

    //wywoływana za każdym razem, gdy tworzone jest GLSurfaceView, ustawiamy kolor, ktory będzie przy czyszczeniu ekranu
    //budujemy nasz shader i wywołujemy create objects
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLManager.buildProgram();
        createObjects();
    }

    //robimy fullscreen, inicjalizujemy macierz
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        glViewport(0, 0, width, height);
        orthoM(viewportMatrix, 0, 0, gameManager.mToShowX, 0, gameManager.mToShowY, 0f, 1f);
    }

    //tworzymy statek na środku ekranu
    private void createObjects() {
        gameManager.spaceShip = new SpaceShip(gameManager.mapWidth / 2, gameManager.mapHeight / 2);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        long startFrameTime = System.currentTimeMillis();

        if (gameManager.isPlaying()) {
            update(fps);
        }

        draw();

        long timeThisFrame = System.currentTimeMillis() - startFrameTime;

        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame;
        }

        if (debbuging) {
            frameCounter++;
            averageFPS = averageFPS + fps;
            if (frameCounter > 100) {
                averageFPS = averageFPS / frameCounter;
                frameCounter = 0;
                Log.e("averageFPS:", " " + averageFPS);
            }
        }
    }

    private void update(long fps) {
    }

    private void draw() {
        //pozycja statku
        pointF = gameManager.spaceShip.getLocation();
        orthoM(viewportMatrix, 0,
                pointF.x - gameManager.mToShowX / 2,
                pointF.x + gameManager.mToShowX / 2,
                pointF.y - gameManager.mToShowY / 2,
                pointF.y + gameManager.mToShowY / 2,
                0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);

        gameManager.spaceShip.draw(viewportMatrix);
    }


}
