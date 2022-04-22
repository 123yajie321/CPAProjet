import controller.Engine;
import interfaces.DataService;
import interfaces.EngineService;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model_data.GameModel;
import tools.User;
import view.GamePane;

import static tools.GameDefaultParameters.CANEVAS_HEIGHT;
import static tools.GameDefaultParameters.CANEVAS_WIDTH;

public class Main extends Application {
    private static DataService data_model;
    private static EngineService engine;
    private static GamePane viewer;
    private static AnimationTimer timer;




    public static void main(String[] args) {
        data_model=new GameModel();
        engine=new Engine(data_model);
        viewer=new GamePane(data_model);
        launch(args);
    }






    @Override
    public void start(Stage stage) throws Exception{
        Pane p=new Pane();
        p.getChildren();
        Canvas canvas=new Canvas(CANEVAS_WIDTH,CANEVAS_HEIGHT);
        viewer.getChildren().add(canvas);
        Scene scene = new Scene(viewer);

        final GraphicsContext context= canvas.getGraphicsContext2D();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.LEFT) engine.turnSnackDirection(User.COMMAND.LEFT);
                if (event.getCode()==KeyCode.RIGHT) engine.turnSnackDirection(User.COMMAND.RIGHT);
                if (event.getCode()==KeyCode.UP) engine.turnSnackDirection(User.COMMAND.UP);
                if (event.getCode()==KeyCode.DOWN) engine.turnSnackDirection(User.COMMAND.DOWN);
                event.consume();
            }
        });
       /* scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.LEFT) engine.releaseCommand(User.COMMAND.LEFT);
                if (event.getCode()==KeyCode.RIGHT) engine.releaseCommand(User.COMMAND.RIGHT);
                if (event.getCode()==KeyCode.UP) engine.releaseCommand(User.COMMAND.UP);
                if (event.getCode()==KeyCode.DOWN) engine.releaseCommand(User.COMMAND.DOWN);
                event.consume();
            }
        });*/


        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(800);
        stage.setResizable(false);

        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                engine.start();
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                engine.stop();
            }
        });
        stage.show();
        EventHandler<ActionEvent> event = e -> {
            try {
                viewer.paint(context);
            } catch (NullPointerException ee){
                stage.close();
            }
        };
        Timeline time = new Timeline(new KeyFrame(Duration.millis(100), event));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

      /*  AnimationTimer timer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                viewer.paint(context);

            }
        };
        timer.start();*/

       /* EventHandler<ActionEvent> event = e -> {
            try {
                gamePane.move();
            } catch (NullPointerException ee){
                stage.close();
            }
        };
        Timeline time = new Timeline(new KeyFrame(Duration.millis(500), event));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

        scene.setOnKeyPressed(e->{
            try {
                gamePane.move(e.getCode());
            } catch (NullPointerException ee){

                stage.close();
            }
        });*/



    }

}
