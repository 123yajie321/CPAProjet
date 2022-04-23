package controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import App.GamePage;
import tools.GameDefaultParameters;

public class HomePageController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void changeWindow_ModeEasy() throws Exception {
            GamePage gamePage=new GamePage();
            gamePage.showGameWindow(GameDefaultParameters.EASY);
    }


    public void changeWindow_ModeMedium() throws Exception {
        GamePage gamePage=new GamePage();
        gamePage.showGameWindow(GameDefaultParameters.MEDIUM);
    }


    public void changeWindow_ModeHard() throws Exception {
        GamePage gamePage=new GamePage();
        gamePage.showGameWindow(GameDefaultParameters.HARD);
    }

}
