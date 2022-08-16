package Simulation;

import java.util.ArrayList;

public class Parentpicker {

    private ArrayList<Double> cumulFitList = new ArrayList<>();

    private ArrayList<String> parentCumulList = new ArrayList<>();

    private ArrayList<String> parentList = new ArrayList<>();

    public Parentpicker(ArrayList<Double> cumulFitList, ArrayList<String> parentCumulList, ArrayList<String> parentList) {
        this.cumulFitList = cumulFitList;
        this.parentCumulList = parentCumulList;
        this.parentList = parentList;
    }

    public ArrayList<Double> getCumulFitList() {
        return cumulFitList;
    }

    public void setCumulFitList(ArrayList<Double> cumulFitList) {
        this.cumulFitList = cumulFitList;
    }

    public ArrayList<String> getParentCumulList() {
        return parentCumulList;
    }

    public void setParentCumulList(ArrayList<String> parentCumulList) {
        this.parentCumulList = parentCumulList;
    }


    public ArrayList<String> getParentList() {
        return parentList;
    }

    public void setParentList(ArrayList<String> parentList) {
        this.parentList = parentList;
    }

    @Override
    public String toString() {
        return "Parentpicker{" +
                "cumulFitList=" + cumulFitList +
                ", parentCumulList=" + parentCumulList +
                ", parentList=" + parentList +
                '}';
    }
}
