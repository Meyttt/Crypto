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
        TextField input = new TextField();
        TextField input1 = new TextField();
        TextField output = new TextField();
        output.setEditable(false);
        Button button = new Button("Отправить");
        button.setOnAction(event -> {
            try {
                DialogMessage reply = cryptoClient.dialog(new DialogMessage(input.getText()));
                output.setStyle("-fx-text-fill: green;");
                output.setText(reply.getText());
            } catch (IOException ex) {
                output.setStyle("-fx-text-fill: red;");
                // todo: отметить красным
                output.setText(ex.getMessage());
            }
        });
        grid.addRow(0, new Label("Сообщение:"), input, button);
        grid.addRow(1, new Label("Ответ:"), output);
        GridPane.setHgrow(input, Priority.ALWAYS);
        GridPane.setHgrow(output, Priority.ALWAYS);
        Scene scene = new Scene(grid);
        primaryStage.setTitle("Обмен");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
