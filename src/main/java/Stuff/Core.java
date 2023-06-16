package Stuff;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.Coordinate;
import alternative.EmptyField;
import alternative.Field;
import alternative.StaticField;

import static java.util.concurrent.CompletableFuture.anyOf;

public class Core {

    /** The first dimension goes right left,
     * the second dimension up own.
     *
     */

    // private final Field[][] _sudokuMatrix = new Field[9][9];
    private final Collection<Field> _sudokuMatrix = new HashSet<>();
    private final List<NumberGroup> sudokuRows = new LinkedList<>();
    private final List<NumberGroup> sudokuColumns = new LinkedList<>();
    private final List<NumberGroup> sudokuBlocks = new LinkedList<>();
    //private final Map<Coordinate, Old.NumberField> alternativeMatrix = new HashMap<>();
    //private final Map<Coordinate, Stuff.NumberGroup> allGroups = new HashMap<>();



    public Core(){
        for (int x = 1; x < 10; x++){
            for (int y = 1; y < 10; y++){
             _sudokuMatrix.add(new EmptyField(new Coordinate(x,y)));
            }
        }
        generateGroups();
        if (sudokuRows.size() == 9){
            if (sudokuColumns.size() == 9){
                if (sudokuBlocks.size() == 9){
                    return;
                }
            }
        }
        else {
            throw new RuntimeException(toString());
        }

    }

    public Core (Core core) {
        core._sudokuMatrix.stream().map(Field::copy).forEach(_sudokuMatrix::add);
        /*for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                _sudokuMatrix.add(core._sudokuMatrix.stream().filter()) = core._sudokuMatrix[x][y].copy();
            }
        }*/
        generateGroups();
        if (sudokuRows.size() == 9){
            if (sudokuColumns.size() == 9){
                if (sudokuBlocks.size() == 9){
                    return;
                }
            }
        }
        else {
            throw new RuntimeException(toString());
        }
    }

    private void generateGroups() {
        for (int x = 1; x <= 9; x++){
            sudokuRows.add(generateRow(x));
        }
        for (int x = 1; x <= 9; x++){
            sudokuColumns.add(generateColumn(x));
        }
        for (int height = 2; height <= 8; height = height + 3){
            for (int length = 2; length <= 8; length = length +3){

             sudokuBlocks.add(generateBlock(height,length));
            }
        }
        

    }





    public Core(int[][] inputMatrix){
        for (int x = 1; x < 10; x++){
            for (int y = 1; y < 10; y++){
                if (inputMatrix[x-1][y-1] == 0){
                    _sudokuMatrix.add(new EmptyField(new Coordinate(x,y)));

                }
                else {
                    _sudokuMatrix.add(new StaticField(inputMatrix[x-1][y-1], new Coordinate(x,y)));

                }
            }
        }
        generateGroups();
        if (sudokuRows.size() == 9){
            if (sudokuColumns.size() == 9){
                if (sudokuBlocks.size() == 9){
                    return;
                }
            }
        }
        else {
            throw new RuntimeException(toString());
        }
    }
    public static Core randomCreate() {
        Core core = new Core();
        int setNumbers = 0;
        for (int i = 1; i < 10; i++) {


            boolean coherence = false;
            while (!coherence) {
                Core safeCore = new Core(core);
              /*  safeCore.start();
                System.out.println(safeCore.doesContainOnlyZero());
                System.out.println(core.doesContainOnlyZero()); */


                try {
                    safeCore = setNumberOfRowRandom(safeCore, setNumbers, safeCore.getRow(i).get_numberFields());
                    coherence = true;
                 /*   System.out.println(safeCore.doesContainOnlyZero());
                    System.out.println(core.doesContainOnlyZero()); */

                    core = safeCore;
                 /*   core.start();

                    System.out.println(safeCore.doesContainOnlyZero());
                    System.out.println(core.doesContainOnlyZero()); */
                } catch (NotAllNumbersArePossibleExeption notAllNumbersArePossibleExeption) {

                } catch (DoubleNumberExeption exeption) {

                }
            }


        }
        return core;
    }

    private static Core setNumberOfRowRandom(Core _core, int setNumbers, Field[] array) throws NotAllNumbersArePossibleExeption, DoubleNumberExeption {
        Core core = _core;
        for (Field field : array) {
            setNumbers++;
            if (field.isFixed() == false) {

                field.setRandomAvalibleNumber();

                core.testCoherence();



                /* throw new RuntimeException(setNumbers + "\n" +
                           Arrays.toString(core.getRow(1).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(2).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(3).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(4).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(5).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(6).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(7).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(8).getGroupAsArray()) + "\n"+
                            Arrays.toString(core.getRow(9).getGroupAsArray()) + "\n"
                           ); */
            }
        }
        return core;
    }







