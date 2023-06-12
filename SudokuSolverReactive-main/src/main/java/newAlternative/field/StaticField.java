package newAlternative.field;

import Stuff.EventManager;
import newAlternative.Task;
import newAlternative.field.Field;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class StaticField extends Field {
    private final int _number;

    @Override
    public boolean isFixed() {
        return true;
    }

    @Override
    public void exclude(int number) {

    }

    @Override
    public Set<Integer> possibleNumbers() {
        Set<Integer> number = new HashSet<>();
        number.add(_number);
        return number;
    }

    @Override
    public int toIntOrZero() {
        return _number;
    }

    @Override
    public void setNumber(int number) {

    }

    @Override
    public void setRandomAvalibleNumber() {

    }

    @Override
    public void giveTask(Task<Field> task) {

    }


    public StaticField(int number) {
        _number = number;
        _trueNumberFoundEvent = new EventManager();
        _changeInPossibleNumbersEvent = new EventManager();
    }
}
