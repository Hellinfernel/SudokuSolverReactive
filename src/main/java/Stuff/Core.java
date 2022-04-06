package Stuff;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.Field;
import alternative.StaticField;
import alternative.EmptyField;

import java.util.*;

public class Core {

    /** The first dimension goes right left,
     * the second dimension up own.
     *
     */

    private final Field[][] sudokuMatrix = new Field[9][9];
    private final List<NumberGroup> sudokuRows = new LinkedList<>();
    private final List<NumberGroup> sudokuColumns = new LinkedList<>();
    private final List<NumberGroup> sudokuBlocks =new LinkedList<>();

    //private final Map<Coordinate, Old.NumberField> alternativeMatrix = new HashMap<>();
    //private final Map<Coordinate, Stuff.NumberGroup> allGroups = new HashMap<>();



    public Core(){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
             sudokuMatrix[x][y] = new EmptyField();
            }
        }
        generateGroups();
    }

    public Core (Core core) {
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                if (core.sudokuMatrix[x][y].isFixed())
                sudokuMatrix[x][y] = new StaticField(core.sudokuMatrix[x][y].toIntOrZero());
                else {
                    sudokuMatrix[x][y] = new EmptyField(core.sudokuMatrix[x][y].possibleNumbers());
                }
            }
        }
        generateGroups();
    }

    private void generateGroups() {
        for (int x = 1; x < 10; x++){
            sudokuRows.add(generateRow(x));
        }
        for (int x = 1; x < 10; x++){
            sudokuColumns.add(generateColumn(x));
        }
        for (int height = 2; height <= 8; height = height + 3){
            for (int length = 2; length <= 8; length = length +3){
             sudokuBlocks.add(generateBlock(height,length));
            }
        }
        

    }





    public Core(int[][] inputMatrix){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                if (inputMatrix[x][y] == 0){
                    sudokuMatrix[x][y] = new EmptyField();
                }
                else {
                    sudokuMatrix[x][y] = new StaticField(inputMatrix[x][y]);
                }
            }
        }
        generateGroups();
    }
    public static Core randomCreate() {
        Core core = new Core();
        int setNumbers = 0;
        for (Field[] array : core.sudokuMatrix) {

            boolean coherence = false;
            while (!coherence) {
                Core safeCore =  new Core(core);


            try {
                setNumbers = setNumberOfRowRandom(safeCore, setNumbers, array);
                coherence = true;
                core = safeCore;

            } catch (NotAllNumbersArePossibleExeption notAllNumbersArePossibleExeption) {

            } catch (DoubleNumberExeption exeption) {

            }
        }


        }
        return core;
    }

    private static int setNumberOfRowRandom(Core core, int setNumbers, Field[] array) throws NotAllNumbersArePossibleExeption, DoubleNumberExeption {
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
        return setNumbers;
    }


    public Field[][] getSudokuMatrix() {
        return sudokuMatrix;
    }
    private NumberGroup generateRow(int rowNumber){
        NumberGroup row = new NumberGroup(sudokuMatrix[rowNumber - 1]);
        return row;
    }
    public NumberGroup getRow(int number){
        return sudokuRows.get(number-1);
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
        Field[] columnArray = new Field[9];
        for (int i = 0; i < 9; i++){
            columnArray[i] = sudokuMatrix[i][columnNumber -1];
        }
        return new NumberGroup(columnArray);
    }
    private NumberGroup generateBlock(int height, int lenght)throws ArrayIndexOutOfBoundsException{
        Field[] blockArray = new Field[9];
        int z = 0;
        for (int x = height - 2; x <= height; x++ ){
        for (int y = lenght - 2; y <= lenght; y++ ) {

            blockArray[z] = sudokuMatrix[x][y];
            z++;
        }}



        return new NumberGroup(blockArray);

    }
    public void start(){
        for (Field[] array : sudokuMatrix) {
            for (Field field : array) {
                if (field.isFixed()) {
                    field._trueNumberFoundEvent.trigger(field.toIntOrZero());
                }
            }
        }
        /*Arrays.stream(sudokuMatrix)
                .sequential()
                .forEach(array -> Arrays.stream(array)
                        .sequential()
                        .forEach(field -> field._changeInPossibleNumbersEvent.trigger())); */
    }
    @Deprecated
    public void parallelStart(){
        for (Field[] array : sudokuMatrix) {
           /* Arrays.stream(array)
                    //  .parallel()
                    .forEach(field -> field._changeInPossibleNumbersEvent.trigger()); */
        }
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

}
