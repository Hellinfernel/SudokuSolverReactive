package Stuff;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.EmptyField;
import alternative.Field;
import alternative.StaticField;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ConcurrentHashMap.newKeySet;

public class NumberGroup {
    private final Set<Field> _numberFields;
    private final Set<Integer> _leftNumbers;


    public NumberGroup(Field[] matrix) throws ArrayIndexOutOfBoundsException {
        _leftNumbers = Collections.synchronizedSet(new HashSet<>(Constants.ALL_NUMBERS));



        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = new LinkedHashSet<>(Arrays.asList(matrix));
        _numberFields.forEach(field -> field._changeInPossibleNumbersEvent.addFunction(this::searchSetableNumber));
        _numberFields.forEach(field -> field._trueNumberFoundEvent.addFunction(this::excludeNumber));




    }
    public NumberGroup(int[] matrix)throws ArrayIndexOutOfBoundsException{
        _leftNumbers = Collections.synchronizedSet(new HashSet<>(Constants.ALL_NUMBERS));

        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = Arrays.stream(matrix)
                .boxed()
                .map(this::generateField)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        _numberFields.stream().forEach(field -> field._changeInPossibleNumbersEvent.addFunction(this::searchSetableNumber));
        _numberFields.stream().forEach(field -> field._trueNumberFoundEvent.addFunction(this::excludeNumber));

    }
    private Field generateField(int number){
        Field field;

            if (number == 0){
                field = new EmptyField();
            }
            else {
                field = new StaticField(number);
            }
            return field;
        }



    /**
     * Searches a number who can be set in this group
     *
     */

    public void searchSetableNumber() {
        for (Integer number : _leftNumbers) {
            List<Field> fields = new ArrayList<>();
            for (Field field : _numberFields) {
                if (field.possibleNumbers().contains(number)) {
                    fields.add(field);
                }
            }
            if (fields.size() == 1){
                fields.get(0).setNumber(number);

            }


        }
    }

    /**
     * searches for a field where this number fits.
     * @param number the number
     */
    public void searchSetableNumber(int number) {

            List<Field> fields = new ArrayList<>();
            for (Field field : _numberFields) {
                if (field.possibleNumbers().contains(number)) {
                    fields.add(field);
                }
            }
            if (fields.size() == 1){
                fields.get(0).setNumber(number);

            }



    }

    /**
     * analyses the group and uses excludeNumber() from NumberFields in _numberFields if there are fields with a guarantied number
     *
     */
    private void excludeNumber(int number) {
        if (number == 0){
            throw new RuntimeException();
          //  System.err.println("this should not happen, excludeNumber got a 0. the event was triggered to the wrong time");
        }
        _leftNumbers.remove(number);
        for (Field field : _numberFields) {
            if (field.isFixed() == false) {
                field.exclude(number);
            }
        }


    }

    /**
     * mainly a test method.
     */

    public void analyse(){
        for (Field field : _numberFields) {
            if (field.isFixed()) {
                field._trueNumberFoundEvent.trigger(field.toIntOrZero());
            }
        }
    }

    public int[] getGroupAsArray(){
        return _numberFields.stream()
                .flatMapToInt(field -> IntStream.of(field.toIntOrZero())).toArray();
    }

    public Field[] get_numberFields() {
        return _numberFields.toArray(new Field[9]);
    }

    public void testCoherence() throws DoubleNumberExeption, NotAllNumbersArePossibleExeption {
        searchForDoubleSetNumbers();
            testIfAllNumbersArePossible();






    }

    /**
     *
     * @return true if all numbers are possible
     */

    public void testIfAllNumbersArePossible() throws NotAllNumbersArePossibleExeption {
        long count = 0L;
        for (Integer ALL_NUMBER : Constants.ALL_NUMBERS) {
            if (isThisNumberPossible(ALL_NUMBER)) {
                count++;
            }
        }
        if (count != 9){
            throw new NotAllNumbersArePossibleExeption("not all numbers are Possible");
        }


    }

    /**
     *
     * @param number the number who is tested.
     * @return a true if there is a field where this number can placed.
     * false should only ocure if the sudoku has mistakes.
     */
    public boolean isThisNumberPossible(int number){
        boolean b = false;
        for (Field field : _numberFields) {
            if (field.possibleNumbers().contains(number)) {
                b = true;
                break;
            }
        }
        if (b){
            return true;
        }
        else return false;
    }

    /**
     *
     * @throws DoubleNumberExeption if there is a number 2 times in the same group
     */

    public void searchForDoubleSetNumbers() throws DoubleNumberExeption{
        List<Integer> fixedNumbers = new LinkedList<>();
        _numberFields.stream()
                .filter(field -> field.toIntOrZero() != 0)
                .forEach(field -> fixedNumbers.add(field.toIntOrZero()));
        fixedNumbers.sort(Comparator.naturalOrder());
        for (int x = 0; x < fixedNumbers.size() - 1;x++ ){

            if (fixedNumbers.get(x).equals(fixedNumbers.get(x + 1))){
                throw new DoubleNumberExeption(fixedNumbers.get(x));
               // return new ErrorMessage("the number "+ fixedNumbers.get(x) + "seems to be appear multible time", false);
            }
        }

    }
}
