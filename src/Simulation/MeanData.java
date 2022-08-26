package Simulation;

import java.util.ArrayList;
import java.util.HashMap;

public class MeanData {

    private HashMap<Double, Double> meanDaphSlopes;
    
    private HashMap<Double, Double> meanDaphInts;
    
    private HashMap<Double, Double> meanSymbSlopes;
    
    private HashMap<Double, Double> meanSymbInts;

    private HashMap<Double, Double> varianceDaphSlopes;

    private HashMap<Double, Double> varianceDaphInts;

    private HashMap<Double, Double> varianceSymbSlopes;

    private HashMap<Double, Double> varianceSymbInts;
    public MeanData() {

    }
    public MeanData(HashMap<Double, Double> meanDaphSlopes, HashMap<Double, Double> meanDaphInts, HashMap<Double, Double> meanSymbSlopes, HashMap<Double, Double> meanSymbInts, HashMap<Double, Double> varianceDaphSlopes, HashMap<Double, Double> varianceDaphInts, HashMap<Double, Double> varianceSymbSlopes, HashMap<Double, Double> varianceSymbInts) {
        this.meanDaphSlopes = meanDaphSlopes;
        this.meanDaphInts = meanDaphInts;
        this.meanSymbSlopes = meanSymbSlopes;
        this.meanSymbInts = meanSymbInts;
        this.varianceDaphSlopes = varianceDaphSlopes;
        this.varianceDaphInts = varianceDaphInts;
        this.varianceSymbSlopes = varianceSymbSlopes;
        this.varianceSymbInts = varianceSymbInts;
    }

    public MeanData calcMeansVariance (Collected_data bigdata) {

        HashMap<Double, Double> meanDaphSlopes = new HashMap<>();
        HashMap<Double, Double> meanDaphInts = new HashMap<>();
        HashMap<Double, Double> meanSymbSlopes = new HashMap<>();
        HashMap<Double, Double> meanSymbInts = new HashMap<>();
        HashMap<Double, Double> varianceDaphSlopes = new HashMap<>();
        HashMap<Double, Double> varianceDaphInts = new HashMap<>();
        HashMap<Double, Double> varianceSymbSlopes = new HashMap<>();
        HashMap<Double, Double> varianceSymbInts = new HashMap<>();

        for (double datapoint : bigdata.getColumns().get("generations").keySet()) {

            double meanDaph = calcAvg(bigdata.getColumns().get("daphSlopes").get(datapoint));
            meanDaphSlopes.put(datapoint, meanDaph);

            varianceDaphSlopes.put(datapoint, calcVariance(bigdata.getColumns().get("daphSlopes").get(datapoint), meanDaph));

            double DaphInts = calcAvg(bigdata.getColumns().get("daphInts").get(datapoint));
            meanDaphInts.put(datapoint, DaphInts);

            varianceDaphInts.put(datapoint, calcVariance(bigdata.getColumns().get("daphInts").get(datapoint), DaphInts));


            double meansymb = calcAvg(bigdata.getColumns().get("symbSlopes").get(datapoint));
            meanSymbSlopes.put(datapoint, meansymb);

            varianceSymbSlopes.put(datapoint, calcVariance(bigdata.getColumns().get("symbSlopes").get(datapoint), meansymb));

            double symbInts = calcAvg(bigdata.getColumns().get("symbInts").get(datapoint));
            meanSymbInts.put(datapoint, symbInts);

            varianceSymbInts.put(datapoint, calcVariance(bigdata.getColumns().get("symbInts").get(datapoint), symbInts));


        }


        return new MeanData(meanDaphSlopes, meanDaphInts, meanSymbSlopes,
                meanSymbInts, varianceDaphSlopes, varianceDaphInts, varianceSymbSlopes, varianceSymbInts);
    }

    public Double calcAvg (ArrayList<Double> listy) {
        double total = 0;
        double avg;
        for (int u = 0; u < listy.size(); u++) {
            total += listy.get(u);
        }
        avg = total / listy.size();
        return avg;
    }

    public Double calcVariance (ArrayList<Double> listy, Double mean) {

        double variance = 0;

        for (int i = 0; i < listy.size(); i++) {
            variance += (listy.get(i) - mean) * (listy.get(i) - mean);
        }
        return Math.sqrt(variance / listy.size())/Math.sqrt(listy.size());
    }

    public HashMap<Double, Double> getMeanDaphSlopes() {
        return meanDaphSlopes;
    }

    public void setMeanDaphSlopes(HashMap<Double, Double> meanDaphSlopes) {
        this.meanDaphSlopes = meanDaphSlopes;
    }

    public HashMap<Double, Double> getMeanDaphInts() {
        return meanDaphInts;
    }

    public void setMeanDaphInts(HashMap<Double, Double> meanDaphInts) {
        this.meanDaphInts = meanDaphInts;
    }

    public HashMap<Double, Double> getMeanSymbSlopes() {
        return meanSymbSlopes;
    }

    public void setMeanSymbSlopes(HashMap<Double, Double> meanSymbSlopes) {
        this.meanSymbSlopes = meanSymbSlopes;
    }

    public HashMap<Double, Double> getMeanSymbInts() {
        return meanSymbInts;
    }

    public void setMeanSymbInts(HashMap<Double, Double> meanSymbInts) {
        this.meanSymbInts = meanSymbInts;
    }

    public HashMap<Double, Double> getVarianceDaphSlopes() {
        return varianceDaphSlopes;
    }

    public void setVarianceDaphSlopes(HashMap<Double, Double> varianceDaphSlopes) {
        this.varianceDaphSlopes = varianceDaphSlopes;
    }

    public HashMap<Double, Double> getVarianceDaphInts() {
        return varianceDaphInts;
    }

    public void setVarianceDaphInts(HashMap<Double, Double> varianceDaphInts) {
        this.varianceDaphInts = varianceDaphInts;
    }

    public HashMap<Double, Double> getVarianceSymbSlopes() {
        return varianceSymbSlopes;
    }

    public void setVarianceSymbSlopes(HashMap<Double, Double> varianceSymbSlopes) {
        this.varianceSymbSlopes = varianceSymbSlopes;
    }

    public HashMap<Double, Double> getVarianceSymbInts() {
        return varianceSymbInts;
    }

    public void setVarianceSymbInts(HashMap<Double, Double> varianceSymbInts) {
        this.varianceSymbInts = varianceSymbInts;
    }

    @Override
    public String toString() {
        return "MeanData{" +
                "meanDaphSlopes=" + meanDaphSlopes +
                ", meanDaphInts=" + meanDaphInts +
                ", meanSymbSlopes=" + meanSymbSlopes +
                ", meanSymbInts=" + meanSymbInts +
                ", varianceDaphSlopes=" + varianceDaphSlopes +
                ", varianceDaphInts=" + varianceDaphInts +
                ", varianceSymbSlopes=" + varianceSymbSlopes +
                ", varianceSymbInts=" + varianceSymbInts +
                '}';
    }
}
