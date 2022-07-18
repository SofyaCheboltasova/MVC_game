package ru.nsu.scheboltasova.gameemvc;
import javafx.animation.*;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class View implements Observer{
    public static final int SMALL_FONT_SIZE = 20;
    public static final int XSHIFT_BUTTONS = 120;
    public static final int YSHIFT_BUTTONS = 90;
    private static final int SHARK_HEIGHT = 150;
    private static final int BIG_FONT_SIZE = 30;
    private static final int SHARK_WIDTH = 230;
    private static final int WARN_HEIGHT = 140;
    private static final float TRANSLATE = 25f;
    private static final int SHIFT_BACKGR = 6;
    private static final int WARN_WIDTH = 320;
    private static final int RES_HEIGHT = 200;
    private static final int RES_WIDTH = 500;
    private static final int STOP_HEIGHT = 400;
    private static final int STOP_WIDTH = 260;
    private static final int DURATION = 1000;
    private static final int WIN_SCORE = 80;
    private static final int DISTANCE = 100;
    public static final int CORRIDOR = 60;
    private static final int SHIFT_X = 4;
    private static final int RES_X = 470;
    private static final int STOP_Y = 250;
    private static final int RES_Y = 450;
    private static final int SCORE = 5;
    private final static int R = 54;
    private final static int G = 15;
    private final static int B = 11;
    private static int flag = 0;

    private ImageView stopPic = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
            "gameemvc/Pictures/paper.png")));
    private ImageView scorePic = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
            "gameemvc/Pictures/paper.png")));

    private final Text results = new Text("Results");
    public Label newGame = new Label("New game");
    public Label closeScore = new Label("Close");
    public Label scores = new Label("Scores");
    public Label close = new Label("Close");
    public Label menu = new Label("Menu");
    private FlowPane ids, scoresText;

    private final VBox stopButtons = new VBox(newGame, scores, close, menu);;
    private PlayerParameters playerParams;
    private FieldParameters fieldParams;
    private static AnimationTimer at;
    private static StackPane warn;
    private final Stage stage;
    private final Scene scene;
    public Label time;

    enum updateMethod{
        GO_RIGTH,
        GO_UP_DOWN,
        WARN_SIGN
    }

    public View() throws IOException {
        playerParams = new PlayerParameters();
        fieldParams = new FieldParameters();

        scene = new Scene(fieldParams.field);
        stage = new Stage();

        CreateBackground();
        CreatePlayer();
        CreateTimer();
    }

    public Scene GetScene(){
        return scene;
    }

    private void CreateBackground() throws IOException {
        scene.setRoot(fieldParams.field);

        final FileInputStream streamBck1 = new FileInputStream("src/main/java/ru/nsu/scheboltasova/gameemvc/Pictures/Backgr.jpg");
        final FileInputStream streamBck2 = new FileInputStream("src/main/java/ru/nsu/scheboltasova/gameemvc/Pictures/Backgr.jpg");

        fieldParams.background1.setImage(new Image(streamBck1));
        fieldParams.background2.setImage(new Image(streamBck2));

        streamBck1.close();
        streamBck2.close();

        fieldParams.background1.setFitHeight(fieldParams.BACKGROUND_HEIGHT);
        fieldParams.background1.setFitWidth(fieldParams.BACKGROUND_WIDTH);

        fieldParams.background2.setFitHeight(fieldParams.BACKGROUND_HEIGHT);
        fieldParams.background2.setFitWidth(fieldParams.BACKGROUND_WIDTH);
    }

    private void CreatePlayer() throws IOException {
        final FileInputStream streamShark = new FileInputStream("src/main/java/ru/nsu/scheboltasova/gameemvc/Pictures/sharkStraight.png");
        playerParams.spriteImg.setImage(new Image(streamShark));
        streamShark.close();

        playerParams.spriteImg.setFitWidth(SHARK_WIDTH);
        playerParams.spriteImg.setFitHeight(SHARK_HEIGHT);

        final TranslateTransition translate = new TranslateTransition(Duration.millis(DURATION));
        translate.setByY(TRANSLATE);
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setAutoReverse(true);

        final SequentialTransition animation = new SequentialTransition(playerParams.shark, translate);
        animation.play();

        fieldParams.field.getChildren().addAll(playerParams.shark);
    }

    public void CreateTimer() throws IOException {
        time = new Label();
        NotificationBackground(250, 100, 1250, 0, time, false);
    }

    public void ChangeTimer(final AtomicInteger minute, final AtomicInteger second){
        time.setText((minute) + ":" + second + "    " + "Stop");
    }

    public void WarningSign() throws IOException {
        final ImageView warnPic = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
                                                                                     "gameemvc/Pictures/paper.png")));
        warnPic.setFitHeight(WARN_HEIGHT);
        warnPic.setFitWidth(WARN_WIDTH);

        final Label label = new Label("Back is forbidden!");
        label.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        label.setTextFill(Color.rgb(R, G, B));

        warn = new StackPane(warnPic, label);
        warn.setLayoutX(RES_X);

        fieldParams.field.getChildren().addAll(warn);
    }

    public void RemoveWarning(){
        fieldParams.field.getChildren().remove(warn);
    }

    public void StopGame() throws FileNotFoundException {
        at.stop();
        stopPic.setFitHeight(STOP_HEIGHT);
        stopPic.setFitWidth(STOP_WIDTH);
        stopPic.setLayoutY(STOP_Y);
        stopPic.setLayoutX(RES_X + 100);;

        newGame.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        newGame.setTextFill(Color.rgb(R, G, B));
        scores.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        scores.setTextFill(Color.rgb(R, G, B));
        close.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        close.setTextFill(Color.rgb(R, G, B));
        menu.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        menu.setTextFill(Color.rgb(R, G, B));

        stopButtons.setLayoutX(RES_X + 120);
        stopButtons.setLayoutY(STOP_Y + 70);

        if(!fieldParams.field.getChildren().contains(stopButtons))
            fieldParams.field.getChildren().addAll(stopPic, stopButtons);
    }

    public void CloseStop(){
        if(fieldParams.field.getChildren().contains(scorePic))
            fieldParams.field.getChildren().removeAll(scorePic, results, closeScore, ids, scoresText);

        fieldParams.field.getChildren().removeAll(stopButtons, stopPic);
        at.start();
    }

    public void ShowScores(int id) {
        scorePic.setFitHeight(STOP_HEIGHT);
        scorePic.setFitWidth(STOP_WIDTH);
        scorePic.setLayoutY(STOP_Y);
        scorePic.setLayoutX(RES_X + 100);

        results.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        results.setFill(Color.rgb(R, G, B));
        results.setLayoutX(RES_X + 120);
        results.setLayoutY(STOP_Y + 50);

        Text idRes = new Text("ID");
        Text idText = new Text(String.valueOf(id));
        idText.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        idRes.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        idRes.setFill(Color.rgb(R, G, B));
        idText.setFill(Color.rgb(R, G, B));

        ids = new FlowPane(Orientation.VERTICAL, 0, 10, idRes, idText);
        ids.setLayoutX(RES_X + 120);
        ids.setLayoutY(STOP_Y + 80);

        Text scoreText = new Text(String.valueOf(playerParams.HEALTH_LINE.getEndX()));
        scoreText.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        scoreText.setFill(Color.rgb(R, G, B));

        Text scoreNum = new Text("Scores");
        scoreNum.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
        scoreNum.setFill(Color.rgb(R, G, B));
        scoreNum.setLayoutX(RES_X + 250);
        scoreNum.setLayoutY(STOP_Y + 80);

        scoresText = new FlowPane(Orientation.VERTICAL, 0, 10, scoreNum, scoreText);
        scoresText.setLayoutX(RES_X + 250);
        scoresText.setLayoutY(STOP_Y + 80);

        closeScore.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        closeScore.setTextFill(Color.rgb(R, G, B));
        closeScore.setLayoutX(RES_X + 120);
        closeScore.setLayoutY(STOP_Y + 300);

        if(!fieldParams.field.getChildren().contains(scorePic))
            fieldParams.field.getChildren().addAll(scorePic, results, closeScore, ids, scoresText);

    }

    private void NotificationBackground(int picW, int picH, int x, int y, Label label, boolean buttons) throws IOException {
        final ImageView timerPic = new ImageView(new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/" +
                                                                                     "gameemvc/Pictures/paper.png")));

        label.setFont(Font.font("Viner Hand ITC", BIG_FONT_SIZE));
        label.setTextFill(Color.rgb(R, G, B));

        timerPic.setFitHeight(picH);
        timerPic.setFitWidth(picW);

        final StackPane labelGroup = new StackPane(timerPic, label);
        labelGroup.setLayoutX(x);
        labelGroup.setLayoutY(y);

        fieldParams.field.getChildren().add(labelGroup);

        if (buttons){
            newGame = new Label("New game");
            menu = new Label("Menu");
            newGame.setUnderline(true);
            menu.setUnderline(true);

            newGame.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
            menu.setFont(Font.font("Viner Hand ITC", SMALL_FONT_SIZE));
            newGame.setTextFill(Color.rgb(R, G, B));
            menu.setTextFill(Color.rgb(R, G, B));

            final FlowPane buttonsGroup = new FlowPane(Orientation.HORIZONTAL, 90, 10, newGame, menu);
            buttonsGroup.setLayoutX(scene.getWidth() / 2 - XSHIFT_BUTTONS);
            buttonsGroup.setLayoutY(scene.getHeight() / 2 + YSHIFT_BUTTONS);

            fieldParams.field.getChildren().add(buttonsGroup);
        }
    }

    public void CheckResults() throws IOException {
        at.stop();

        if(playerParams.HEALTH_LINE.getEndX() <= WIN_SCORE) {
            final Label lose = new Label("Sorry! You've lost with  " + playerParams.HEALTH_LINE.getEndX() + " scores");
            NotificationBackground(RES_WIDTH, RES_HEIGHT, RES_X, RES_Y, lose, true);
        }

        else {
            final Label win = new Label("Congrats! You win with  " + playerParams.HEALTH_LINE.getEndX() + " scores");
            NotificationBackground(RES_WIDTH, RES_HEIGHT, RES_X, RES_Y, win, true);
        }
    }

    private void ChangeHealth(final int i, final FieldParameters fieldParams, final PlayerParameters player){
        if(fieldParams.subjectList.get(i).getImage() == fieldParams.diver.getImage()
                && player.HEALTH_LINE.getEndX() <= SHARK_WIDTH) {
            player.HEALTH_LINE.setEndX(player.HEALTH_LINE.getEndX() + SCORE);
        }

        else if(fieldParams.subjectList.get(i).getImage() == fieldParams.boot.getImage()
                && player.HEALTH_LINE.getEndX() >= SCORE) {
            player.HEALTH_LINE.setEndX(player.HEALTH_LINE.getEndX() - SCORE);
        }
    }

    private void ChangeBackground(final FieldParameters fieldParams){
        if(fieldParams.background1.getLayoutX() == -fieldParams.background1.getFitWidth())
            fieldParams.background1.setLayoutX(0);

        if(fieldParams.background2.getLayoutX() == -fieldParams.background2.getFitWidth())
            fieldParams.background2.setLayoutX(0);

        fieldParams.background1.setLayoutX(fieldParams.background1.getLayoutX() - SHIFT_BACKGR);
        fieldParams.background2.setLayoutX(fieldParams.background1.getLayoutX() + fieldParams.BACKGROUND_WIDTH - SHIFT_BACKGR);
    }

    private void CheckEat(final FieldParameters fieldParams, final PlayerParameters player){
        for(int i = 0; i < fieldParams.SUBJECT_NUM; ++i) {
            if ((fieldParams.subjectList.get(i).getLayoutY() + CORRIDOR <= player.sharkSprite.getLayoutY() + SHARK_HEIGHT
                && fieldParams.subjectList.get(i).getLayoutY() >= player.sharkSprite.getLayoutY())
                && (fieldParams.subjectList.get(i).getLayoutX() == player.sharkSprite.getLayoutX() + SHARK_WIDTH
                || fieldParams.subjectList.get(i).getLayoutX() == player.sharkSprite.getLayoutX() + SHARK_WIDTH)) {

                ChangeHealth(i, fieldParams, player);
                fieldParams.subjectList.get(i).setLayoutX(scene.getWidth() - scene.getWidth() * 2);
            }
        }
    }

    public void FlowSubjects(final FieldParameters fieldParams, final PlayerParameters player) {
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ChangeBackground(fieldParams);
                CheckEat(fieldParams, player);

                for (int i = 0; i < fieldParams.SUBJECT_NUM; ++i) {
                    if (fieldParams.subjectList.get(i).getLayoutX() < 0 || flag < fieldParams.SUBJECT_NUM) {
                        if (flag <= fieldParams.SUBJECT_NUM) flag++;

                        fieldParams.subjectList.get(i).setLayoutX(fieldParams.SUBJECT_X + i * DISTANCE);
                        fieldParams.subjectList.get(i).setLayoutY(Math.random() * 200 + 400);
                    }
                    fieldParams.subjectList.get(i).setLayoutX(fieldParams.subjectList.get(i).getLayoutX() - SHIFT_X);
                }
            }
        };
        at.start();
    }

    public void UpdatePlayerParam() throws IOException {
        playerParams = new PlayerParameters();
        fieldParams = new FieldParameters();

        CreateBackground();
        CreatePlayer();
        CreateTimer();
        Show();
    }

    public void CloseStage(){
        stage.close();
    }

    public void Show(){
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        FlowSubjects(fieldParams, playerParams);
    }

    @Override
    public void update(Enum<?> method, double[] coords) throws IOException {
        if(method.name().equals(updateMethod.GO_RIGTH.name())){
            playerParams.sharkSprite.setLayoutX(coords[0]);
            playerParams.HEALTH_LINE.setLayoutX(coords[1]);
            RemoveWarning();
        }

        if(method.name().equals(updateMethod.GO_UP_DOWN.name())){
            playerParams.sharkSprite.setLayoutY(coords[0]);
            playerParams.HEALTH_LINE.setLayoutY(coords[1]);

            RemoveWarning();
        }

        if(method.name().equals(updateMethod.WARN_SIGN.name())){
            WarningSign();
        }
    }
}
