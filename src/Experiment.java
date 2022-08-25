import Organism.Daphnia;
import Organism.Symbiont;
import Simulation.Collected_data;
import Simulation.Populations;
import Simulation.Simulation;
import Simulation.Variables;
import Simulation.MeanData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Experiment {

    public Experiment (String expName, int runs, double scarcity, double num_of_gens, double daphPopSize, double symbPopSize, double mut_chance, double mutStepSize, double vir_parD, double vir_parS, double fitPen, double fitPenSymb, String variablePar, String mode, HashMap<String, Double> modeArgs) throws IOException {



    Variables dummyVars = new Variables( new HashMap<>());


    dummyVars.getVarDict().put("scarcity", scarcity);
    dummyVars.getVarDict().put("num_of_gens", num_of_gens);
    dummyVars.getVarDict().put("daphPopSize", daphPopSize);
    dummyVars.getVarDict().put("symbPopSize", symbPopSize);
    dummyVars.getVarDict().put("mut_chance", mut_chance);
    dummyVars.getVarDict().put("mutStepSize", mutStepSize);
    dummyVars.getVarDict().put("vir_parD", vir_parD);
    dummyVars.getVarDict().put("vir_parS", vir_parS);
    dummyVars.getVarDict().put("fitPen", fitPen);
        dummyVars.getVarDict().put("fitPenSymb", fitPenSymb);



    HashMap<String, HashMap<Double, ArrayList<Double>>> columns = new HashMap<>();

    HashMap<Double, ArrayList<Double>> generations = new HashMap<>();
    HashMap<Double, ArrayList<Double>> scarcityList = new HashMap<>();
    HashMap<Double, ArrayList<Double>> vir_parDList = new HashMap<>();
    HashMap<Double, ArrayList<Double>> vir_parSList = new HashMap<>();
    HashMap<Double, ArrayList<Double>> fitnessPenalty = new HashMap<>();
    HashMap<Double, ArrayList<Double>> mutation_chance = new HashMap<>();
    HashMap<Double, ArrayList<Double>> mutStepSizeList = new HashMap<>();
    HashMap<Double, ArrayList<Double>> daphSlopes = new HashMap<>();
    HashMap<Double, ArrayList<Double>> daphInts = new HashMap<>();
    HashMap<Double, ArrayList<Double>> symbSlopes = new HashMap<>();
    HashMap<Double, ArrayList<Double>> symbInts = new HashMap<>();

    Collected_data bigData = new Collected_data(columns);


        bigData.getColumns().put("generations", generations);
        bigData.getColumns().put("vir_parD", vir_parDList);
        bigData.getColumns().put("vir_parS", vir_parSList);
        bigData.getColumns().put("fitPen", fitnessPenalty);
        bigData.getColumns().put("mut_chance", mutation_chance);
        bigData.getColumns().put("mutStepSize", mutStepSizeList);
        bigData.getColumns().put("daphSlopes", daphSlopes);
        bigData.getColumns().put("daphInts", daphInts);
        bigData.getColumns().put("symbSlopes", symbSlopes);
        bigData.getColumns().put("symbInts", symbInts);
        bigData.getColumns().put("scarcity", scarcityList);

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


        for (int runNum = 0; runNum < runs; runNum++) {
        bigData = new Simulation().simulator(dummyVars.getVarDict(), bigData, datapoints, variablePar, mode, modeArgs);


    }

    MeanData maeniee = new MeanData().calcMeansVariance(bigData);
    new Simulation().toTXT(bigData, maeniee, expName);

}

}
