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

        Experiment exp1 = new Experiment("test5", 5, 0.3, 2000, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.3,
                "scarcity", "linear", modeArgs);




    }

}

