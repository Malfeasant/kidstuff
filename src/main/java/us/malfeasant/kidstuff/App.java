package us.malfeasant.kidstuff;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Memory mem = new Memory();

        var gfx = new Canvas(720, 480);
        var painter = new CanvasPainter(gfx, mem);
        var pane = new StackPane(gfx);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        painter.start();
    }
}
