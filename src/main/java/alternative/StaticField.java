package alternative;

import Stuff.EventManager;

import java.util.HashSet;
import java.util.Set;

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

    public StaticField(int number) {
        _number = number;
        _trueNumberFoundEvent = new EventManager();
        _changeInPossibleNumbersEvent = new EventManager();
    }
}
