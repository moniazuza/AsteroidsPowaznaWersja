package com.kozakteam.asteroids;

/**
 * Created by User on 15.06.2017.
 */

import android.util.StringBuilderPrinter;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

public class GLManager {

    public static final int COMPONENTS_PER_VERTEX = 3;
    public static final int FLOAT_SIZE = 4;
    public static final int STRIDE = (COMPONENTS_PER_VERTEX) * FLOAT_SIZE;

    public static final int ELEMENTS_PER_VERTEX = 3; //x,y,z

    //stałe reprezentujące typy glsl w shaderach (macierz, pozycja, kolor)
    public static final String U_MATRIX = "u_Matrix";
    public static final String A_POSITION = "a_Position";
    public static final String U_COLOR = "u_Color";

    //inty dopasowane do stringów wyżej (gdzie ten string znajduje się w glProgram)
    public static int uMatrixLoctation;
    public static int aPositionLoctation;
    public static int uColorLoctation;

    private static String vertexShader =
            "uniform mat4 u_Matrix;" +
                    "attribute vec4 a_Position;" +
                    "void main()" +
                    "{" +
                    "gl_Position = u_Matrix * a_Position;" +
                    "gl_PointSize = 3.0;" +
                    "}";

    private static String fragmentShader =
            "precision mediump float;" +
                    "uniform vec4 u_Color;" +
                    "void main()" +
                    "{" +
                    "gl_FragColor = u_Color;" +
                    "}";

    private static int program;

    public static int getGLProgram() {
        return program;
    }

    //kompiluje shadery do obiektu glProgram
    public static int buildProgram() {
        return linkProgram(compileVertexShader(), compileFragmentShader());
    }

    private static int compileVertexShader() {
        return compileShader(GL_VERTEX_SHADER, vertexShader);
    }

    private static int compileFragmentShader() {
        return compileShader(GL_FRAGMENT_SHADER, fragmentShader);
    }

    //tworzy obiekt shader, przechowujący jego id, przekazuje kod shadera i go kompiluje
    private static int compileShader(int type, String shaderCode) {
        final int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }

    private static int linkProgram(int vertexShaer, int fragmentShader) {
        program = glCreateProgram();
        glAttachShader(program, vertexShaer);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        return program;
    }
}
