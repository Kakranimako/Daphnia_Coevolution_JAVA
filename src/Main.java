

import Experiment.Experiment;
import Experiment.ModeArgs;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws IOException {

        String expName = "test_horizontal_";
        int runs = 10;
        double initMeanGene1 = 5;
        double initMeanGene2 = 0;
        double initMeanGene3 = 0;
        double initVariance = 0.5;
        double scarcity = 0.5;
        double num_of_gens = 1500;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.1;
        double vir_parD = 0.1;
        double vir_parS = 0.2;
        double fitPen = 1;
        double fitPenSymb = 0.95;
        String variablePar = "scarcity";
        String mode = "sinus";



        //"different modes are: linear, static, sinus, step, random"
        long start = System.currentTimeMillis();
        Experiment exp1 = new Experiment(expName + "0_0", runs, initMeanGene1, initMeanGene2, initMeanGene3, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                variablePar, 0.5, variablePar, 0.4,
                mode, ModeArgs.getModeArgs(-0.00075, 0.2, 1.0, 2.0, 1.0, 0.4, 1.0));

        System.out.println("done");



        exp1.startExp();


       //ArrayList<Experiment> expList = new ArrayList<>(Arrays.asList(exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp10, exp11, exp12));



       //expList.parallelStream().forEach(experiment -> experiment.startExp());
       long stop = System.currentTimeMillis();

       System.out.println("parrelisme " +  String.valueOf(stop-start));



    }

}

