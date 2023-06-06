package Stuff;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventManager<T>  {

    List<Consumer<T>> functions = new ArrayList<>();

    public void addFunction (Consumer<T> function){
        functions.add(function);
    }
    public void trigger(T t){
        functions.forEach(function -> function.accept(t));
    }
}
