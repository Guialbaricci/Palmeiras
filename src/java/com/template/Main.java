package com.template;

import com.template.controller.MainController;
import com.template.validator.IJogadoresValidator;
import com.template.validator.JogadoresValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception {
        IJogadoresValidator jogadorValidator = new JogadoresValidator();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(jogadorValidator);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene((Parent) loader.load(), 802, 533);
        stage.setTitle("Jogadores Palmeiras");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}