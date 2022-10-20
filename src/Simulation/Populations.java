package Simulation;

import Organism.Daphnia;
import Organism.Symbiont;


import java.util.HashMap;

public class Populations {

    private HashMap<String, Daphnia> daphniaPop = new HashMap<String, Daphnia>();

    private HashMap<String, Symbiont> symbiontPop = new HashMap<String, Symbiont>();

    private HashMap<String, Symbiont> gutSymbionts = new HashMap<String, Symbiont>();

    private HashMap<String, Symbiont> envSymbionts = new HashMap<String, Symbiont>();

    public Populations(HashMap<String, Daphnia> daphniaPop, HashMap<String, Symbiont> symbiontPop, HashMap<String, Symbiont> gutSymbionts, HashMap<String, Symbiont> envSymbionts) {
        this.daphniaPop = daphniaPop;
        this.symbiontPop = symbiontPop;
        this.gutSymbionts = gutSymbionts;
        this.envSymbionts = envSymbionts;
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

    public HashMap<String, Symbiont> getGutSymbionts() {
        return gutSymbionts;
    }

    public void setGutSymbionts(HashMap<String, Symbiont> gutSymbionts) {
        this.gutSymbionts = gutSymbionts;
    }

    public HashMap<String, Symbiont> getEnvSymbionts() {
        return envSymbionts;
    }

    public void setEnvSymbionts(HashMap<String, Symbiont> envSymbionts) {
        this.envSymbionts = envSymbionts;
    }

    @Override
    public String toString() {
        return "Populations{" +
                "daphniaPop=" + daphniaPop +
                ", symbiontPop=" + symbiontPop +
                ", gutSymbionts=" + gutSymbionts +
                ", envSymbionts=" + envSymbionts +
                '}';
    }
}
