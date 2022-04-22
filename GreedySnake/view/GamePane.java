package view;


import interfaces.ReadService;
import interfaces.ViewerService;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;

import static tools.GameDefaultParameters.*;


public class GamePane extends BorderPane implements ViewerService {
    //private BorderPane gamePane;

    private static ReadService gameModel;

    private Text score;

    private Text help;

    private Button start;

    private double length;

    private int size;

    private boolean isStart;




    public GamePane(ReadService gm /*double length, int size*/){
        this.gameModel = gm;

    }

    private void start() {
    }


    public  void paint(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANEVAS_WIDTH, CANEVAS_HEIGHT);
        paintSnake(gc);


        gc.setFill(Color.YELLOW);
        Point food = gameModel.getFood();
        gc.fillOval(food.x * Cube_size + 1, food.y * Cube_size + 1, Cube_size, Cube_size);

        if(gameModel.gameIsOver()){
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(40));
            gc.fillText("Game Over, presse 'R' to continue", 200, 200);
        }

        gc.setFill(Color.AQUAMARINE);
        gc.setFont(new Font(20));
        gc.fillText("Score:"+ gameModel.getScore()+" ", 40, 40);
    }

    public static void paintSnake(GraphicsContext gc){

        Point[] snakeBody = gameModel.getSnake().getBody();
        Point head =snakeBody[0];
        gc.setFill(Color.RED);
        gc.fillRect(head.x * Cube_size + 1, head.y * Cube_size + 1, Cube_size - 2, Cube_size - 2);
        for(int i=1; i < gameModel.getSnakeSize(); i++){
            Point p = snakeBody[i];
            gc.setFill(Color.BLUE);
            gc.fillRect(p.x * Cube_size + 1, p.y * Cube_size + 1, Cube_size - 2, Cube_size - 2);

            if (head.x == p.x && head.y == p.y) {
                gc.setFill(Color.GREEN);
                gc.fillRect(p.x * Cube_size + 1, p.y * Cube_size + 1, Cube_size - 2, Cube_size - 2);
            }
        }


    }


    @Override
    public void init() {

    }

    @Override
    public Parent getPanel() {

        Group panel=new Group();
        return  panel;

    }

    @Override
    public void setMainWindowWidth(double w) {

    }

    @Override
    public void setMainWindowHeight(double h) {

    }
}
