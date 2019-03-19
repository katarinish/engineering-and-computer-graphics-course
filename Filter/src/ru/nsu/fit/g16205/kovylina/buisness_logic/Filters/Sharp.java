package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

public class Sharp extends Filter {
    public Sharp() {}

    @Override
    protected void initParameters() {
        matrix = new int[][] {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}};
    }
}
