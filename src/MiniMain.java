import Experiment.Experiment;
import Experiment.ExperimentMatrix;

import java.io.IOException;
import java.util.HashMap;

public class MiniMain {

    private String variablePar1;
    private String variablePar2;

    public MiniMain(String variablePar1, String variablePar2) {
        this.variablePar1 = variablePar1;
        this.variablePar2 = variablePar2;
    }


    public void miniMainLaunch () throws IOException {


        int runs = 100;
        double initGene1 = -2;
        double initGene2 = 1;
        double initVarGene1 = 0.5;
        double initVarGene2 = 0.05;
        double resistGene = 0.5;
        double resistVar = 0.1;
        double scarcity = 0.5;
        double num_of_gens = 1000;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.005;
        double D_resistCoeff = 0.3;
        double S_resistCoeff = 0.5;
        double S_virCoeff = 0.6;
        double thresholdFit = 0.6;
        double S_reducedFit = 0.25;
        String mode = "static";
        long start = System.currentTimeMillis();



        //"different modes are: linear, static, sinus, step, random"


        ExperimentMatrix expMatrixSetup = new ExperimentMatrix(runs, initGene1, initVarGene1, initGene2, initVarGene2, resistGene,
                resistVar, scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize, thresholdFit, S_reducedFit,
                D_resistCoeff, S_resistCoeff, S_virCoeff, mode, start);

        HashMap<String, Experiment> expList = expMatrixSetup.expMatrix(variablePar1, variablePar2);

        expList.values().parallelStream().forEach(experiment -> experiment.startExp());
        //for (Experiment exp: expList.values()) {
            //exp.startExp();
        //}
        System.out.println("This set matrix experiments done");

    }
}
