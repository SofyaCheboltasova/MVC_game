package ru.nsu.scheboltasova.gameemvc;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class for starting the game, creating background with buttons, handling clicks to move to the main game
 */
public final class ModelStart extends Application {
    private final static int BACKGROUND_HEIGHT = 1000;
    private final static int BACKGROUND_WIDTH = 1500;
    private final static int BUTTONS_WIDTH = 1500;
    private final static int BUTTONS_HEIGTH = 800;
    private final static int SMALL_FONT_SIZE = 40;
    private static final int BIG_FONT_SIZE = 120;
    private static final int FP_BUTTONS_W = 200;
    private static final int FP_BUTTONS_H = 500;
    private static final int BLOOD_PIC_H = 250;
    private static final int BLOOD_PIC_W = 230;
    private static final int GO_BUTTON_W = 930;
    private static final int GO_BUTTON_H = 470;
    private static final int ROOT_X = 1500;
    private static final int ROOT_Y = 950;
    private final static int R = 54;
    private final static int G = 15;
    private final static int B = 11;

    private final static TextField textField = new TextField();
    private static FlowPane startWindow, bloodPic, goButton;
    private static Label exit, start, settings;
    private static Group field;

    private void createStartField() throws IOException {
        ImageView background = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
                                                                                 "gameemvc/Pictures/Sharks2.gif")));
        background.setFitWidth(BACKGROUND_WIDTH);
        background.setFitHeight(BACKGROUND_HEIGHT);

        exit = new Label("EXIT");
        Label goLabel = new Label("GO");
        start = new Label("START");
        settings = new Label("SETTINGS");
        Text gameName = new Text("SHARK RACE");

        exit.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        start.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        goLabel.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        settings.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        gameName.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));

        exit.setTextFill(Color.rgb(R, G, B));
        start.setTextFill(Color.rgb(R, G, B));
        goLabel.setTextFill(Color.rgb(R, G, B));
        settings.setTextFill(Color.rgb(R, G, B));
        gameName.setFill(Color.rgb(R, G, B));
        gameName.setStroke(Color.rgb(R, G, B));

        FlowPane buttons = new FlowPane(Orientation.HORIZONTAL, FP_BUTTONS_W, FP_BUTTONS_H, exit, start, settings);
        buttons.setPrefSize(BACKGROUND_WIDTH, BUTTONS_HEIGTH);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        FlowPane root = new FlowPane(gameName);
        root.setPrefSize(ROOT_X, ROOT_Y);
        root.setAlignment(Pos.TOP_CENTER);

        field = new Group(background, root, buttons);
    }

    private void createStartButton() throws IOException {
        ImageView blood = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
                                                                            "gameemvc/Pictures/enterNamePic.png")));
        blood.setFitHeight(BLOOD_PIC_H);
        blood.setFitWidth(BLOOD_PIC_W);

        Label goLabel = new Label("GO");
        goLabel.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        goLabel.setTextFill(Color.rgb(R, G, B));

        goButton = new FlowPane(goLabel);
        goButton.setPrefSize(GO_BUTTON_W, GO_BUTTON_H);
        goButton.setAlignment(Pos.BOTTOM_RIGHT);

        bloodPic = new FlowPane(blood);
        bloodPic.setPrefSize(BUTTONS_WIDTH, BUTTONS_HEIGTH - 100);
        bloodPic.setAlignment(Pos.BOTTOM_CENTER);

        textField.setPrefSize(225, 30);

        startWindow = new FlowPane(Orientation.HORIZONTAL, textField);
        startWindow.setPrefSize(860, 450);
        startWindow.setAlignment(Pos.BOTTOM_RIGHT);

        field.getChildren().addAll(bloodPic, startWindow, goButton);
    }

    @Override
    public void start(final Stage stage) throws IOException {
        createStartField();

        exit.setOnMouseClicked(mouseEvent -> stage.close());

        start.setOnMouseClicked(mouseEvent -> {
            try { createStartButton();}
            catch (IOException e) { e.printStackTrace(); }

            goButton.setOnMouseClicked(mouseEvent1 -> {
                try {
                    if (!textField.getText().equals("")) {
                        Player player = new Player(stage, Integer.parseInt(textField.getText()));
                        player.Actions();
                        stage.hide();
                    }
                }
                catch (Exception e) { e.printStackTrace(); }
            });
        });

        settings.setOnMouseClicked(mouseEvent -> {
            if (field.getChildren().contains(bloodPic))
                field.getChildren().removeAll(startWindow, bloodPic, goButton);
        });

        final Scene scene = new Scene(field);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
