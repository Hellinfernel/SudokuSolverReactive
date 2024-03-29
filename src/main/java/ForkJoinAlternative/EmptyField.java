package ForkJoinAlternative;

import Stuff.Constants;
import Stuff.EventManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EmptyField extends Field {
    // private final Coordinate _coordinate;
    private final Set<Integer> _possibleNumbers;

    public EmptyField(/* final Coordinate coordinate */) {
        // _coordinate = coordinate;
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();
        _possibleNumbers = Collections.synchronizedSet(new HashSet<>());
        _possibleNumbers.addAll(Constants.ALL_NUMBERS);
    }

    public EmptyField(int number) {
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();
        _possibleNumbers = Collections.synchronizedSet(new HashSet<>());
        _possibleNumbers.addAll(Constants.ALL_NUMBERS);
        if (number > 9 || number < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            _possibleNumbers.remove(number);
        }

    }

    public EmptyField(Set<Integer> possibleNumbers) {
        _changeInPossibleNumbersEvent = new EventManager<>();
        _trueNumberFoundEvent = new EventManager<>();

        _possibleNumbers = Collections.synchronizedSet(new HashSet<>());
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

        if (isFixed()) {
            _trueNumberFoundEvent.trigger(toIntOrZero());
        }

    }

    @Override
    public synchronized Set<Integer> possibleNumbers() {
        return new HashSet<>(_possibleNumbers);
    }

    @Override
    public synchronized int toIntOrZero() {
        if (_possibleNumbers.size() == 1) {
            return possibleNumbers().stream().findFirst().get();
        }
        return 0;
    }

    @Override
    public synchronized void setNumber(int number) {

        _possibleNumbers
                .stream()
                .filter(pNumber -> pNumber != number)
                .forEach(this::exclude);

    }

    @Override
    public synchronized void setRandomAvalibleNumber() {
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
