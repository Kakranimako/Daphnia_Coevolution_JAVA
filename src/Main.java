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

        Variables dummyVars = new Variables( new HashMap<>());

        dummyVars.getVarDict().put("scarcity", 0.3);
        dummyVars.getVarDict().put("num_of_gens", 7000.0);
        dummyVars.getVarDict().put("daphPopSize", 1000.0);
        dummyVars.getVarDict().put("symbPopSize", 2000.0);
        dummyVars.getVarDict().put("mut_chance", 0.0);
        dummyVars.getVarDict().put("mutStepSize", 0.0);
        dummyVars.getVarDict().put("vir_parD", 0.0);
        dummyVars.getVarDict().put("vir_parS", 0.0);
        dummyVars.getVarDict().put("fitPen", 1.0);



        HashMap<String, HashMap<Double, ArrayList<Double>>> columns = new HashMap<>();
        
        HashMap<Double, ArrayList<Double>> generations = new HashMap<>();
        HashMap<Double, ArrayList<Double>> scarcity = new HashMap<>();
        HashMap<Double, ArrayList<Double>> vir_parD = new HashMap<>();
        HashMap<Double, ArrayList<Double>> vir_parS = new HashMap<>();
        HashMap<Double, ArrayList<Double>> fitnessPenalty = new HashMap<>();
        HashMap<Double, ArrayList<Double>> mutation_chance = new HashMap<>();
        HashMap<Double, ArrayList<Double>> mutStepSize = new HashMap<>();
        HashMap<Double, ArrayList<Double>> daphSlopes = new HashMap<>();
        HashMap<Double, ArrayList<Double>> daphInts = new HashMap<>();
        HashMap<Double, ArrayList<Double>> symbSlopes = new HashMap<>();
        HashMap<Double, ArrayList<Double>> symbInts = new HashMap<>();

        Collected_data bigData = new Collected_data(columns);


        bigData.getColumns().put("generations", generations);
        bigData.getColumns().put("vir_parD", vir_parD);
        bigData.getColumns().put("vir_parS", vir_parS);
        bigData.getColumns().put("fitPen", fitnessPenalty);
        bigData.getColumns().put("mut_chance", mutation_chance);
        bigData.getColumns().put("mutStepSize", mutStepSize);
        bigData.getColumns().put("daphSlopes", daphSlopes);
        bigData.getColumns().put("daphInts", daphInts);
        bigData.getColumns().put("symbSlopes", symbSlopes);
        bigData.getColumns().put("symbInts", symbInts);
        bigData.getColumns().put("scarcity", scarcity);

        ArrayList<Double> datapoints = new ArrayList<>();
        double multiplier = dummyVars.getVarDict().get("num_of_gens")/100.0;
        for (int i = 0; i <= 100; i++ ) {
            datapoints.add(i*multiplier);
        }

        for (HashMap<Double, ArrayList<Double>> column: bigData.getColumns().values()) {

            for (Double datapoint : datapoints) {

                column.put(datapoint, new ArrayList<>());

            }
        }



        Simulation simulator = new Simulation();

        int runs = 7;
        String filename = "testrun3";

        for (int runNum = 0; runNum < runs; runNum++) {
            bigData = simulator.simulator(dummyPops, dummyVars.getVarDict(), bigData, datapoints, runNum);
        }

        MeanData maeniee = new MeanData().calcMeansVariance(bigData);

        simulator.toTXT(bigData, maeniee, filename);

    }

}

