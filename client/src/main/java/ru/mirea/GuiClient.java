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

import java.io.IOException;
import java.net.URI;

/**
 * Created by master on 28.11.2016.
 */
public class GuiClient extends Application {

    private CryptoClient cryptoClient;

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Button button = new Button("Отправить");
        button.setOnAction(event -> {
            try {
                DialogMessage reply = cryptoClient.dialog(new DialogMessage(loginInput.getText()));
//                DialogMessage reply = cryptoClient.verification(new VerificationData(loginInput.getText(),passwordInput));
//                output.setStyle("-fx-text-fill: green;");
//                output.setText(reply.getText());
            } catch (IOException ex) {
                output.setStyle("-fx-text-fill: red;");
                // todo: отметить красным
                output.setText(ex.getMessage());
            }
        });
        grid.addRow(0, new Label("Логин:"), loginInput, button);
        grid.addRow(1, new Label("Пароль:"), passwordInput);
        GridPane.setHgrow(loginInput, Priority.ALWAYS);
        GridPane.setHgrow(passwordInput, Priority.ALWAYS);
        Scene scene = new Scene(grid);
        primaryStage.setTitle("Обмен");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
