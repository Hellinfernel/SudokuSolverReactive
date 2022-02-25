package alternative;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Field {
    boolean isFixed();
    default void exclude(int number){

    }

    final class StaticField implements Field {

        @Override
        public boolean isFixed() {
            return true;
        }
    }

    final class UserField implements  Field {
        private final Coordinate _coordinate;
        private final Set<Integer> _possibleNumbers;

        public UserField(final Coordinate coordinate){
            _coordinate = coordinate;
            _possibleNumbers = new HashSet<>(List.of(1,2,3,4,5,6,7,8,9));
        }

        @Override
        public boolean isFixed() {
            return _possibleNumbers.size() == 1;
        }

        @Override
        public void exclude(final int number) {
            if (isFixed()) {
                return;
            }
            if (!_possibleNumbers.contains(number)) {
                return;
            }
            _possibleNumbers.remove(number);
            //_eventManager.fieldChanged(_coordinate);
        }
    }
}
