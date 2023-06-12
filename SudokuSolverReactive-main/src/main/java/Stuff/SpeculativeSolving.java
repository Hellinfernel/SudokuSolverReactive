package Stuff;

import Exeptions.DoubleNumberExeption;
import Exeptions.NotAllNumbersArePossibleExeption;
import alternative.Coordinate;
import alternative.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpeculativeSolving implements SpeculativeSolvingInterface {
    public Core completeSolving(Core core) {
        core.kickStartEventTrigger();

        Field triedField = SpeculativeSolvingInterface.searchCanidateForSpeculativeTry(core);
        List<CompletableFuture<Core>> completableFutures = new ArrayList<>();
        triedField.possibleNumbers().forEach(integer -> completableFutures.add(speculativeSolvingTry(core, triedField.getCoordinate(), integer)));
        CompletableFuture<Core> finishedCore = new CompletableFuture<>();
        for (CompletableFuture<Core> coreCompletableFuture : completableFutures) {
            finishedCore = finishedCore.applyToEitherAsync(coreCompletableFuture, thatCore -> thatCore);
        }
        return finishedCore.join();


    }
    /**
     * Tries to solve a Core by adding a number to a field where it isnt save that it can contain this number
     * @param coordinate the coordinate of the field where the number is speculated.
     * @param speculatedNumber the speculated number
     * @return a CompletableFuture<Core> or an error if the
     */
    private CompletableFuture<Core> speculativeSolvingTry(Core core,Coordinate coordinate, int speculatedNumber){
        Core saveCore = new Core(core);
        Field targetField = saveCore.getFieldByCoordinate(coordinate);
        CompletableFuture<Core> completableFuture = CompletableFuture.supplyAsync(() -> {

            try {
                targetField.setNumber(speculatedNumber);
                saveCore.testCoherence();
                if (!saveCore.isSolvedCompleatly()){
                    completeSolving(saveCore);
                }

            } catch (DoubleNumberExeption | NotAllNumbersArePossibleExeption exeption) {
                exeption.printStackTrace();
            }
            return saveCore;

        }).exceptionally(throwable -> new Core());
        return completableFuture;
        //return core;
    }




}