    public Collection<Field> get_sudokuMatrix() {
        return _sudokuMatrix;
    }
    private NumberGroup generateRow(int rowNumber){
        NumberGroup row = new NumberGroup(get_sudokuMatrix().stream().filter(field -> field.getCoordinate()._x == rowNumber).collect(Collectors.toList()));
        return row;
    }
    public NumberGroup getRow(int number){
        return sudokuRows.get(number - 1);
    }
    public NumberGroup getColumn(int number){
        return sudokuColumns.get(number-1);
    }

    /**
     *
     * @param number
     * @return
     * thats the supposed order:
     * 123
     * 456
     * 789
     */
    public NumberGroup getBlock(int number){
        return sudokuBlocks.get(number-1);
    }
    private NumberGroup generateColumn(int columnNumber){
        return new NumberGroup(get_sudokuMatrix().stream().filter(field -> field.getCoordinate()._y == columnNumber).collect(Collectors.toList()));
    }
    private NumberGroup generateBlock(int height, int lenght)throws ArrayIndexOutOfBoundsException{
        Collection<Field> fieldCollection= new HashSet<>();
        for (int x = height - 1; x <= height +1; x++ ){
        for (int y = lenght - 1; y <= lenght +1; y++ ) {

            int finalX = x;
            int finalY = y;
            _sudokuMatrix.stream().filter(field -> field.getCoordinate().coodinateIsEqual(finalX, finalY)).forEach(fieldCollection::add);
        }}



        return new NumberGroup(fieldCollection);

    }
    public void kickStartEventTrigger(){
        _sudokuMatrix.stream()
                .filter(Field::isFixed)
                .forEach(field -> field._trueNumberFoundEvent.trigger(field.toIntOrZero()));
        if (!isSolvedCompleatly()){
            sudokuRows.forEach(NumberGroup::searchSetableNumber);
            sudokuColumns.forEach(NumberGroup::searchSetableNumber);
            sudokuBlocks.forEach(NumberGroup::searchSetableNumber);

        }
        /*for (Field[] array : _sudokuMatrix) {
            for (Field field : array) {
                if (field.isFixed()) {
                    field._trueNumberFoundEvent.trigger(field.toIntOrZero());
                }
            }
        } */
        /*Arrays.stream(sudokuMatrix)
                .sequential()
                .forEach(array -> Arrays.stream(array)
                        .sequential()
                        .forEach(field -> field._changeInPossibleNumbersEvent.trigger())); */
    }


    public void testCoherence() throws DoubleNumberExeption, NotAllNumbersArePossibleExeption {
        for (NumberGroup sudokuRow : sudokuRows) {
            sudokuRow.testCoherence();
        }
        for (NumberGroup sudokuColumn : sudokuColumns) {
            sudokuColumn.testCoherence();
        }
        for (NumberGroup sudokuBlock : sudokuBlocks) {
            sudokuBlock.testCoherence();
        }


    }

    /**
     * @return true if every field.possibleNumbers contains all possible numbers
     */
    public boolean doesContainOnlyZero(){
        return _sudokuMatrix.stream().allMatch(field -> field.possibleNumbers().containsAll(Constants.ALL_NUMBERS));
       /* for (Field[] array : _sudokuMatrix) {
            for (Field field : array) {
                if (!field.possibleNumbers().containsAll(Constants.ALL_NUMBERS)) {
                    return false;
                }
            }
        }
        return true; */
    }
    public boolean isSolvedCompleatly(){

        return _sudokuMatrix.stream().allMatch(Field::isFixed);
        /* for (Field[] array : _sudokuMatrix) {
            for (Field field : array) {
                if (!field.isFixed()) {
                    return false;
                }
            }
        }
        return true; */
    }
    public Field getFieldByCoordinate(int x, int y){
        return _sudokuMatrix.stream().filter(field -> field.getCoordinate().coodinateIsEqual(x,y)).findFirst().orElseThrow();
    }
    public Field getFieldByCoordinate(Coordinate coordinate){
        return _sudokuMatrix.stream().filter(field -> field.getCoordinate().coodinateIsEqual(coordinate._x,coordinate._y)).findFirst().orElseThrow();
    }


    @Override
    public String toString() {
        return "Core{" +
                "_sudokuMatrix=" + _sudokuMatrix.size() +
                ", sudokuRows=" + sudokuRows.size() +
                ", sudokuColumns=" + sudokuColumns.size() +
                ", sudokuBlocks=" + sudokuBlocks.size() +
                '}';
    }
}
