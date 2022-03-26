package Stuff;

import alternative.Field;
import alternative.StaticField;
import alternative.EmptyField;

import java.util.Arrays;

public class Core {

    /** The first dimension goes right left,
     * the second dimension up own.
     *
     */

    private final Field[][] sudokuMatrix = new Field[9][9];
    //private final Map<Coordinate, Stuff.NumberField> alternativeMatrix = new HashMap<>();
    //private final Map<Coordinate, Stuff.NumberGroup> allGroups = new HashMap<>();



    public Core(){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
             sudokuMatrix[x][y] = new EmptyField();
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
    }

    public Field[][] getSudokuMatrix() {
        return sudokuMatrix;
    }
    public NumberGroup getRow(int rowNumber){
        NumberGroup row = new NumberGroup(sudokuMatrix[rowNumber - 1]);
        return row;
    }
    public NumberGroup getColumn(int columnNumber){
        Field[] columnArray = new Field[9];
        for (int i = 0; i < 9; i++){
            columnArray[i] = sudokuMatrix[i][columnNumber -1];
        }
        NumberGroup column = new NumberGroup(columnArray);
        return column;
    }
    public NumberGroup getBlock(int height, int lenght)throws ArrayIndexOutOfBoundsException{
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
        Arrays.stream(sudokuMatrix)
                .sequential()
                .forEach(array -> Arrays.stream(array)
                        .sequential()
                        .forEach(field -> field._changeInPossibleNumbersEvent.trigger()));
    }
    public void parallelStart(){
        Arrays.stream(sudokuMatrix)
                .parallel()
                .forEach(array -> Arrays.stream(array)
                        .parallel()
                        .forEach(field -> field._changeInPossibleNumbersEvent.trigger()));
    }
    public void testCoherence(){

    }

}
