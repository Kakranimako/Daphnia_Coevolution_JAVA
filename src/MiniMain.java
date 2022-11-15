import Experiment.Experiment;
import Experiment.ExperimentMatrix;
import Experiment.ModeArgs;
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
        double num_of_gens = 20000;
        double daphPopSize = 1000;
        double symbPopSize = 2000;
        double mut_chance = 0.01;
        double mutStepSize = 0.05;
        double D_resistCoeff = 0.35;
        double S_resistCoeff = 0.5;
        double S_virCoeff = 0.6;
        double thresholdFit = 0.6;
        double S_reducedFit = 0.25;
        String mode = "sinus";
        long start = System.currentTimeMillis();


        String foldername = mode + "_" + variablePar1;
        String expName = mode + "_" + variablePar1;



        Experiment exp1 = new Experiment(foldername, expName,
                runs, scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize, initGene1,
                initVarGene1, initGene2, initVarGene2, resistGene, resistVar, D_resistCoeff, S_resistCoeff, S_virCoeff, thresholdFit, S_reducedFit,
                variablePar1, 0.5, variablePar2, 0.5,
                mode, ModeArgs.getModeArgs(0.0, -1.2, 0.1, 5.0, 0.5, 0.4, 1.0));

        exp1.startExp();
        //"different modes are: linear, static, sinus, step, random"


        //ExperimentMatrix expMatrixSetup = new ExperimentMatrix(runs, initGene1, initVarGene1, initGene2, initVarGene2, resistGene,
                //resistVar, scarcity, num_of_gens, daphPopSize, symbPopSize, mut_chance, mutStepSize, thresholdFit, S_reducedFit,
                //D_resistCoeff, S_resistCoeff, S_virCoeff, mode, start);

        //HashMap<String, Experiment> expList = expMatrixSetup.expMatrix(variablePar1, variablePar2);

        //expList.values().parallelStream().forEach(experiment -> experiment.startExp());

        //System.out.println("This set matrix experiments done");

    }
}
