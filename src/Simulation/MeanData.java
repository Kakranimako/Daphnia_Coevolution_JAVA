package Simulation;

import java.util.ArrayList;

public class MeanData {

    private ArrayList<Double> meanDaphSlopes;
    
    private ArrayList<Double> meanDaphInts;
    
    private ArrayList<Double> meanSymbSLopes;
    
    private ArrayList<Double> meanSymbInts;

    private ArrayList<Double> varianceDaphSlopes;

    private ArrayList<Double> varianceDaphInts;

    private ArrayList<Double> varianceSymbSLopes;

    private ArrayList<Double> varianceSymbInts;

    public MeanData () {

    }
    public MeanData(ArrayList<Double> meanDaphSlopes, ArrayList<Double> meanDaphInts, ArrayList<Double> meanSymbSLopes, ArrayList<Double> meanSymbInts, ArrayList<Double> varianceDaphSlopes, ArrayList<Double> varianceDaphInts, ArrayList<Double> varianceSymbSLopes, ArrayList<Double> varianceSymbInts) {
        this.meanDaphSlopes = meanDaphSlopes;
        this.meanDaphInts = meanDaphInts;
        this.meanSymbSLopes = meanSymbSLopes;
        this.meanSymbInts = meanSymbInts;
        this.varianceDaphSlopes = varianceDaphSlopes;
        this.varianceDaphInts = varianceDaphInts;
        this.varianceSymbSLopes = varianceSymbSLopes;
        this.varianceSymbInts = varianceSymbInts;
    }

    public MeanData calcMeansVariance (Collected_data bigdata) {

        ArrayList<Double> meanDaphSlopes = new ArrayList<>();
        ArrayList<Double> meanDaphInts = new ArrayList<>();
        ArrayList<Double> meanSymbSLopes = new ArrayList<>();
        ArrayList<Double> meanSymbInts = new ArrayList<>();
        ArrayList<Double> varianceDaphSlopes = new ArrayList<>();
        ArrayList<Double> varianceDaphInts = new ArrayList<>();
        ArrayList<Double> varianceSymbSLopes = new ArrayList<>();
        ArrayList<Double> varianceSymbInts = new ArrayList<>();

        for (int i = 0; i < bigdata.getDaphSlopes().size(); i++){

           meanDaphSlopes.add(calcAvg(bigdata.getDaphSlopes().get(i)));
           varianceDaphSlopes.add(calcVariance(bigdata.getDaphSlopes().get(i), meanDaphSlopes.get(i)));
           
           meanDaphInts.add(calcAvg(bigdata.getDaphInts().get(i)));
           varianceDaphInts.add(calcVariance(bigdata.getDaphInts().get(i), meanDaphInts.get(i)));

           meanSymbSLopes.add(calcAvg(bigdata.getSymbSlopes().get(i)));
           varianceSymbSLopes.add(calcVariance(bigdata.getSymbSlopes().get(i), meanSymbSLopes.get(i)));
           
           meanSymbInts.add(calcAvg(bigdata.getSymbInts().get(i)));
           varianceSymbInts.add(calcVariance(bigdata.getSymbInts().get(i), meanSymbInts.get(i)));
        }


        return new MeanData(meanDaphSlopes, meanDaphInts, meanSymbSLopes,
                meanSymbInts, varianceDaphSlopes, varianceDaphInts, varianceSymbSLopes, varianceSymbInts);
    }

    public Double calcAvg (ArrayList<Double> listy) {
        double total = 0;
        double avg = 0;
        for (int u = 0; u < listy.size(); u++) {

            total += listy.get(u);
            avg = total / listy.size();
        }

        return avg;
    }

    public Double calcVariance (ArrayList<Double> listy, Double mean) {

        double variance = 0;

        for (int i = 0; i < listy.size(); i++) {
            variance += (listy.get(i) - mean) * (listy.get(i) - mean);
        }
        return variance / listy.size();
    }

    public ArrayList<Double> getMeanDaphSlopes() {
        return meanDaphSlopes;
    }

    public void setMeanDaphSlopes(ArrayList<Double> meanDaphSlopes) {
        this.meanDaphSlopes = meanDaphSlopes;
    }

    public ArrayList<Double> getMeanDaphInts() {
        return meanDaphInts;
    }

    public void setMeanDaphInts(ArrayList<Double> meanDaphInts) {
        this.meanDaphInts = meanDaphInts;
    }

    public ArrayList<Double> getMeanSymbSLopes() {
        return meanSymbSLopes;
    }

    public void setMeanSymbSLopes(ArrayList<Double> meanSymbSLopes) {
        this.meanSymbSLopes = meanSymbSLopes;
    }

    public ArrayList<Double> getMeanSymbInts() {
        return meanSymbInts;
    }

    public void setMeanSymbInts(ArrayList<Double> meanSymbInts) {
        this.meanSymbInts = meanSymbInts;
    }

    public ArrayList<Double> getVarianceDaphSlopes() {
        return varianceDaphSlopes;
    }

    public void setVarianceDaphSlopes(ArrayList<Double> varianceDaphSlopes) {
        this.varianceDaphSlopes = varianceDaphSlopes;
    }

    public ArrayList<Double> getVarianceDaphInts() {
        return varianceDaphInts;
    }

    public void setVarianceDaphInts(ArrayList<Double> varianceDaphInts) {
        this.varianceDaphInts = varianceDaphInts;
    }

    public ArrayList<Double> getVarianceSymbSLopes() {
        return varianceSymbSLopes;
    }

    public void setVarianceSymbSLopes(ArrayList<Double> varianceSymbSLopes) {
        this.varianceSymbSLopes = varianceSymbSLopes;
    }

    public ArrayList<Double> getVarianceSymbInts() {
        return varianceSymbInts;
    }

    public void setVarianceSymbInts(ArrayList<Double> varianceSymbInts) {
        this.varianceSymbInts = varianceSymbInts;
    }

    @Override
    public String toString() {
        return "MeanData{" +
                "meanDaphSlopes=" + meanDaphSlopes +
                ", meanDaphInts=" + meanDaphInts +
                ", meanSymbSLopes=" + meanSymbSLopes +
                ", meanSymbInts=" + meanSymbInts +
                ", varianceDaphSlopes=" + varianceDaphSlopes +
                ", varianceDaphInts=" + varianceDaphInts +
                ", varianceSymbSLopes=" + varianceSymbSLopes +
                ", varianceSymbInts=" + varianceSymbInts +
                '}';
    }
}
