

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

        String expName = "testscar";
        int runs = 20;
        double initMeanGene1 = 0.0;
        double initMeanGene2 = 0.5;
        double initVariance = 0.5;
        double scarcity = 0.5;
        double num_of_gens = 1500;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.01;
        double vir_parD = 0.5;
        double vir_parS = 0.4;
        double fitPen = 1.0;
        double fitPenSymb = 1.0;
        String variablePar = "scarcity";
        String mode = "static";



        //"different modes are: linear, static, sinus, step, random"
        long start = System.currentTimeMillis();
        Experiment exp1 = new Experiment(expName + "0_0", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.0,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        System.out.println("done");

        Experiment exp12 = new Experiment(expName + "0_1", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.1,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


        Experiment exp2 = new Experiment(expName + "0_2", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.2,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


        Experiment exp3 = new Experiment(expName + "0_3", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.3,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


        Experiment exp4 = new Experiment(expName + "0_4", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.4,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


        Experiment exp5 = new Experiment(expName + "0_5", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.5,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


        Experiment exp6 = new Experiment(expName + "0_6", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.6,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        Experiment exp7 = new Experiment(expName + "0_8", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 0.8,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        Experiment exp8 = new Experiment(expName + "1_0", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 1.0,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        Experiment exp9 = new Experiment(expName + "1_2", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 1.2,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        Experiment exp10 = new Experiment(expName + "1_5", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 1.5,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        Experiment exp11 = new Experiment(expName + "2_0", runs, initMeanGene1, initMeanGene2, initVariance,
                scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize,
                vir_parD, vir_parS, fitPen, fitPenSymb, variablePar, 2.0,
                mode, ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));



       ArrayList<Experiment> expList = new ArrayList<>(Arrays.asList(exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8, exp9, exp10, exp11, exp12));



       expList.parallelStream().forEach(experiment -> experiment.startExp());
       long stop = System.currentTimeMillis();

       System.out.println("parrelisme " +  String.valueOf(stop-start));



    }

}

