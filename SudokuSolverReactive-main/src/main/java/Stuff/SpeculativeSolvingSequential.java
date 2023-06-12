package Stuff;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.Coordinate;
import alternative.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SpeculativeSolvingSequential implements SpeculativeSolvingInterface {

    @Override
    public Core completeSolving(Core core) throws CoreNotCoherentExeption {
        core.kickStartEventTrigger();
        Core coherentCoreFound = null;

            Field triedField;
            triedField = SpeculativeSolvingInterface.searchCanidateForSpeculativeTry(core);


            Set<Integer> possibleNumbers = new HashSet<>(triedField.possibleNumbers());
            for (Integer integer : possibleNumbers) {
            Core saveCore = new Core(core);

                try {
                    coherentCoreFound = speculativeSolvingTry(saveCore,triedField.getCoordinate(),integer);
                } catch (DoubleNumberExeption exeption) {

                } catch (NotAllNumbersArePossibleExeption notAllNumbersArePossibleExeption) {

                } catch (CoreNotCoherentExeption exeption){

                }
            }
            if (coherentCoreFound == null){
                throw new CoreNotCoherentExeption();
            }
            try {
                coherentCoreFound.testCoherence();
            } catch (NotAllNumbersArePossibleExeption | DoubleNumberExeption notAllNumbersArePossibleExeption) {
                throw new CoreNotCoherentExeption();
            }




        return coherentCoreFound;


    }
    public Collection<Core> completeAnalysis(Core core) throws CoreNotCoherentExeption {
        Core deepAnalysisCore = core;
        deepAnalysisCore.kickStartEventTrigger();
        Collection<Core> allPossibleCores = new HashSet<>();
        for (Field field : deepAnalysisCore.get_sudokuMatrix()) {
            if (!field.isFixed()) {
                for (Integer integer : field.possibleNumbers()) {
                    try {
                        allPossibleCores.addAll(speculativeBranch(deepAnalysisCore, field.getCoordinate(), integer));
                    } catch (DoubleNumberExeption | NotAllNumbersArePossibleExeption | CoreNotCoherentExeption exeption) {
                        deepAnalysisCore.getFieldByCoordinate(field.getCoordinate()).exclude(integer);
                    }

                }
            }
        }
        if (allPossibleCores.size() == 0){
            throw new CoreNotCoherentExeption();
        }

            try {
                deepAnalysisCore.testCoherence();
            } catch (DoubleNumberExeption | NotAllNumbersArePossibleExeption exeption) {
                throw new CoreNotCoherentExeption();
            }

        return allPossibleCores;

    }
    private Collection<Core> speculativeBranch(Core core, Coordinate coordinate, int speculatedNumber)throws DoubleNumberExeption, NotAllNumbersArePossibleExeption, CoreNotCoherentExeption{
        Core saveCore = new Core(core);
        saveCore.getFieldByCoordinate(coordinate).setNumber(speculatedNumber);
        saveCore.testCoherence();
        if (saveCore.isSolvedCompleatly()){
            HashSet<Core> set = new HashSet<>();
            set.add(saveCore);
            return set;
        }
        else {
            return new SpeculativeSolvingSequential().completeAnalysis(core);
        }


    }
    /**
     * Tries to solve a Core by adding a number to a field where it isnt save that it can contain this number
     * @param coordinate the coordinate of the field where the number is speculated.
     * @param speculatedNumber the speculated number
     * @return a CompletableFuture<Core> or an error if the
     */
    private Core speculativeSolvingTry(Core core, Coordinate coordinate, int speculatedNumber) throws DoubleNumberExeption, NotAllNumbersArePossibleExeption, CoreNotCoherentExeption {
        Core saveCore = new Core(core);
        saveCore.getFieldByCoordinate(coordinate).setNumber(speculatedNumber);
        saveCore.testCoherence();
        if (saveCore.isSolvedCompleatly()){
            return saveCore;
        }
        else {
            return new SpeculativeSolvingSequential().completeSolving(saveCore);
        }
        //return core;
    }


}
