package ru.nsu.scheboltasova.gameemvc;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class for creating requests
 */
public final class Player {
    private final Controller controller;

    public Player(Stage stage, int id) throws IOException {
        controller = new Controller(stage, id);
    }

    public void Actions(){
        final Scene scene = controller.Creation();

        scene.setOnKeyPressed(keyEvent -> {
            try {
                controller.CheckButtonClicked(keyEvent);
            }
            catch (IOException | NullPointerException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        scene.setOnMouseClicked(controller::CheckMouseClicked);
        controller.Showing();
    }
}
