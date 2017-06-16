package com.kozakteam.asteroids;

import java.util.Random;

/**
 * Created by User on 17.06.2017.
 */

public class Star extends GameObject {

    //potrzebny będzie w update:
    Random random;

    public Star(int mapWidth, int mapHeigt){
        setType(Type.STAR);
        random=new Random();
        setLocation(random.nextInt(mapWidth), random.nextInt(mapHeigt));
        //pozycja przy rysowaniu ta sama co ten random
        float[] starVertices = new float[]{0,0,0};
        setVertices(starVertices);
    }

    //żeby gwiazdki mrugały
    public void update(){
        int n= random.nextInt(1000);
        if (n==0){
            if(isActive()){
                setActive(false);
            }else {
                setActive(true);
            }
        }
    }
}
