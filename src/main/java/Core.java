public class Core {

    /** The first dimension goes right left,
     * the second dimension up own.
     *
     */

    private final NumberField[][] sudokuMatrix = new NumberField[9][9];




    public Core(){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
             sudokuMatrix[x][y] = new NumberField();
            }
        }
    }
    public Core(int[][] inputMatrix){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                sudokuMatrix[x][y] = new NumberField(inputMatrix[x][y]);
            }
        }
    }

    public NumberField[][] getSudokuMatrix() {
        return sudokuMatrix;
    }
    public NumberGroup getRow(int rowNumber){
        NumberGroup row = new NumberGroup(sudokuMatrix[rowNumber - 1]);
        return row;
    }
    public NumberGroup getColumn(int columnNumber){
        NumberField[] columnArray = new NumberField[9];
        for (int i = 0; i < 9; i++){
            columnArray[i] = sudokuMatrix[i][columnNumber -1];
        }
        NumberGroup column = new NumberGroup(columnArray);
        return column;
    }
    public NumberGroup getBlock(int height, int lenght)throws ArrayIndexOutOfBoundsException{
        NumberField[] blockArray = new NumberField[9];
        int z = 0;
        for (int x = height - 2; x <= height; x++ ){
        for (int y = lenght - 2; y <= lenght; y++ ) {

            blockArray[z] = sudokuMatrix[x][y];
            z++;
        }}



        return new NumberGroup(blockArray);

    }
}
