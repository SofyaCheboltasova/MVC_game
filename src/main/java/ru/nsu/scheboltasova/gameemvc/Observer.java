package ru.nsu.scheboltasova.gameemvc;
import java.io.IOException;

public interface Observer {
        void update (Enum<?> method, double[] coords) throws IOException;
}
