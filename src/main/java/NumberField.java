import java.util.*;
import java.util.function.Consumer;

public class NumberField {


    
    public final boolean[] isThisNumberPossible = new boolean[9];
    public EventManager changeInPossibleNumbersEvent = new EventManager();

    public NumberField(int number){


        
        for (int a = 0; a< 9;a++ ){
            boolean field;
            if (a+1 == number){
                field = true;
            }
            else {
                field = false;
            }
            isThisNumberPossible[a] = field;
        }

    }




    public NumberField(){
        for (int a = 0; a< 9;a++ ){
            isThisNumberPossible[a] = true;
        }
    }
    public ArrayList<Integer> possibleNumbers(){
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (isThisNumberPossible[i] == true){
                numbers.add(i + 1);
            }
        }
        return numbers;
    }
    public void excludeNumber(int x) throws ArrayIndexOutOfBoundsException{
        if (x < 1 || x > 9){
            if(isThisNumberPossible[x-1] == true){

                isThisNumberPossible[x-1] = false;
                changeInPossibleNumbersEvent.trigger();
            }

        }









    }

    public int toIntOrZero() {
        if (possibleNumbers().stream().count() == 1){
            Optional<Integer> optionalNumberField =possibleNumbers().stream().findFirst();
            if (optionalNumberField.isPresent()){
                return optionalNumberField.get();
            }
            else {
                throw new NullPointerException();
            }


        }
        else  {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "NumberField{" +
                "possible number" + possibleNumbers().stream().findFirst().orElse(0).toString() +
                '}';
    }
}
