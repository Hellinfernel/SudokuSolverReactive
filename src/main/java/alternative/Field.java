package alternative;

import Stuff.EventManager;

import java.util.*;

public abstract class Field {
    public abstract boolean isFixed();
    public abstract void exclude(int number);
    public abstract Set<Integer> possibleNumbers();
    public abstract int toIntOrZero();
    public EventManager _changeInPossibleNumbersEvent;

}
