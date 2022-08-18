import Organism.Daphnia;
import Organism.Symbiont;
import Simulation.Populations;

import Simulation.Simulation;
import Simulation.Variables;
import Simulation.Collected_data;
import Simulation.MeanData;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;






public class Main {

    public static void main(String[] args) throws IOException {

        HashMap<String, Daphnia> daphniaPop = new HashMap<String, Daphnia>();

        HashMap<String, Symbiont> symbiontPop = new HashMap<String, Symbiont>();

        HashMap<String, Symbiont> gutSymbionts = new HashMap<String, Symbiont>();

        HashMap<String, Symbiont> envSymbionts = new HashMap<String, Symbiont>();

        Populations dummyPops = new Populations(daphniaPop,symbiontPop,gutSymbionts,envSymbionts);

        Variables dummyVars = new Variables(0.3, 1000, 1000, 2000, 0.01, 0.5, 0.4, 0.8, 0.03);
        int runs = 100;

        ArrayList<ArrayList<Integer>> generations = new ArrayList<>();
        ArrayList<ArrayList<Double>> scarcity = new ArrayList<>();
        ArrayList<ArrayList<Double>> vir_parD = new ArrayList<>();
        ArrayList<ArrayList<Double>> vir_parS = new ArrayList<>();
        ArrayList<ArrayList<Double>> fitnessPenalty = new ArrayList<>();
        ArrayList<ArrayList<Double>> mutation_chance = new ArrayList<>();
        ArrayList<ArrayList<Double>> mutStepSize = new ArrayList<>();
        ArrayList<ArrayList<Double>> daphSlopes = new ArrayList<>();
        ArrayList<ArrayList<Double>> daphInts = new ArrayList<>();
        ArrayList<ArrayList<Double>> symbSlopes = new ArrayList<>();
        ArrayList<ArrayList<Double>> symbInts = new ArrayList<>();

        Collected_data bigData = new Collected_data(generations, scarcity, vir_parD, vir_parS, fitnessPenalty, mutation_chance, mutStepSize, daphSlopes, daphInts, symbSlopes, symbInts);

        for (int runNum = 0; runNum < runs; runNum++) {
            bigData.getScarcity().add(runNum, new ArrayList<Double>());
            bigData.getDaphInts().add(runNum, new ArrayList<Double>());
            bigData.getDaphSlopes().add(runNum, new ArrayList<Double>());
            bigData.getSymbSlopes().add(runNum, new ArrayList<Double>());
            bigData.getSymbInts().add(runNum, new ArrayList<Double>());
            bigData.getFitnessPenalty().add(runNum, new ArrayList<Double>());
            bigData.getMutation_chance().add(runNum, new ArrayList<Double>());
            bigData.getMutStepSize().add(runNum, new ArrayList<Double>());
            bigData.getGeneration().add(runNum, new ArrayList<Integer>());
            bigData.getVir_parD().add(runNum, new ArrayList<Double>());
            bigData.getVir_parS().add(runNum, new ArrayList<Double>());
        }

        Simulation simulator = new Simulation();

        for (int runNum = 0; runNum < runs; runNum++) {
            bigData = simulator.simulator(dummyPops, dummyVars, bigData, runNum);
        }

        MeanData maeniee = new MeanData().calcMeansVariance(bigData);

        simulator.toTXT(bigData, maeniee);

    }

}

