package ru.nsu.scheboltasova.gameemvc;

import java.io.IOException;

public interface Observable {
    default void registerObserver(View o) {
    }

    default void removeObserver(View o) {
    }

    default void notifyObservers(Enum<?> method) throws IOException {

    }
}
