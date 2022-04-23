package model_data;

import tools.GameDefaultParameters;

import java.awt.*;

import static tools.GameDefaultParameters.SNAKE_SIZE_MAX;

public class Snake {


    private int len; //length of snake

    private final Point[] body;



    public Snake(){

        len = GameDefaultParameters.INITIAL_SIZE;
        body = new Point[SNAKE_SIZE_MAX];
        for(int i=0; i<len;i++){
            body[i] = new Point(len-i+1,0);
            System.out.println(i+ " x "+body[i].x);
        }
    }

    public Point getHead(){
        return this.body[0];
    }

    public int getSize(){
        return this.len;

    }
    public void incrementSize(){
        this.len++;
    }

    public Point[] getBody(){
        return this.body;
    }



}
