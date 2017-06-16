package com.kozakteam.asteroids;

/**
 * Created by User on 17.06.2017.
 */

public class Border extends GameObject {

    public Border(float mapWidth, float mapHeight) {
        setType(Type.BORDER);
        float width = mapWidth;
        float height = mapHeight;
        setSize(width, height);
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        //każda trójka, to punkt, każde dwie trójki współrzędnych, to jedna linia
        float[] borderVertices = new float[]{
                -halfWidth, -halfHeight, 0,
                halfWidth, -halfHeight, 0,
                halfWidth, -halfHeight, 0,
                halfWidth, halfHeight, 0,
                halfWidth, halfHeight, 0,
                -halfWidth, halfHeight, 0,
                -halfWidth, halfHeight, 0,
                -halfWidth, -halfHeight, 0
        };
        setVertices(borderVertices);
    }
}
