package ru.mirea;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ru.mirea.common.VerificationData;
import ru.mirea.common.VerificationException;

import java.io.IOException;
import java.net.URI;

/**
 * Created by master on 28.11.2016.
 */
public class GuiClient extends Application {
    Scene scene;
    Stage stage;
    private CryptoClient cryptoClient;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
        cryptoClient = new CryptoClient(new URI("http://localhost:8080"));
        cryptoClient.exchangeBase();
        cryptoClient.exchangeKeys();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        TextField loginInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button ver = new Button("Вход");
        Button reg = new Button("Регистрация");
        grid.addRow(0, new Label("Логин:"), loginInput, reg);
        grid.addRow(1, new Label("Пароль:"), passwordInput,ver);
        GridPane.setHgrow(loginInput, Priority.ALWAYS);
        GridPane.setHgrow(passwordInput, Priority.ALWAYS);
        Scene sceneFirst = new Scene(grid);
        scene = sceneFirst;
        primaryStage.setTitle("Обмен");
        primaryStage.setScene(scene);
        primaryStage.show();

        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(5));
        grid1.setHgap(5);
        grid1.setVgap(5);
        TextField output = new TextField();
        output.setLayoutX(6);
        output.setLayoutX(5);
//        output.setEditable(false);
        grid1.addRow(0,new Label("Ответ: "),output);
        Scene sceneSecond = new Scene(grid1);
        loginInput.setOnMouseClicked(event -> {
            loginInput.clear();
            }
        );
        passwordInput.setOnMouseClicked(event -> {
            passwordInput.clear();
        }
        );
        GridPane errorGridPane = new GridPane();
        Label errorLabel = new Label();
        errorGridPane.addRow(0, errorLabel);
        errorGridPane.setAlignment(Pos.CENTER);
        Scene errorScene = new Scene(errorGridPane);
        Stage errorStage = new Stage();
        errorStage.setScene(errorScene);
        errorStage.setTitle("Ошибка");
        errorStage.setHeight(100);
        errorStage.setWidth(200);
        errorStage.setResizable(false);
        reg.setOnAction(event -> {
                    try {
                        String answer = cryptoClient.registration(new VerificationData(loginInput.getText(), passwordInput.getText()));
                        switch (answer) {
                            case ("New user added"):
                                errorLabel.setText("New user added!");
                                errorLabel.setStyle("-fx-text-fill: yellowgreen;");
                                primaryStage.setScene(sceneSecond);
                                errorStage.show();
                                break;
                            case ("Login already exists"):
                                errorLabel.setText("Login already exists");
                                errorLabel.setStyle("-fx-text-fill: red;");
                                errorStage.show();
                                break;
                        }

                    } catch (IOException ex) {
                    } catch (VerificationException e) {
                        e.printStackTrace();
                    }
                });

        ver.setOnAction(event -> {
            try {
                String answer = cryptoClient.verification(new VerificationData(loginInput.getText(),passwordInput.getText()));
                switch (answer){
                    case"Incorrect login":
                        errorLabel.setText("Incorrect login");
                        errorLabel.setStyle("-fx-text-fill: red;");
                        errorStage.show();
                        break;
                    case "Incorrect password":
                        errorLabel.setText("Incorrect password");
                        errorLabel.setStyle("-fx-text-fill: red;");
                        errorStage.show();
                        break;
                    case "Correct password":
                        errorLabel.setText("Correct userdata");
                        errorLabel.setStyle("-fx-text-fill: yellowgreen;");
                        primaryStage.setScene(sceneSecond);
                        errorStage.show();
                        break;
                }

            } catch (IOException ex) {
//                output.setStyle("-fx-text-fill: red;");
//                output.setText(ex.getMessage());
            } catch (VerificationException e) {
                e.printStackTrace();
            }
        });
        primaryStage.show();

    }

}
