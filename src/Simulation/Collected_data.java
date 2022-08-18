package Simulation;

import java.util.ArrayList;
import java.util.HashMap;

public class Collected_data {

    private ArrayList<ArrayList<Integer>> generation;
    private ArrayList<ArrayList<Double>> scarcity;
    private ArrayList<ArrayList<Double>> vir_parD;
    private ArrayList<ArrayList<Double>> vir_parS;
    private ArrayList<ArrayList<Double>> fitnessPenalty;
    private ArrayList<ArrayList<Double>> mutation_chance;
    private ArrayList<ArrayList<Double>> mutStepSize;
    private ArrayList<ArrayList<Double>> daphSlopes;
    private ArrayList<ArrayList<Double>> daphInts;
    private ArrayList<ArrayList<Double>> symbSlopes;
    private ArrayList<ArrayList<Double>> symbInts;

    public Collected_data(ArrayList<ArrayList<Integer>> generation, ArrayList<ArrayList<Double>> scarcity, ArrayList<ArrayList<Double>> vir_parD, ArrayList<ArrayList<Double>> vir_parS, ArrayList<ArrayList<Double>> fitnessPenalty, ArrayList<ArrayList<Double>> mutation_chance, ArrayList<ArrayList<Double>> mutStepSize, ArrayList<ArrayList<Double>> daphSlopes, ArrayList<ArrayList<Double>> daphInts, ArrayList<ArrayList<Double>> symbSlopes, ArrayList<ArrayList<Double>> symbInts) {
        this.generation = generation;
        this.scarcity = scarcity;
        this.vir_parD = vir_parD;
        this.vir_parS = vir_parS;
        this.fitnessPenalty = fitnessPenalty;
        this.mutation_chance = mutation_chance;
        this.mutStepSize = mutStepSize;
        this.daphSlopes = daphSlopes;
        this.daphInts = daphInts;
        this.symbSlopes = symbSlopes;
        this.symbInts = symbInts;
    }

    public ArrayList<ArrayList<Integer>> getGeneration() {
        return generation;
    }

    public void setGeneration(ArrayList<ArrayList<Integer>> generation) {
        this.generation = generation;
    }

    public ArrayList<ArrayList<Double>> getScarcity() {
        return scarcity;
    }

    public void setScarcity(ArrayList<ArrayList<Double>> scarcity) {
        this.scarcity = scarcity;
    }

    public ArrayList<ArrayList<Double>> getVir_parD() {
        return vir_parD;
    }

    public void setVir_parD(ArrayList<ArrayList<Double>> vir_parD) {
        this.vir_parD = vir_parD;
    }

    public ArrayList<ArrayList<Double>> getVir_parS() {
        return vir_parS;
    }

    public void setVir_parS(ArrayList<ArrayList<Double>> vir_parS) {
        this.vir_parS = vir_parS;
    }

    public ArrayList<ArrayList<Double>> getFitnessPenalty() {
        return fitnessPenalty;
    }

    public void setFitnessPenalty(ArrayList<ArrayList<Double>> fitnessPenalty) {
        this.fitnessPenalty = fitnessPenalty;
    }

    public ArrayList<ArrayList<Double>> getMutation_chance() {
        return mutation_chance;
    }

    public void setMutation_chance(ArrayList<ArrayList<Double>> mutation_chance) {
        this.mutation_chance = mutation_chance;
    }

    public ArrayList<ArrayList<Double>> getMutStepSize() {
        return mutStepSize;
    }

    public void setMutStepSize(ArrayList<ArrayList<Double>> mutStepSize) {
        this.mutStepSize = mutStepSize;
    }

    public ArrayList<ArrayList<Double>> getDaphSlopes() {
        return daphSlopes;
    }

    public void setDaphSlopes(ArrayList<ArrayList<Double>> daphSlopes) {
        this.daphSlopes = daphSlopes;
    }

    public ArrayList<ArrayList<Double>> getDaphInts() {
        return daphInts;
    }

    public void setDaphInts(ArrayList<ArrayList<Double>> daphInts) {
        this.daphInts = daphInts;
    }

    public ArrayList<ArrayList<Double>> getSymbSlopes() {
        return symbSlopes;
    }

    public void setSymbSlopes(ArrayList<ArrayList<Double>> symbSlopes) {
        this.symbSlopes = symbSlopes;
    }

    public ArrayList<ArrayList<Double>> getSymbInts() {
        return symbInts;
    }

    public void setSymbInts(ArrayList<ArrayList<Double>> symbInts) {
        this.symbInts = symbInts;
    }

    @Override
    public String toString() {
        return "Collected_data{" +
                "generation=" + generation +
                ", scarcity=" + scarcity +
                ", vir_parD=" + vir_parD +
                ", vir_parS=" + vir_parS +
                ", fitnessPenalty=" + fitnessPenalty +
                ", mutation_chance=" + mutation_chance +
                ", mutStepSize=" + mutStepSize +
                ", daphSlopes=" + daphSlopes +
                ", daphInts=" + daphInts +
                ", symbSlopes=" + symbSlopes +
                ", symbInts=" + symbInts +
                '}';
    }
}
