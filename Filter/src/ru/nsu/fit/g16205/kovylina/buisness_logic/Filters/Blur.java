package ru.nsu.fit.g16205.kovylina.buisness_logic.Filters;

import ru.nsu.fit.g16205.kovylina.buisness_logic.Filter;

public class Blur extends Filter {

    public Blur() {
        super();
    }

    @Override
    protected void initParameters() {
        matrix = new int[][]{
                {1, 2, 3, 2 , 1},
                {2, 4, 5, 4, 2},
                {3, 5 ,6 , 5, 3},
                {2, 4, 5, 4, 2},
                {1, 2, 3, 2 , 1}};

        div = 74;
    }
}
