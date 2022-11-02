package Experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExperimentMatrix {

    String expName;
    private int runs;
    private double initGene1;
    private double initVarGene2;
    private double initGene2;
    private double initVarGene1;
    private double resistGene;
    private double resistVar;
    private double scarcity;
    private double num_of_gens;
    private double daphPopSize;
    private double symbPopSize;
    private double mut_chance;
    private double mutStepSize;
    private double thresholdFit;
    private double reducedFitS;

    private double resistCoeffD;
    private double resistCoeffS;
    private double virCoeffS;
    String mode;

    public ExperimentMatrix(int runs, double initGene1, double initVarGene1, double initGene2,
                            double initVarGene2, double resistGene, double resistVar, double scarcity, double num_of_gens,
                            double daphPopSize, double symbPopSize, double mut_chance, double mutStepSize, double thresholdFit,
                            double reducedFitS, double resistCoeffD, double resistCoeffS, double virCoeffS, String mode, long start) {

        this.runs = runs;
        this.initGene1 = initGene1;
        this.initVarGene2 = initVarGene2;
        this.initGene2 = initGene2;
        this.initVarGene1 = initVarGene1;
        this.resistGene = resistGene;
        this.resistVar = resistVar;
        this.scarcity = scarcity;
        this.num_of_gens = num_of_gens;
        this.daphPopSize = daphPopSize;
        this.symbPopSize = symbPopSize;
        this.mut_chance = mut_chance;
        this.mutStepSize = mutStepSize;
        this.thresholdFit = thresholdFit;
        this.reducedFitS = reducedFitS;
        this.resistCoeffD = resistCoeffD;
        this.resistCoeffS = resistCoeffS;
        this.virCoeffS = virCoeffS;
        this.mode = mode;
        this.start = start;
    }

    //"different modes are: linear, static, sinus, step, random"
    long start = System.currentTimeMillis();

    public HashMap<String, Experiment> expMatrix(String variablePar1, String variablePar2) {

        ArrayList<Double> parValues = new ArrayList<>(Arrays.asList(0.0, 0.1, 0.2, 0.3, 0.4,
                0.5));
        ArrayList<Double> parValues2 = new ArrayList<>(Arrays.asList(0.0, 0.1, 0.2, 0.3, 0.4,
                0.5));

        HashMap<String, Experiment> expList = new HashMap<String, Experiment>();

        for (int i = 0; i < parValues.size(); i++) {
            for (int j = 0; j < parValues2.size(); j++) {

                double num1 = parValues.get(i) *1000;
                double num2 = parValues2.get(j) *1000;

                Experiment exp1 = new Experiment(mode + "_" + variablePar1 + "_" + variablePar2,
                        mode + "_" + variablePar1 + "_" + (int) num1  + "_" + variablePar2 + "_" + (int) num2,
                        runs, scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize, initGene1,
                        initVarGene1, initGene2, initVarGene2, resistGene, resistVar, resistCoeffD, resistCoeffS, virCoeffS, thresholdFit, reducedFitS,
                        variablePar1, parValues.get(i), variablePar2, parValues2.get(j),
                        mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

                expList.put(expName + parValues.get(i) + parValues2.get(j), exp1);
            }

        }
        System.out.println("number of experiments: " + expList.size() );
        return expList;


    }






}
