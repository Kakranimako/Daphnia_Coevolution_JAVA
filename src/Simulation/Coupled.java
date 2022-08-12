package Simulation;

import Organism.Daphnia;
import Organism.Symbiont;

import java.util.HashMap;

public class Coupled {

    private HashMap<String, Daphnia> coupledDaphs;
    private HashMap<String, Symbiont> coupledSymbs;

    private HashMap<String, Daphnia> nonCoupledDaphs;
    private HashMap<String, Symbiont> nonCoupledSymbs;




    public Coupled(HashMap<String, Daphnia> coupledDaphs, HashMap<String, Symbiont> coupledSymbs, HashMap<String, Daphnia> nonCoupledDaphs, HashMap<String, Symbiont> nonCoupledSymbs) {
        this.coupledDaphs = coupledDaphs;
        this.coupledSymbs = coupledSymbs;
        this.nonCoupledDaphs = nonCoupledDaphs;
        this.nonCoupledSymbs = nonCoupledSymbs;
    }

    public HashMap<String, Daphnia> getCoupledDaphs() {
        return coupledDaphs;
    }

    public HashMap<String, Daphnia> getNonCoupledDaphs() {
        return nonCoupledDaphs;
    }

    public void setNonCoupledDaphs(HashMap<String, Daphnia> nonCoupledDaphs) {
        this.nonCoupledDaphs = nonCoupledDaphs;
    }

    public HashMap<String, Symbiont> getNonCoupledSymbs() {
        return nonCoupledSymbs;
    }

    public void setNonCoupledSymbs(HashMap<String, Symbiont> nonCoupledSymbs) {
        this.nonCoupledSymbs = nonCoupledSymbs;
    }

    public void setCoupledDaphs(HashMap<String, Daphnia> coupledDaphs) {
        this.coupledDaphs = coupledDaphs;
    }

    public HashMap<String, Symbiont> getCoupledSymbs() {
        return coupledSymbs;
    }

    public void setCoupledSymbs(HashMap<String, Symbiont> coupledSymbs) {
        this.coupledSymbs = coupledSymbs;
    }

    @Override
    public String toString() {
        return "Coupled{" +
                "coupledDaphs=" + coupledDaphs +
                ", coupledSymbs=" + coupledSymbs +
                ", nonCoupledDaphs=" + nonCoupledDaphs +
                ", nonCoupledSymbs=" + nonCoupledSymbs +
                '}';
    }
}
