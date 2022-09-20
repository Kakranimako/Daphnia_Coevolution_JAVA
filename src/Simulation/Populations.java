package Simulation;

import Organism.Daphnia;
import Organism.Symbiont;
import org.w3c.dom.html.HTMLHeadElement;

import java.util.HashMap;

public class Populations {

    private HashMap<String, Daphnia> daphniaPop = new HashMap<String, Daphnia>();

    private HashMap<String, Symbiont> symbiontPop = new HashMap<String, Symbiont>();


    public Populations(HashMap<String, Daphnia> daphniaPop, HashMap<String, Symbiont> symbiontPop) {
        this.daphniaPop = daphniaPop;
        this.symbiontPop = symbiontPop;

    }

    public HashMap<String, Daphnia> getDaphniaPop() {
        return daphniaPop;
    }

    public void setDaphniaPop(HashMap<String, Daphnia> daphniaPop) {
        this.daphniaPop = daphniaPop;
    }

    public HashMap<String, Symbiont> getSymbiontPop() {
        return symbiontPop;
    }

    public void setSymbiontPop(HashMap<String, Symbiont> symbiontPop) {
        this.symbiontPop = symbiontPop;
    }


    @Override
    public String toString() {
        return "Populations{" +
                "daphniaPop=" + daphniaPop +
                ", symbiontPop=" + symbiontPop +
                '}';
    }
}
