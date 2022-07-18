package ru.nsu.scheboltasova.gameemvc;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class for default field settings: background pics and flow of subjects
 */
public final class FieldParameters {
    public static final int SUBJECT_HEIGHT = 60;
    public static final int SUBJECT_WIDTH = 50;
    public final int BACKGROUND_HEIGHT = 1000;
    public final int BACKGROUND_WIDTH = 3000;
    public final int SUBJECT_NUM = 14;
    public final int SUBJECT_X = 1500;

    public final List<ImageView> subjectList;
    public final ImageView background1;
    public final ImageView background2;
    public ImageView boot, diver;
    public final StackPane warn;
    public final Group field;

    private final Image img2 = new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/gameemvc/Pictures/diver.png"));
    private final Image img1 = new Image(new FileInputStream("src/main/java/ru/nsu/scheboltasova/gameemvc/Pictures/boots.png"));

    public FieldParameters() throws IOException {
        background1 = new ImageView();
        background2 = new ImageView();
        subjectList = new ArrayList<>();
        warn = new StackPane();
        field = new Group();

        for(int i = 0; i < 7; ++i) {
            boot = new ImageView(img1);
            subjectList.add(i, boot);
        }
        for(int i = 7; i < 14; ++i){
            diver = new ImageView(img2);
            subjectList.add(i, diver);
        }

        DefaultField();

        field.getChildren().addAll(background1, background2);
        for(int i = 0; i < SUBJECT_NUM; ++i)
            field.getChildren().add(subjectList.get(i));
    }

    public void DefaultField(){
        for(int i = 0; i < SUBJECT_NUM; ++i) {
            subjectList.get(i).setFitHeight(SUBJECT_HEIGHT);
            subjectList.get(i).setFitWidth(SUBJECT_WIDTH);
        }
        Collections.shuffle(subjectList);
    }
}
