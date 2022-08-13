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

        Variables dummyVars = new Variables(0.3, 100, 10, 20, 0.01, 0.2, 0.2);

        Simulation simulator = new Simulation(dummyPops, dummyVars);

    }



}
