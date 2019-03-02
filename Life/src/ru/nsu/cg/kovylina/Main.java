package ru.nsu.cg.kovylina;

import ru.nsu.cg.kovylina.controller.LifeGameController;
import ru.nsu.cg.kovylina.utils.Configuration;
import ru.nsu.cg.kovylina.utils.Constants;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration(
                Constants.ROWS,
                Constants.COLUMNS,
                Constants.HEX_SIDE,
                Constants.BOUNDARY_WIDTH,
                Constants.MODE
        );

        LifeGameController gameController = new LifeGameController(config);
        gameController.start();
    }
}
