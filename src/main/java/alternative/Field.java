package alternative;

import java.util.Set;

import Stuff.EventManager;


public abstract class Field {

    public abstract boolean isFixed();

    public abstract void exclude( int number );

    public abstract Set<Integer> possibleNumbers();

    public abstract int toIntOrZero();

    public abstract void setNumber( int number );

    public EventManager<Integer> _trueNumberFoundEvent;
    public EventManager<Integer> _changeInPossibleNumbersEvent;

    public abstract void setRandomAvalibleNumber();

    public Field copy() {
        if ( isFixed() ) {
            return new StaticField(toIntOrZero());
        } else {
            return new EmptyField(possibleNumbers());
        }
    }
}
