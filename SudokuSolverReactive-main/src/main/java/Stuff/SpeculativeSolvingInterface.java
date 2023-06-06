package Stuff;

import alternative.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public interface SpeculativeSolvingInterface {
    public Core completeSolving(Core core) throws CoreNotCoherentExeption;
    public static Field searchCanidateForSpeculativeTry(Core core) throws NoSuchElementException{
        return core.get_sudokuMatrix().stream()
                .filter(field -> !field.isFixed())
                .min(Comparator.comparing((Field field) -> field.possibleNumbers().size()))
                .orElseThrow();


    }
}
