package alternative;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Stuff.Constants;
import Stuff.EventManager;

public class EmptyField extends Field {
    private final Coordinate _coordinate;
    private final Set<Integer> _possibleNumbers;

    public EmptyField(/* final Coordinate coordinate */Coordinate coordinate) {
        _coordinate = coordinate;
        // _coordinate = coordinate;
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();
        _possibleNumbers = ConcurrentHashMap.newKeySet();
        _possibleNumbers.addAll(Constants.ALL_NUMBERS);
    }

    public EmptyField(int number, Coordinate coordinate) {
        _coordinate = coordinate;
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();
        _possibleNumbers = ConcurrentHashMap.newKeySet();
        _possibleNumbers.addAll(Constants.ALL_NUMBERS);
        if (number > 9 || number < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            _possibleNumbers.remove(number);
        }

    }

    public EmptyField(Set<Integer> possibleNumbers, Coordinate coordinate) {
        _coordinate = coordinate;
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();

        _possibleNumbers = ConcurrentHashMap.newKeySet();
        _possibleNumbers.addAll(possibleNumbers);
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
        _changeInPossibleNumbersEvent.trigger(number);

        if (isFixed()) {
            _trueNumberFoundEvent.trigger(toIntOrZero());
        }

    }

    @Override
    public Set<Integer> possibleNumbers() {
        return new HashSet<>(_possibleNumbers);
    }

    @Override
    public int toIntOrZero() {
        if (_possibleNumbers.size() == 1) {
            return possibleNumbers().stream().findFirst().get();
        }
        return 0;
    }

    @Override
    public void setNumber(int number) {
        _possibleNumbers
                .stream()
                .filter(pNumber -> pNumber != number)
                .forEach(this::exclude);
    }

    @Override
    public Coordinate getCoordinate() {
        return _coordinate;
    }

    @Override
    public void setRandomAvalibleNumber() {
        if (isFixed() == false){
            if (toIntOrZero() == 0){
                Random random = new Random();
                List<Integer> possibleNumbers = new ArrayList<>(_possibleNumbers);
                int number = possibleNumbers.get(random.nextInt(possibleNumbers.size()));
                setNumber(number);

            }

        }

    }

}
