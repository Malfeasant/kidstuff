package us.malfeasant.kidstuff;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var text = new TextArea();
        text.setWrapText(true);
        text.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
//                System.out.println(text.getParagraphs());
                System.out.println(text.getCaretPosition());
                System.out.println(text.getAnchor());
            }
        });

        var gfx = new Canvas(640, 480);
        var pane = new SplitPane(gfx, text);
        pane.setOrientation(Orientation.VERTICAL);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
