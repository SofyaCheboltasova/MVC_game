package ru.nsu.scheboltasova.gameemvc;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Class for default player settings: pic of shark and shark's health-line
 */
public final class PlayerParameters {
    private static final int SHARK_Y = 450;
    private static final int SHARK_X = 0;
    private static final int HEALTH_Y = 560;
    private static final int HEALTH_X = 10;
    public final FlowPane sharkSprite;
    public final ImageView spriteImg;
    public final Line HEALTH_LINE;
    public final Group shark;

    public PlayerParameters() {
        spriteImg = new ImageView();
        sharkSprite = new FlowPane(spriteImg);
        sharkSprite.setLayoutX(SHARK_X);
        sharkSprite.setLayoutY(SHARK_Y);

        HEALTH_LINE= new Line();
        HEALTH_LINE.setLayoutX(HEALTH_X);
        HEALTH_LINE.setLayoutY(HEALTH_Y);
        HEALTH_LINE.setStrokeWidth(5);
        HEALTH_LINE.setStroke(Color.DARKRED);

        shark = new Group(sharkSprite, HEALTH_LINE);
    }
}
