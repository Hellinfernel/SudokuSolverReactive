package alternative;

import Stuff.EventManager;

import java.util.*;

public abstract class Field {
    public abstract boolean isFixed();
    public abstract void exclude(int number);
    public abstract Set<Integer> possibleNumbers();
    public abstract int toIntOrZero();
    public abstract void setNumber(int number);
    public EventManager<Integer> _trueNumberFoundEvent;
    public EventManager<Integer> _changeInPossibleNumbersEvent;

    public abstract void setRandomAvalibleNumber();
}
