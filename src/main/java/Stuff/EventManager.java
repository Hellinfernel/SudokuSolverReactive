package Stuff;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventManager  {

    List<Consumer> functions = new ArrayList<>();

    void addFunction (Consumer function){
        functions.add(function);
    }
    public void trigger(){
        functions.forEach(function -> function.accept(null));
    }
}
