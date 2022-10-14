package Simulation;

import java.util.HashMap;

public class Variables {

    private HashMap<String, Double> varDict;

    public Variables(HashMap<String, Double> varDict) {
        this.varDict = varDict;
    }

    public HashMap<String, Double> getVarDict() {
        return varDict;
    }

    public void setVarDict(HashMap<String, Double> varDict) {
        this.varDict = varDict;
    }
}

