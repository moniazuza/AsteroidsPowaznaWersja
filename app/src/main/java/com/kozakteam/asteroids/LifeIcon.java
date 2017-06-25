package com.kozakteam.asteroids;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.Matrix.orthoM;
import static com.kozakteam.asteroids.GLManager.A_POSITION;
import static com.kozakteam.asteroids.GLManager.COMPONENTS_PER_VERTEX;
import static com.kozakteam.asteroids.GLManager.FLOAT_SIZE;
import static com.kozakteam.asteroids.GLManager.STRIDE;
import static com.kozakteam.asteroids.GLManager.U_COLOR;
import static com.kozakteam.asteroids.GLManager.U_MATRIX;
import static javax.microedition.khronos.opengles.GL10.GL_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_LINES;

/**
 * Created by user on 25.06.2017.
 */

public class LifeIcon {
    // dla GLManager zapamietany statyczny import
    // into a GL space coordinate (-1,-1 to 1,1)
    private final float[] viewportMatrix = new float[16];

    private static int glProgram;

    private int numVertices;

    private FloatBuffer vertices;
    public LifeIcon(GameManager gm, int nthIcon) {

        // The HUD needs its own viewport viewport dla HUD, height podany w pixels. startuje z Y bo openGL to up/down
        orthoM(viewportMatrix, 0, 0,
                gm.screenWidth, gm.screenHeight, 0, 0f, 1f);
        float padding = gm.screenWidth / 160;
        float iconHeight = gm.screenHeight / 15;
        float iconWidth = gm.screenWidth / 30;
        float startX = 10 + (padding + iconWidth) * nthIcon;
        float startY = iconHeight;
        PointF p1 = new PointF();
        p1.x = startX;
        p1.y = startY;
        PointF p2 = new PointF();
        p2.x = startX + iconWidth;
        p2.y = startY;
        PointF p3 = new PointF();
        p3.x = startX + iconWidth / 2;
        p3.y = startY - iconHeight;

        // utworzenie 4 punkow array, podanie wymiarow
        float[] modelVertices = new float[]{
                // A line from point 1 to point 2
                p1.x, p1.y, 0,
                p2.x, p2.y, 0,
                // Point 2 to point 3
                p2.x, p2.y, 0,
                p3.x, p3.y, 0,
                // Point 3 to point 1
                p3.x, p3.y, 0,
                p1.x, p1.y, 0,
        };

        final int ELEMENTS_PER_VERTEX = 3;// x,y,z
        int numElements = modelVertices.length;
        numVertices = numElements / ELEMENTS_PER_VERTEX;

        vertices = ByteBuffer.allocateDirect(
                numElements
                        * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(modelVertices);
        glProgram = GLManager.getGLProgram();
    };
        public void draw(){

            glUseProgram(glProgram);
            int uMatrixLocation = glGetUniformLocation
                    (glProgram, U_MATRIX);
            int aPositionLocation = glGetAttribLocation
                    (glProgram, A_POSITION);
            int uColorLocation = glGetUniformLocation
                    (glProgram, U_COLOR);
            vertices.position(0);
            glVertexAttribPointer(
                    aPositionLocation,
                    COMPONENTS_PER_VERTEX,
                    GL_FLOAT,
                    false,
                    STRIDE,
                    vertices);
            glEnableVertexAttribArray(aPositionLocation);
            glUniformMatrix4fv(uMatrixLocation, 1,
                    false, viewportMatrix, 0);
            glUniform4f(uColorLocation, 1.0f,
                    1.0f, 0.0f, 1.0f);
            glDrawArrays(GL_LINES, 0, numVertices);
        }
    }