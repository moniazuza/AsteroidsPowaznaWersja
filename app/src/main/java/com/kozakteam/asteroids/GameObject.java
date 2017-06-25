package com.kozakteam.asteroids;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setRotateM;
import static android.opengl.Matrix.translateM;
import static com.kozakteam.asteroids.GLManager.A_POSITION;
import static com.kozakteam.asteroids.GLManager.COMPONENTS_PER_VERTEX;
import static com.kozakteam.asteroids.GLManager.ELEMENTS_PER_VERTEX;
import static com.kozakteam.asteroids.GLManager.FLOAT_SIZE;
import static com.kozakteam.asteroids.GLManager.STRIDE;
import static com.kozakteam.asteroids.GLManager.U_COLOR;
import static com.kozakteam.asteroids.GLManager.U_MATRIX;
import static com.kozakteam.asteroids.GLManager.aPositionLoctation;
import static com.kozakteam.asteroids.GLManager.uColorLoctation;
import static com.kozakteam.asteroids.GLManager.uMatrixLoctation;

/**
 * Created by User on 15.06.2017.
 */

public class GameObject {

    boolean active;

    public enum Type {SPACESHIP, ASTEROID, BORDER, BULLET, STAR}


    private Type type;
    private static int glProgram = -1;

    private int elementsNumber;
    private int verticesNumber;

    //do pozycji obiektu
    private float[] modelVertices;

    //prędkość i kierunek poruszania
    private float xVelocity = 0f;
    private float yVelocity = 0f;
    private float speed = 0;
    private float maxSpeed = 200;

    //środek obiektu - czyli jego pozycja
    private PointF location = new PointF();

    //openGL chce dane jako float buffer, dlatego tak robimy
    private FloatBuffer vertices;

    //do zmiany pozycji każdego punktu obiektu, który rysujemy
    private final float[] modelMatrix = new float[16];

    //więcej maierzy, bo open gl tak chce
    float[] viewportModelMatrix = new float[16];
    float[] rotateViewportModelMatrix = new float[16];

    //w którą stronę obiekt jest skierowany
    private float facingAngle = 90f;

    //prędkość obrotu
    private float rotationRate = 0f;

    //w którym kierunku zmierza
    private float travellingAngle = 0f;

    //wymiary
    private float length;
    private float width;

    public GameObject() {
        //czy shadery są skompilowane, jeśli nie, to robimy to
        if (glProgram == -1) {
            setGLProgram();
            glUseProgram(glProgram);
            uMatrixLoctation = glGetUniformLocation(glProgram, U_MATRIX);
            aPositionLoctation = glGetAttribLocation(glProgram, A_POSITION);
            uColorLoctation = glGetUniformLocation(glProgram, U_COLOR);
        }
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setGLProgram() {
        glProgram = GLManager.getGLProgram();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSize(float width, float length) {
        this.width = width;
        this.length = length;
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(float x, float y) {
        this.location.x = x;
        this.location.y = y;
    }

    public void setRotationRate(float rotationRate) {
        this.rotationRate = rotationRate;
    }

    public float getTravellingAngle() {
        return travellingAngle;
    }

    public void setTravellingAngle(float travellingAngle) {
        this.travellingAngle = travellingAngle;
    }

    public float getFacingAngle() {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setVertices(float[] objectVertices) {
        modelVertices = new float[objectVertices.length];
        modelVertices = objectVertices;
        elementsNumber = modelVertices.length;
        verticesNumber = elementsNumber / ELEMENTS_PER_VERTEX;
        vertices = ByteBuffer.allocateDirect(elementsNumber * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(modelVertices);
    }

    void move(float fps) {
        if (xVelocity != 0) {
            location.x += xVelocity / fps;
        }
        if (yVelocity != 0) {
            location.y += yVelocity / fps;
        }
        if (rotationRate != 0) {
            facingAngle = facingAngle + rotationRate / fps;
        }
    }

    public void draw(float[] viewportMatrix) {
        glUseProgram(glProgram);
        vertices.position(0);
        glVertexAttribPointer(aPositionLoctation,
                COMPONENTS_PER_VERTEX,
                GL_FLOAT,
                false,
                STRIDE,
                vertices);
        glEnableVertexAttribArray(aPositionLoctation);
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, location.x, location.y, 0);
        multiplyMM(viewportModelMatrix, 0,
                viewportMatrix, 0, modelMatrix, 0);
        setRotateM(modelMatrix, 0, facingAngle, 0, 0, 1.0f);
        multiplyMM(rotateViewportModelMatrix, 0,
                viewportModelMatrix, 0, modelMatrix, 0);
        glUniformMatrix4fv(uMatrixLoctation, 1, false, rotateViewportModelMatrix, 0);
        glUniform4f(uColorLoctation, 1.0f, 1.0f, 1.0f, 1.0f);

        switch (type) {
            case SPACESHIP:
                glDrawArrays(GL_TRIANGLES, 0, verticesNumber);
                break;
            case ASTEROID:
                glDrawArrays(GL_LINES, 0, verticesNumber);
                break;
            case BORDER:
                glDrawArrays(GL_LINES, 0, verticesNumber);
                break;
            case STAR:
                glDrawArrays(GL_POINTS, 0, verticesNumber);
                break;
            case BULLET:
                glDrawArrays(GL_POINTS, 0, verticesNumber);
                break;
        }

    }


}
