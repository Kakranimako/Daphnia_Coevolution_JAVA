

import Experiment.Experiment;
import Experiment.ExperimentMatrix;
import Experiment.ModeArgs;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws IOException {

        String expName = "testscar";
        int runs = 2;
        double initMeanGene1 = 0.0;
        double initMeanGene2 = 0.5;
        double initVariance = 0.5;
        double scarcity = 0.5;
        double num_of_gens = 1000;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.01;
        double vir_parD = 0.5;
        double vir_parS = 0.4;
        double fitPen = 1.0;
        double fitPenSymb = 0.5;
        String variablePar1 = "scarcity";
        String variablePar2 = "vir_parD";
        String mode = "static";



        //"different modes are: linear, static, sinus, step, random"
        long start = System.currentTimeMillis();
        ExperimentMatrix expMatrixSetup = new ExperimentMatrix(expName, runs, initMeanGene1, initMeanGene2, initVariance, scarcity, num_of_gens, daphPopSize,
                symbPopSize, mut_chance, mutStepSize, vir_parD, vir_parS, fitPen, fitPenSymb, mode);

        HashMap<String, Experiment> expList = expMatrixSetup.expMatrix(variablePar1, variablePar2);

       expList.values().parallelStream().forEach(experiment -> experiment.startExp());
       long stop = System.currentTimeMillis();

       System.out.println("parrelisme " +  String.valueOf(stop-start));



    }

}

