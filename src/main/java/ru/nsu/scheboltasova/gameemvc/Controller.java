package ru.nsu.scheboltasova.gameemvc;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for handling Player's requests
 */
final class Controller {
    private final static float SHIFT_Y = 15f;
    private final static int DEADLINE = 40;
    public boolean continueGame = true;
    private AtomicInteger second;
    private AtomicInteger minute;
    private final Stage menuStage;
    private final Model model;
    private final View view;
    private Timeline clock;
    private final int id;


    public Controller(final Stage stage, int id) throws IOException {
        view = new View();
        model = new Model();
        model.registerObserver(view);

        this.id = id;
        menuStage = stage;
        GameTimer();
    }

    public Scene Creation() {
        return view.GetScene();
    }

    public void CheckButtonClicked(final KeyEvent keyEvent) throws IOException, InterruptedException {
        if(continueGame) {
            if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                model.GoBack();
            }

            if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                model.GoRight();
            }

            if (keyEvent.getCode().equals(KeyCode.UP)) {
                model.GoUpDown((-1) * SHIFT_Y);
            }

            if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                model.GoUpDown(SHIFT_Y);
            }
        }
    }

    /**
     * Handling clicks on all buttons in the game
     */
    public void CheckMouseClicked(final MouseEvent mouseEvent) {
        view.time.setOnMouseClicked((mouseEvent1 -> {
            try {
                continueGame = false;
                clock.stop();
                view.StopGame();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));

        if (view.scores != null) {
            view.scores.setOnMouseClicked(mouseEvent1 -> {
                view.ShowScores(id);
            });
        }

        if (view.close != null) {
            view.close.setOnMouseClicked(mouseEvent1 -> {
                view.CloseStop();
                if(second.get() != 0) {
                    continueGame = true;
                    clock.play();
                }
            });
        }

        if(view.closeScore != null){
            view.closeScore.setOnMouseClicked(mouseEvent1 -> {
                view.CloseStop();
                if(second.get() != 0) {
                    continueGame = true;
                    clock.play();
                }
            });
        }

        if (view.newGame != null) {
            view.newGame.setOnMouseClicked(mouseEvent1 -> {
                try {
                    NewGame();
                    continueGame = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (view.menu != null) {
            view.menu.setOnMouseClicked(mouseEvent2 -> {
                view.CloseStage();
                menuStage.show();
            });
        }

        if(view.scores != null) {
            view.scores.setOnMouseClicked(mouseEvent1 -> {
                view.ShowScores(id);
            });
        }
    }

    private void NewGame() throws IOException {
        model.removeObserver(view);

        view.UpdatePlayerParam();
        model.UpdatePlayerParam();
        GameTimer();

        model.registerObserver(view);

        continueGame = true;
    }

    /**
     * Game timer starts in every new game. Time in seconds is set in DEADLINE
     */
    private void GameTimer() {
        second = new AtomicInteger(DEADLINE);
        minute = new AtomicInteger(0);

        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            second.getAndDecrement();
            view.ChangeTimer(minute, second);

            if(second.get() == 0) {
                try {
                    continueGame = false;
                    clock.stop();
                    view.CheckResults();
                }
                catch (IOException ex) { ex.printStackTrace(); }
            }
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(DEADLINE);
        clock.play();
    }

    public void Showing() {
        view.Show();
    }
}
