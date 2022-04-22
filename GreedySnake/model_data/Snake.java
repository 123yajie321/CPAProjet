package model_data;

import java.awt.*;

public class Snake {
   // private Point head; //head of snake

    private int len; //length of snake

    private final Point[] body;

    //private final int size;

    public Snake(){
        //head = new Point(4,0);
        len = 4;
        //this.size = size;
        body = new Point[GameModel.SIZE*GameModel.SIZE];
        for(int i=0; i<len;i++){
            body[i] = new Point(len-i+1,0);
            System.out.println(i+ " x "+body[i].x);
        }
        //map[(int)(Math.random()*size*size)] = -1;
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
    /*
    public int[] snake(){
        return this.snake();
    }*/


}
