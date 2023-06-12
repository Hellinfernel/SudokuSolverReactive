package Experimental;

import newAlternative.Task;

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class TaskSchedular<T> extends PriorityQueue<Task<T>> {
    T t;

    public TaskSchedular(T t){
        this.t = t;

    }
    Thread thread = new Thread(){
        @Override
        public void run() {
            Task<T> task = TaskSchedular.this.poll();
            if (task == null){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                task.accept(t);
            }
        }
    };

    @Override
    public boolean offer(Task<T> tTask) {
        boolean Boolean = super.offer(tTask);
        thread.notify();
        return Boolean;
    }

}
