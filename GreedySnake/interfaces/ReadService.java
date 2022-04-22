package interfaces;

import model_data.Snake;
import tools.User;

import java.awt.*;

public interface ReadService {
    Point[] getSnakeBody();
    Point getFood();
    Snake getSnake();
    int getScore();
    boolean gameIsOver();
    User.COMMAND getDirection();
    int getSnakeSize();
    Point getSnakeHead();

}
