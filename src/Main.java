import Organism.Daphnia;
import Organism.Symbiont;
import Simulation.Populations;

import Simulation.Simulation;
import Simulation.Variables;


import java.util.HashMap;






public class Main {

    public static void main(String[] args) {

        HashMap<String, Daphnia> daphniaPop = new HashMap<String, Daphnia>();

        HashMap<String, Symbiont> symbiontPop = new HashMap<String, Symbiont>();

        HashMap<String, Symbiont> gutSymbionts = new HashMap<String, Symbiont>();

        HashMap<String, Symbiont> envSymbionts = new HashMap<String, Symbiont>();

        Populations dummyPops = new Populations(daphniaPop,symbiontPop,gutSymbionts,envSymbionts);

        Variables dummyVars = new Variables(0.3, 1000, 1000, 2000, 0.01, 0.2, 0.2);
        int i = 0;
        while (i < dummyVars.getNum_of_gen()) {
            Simulation simulator = new Simulation(dummyPops, dummyVars);

            i+=1;
        }
    }
}

