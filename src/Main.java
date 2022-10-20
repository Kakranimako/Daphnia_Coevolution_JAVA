

import Experiment.Experiment;
import Experiment.ExperimentMatrix;

import java.io.IOException;

import java.util.HashMap;



public class Main {

    public static void main(String[] args) throws IOException {


        int runs = 2;
        double initGene1 = 0.0;
        double initGene2 = 0.1;
        double initVarGene1 = 0.1;
        double initVarGene2 = 0.1;
        double resistGene = 0.1;
        double resistVar = 0.1;
        double scarcity = 0.5;
        double num_of_gens = 1000;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.001;
        double D_resistCoeff = 0.002;
        double S_resistCoeff = 0.02;
        double S_virCoeff = 0.02;
        double D_reducedFit = 0;
        double S_reducedFit = 0.3;
        String variablePar1 = "scarcity";
        String variablePar2 = "D_resistCoeff";
        String mode = "static";
        long start = System.currentTimeMillis();



        //"different modes are: linear, static, sinus, step, random"


        ExperimentMatrix expMatrixSetup = new ExperimentMatrix(runs, initGene1, initVarGene1, initGene2, initVarGene2, resistGene,
                resistVar, scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize, D_reducedFit, S_reducedFit,
                D_resistCoeff, S_resistCoeff, S_virCoeff, mode, start);

        HashMap<String, Experiment> expList = expMatrixSetup.expMatrix(variablePar1, variablePar2);

       expList.values().parallelStream().forEach(experiment -> experiment.startExp());
       long stop = System.currentTimeMillis();

       System.out.println("parrelisme " +  String.valueOf(stop-start));



    }

}

