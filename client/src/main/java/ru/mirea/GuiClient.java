package ru.mirea;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ru.mirea.common.DialogMessage;
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
        // todo: добавить кнопку соединения
        cryptoClient = new CryptoClient(new URI("http://localhost:8080")); // todo: настраиваемый параметр
        cryptoClient.exchangeBase();
        cryptoClient.exchangeKeys();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        TextField loginInput = new TextField();
        TextField passwordInput = new TextField();
        TextField output = new TextField();
        output.setEditable(false);
        Button ver = new Button("Вход");
        Button reg = new Button("Регистрация");
        reg.setOnAction(event -> {
            try {
                String answer = cryptoClient.registration(new VerificationData(loginInput.getText(),passwordInput.getText()));
                output.setText(answer);
            } catch (IOException ex) {
                output.setStyle("-fx-text-fill: red;");
                output.setText(ex.getMessage());
            } catch (VerificationException e) {
                e.printStackTrace();
            }
        });
        ver.setOnAction(event -> {
            try {
                String answer = cryptoClient.verification(new VerificationData(loginInput.getText(),passwordInput.getText()));
                output.setText(answer);
            } catch (IOException ex) {
                output.setStyle("-fx-text-fill: red;");
                output.setText(ex.getMessage());
            } catch (VerificationException e) {
                e.printStackTrace();
            }
        });
        grid.addRow(0, new Label("Логин:"), loginInput, reg);
        grid.addRow(1, new Label("Пароль:"), passwordInput,ver);
        grid.addRow(2,new Label("Ответ: "),output);
        GridPane.setHgrow(loginInput, Priority.ALWAYS);
        GridPane.setHgrow(passwordInput, Priority.ALWAYS);
        Scene sceneFirst = new Scene(grid);
        scene = sceneFirst;
        primaryStage.setTitle("Обмен");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
   }
