package controller;


import interfaces.DataService;
import interfaces.EngineService;
import interfaces.RequireDataService;
import tools.GameDefaultParameters;
import tools.User;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static tools.GameDefaultParameters.*;


public class Engine implements EngineService , RequireDataService {

    private int engineSpeed ;
    private Timer engineClock;
    private DataService data;
    private User.COMMAND command;

    public Engine(){

    }

    @Override
    public void init(){
        engineClock = new Timer();
        command = User.COMMAND.NONE;

    }



    @Override
    public void bindDataService(DataService service) {
        this.data=service;
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
                // At the beginning, although the snake does not move, its head is facing right, so it cannot move to the left
                if(direction!= User.COMMAND.RIGHT&&direction!=User.COMMAND.NONE){
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


    // Cette fonction est utilisée par la  fontion New_food
    // afin de déterminer que les coordonnées du new_food n'entrent pas en collision avec le corps du "snake"
    public  boolean isCollision(int x,int y){
        for(int i = 0; i < data.getSnakeSize();i++){
            Point point = data.getSnakeBody()[i];
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }

    //Déterminez si le corps du serpent dépasse l'écran de jeu. Si c'est le cas, le jeu est terminé.
    public  boolean outBorder(){
        if ( data.getSnakeHead().x < 0 || data.getSnakeHead().x >= NB_CUBE_WIDTH|| data.getSnakeHead().y < 0 || data.getSnakeHead().y >= NB_CUBE_HEIGHT) {
            System.out.println("x:"+data.getSnakeHead().x+" y: "+data.getSnakeHead().y);
            return true;
        }
        return false;
    }
    //Déterminez si la tête du serpent touche le corps, si c'est le cas, return true.
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

    //Générer de nouvelles coordonnées de "food“
    public Point newFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(NB_CUBE_WIDTH);
            y = random.nextInt(NB_CUBE_HEIGHT);
        } while (isCollision(x, y));
        return new Point(x,y);
    }

    //Déterminer si le serpent entre en collision avec du "food"
    public void TryEatFood(){
        Point head=data.getSnakeHead();
        if (head.x == data.getFood().x && head.y ==  data.getFood().y) {
            Point[] body= data.getSnakeBody();
            body[data.getSnakeSize()] = new Point(-1, -1);
            data.incrementSize();
            data.setFood(newFood());
            System.out.println(" new food  :x y "+data.getFood().x+" "+data.getFood().y);
            data.incrementScore(1);
        }
    }

   public void avancer(){
        if(data.getDirection()!=User.COMMAND.NONE) {
            Point[] snackBody = data.getSnakeBody();
            for (int i = data.getSnakeSize() - 1; i >= 1; i--) {
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
            TryEatFood();
            if(isMaxSizeReached()){
                data.setGameOver(true);
            }

            if (data.gameIsOver()) {
                stop();
                System.out.println("game over");
            }

        }
   }

public void setEngineSpeed(int speed){
        this.engineSpeed=speed;
}
public void restartGame(){
        data.init();
        init();
        start();

}
    public boolean isMaxSizeReached(){
        return data.getSnakeSize()>= GameDefaultParameters.SNAKE_SIZE_MAX;
    }

}
