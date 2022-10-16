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
    private String variablePar1;
    private String variablePar2;
    private String mode;
    private HashMap<String, Double> modeArgs;

    private ProgressBar progressbar;
    private String expName;

    private String foldername;
    private ArrayList<Double> datapoints;
    private int runs;

    private double varParValue1;
    private double varParValue2;



    public Experiment (String foldername, String expName, int runs, double scarcity, double num_of_gens, double daphPopSize,
                       double symbPopSize, double mut_chance, double mutStepSize, double initGene1, double initVar1,
                       double initGene2, double initVar2, double resistGene, double resistVar, double D_resistCoeff,
                       double S_resistCoeff, double S_virCoeff, double D_reducedFit, double S_reducedFit, String variablePar1, double varParValue1,
                       String variablePar2, double varParValue2, String mode, HashMap<String, Double> modeArgs) {

        Variables dummyVars = new Variables( new HashMap<>());


        dummyVars.getVarDict().put("scarcity", scarcity);
        dummyVars.getVarDict().put("num_of_gens", num_of_gens);
        dummyVars.getVarDict().put("daphPopSize", daphPopSize);
        dummyVars.getVarDict().put("symbPopSize", symbPopSize);
        dummyVars.getVarDict().put("mut_chance", mut_chance);
        dummyVars.getVarDict().put("mutStepSize", mutStepSize);
        dummyVars.getVarDict().put("D_resistCoeff", D_resistCoeff);
        dummyVars.getVarDict().put("S_resistCoeff", S_resistCoeff);
        dummyVars.getVarDict().put("D_reducedFit", D_reducedFit);
        dummyVars.getVarDict().put("S_reducedFit", S_reducedFit);
        dummyVars.getVarDict().put("initGene1", initGene1);
        dummyVars.getVarDict().put("initGene2", initGene2);
        dummyVars.getVarDict().put("initVar1", initVar1);
        dummyVars.getVarDict().put("initVar2", initVar2);
        dummyVars.getVarDict().put("resistVar", resistVar);
        dummyVars.getVarDict().put("resistGene", resistGene);
        dummyVars.getVarDict().put("S_virCoeff", S_virCoeff);


        dummyVars.getVarDict().put(variablePar1, varParValue1);
        dummyVars.getVarDict().put(variablePar2, varParValue2);




        HashMap<String, HashMap<Double, ArrayList<Double>>> columns = new HashMap<>();

        HashMap<Double, ArrayList<Double>> generations = new HashMap<>();
        HashMap<Double, ArrayList<Double>> scarcityList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> virulenceList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> FitS_List = new HashMap<>();
        HashMap<Double, ArrayList<Double>> fitD_List = new HashMap<>();
        HashMap<Double, ArrayList<Double>> mutation_chance = new HashMap<>();
        HashMap<Double, ArrayList<Double>> mutStepSizeList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> D_reducedFitList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> S_reducedFitList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> D_resistCoeffList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> S_resistCoeffList = new HashMap<>();
        HashMap<Double, ArrayList<Double>> S_virCoeffList = new HashMap<>();


        Collected_data bigData = new Collected_data(columns);


        bigData.getColumns().put("generations", generations);
        bigData.getColumns().put("virulence", virulenceList);
        bigData.getColumns().put("avgFitS", FitS_List);
        bigData.getColumns().put("avgFitD", fitD_List);
        bigData.getColumns().put("mut_chance", mutation_chance);
        bigData.getColumns().put("mutStepSize", mutStepSizeList);
        bigData.getColumns().put("D_reducedFit", D_reducedFitList);
        bigData.getColumns().put("S_reducedFit", S_reducedFitList);
        bigData.getColumns().put("D_resistCoeff", D_resistCoeffList);
        bigData.getColumns().put("scarcity", scarcityList);
        bigData.getColumns().put("S_resistCoeff", S_resistCoeffList);
        bigData.getColumns().put("S_virCoeff", S_virCoeffList);



        // create datapoints that we actually want to record the data for
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
        this.variablePar1 = variablePar1;
        this.variablePar2 = variablePar2;
        this.mode = mode;
        this.modeArgs = modeArgs;
        this.expName = expName;
        this.varParValue1 = varParValue1;
        this.varParValue2 = varParValue2;
        this.foldername = foldername;



    }

    public int startExp() {

        System.out.println("started now " + expName);

        for (int runNum = 0; runNum < runs; runNum++) {

            progressbar.step();

            bigData = new Simulation().simulator(varDict, bigData, datapoints, variablePar1, mode, modeArgs);
        }

        MeanData maeniee = new MeanData().calcMeansVariance(bigData);
        try {
            new Simulation().toTXT(bigData, maeniee, varDict, mode, variablePar1, varParValue1, variablePar2, varParValue2, foldername, expName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("stopped now " + expName);
        progressbar.close();
        return 0;

    }
}


