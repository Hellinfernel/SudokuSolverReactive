package Stuff;

import alternative.Field;

import java.util.Arrays;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class NumberGroup {
    private final Field[] _numberFields;


    public NumberGroup(Field[] matrix) throws ArrayIndexOutOfBoundsException {

        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = new Field[9];
        for (int i = 0; i < 9; i++) {
            _numberFields[i] = matrix[i];
            _numberFields[i]._changeInPossibleNumbersEvent.addFunction(c -> analyse());
        }


    }

    /**
     * analyses the group and uses excludeNumber() from NumberFields in _numberFields if there are fields with a guarantied number
     *
     */

    public void analyse() {
        Arrays.stream(_numberFields)
                .filter(field -> field.possibleNumbers().size() == 1)
                .forEach(field -> Arrays.stream(_numberFields)
                        .forEach(f -> f.exclude(field.possibleNumbers()
                                .stream()
                                .findFirst()
                                .orElse(0))));
    }

    public int[] getGroupAsArray(){
        int[] array = new int[9];
        for (int i = 0; i < 9; i++) {
            array[i] = _numberFields[i].toIntOrZero();

        }
        return array;
    }

    public Field[] get_numberFields() {
        return _numberFields;
    }
}
