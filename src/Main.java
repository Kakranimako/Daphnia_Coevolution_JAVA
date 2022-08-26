import Organism.Daphnia;
import Organism.Symbiont;
import Simulation.Populations;

import Simulation.Simulation;
import Simulation.Variables;
import Simulation.Collected_data;
import Simulation.MeanData;
import Simulation.testorg;
import Simulation.testchoose;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Main {

    public static void main(String[] args) throws IOException {


        HashMap<String, Double> modeArgs = new HashMap<>();
        modeArgs.put("slope", 0.002);
        modeArgs.put("horizonShift", 0.2);
        modeArgs.put("period", 1.0);
        modeArgs.put("phases", 5.0);
        modeArgs.put("vertShift", 0.0);
        modeArgs.put("mean", 0.4);
        modeArgs.put("variance", 1.0);

        //"different modes are: linear, static, sinus, step, random"

        Experiment exp1 = new Experiment("scar0_0", 4, 0.0, 1000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "scarcity", "random", modeArgs);

        System.out.println("done");

        exp1 = new Experiment("scar0_2", 100, 0.2, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", modeArgs);

        exp1 = new Experiment("scar0_4", 100, 0.4, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", modeArgs);

        exp1 = new Experiment("scar0_6", 100, 0.6, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", modeArgs);

        exp1 = new Experiment("scar0_8", 100, 0.8, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", modeArgs);

        exp1 = new Experiment("scar1_0", 100, 1.0, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", modeArgs);



    }

}

