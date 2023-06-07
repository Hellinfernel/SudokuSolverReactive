package Exeptions;

public class DoubleNumberExeption extends Exception {
    int doubleFoundNumber;
    public DoubleNumberExeption(int number){
        doubleFoundNumber = number;

    }

    @Override
    public String toString() {
        return "DoubleNumberExeption{" +
                "doubleFoundNumber=" + doubleFoundNumber +
                '}';
    }

}
