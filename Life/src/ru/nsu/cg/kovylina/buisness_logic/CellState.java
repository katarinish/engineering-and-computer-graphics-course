package ru.nsu.cg.kovylina.buisness_logic;

import java.awt.*;

public enum CellState {
    DEAD {
        @Override
        public Color getColor() {
            return new Color(222,224,226);
        }
    },
    ALIVE {
        @Override
        public Color getColor() {
            return new Color(47,42,64);
        }
    },
    LONELY {
        @Override
        public Color getColor() {
            return new Color(141,178,180);
        }
    },
    OVERPOPULATED {
        @Override
        public Color getColor() {
            return new Color(63,113,107);
        }
    },
    READY_TO_BORN {
        @Override
        public Color getColor() {
            return new Color(144,73,99);
        }
    };

    public abstract Color getColor();
}
