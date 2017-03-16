package model.core;

import model.entity.Field;

/**
 * Created by Taras on 10.03.2017.
 * пока незнаю зачем, но пусть будет что-то общее для всех команд
 */
public abstract class Command {
    public boolean isValid = true;
    protected abstract boolean run();



    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
