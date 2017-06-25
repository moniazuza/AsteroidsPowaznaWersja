package com.kozakteam.asteroids;

/**
 * Created by user on 25.06.2017.
 */

import android.graphics.PointF;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.orthoM;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.kozakteam.asteroids.GLManager.A_POSITION;
import static com.kozakteam.asteroids.GLManager.COMPONENTS_PER_VERTEX;
import static com.kozakteam.asteroids.GLManager.FLOAT_SIZE;
import static com.kozakteam.asteroids.GLManager.STRIDE;
import static com.kozakteam.asteroids.GLManager.U_COLOR;
import static com.kozakteam.asteroids.GLManager.U_MATRIX;


public class GameButton {


    // koordynacja GL (-1,-1 to 1,1)
    // rysujemy na ekranie
    private final float[] viewportMatrix = new float[16];

    // kompilacja i laczenie cieni
    private static int glProgram;
    // ile kawalkow potrzeba do rysowania naszego obiekt

    private int numVertices;

    private FloatBuffer vertices;


    public GameButton(int top, int left,
                      int bottom, int right, GameManager gameManager) {
        //HUD

        orthoM(viewportMatrix, 0, 0,
                gameManager.screenWidth, gameManager.screenHeight, 0, 0, 1f);

        int width = (right - left) / 2;
        int height = (top - bottom) / 2;
        left = left + width / 2;
        right = right - width / 2;
        top = top - height / 2;
        bottom = bottom + height / 2;
        PointF p1 = new PointF();
        p1.x = left;
        p1.y = top;
        PointF p2 = new PointF();
        p2.x = right;
        p2.y = top;
        PointF p3 = new PointF();
        p3.x = right;
        p3.y = bottom;
        PointF p4 = new PointF();
        p4.x = left;
        p4.y = bottom;

        // dodanie 4 punkow do buttons, podanie tylko wymiarow
        float[] modelVertices = new float[]{
                // A line from point 1 to point 2
                p1.x, p1.y, 0,
                p2.x, p2.y, 0,
                // Point 2 to point 3
                p2.x, p2.y, 0,
                p3.x, p3.y, 0,
                // Point 3 to point 4
                p3.x, p3.y, 0,
                p4.x, p4.y, 0,
                // Point 4 to point 1
                p4.x, p4.y, 0,
                p1.x, p1.y, 0
        };


        //ile elementow bedzie uzywane
        final int ELEMENTS_PER_VERTEX = 3;// x,y,z
        int numElements = modelVertices.length;
        numVertices = numElements/ELEMENTS_PER_VERTEX;

        // inicjalizacja ByteBuffer object opierajaca sie na liczbie vertices w button podana jako float
        vertices = ByteBuffer.allocateDirect(
                numElements
                        * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        // dodanie button do byteBuffer
        vertices.put(modelVertices);
        glProgram = GLManager.getGLProgram();

    }

    public void draw(){
        // powiedzenie OpenGL by uzywal glProgram
        glUseProgram(glProgram);

        // podanie lokalizacji dla glProgram
        int uMatrixLocation = glGetUniformLocation(glProgram,
                U_MATRIX);
        int aPositionLocation =
                glGetAttribLocation(glProgram, A_POSITION);
        int uColorLocation = glGetUniformLocation(glProgram, U_COLOR);
        vertices.position(0);
        glVertexAttribPointer(
                aPositionLocation,
                COMPONENTS_PER_VERTEX,
                GL_FLOAT,
                false,
                STRIDE,
                vertices);
        glEnableVertexAttribArray(aPositionLocation);
        // podanie nowych wymiarow dla openGL
        glUniformMatrix4fv(uMatrixLocation, 1, false, viewportMatrix,
                0);
        // zmiana kolorow shadera
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        // rysowanie lini
        glDrawArrays(GL_LINES, 0, numVertices);
    }
}