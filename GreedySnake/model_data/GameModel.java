package model_data;

import interfaces.DataService;
import tools.GameDefaultParameters;
import tools.User;


import java.awt.*;

public class GameModel implements DataService {
    static Point food;
    static Snake snake;
    static Boolean gameOver;
    private User.COMMAND direction;
    //public final int size = 50;
   // public   static final int SIZE = 80;
    private static int score;

    public Snake getSnake(){
        return this.snake;
    }

    public GameModel(){
    }





    public boolean gameIsOver(){
        return this.gameOver;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public void init() {
        snake = new Snake();
        food =  new Point(5,5);
        gameOver = false;
        direction = User.COMMAND.NONE;
        score = 0;
    }

    @Override
    public Point[] getSnakeBody() {
        return this.snake.getBody();
    }

    @Override
    public Point getFood() {
        return this.food;
    }
    @Override
    public  void  setFood(Point p){
        this.food=p;
    }
    @Override
    public void setGameOver(Boolean value){
        this.gameOver=value;
    }

    public void  incrementScore(int value){
        this.score=score+value;
    }

    public  User.COMMAND getDirection(){
        return this.direction;
    }

    @Override
    public int getSnakeSize() {
        return this.snake.getSize();
    }

    public  Point getSnakeHead(){
       return snake.getHead();
    }
    public void setDirection(User.COMMAND c){
        this.direction=c;
    }
    public boolean isMaxSizeReached(){
        return this.snake.getSize()>= GameDefaultParameters.SNAKE_SIZE_MAX;
    }


}
