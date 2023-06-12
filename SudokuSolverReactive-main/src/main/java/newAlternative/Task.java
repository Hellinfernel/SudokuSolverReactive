package newAlternative;

import java.util.function.Consumer;

public class Task<T> implements Consumer<T>, Comparable<Task> {
    byte _priority;
    Consumer<T> _consumer;

    public Task(Consumer<T> consumer, byte priority){
        _priority = priority;
        _consumer = consumer;
    }

    @Override
    public void accept(T t) {
        _consumer.accept(t);

    }

    @Override
    public int compareTo(Task otherTask) {
        return _priority - otherTask._priority;
    }


}
