package Exeptions;

public class NotAllNumbersArePossibleExeption extends Exception {
    String _message;

    public NotAllNumbersArePossibleExeption(String not_all_numbers_are_possible) {
        _message = not_all_numbers_are_possible;
    }

    @Override
    public String toString() {
        return "NotAllNumbersArePossibleExeption{" +
                "_message='" + _message + '\'' +
                '}';
    }


}
