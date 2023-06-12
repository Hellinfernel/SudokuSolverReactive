package Stuff;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class Constants {
    public static final Set<Integer> ALL_NUMBERS = new LinkedHashSet<>(Collections.unmodifiableCollection(Arrays.asList(1,2,3,4,5,6,7,8,9)));
    //public static final Set<Integer> STARTING_NUMBERS = new LinkedHashSet<>(Collections.unmodifiableCollection(Arrays.asList(9,9,9,9,9,9,9,9,9)));
    public static final ExecutorService forkJoinPool = ForkJoinPool.commonPool();


}
