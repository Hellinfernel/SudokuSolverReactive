import java.util.Arrays;
import java.util.EventListener;
import java.util.function.Function;

public class NumberGroup {
     private final NumberField[] _numberFields;



    NumberGroup(NumberField[] matrix) throws ArrayIndexOutOfBoundsException {
        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = new NumberField[9];
        for (int i = 0; i < 9; i++) {
            _numberFields[i] = matrix[i];
            _numberFields[i].changeInPossibleNumbersEvent.addFunction(c -> analyse());
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
                        .forEach(f -> f.excludeNumber(field.possibleNumbers()
                                .stream()
                                .findFirst()
                                .orElse(0))));


    }

    int[] getGroupAsArray(){
        int[] array = new int[9];
        for (int i = 0; i < 9; i++) {
            array[i] = _numberFields[i].toIntOrZero();

        }
        return array;
    }




}
