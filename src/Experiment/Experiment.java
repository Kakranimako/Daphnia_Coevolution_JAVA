package Experiment;


import Simulation.Collected_data;

import Simulation.Simulation;
import Simulation.Variables;
import Simulation.MeanData;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;


public class Experiment {


    private HashMap<String, Double> varDict;
    private Collected_data bigData;
    private String variablePar;
    private String mode;
    private HashMap<String, Double> modeArgs;

    private ProgressBar progressbar;
    private String expName;

    private ArrayList<Double> datapoints;
    private int runs;



    public Experiment (String expName, int runs, double initMeanGene1, double initMeanGene2, double initVariance, double scarcity, double num_of_gens, double daphPopSize,
                       double symbPopSize, double mut_chance, double mutStepSize, double vir_parD,
                       double vir_parS, double fitPen, double fitPenSymb, String variablePar, String mode,
                       HashMap<String, Double> modeArgs) {

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
        dummyVars.getVarDict().put("initMeanGene1", initMeanGene1);
        dummyVars.getVarDict().put("initMeanGene2", initMeanGene2);
        dummyVars.getVarDict().put("initVariance",initVariance);



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
        ProgressBarBuilder pb = new ProgressBarBuilder();
        pb.setStyle(ProgressBarStyle.COLORFUL_UNICODE_BLOCK);
        pb.setInitialMax(runs);
        pb.setTaskName(expName+ ": ");


        this.progressbar = pb.build();
        this.varDict = dummyVars.getVarDict();
        this.runs = runs;
        this.bigData = bigData;
        this.datapoints = datapoints;
        this.variablePar = variablePar;
        this.mode = mode;
        this.modeArgs = modeArgs;
        this.expName = expName;



    }

    public int startExp() {



        for (int runNum = 0; runNum < runs; runNum++) {

            progressbar.step();

            bigData = new Simulation().simulator(varDict, bigData, datapoints, variablePar, mode, modeArgs);
        }

        MeanData maeniee = new MeanData().calcMeansVariance(bigData);
        try {
            new Simulation().toTXT(bigData, maeniee, varDict, mode, expName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("stopped now " + expName);
        progressbar.close();
        return 0;

    }
}

