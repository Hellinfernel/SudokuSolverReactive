package newAlternative.field;

import Stuff.EventManager;
import newAlternative.Task;


import java.util.Set;
import java.util.function.Consumer;


public abstract class Field {

    public abstract boolean isFixed();

    public abstract void exclude(int number);

    public abstract Set<Integer> possibleNumbers();

    public abstract int toIntOrZero();

    public abstract void setNumber(int number);

    public EventManager<Integer> _trueNumberFoundEvent;
    public EventManager<Integer> _changeInPossibleNumbersEvent;

    protected abstract void setRandomAvalibleNumber();

    public abstract void giveTask(Task<Field> task);


    public Field copy() {
        if ( isFixed() == true ) {
            return new StaticField(toIntOrZero());
        } else {
            return new EmptyField(possibleNumbers());
        }
    }

    @Override
    public String toString() {
        return String.valueOf(toIntOrZero());
    }
}
