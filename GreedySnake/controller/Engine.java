package controller;


import interfaces.DataService;
import interfaces.EngineService;
import tools.User;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static tools.GameDefaultParameters.*;


public class Engine implements EngineService {

    private Timer engineClock;
    private DataService data;
    private User.COMMAND command;
    private boolean turnLeft,turnRight,turnUp,turnDown;
    public Engine(DataService d){
        this.data=d;
        engineClock = new Timer();
        command = User.COMMAND.NONE;
    }
    @Override
    public void init(){
        engineClock = new Timer();
        command = User.COMMAND.NONE;
        turnLeft = false;
        turnRight = false;
        turnUp = false;
        turnDown = false;
    }

    @Override
    public void start() {
        engineClock.schedule(new TimerTask(){
            @Override
            public void run() {
                avancer();
            }
        },0,engineSpeed);

    }

    @Override
    public void stop() {
        engineClock.cancel();
    }

    @Override
    public void turnSnackDirection(User.COMMAND c) {
        User.COMMAND direction=data.getDirection();
        switch(c){
            case UP:
                if(direction!= User.COMMAND.DOWN){
                        data.setDirection(User.COMMAND.UP);
                }
                break;

            case DOWN:
                if(direction!= User.COMMAND.UP){
                    data.setDirection(User.COMMAND.DOWN);
                }
                break;
            case LEFT:
                if(direction!= User.COMMAND.RIGHT){
                    data.setDirection(User.COMMAND.LEFT);
                }
                break;
            case RIGHT:
                if(direction!= User.COMMAND.LEFT){
                    data.setDirection(User.COMMAND.RIGHT);
                }
                break;
        }

    }



    public  boolean isCollision(int x,int y){
        for(int i = 0; i < data.getSnakeSize();i++){
            Point point = data.getSnakeBody()[i];
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    public  boolean outBorder(){
        if ( data.getSnake().getHead().x < 0 || data.getSnake().getHead().x >= CANEVAS_WIDTH || data.getSnake().getHead().y < 0 || data.getSnake().getHead().y >= CANEVAS_HEIGHT) {
            return true;
        }
        return false;
    }

    public  boolean bodyTouched(){
        Point head=data.getSnakeHead();
        for (int i = 1; i < data.getSnakeSize(); i++) {
            Point point = data.getSnakeBody()[i];
            if (head.x == point.x && head.y == point.y) {

                return true;
            }
        }
        return false;
    }
    public Point newFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(NB_CUBE_WIDTH-5);
            y = random.nextInt(NB_CUBE_WIDTH-5);
        } while (isCollision(x, y));
        return new Point(x,y);
    }

    public void TryEatFood(){
        if (data.getSnake().getHead().x == data.getFood().x && data.getSnake().getHead().y ==  data.getFood().y) {
            Point[] body=data.getSnake().getBody();
            body[data.getSnake().getSize()] = new Point(-1, -1);
            data.getSnake().incrementSize();
            data.setFood(newFood());
            System.out.println(" new food  :x y "+data.getFood().x+" "+data.getFood().y);
            data.incrementScore(1);
        }
    }

   public void avancer(){
        if(data.getDirection()!=User.COMMAND.NONE) {
            Point[] snackBody = data.getSnakeBody();
            for (int i = data.getSnake().getSize() - 1; i >= 1; i--) {
                snackBody[i].x = snackBody[i - 1].x;
                snackBody[i].y = snackBody[i - 1].y;
            }
            Point head = snackBody[0];
            switch (data.getDirection()) {
                case UP:
                    head.y--;
                    break;
                case DOWN:
                    head.y++;
                    break;
                case LEFT:
                    head.x--;
                    break;
                case RIGHT:
                    head.x++;
                    break;

            }
            if (outBorder()) {
                System.out.println("out Border");
                data.setGameOver(true);
            }
            if (bodyTouched()) {
                System.out.println("body touched");
                data.setGameOver(true);
            }
            if (data.gameIsOver()) {
                stop();
                System.out.println("game over");
            }
            TryEatFood();
        }
   }




}