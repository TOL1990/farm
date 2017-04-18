package model.core;

import model.entity.Field;

/**
 * Created by Taras on 10.03.2017.
 * Вспомагательный не используется
 */
public class Core {

    public Field field = new Field();
    public static void main(String[] args) {

    }
    public Core() {
        field.createEmptyCells();
    }
}
