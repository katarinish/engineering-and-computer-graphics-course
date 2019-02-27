package ru.nsu.cg.kovylina;

import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.Configuration;
import ru.nsu.cg.kovylina.utils.Mode;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration(
                15,
                15,
                30,
                2,
                Mode.REPLACE
        );

        LifeGameController gameController = new LifeGameController(config);
        gameController.start();
    }
}
