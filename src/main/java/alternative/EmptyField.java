package alternative;

import Stuff.EventManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmptyField extends Field {
    // private final Coordinate _coordinate;
    private final Set<Integer> _possibleNumbers;

    public EmptyField(/* final Coordinate coordinate */) {
        // _coordinate = coordinate;
        _changeInPossibleNumbersEvent = new EventManager();
        _possibleNumbers = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public EmptyField(int number) {
        _changeInPossibleNumbersEvent = new EventManager();
        _possibleNumbers = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        if (number > 9 || number < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            _possibleNumbers.remove(number);
        }
    }

    @Override
    public boolean isFixed() {
        return _possibleNumbers.size() == 1;
    }

    @Override
    public synchronized void exclude(final int number) {
        if (isFixed()) {
            return;
        }
        if (!_possibleNumbers.contains(number)) {
            return;
        }
        _possibleNumbers.remove(number);
        _changeInPossibleNumbersEvent.trigger();

    }

    @Override
    public Set<Integer> possibleNumbers() {
        return _possibleNumbers;
    }

    @Override
    public int toIntOrZero() {
        if (_possibleNumbers.size() == 1) {
            return possibleNumbers().stream().findFirst().get();
        }
        return 0;
    }
}
