package App;

import controller.Engine;
import interfaces.DataService;
import interfaces.EngineService;
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

public class GamePage extends Application {
    private static DataService data_model;
    private static EngineService engine;
    private static GamePane viewer;






    public static void main(String[] args) {
        data_model=new GameModel();
        engine=new Engine();
        ((Engine)engine).bindDataService(data_model);
        launch(args);
    }






    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/homePage.fxml"));
        stage.setTitle("Greedy Snake");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();




    }

    public void showGameWindow(int engienspeed) throws  Exception{
       viewer=new GamePane();
       ((GamePane)viewer).bindReadService(data_model);
        data_model.init();
        engine.init();
        viewer.init();
        engine.setEngineSpeed(engienspeed);
        Stage stage=new Stage();
        Pane p=new Pane();
        p.getChildren();
        Canvas canvas=new Canvas(CANEVAS_WIDTH,CANEVAS_HEIGHT);
        viewer.getChildren().add(canvas);
        Scene scene = new Scene(viewer,CANEVAS_WIDTH,CANEVAS_HEIGHT);

        final GraphicsContext context= canvas.getGraphicsContext2D();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.LEFT) engine.turnSnackDirection(User.COMMAND.LEFT);
                if (event.getCode()==KeyCode.RIGHT) engine.turnSnackDirection(User.COMMAND.RIGHT);
                if (event.getCode()==KeyCode.UP) engine.turnSnackDirection(User.COMMAND.UP);
                if (event.getCode()==KeyCode.DOWN) engine.turnSnackDirection(User.COMMAND.DOWN);
                if (event.getCode()==KeyCode.R){engine.restartGame();}
                    event.consume();
            }
        });

        stage.setScene(scene);
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
        Timeline time = new Timeline(new KeyFrame(Duration.millis(70), event));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

    }

}
