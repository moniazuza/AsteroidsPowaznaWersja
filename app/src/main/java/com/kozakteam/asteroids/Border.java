package com.kozakteam.asteroids;

/**
 * Created by User on 17.06.2017.
 */

public class Border extends GameObject {

    public Border(float mapWidth, float mapHeight) {
        setType(Type.BORDER);

        setLocation(mapHeight/2,mapHeight/2);

        float width = mapWidth;
        float height = mapHeight;
        setSize(width, height);
        float[] borderVertices = new float[]{

                - width/2, -height/2, 0,
                width/2, -height/2, 0,

                width/2, -height/2, 0,
                width/2, height/2, 0,

                width/2, height/2, 0,
                -width/2, height/2, 0,

                -width/2, height/2, 0,
                - width/2, -height/2, 0,
        };

        setVertices(borderVertices);
    }
}
