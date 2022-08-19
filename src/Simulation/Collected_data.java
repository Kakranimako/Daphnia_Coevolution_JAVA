package Simulation;

import java.util.ArrayList;
import java.util.HashMap;


public class Collected_data {

    private HashMap<String, HashMap<Double, ArrayList<Double>>> columns;


    public Collected_data(HashMap<String, HashMap<Double, ArrayList<Double>>> columns) {
        this.columns = columns;

    }

    public HashMap<String, HashMap<Double, ArrayList<Double>>> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "Collected_data{" +
                "columns=" + columns;

    }

    public void setColumns(HashMap<String,HashMap<Double, ArrayList<Double>>> columns) {
        this.columns = columns;
    }





}
