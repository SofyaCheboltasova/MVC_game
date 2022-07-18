package ru.nsu.scheboltasova.gameemvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for changing Player's coordinates and notifying View
 */
public final class Model implements Observable{
    enum updateMethod{
        GO_RIGTH,
        GO_UP_DOWN,
        WARN_SIGN
    }

    final double[] coords = new double[2];
    private PlayerParameters playerParameters;
    private static final int HEALTH_SHIFT = 110;
    private static final double STAGE = 1500;
    private static final int SCORE = 5;
    public boolean ifWarn = false;
    private final List<View> views;

    public Model() {
        views = new ArrayList<>();
        playerParameters = new PlayerParameters();
    }

    public void GoRight() throws IOException {
        if(playerParameters.sharkSprite.getLayoutX() < STAGE / 8) {
            playerParameters.sharkSprite.setLayoutX(playerParameters.sharkSprite.getLayoutX() + SCORE);
            playerParameters.HEALTH_LINE.setLayoutX(playerParameters.sharkSprite.getLayoutX() + SCORE);
        }

        ifWarn = false;

        coords[0] = playerParameters.sharkSprite.getLayoutX();
        coords[1] = playerParameters.HEALTH_LINE.getLayoutX();

        notifyObservers(updateMethod.GO_RIGTH);
    }

    public void GoUpDown(final double shift_y) throws IOException {
        playerParameters.sharkSprite.setLayoutY(playerParameters.sharkSprite.getLayoutY() + shift_y);
        playerParameters.HEALTH_LINE.setLayoutY(playerParameters.sharkSprite.getLayoutY() + HEALTH_SHIFT);

        ifWarn = false;

        coords[0] = playerParameters.sharkSprite.getLayoutY();
        coords[1] = playerParameters.HEALTH_LINE.getLayoutY();

        notifyObservers(updateMethod.GO_UP_DOWN);
    }

    public void GoBack() throws IOException {
        if(!ifWarn) {
            ifWarn = true;
            notifyObservers(updateMethod.WARN_SIGN);
        }
    }

    public void UpdatePlayerParam() {
        this.playerParameters = new PlayerParameters();
    }

    @Override
    public void registerObserver(View o) {
        views.add(o);
    }

    @Override
    public void removeObserver(View o) {
        views.remove(o);
    }

    @Override
    public void notifyObservers(Enum<?> method) throws IOException {
        for (Observer o : views)
            o.update(method, coords);
    }
}
