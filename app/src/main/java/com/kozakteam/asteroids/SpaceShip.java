package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by User on 15.06.2017.
 */

class SpaceShip extends GameObject {
    public SpaceShip(float locationX, float locationY) {
        super();
        //ustawiamy typ, żeby metoda draw z gameobject wiedziała, co rysować
        setType(Type.SPACESHIP);
        setLocation(locationX, locationY);
        float width = 15;
        float length = 20;
        setSize(width, length);
        //żeby nie zawracać sobie dupy ciągłym dzieleniem
        float halfWidth = width / 2;
        float halfLength = length / 2;
        //definiujemy kształt statku jako trójkąt
        //punkt po punkcie, przeciwnie do ruchu wskazówek zegara
        float[] spaceShipVertices = new float[]{
                -halfWidth, -halfLength, 0,
                halfWidth, -halfLength, 0,
                0, 0 + halfLength, 0
        };
        setVertices(spaceShipVertices);
    }
}
