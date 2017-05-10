package model.core;

/**
 * Created by Taras on 10.03.2017.
 * пока незнаю зачем, но пусть будет что-то общее для всех команд
 */
public abstract class Command {
    protected String error;
    private boolean valid = true;
    protected abstract boolean run();



    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getError() {
        return error;
    }
}
