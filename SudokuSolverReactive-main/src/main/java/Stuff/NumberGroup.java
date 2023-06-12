package Stuff;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.Coordinate;
import alternative.EmptyField;
import alternative.Field;
import alternative.StaticField;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ConcurrentHashMap.newKeySet;

public class NumberGroup {
    private final Set<Field> _numberFields;
    //private final Map<Integer,Integer> _leftNumbers;


    public NumberGroup(Field[] matrix) throws ArrayIndexOutOfBoundsException {



        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = Collections.checkedSortedSet(new TreeSet<>(Comparator.comparing(Field::getCoordinate)), Field.class);
                _numberFields.addAll(Arrays.stream(matrix).collect(Collectors.toSet()));
        _numberFields.forEach(field -> field._changeInPossibleNumbersEvent.addFunction(this::searchSetableNumber));
        _numberFields.forEach(field -> field._trueNumberFoundEvent.addFunction(this::excludeNumber));
        //_leftNumbers = setupLeftNumbers();







    }

    private Map<Integer, Integer> setupLeftNumbers() {
        final Map<Integer, Integer> _leftNumbers;
        _leftNumbers = Collections.synchronizedMap(new HashMap<Integer,Integer>());
        Constants.ALL_NUMBERS.stream()
                .forEach(number -> _leftNumbers.put(number, ((int) _numberFields.stream()
                        .filter(field -> field.possibleNumbers()
                                .contains(number))
                        .count())));
        return _leftNumbers;
    }

    public NumberGroup(Collection<Field> collection) throws ArrayIndexOutOfBoundsException {




        if (collection.size() != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        _numberFields = Collections.checkedSortedSet(new TreeSet<Field>(Comparator.comparing(Field::getCoordinate)), Field.class);
        _numberFields.addAll(collection);
        _numberFields.forEach(field -> field._changeInPossibleNumbersEvent.addFunction(this::searchSetableNumber));
        _numberFields.forEach(field -> field._trueNumberFoundEvent.addFunction(this::excludeNumber));
        //_leftNumbers = setupLeftNumbers();




    }
    // DO NOT USE
    public NumberGroup(int[] matrix)throws ArrayIndexOutOfBoundsException{
        //_leftNumbers = setupLeftNumbers();
        List<Coordinate> coordinates = new LinkedList<>();
        Constants.ALL_NUMBERS.forEach(number -> coordinates.add(new Coordinate(number,1)));

        if (matrix.length != 9){
            throw new ArrayIndexOutOfBoundsException();
        }
        TreeSet<Field> fields = new TreeSet<>(Comparator.comparing(Field::getCoordinate));
        for (int i : matrix) {
            Integer number = i;
            Field generateField = generateField(number, coordinates.get(i-1));
            fields.add(generateField);
        }
        _numberFields = fields;
        _numberFields.stream().forEach(field -> field._changeInPossibleNumbersEvent.addFunction(this::searchSetableNumber));
        _numberFields.stream().forEach(field -> field._trueNumberFoundEvent.addFunction(this::excludeNumber));

    }

    /**
     * only used for test poporses
     * @param number
     * @return
     */
    private Field generateField(int number, Coordinate coordinate){
        Field field;

            if (number == 0){
                field = new EmptyField(new Coordinate(1,1));
            }
            else {
                field = new StaticField(number, new Coordinate(1,1));
            }
            return field;
        }



    /**
     * Searches a number who can be set in this group
     *
     */

    /*public void searchSetableNumber() {
        for (Integer number : get_leftNumbers()) {
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
    }*\

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
        //_leftNumbers.replace(number, _leftNumbers.get(number) - 1);
        for (Field field : _numberFields) {
            if (field.isFixed() == false) {
                field.exclude(number);
            }
        }
        /*if (_leftNumbers.get(number) == 1){
            _numberFields.stream()
                    .filter(field -> field.possibleNumbers().contains(number))
                    .findFirst()
                    //TODO Correct Error message
                    .ifPresentOrElse(field -> field.setNumber(number), () -> System.out.println("error, this shouldnt happen"));
        }*/



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

 /*   public Set<Integer> get_leftNumbers() {
        return _leftNumbers.keySet();
    }*/
}
