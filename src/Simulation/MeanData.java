package Simulation;

import java.util.ArrayList;
import java.util.HashMap;

public class MeanData {

    private HashMap<Double, Double> meansusceptChance;
    
    private HashMap<Double, Double> meanhostAdv;
    
    private HashMap<Double, Double> meanattachChance;
    
    private HashMap<Double, Double> meansymbAdv;

    private HashMap<Double, Double> variancesusceptChance;

    private HashMap<Double, Double> variancehostAdv;

    private HashMap<Double, Double> varianceattachChance;

    private HashMap<Double, Double> variancesymbAdv;

    private HashMap<Double, Double> meanfitDaph;

    private HashMap<Double, Double> meanfitSymb;
    private HashMap<Double, Double> variancefitDaph;
    private HashMap<Double, Double> variancefitSymb;


    public MeanData() {

    }
    public MeanData(HashMap<Double, Double> meansusceptChance, HashMap<Double, Double> meanhostAdv, HashMap<Double,
            Double> meanattachChance, HashMap<Double, Double> meansymbAdv, HashMap<Double, Double> variancesusceptChance,
                    HashMap<Double, Double> variancehostAdv, HashMap<Double, Double> varianceattachChance,
                    HashMap<Double, Double> variancesymbAdv,
                    HashMap<Double, Double> meanfitSymb, HashMap<Double, Double> variancefitSymb,
                    HashMap<Double, Double> meanfitDaph, HashMap<Double, Double> variancefitDaph) {

        this.meansusceptChance = meansusceptChance;
        this.meanhostAdv = meanhostAdv;
        this.meanattachChance = meanattachChance;
        this.meansymbAdv = meansymbAdv;
        this.variancesusceptChance = variancesusceptChance;
        this.variancehostAdv = variancehostAdv;
        this.varianceattachChance = varianceattachChance;
        this.variancesymbAdv = variancesymbAdv;
        this.meanfitDaph = meanfitDaph;
        this.meanfitSymb = meanfitSymb;
        this.variancefitDaph = variancefitDaph;
        this.variancefitSymb = variancefitSymb;


    }

    public MeanData calcMeansVariance (Collected_data bigdata) {

        HashMap<Double, Double> meansusceptChance = new HashMap<>();
        HashMap<Double, Double> meanhostAdv = new HashMap<>();
        HashMap<Double, Double> meanattachChance = new HashMap<>();
        HashMap<Double, Double> meansymbAdv = new HashMap<>();
        HashMap<Double, Double> variancesusceptChance = new HashMap<>();
        HashMap<Double, Double> variancehostAdv = new HashMap<>();
        HashMap<Double, Double> varianceattachChance = new HashMap<>();
        HashMap<Double, Double> variancesymbAdv = new HashMap<>();

        HashMap<Double, Double> meanfitSymb = new HashMap<>();
        HashMap<Double, Double> variancefitSymb = new HashMap<>();
        HashMap<Double, Double> meanfitDaph = new HashMap<>();
        HashMap<Double, Double> variancefitDaph = new HashMap<>();

        

        for (double datapoint : bigdata.getColumns().get("generations").keySet()) {

            double meanDaph = calcAvg(bigdata.getColumns().get("susceptChance").get(datapoint));
            meansusceptChance.put(datapoint, meanDaph);

            variancesusceptChance.put(datapoint, calcVariance(bigdata.getColumns().get("susceptChance").get(datapoint), meanDaph));

            double hostAdv = calcAvg(bigdata.getColumns().get("hostAdv").get(datapoint));
            meanhostAdv.put(datapoint, hostAdv);

            variancehostAdv.put(datapoint, calcVariance(bigdata.getColumns().get("hostAdv").get(datapoint), hostAdv));


            double meansymb = calcAvg(bigdata.getColumns().get("attachChance").get(datapoint));
            meanattachChance.put(datapoint, meansymb);

            varianceattachChance.put(datapoint, calcVariance(bigdata.getColumns().get("attachChance").get(datapoint), meansymb));

            double symbAdv = calcAvg(bigdata.getColumns().get("symbAdv").get(datapoint));
            meansymbAdv.put(datapoint, symbAdv);

            variancesymbAdv.put(datapoint, calcVariance(bigdata.getColumns().get("symbAdv").get(datapoint), symbAdv));

            double meanFitSymb = calcAvg(bigdata.getColumns().get("fitSymb").get(datapoint));
            meanfitSymb.put(datapoint, meanFitSymb);

            variancefitSymb.put(datapoint, calcVariance(bigdata.getColumns().get("fitSymb").get(datapoint), meanFitSymb));

            double fitDaph = calcAvg(bigdata.getColumns().get("fitDaph").get(datapoint));
            meanfitDaph.put(datapoint, fitDaph);

            variancefitDaph.put(datapoint, calcVariance(bigdata.getColumns().get("fitDaph").get(datapoint), fitDaph));


        }


        return new MeanData(meansusceptChance, meanhostAdv, meanattachChance,
                meansymbAdv, variancesusceptChance, variancehostAdv, varianceattachChance, variancesymbAdv, meanfitSymb,
                variancefitSymb, meanfitDaph, variancefitDaph);
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

    public HashMap<Double, Double> getMeansusceptChance() {
        return meansusceptChance;
    }

    public void setMeansusceptChance(HashMap<Double, Double> meansusceptChance) {
        this.meansusceptChance = meansusceptChance;
    }

    public HashMap<Double, Double> getMeanhostAdv() {
        return meanhostAdv;
    }

    public void setMeanhostAdv(HashMap<Double, Double> meanhostAdv) {
        this.meanhostAdv = meanhostAdv;
    }

    public HashMap<Double, Double> getMeanattachChance() {
        return meanattachChance;
    }

    public void setMeanattachChance(HashMap<Double, Double> meanattachChance) {
        this.meanattachChance = meanattachChance;
    }

    public HashMap<Double, Double> getMeansymbAdv() {
        return meansymbAdv;
    }

    public void setMeansymbAdv(HashMap<Double, Double> meansymbAdv) {
        this.meansymbAdv = meansymbAdv;
    }

    public HashMap<Double, Double> getVariancesusceptChance() {
        return variancesusceptChance;
    }

    public void setVariancesusceptChance(HashMap<Double, Double> variancesusceptChance) {
        this.variancesusceptChance = variancesusceptChance;
    }

    public HashMap<Double, Double> getVariancehostAdv() {
        return variancehostAdv;
    }

    public void setVariancehostAdv(HashMap<Double, Double> variancehostAdv) {
        this.variancehostAdv = variancehostAdv;
    }

    public HashMap<Double, Double> getVarianceattachChance() {
        return varianceattachChance;
    }

    public void setVarianceattachChance(HashMap<Double, Double> varianceattachChance) {
        this.varianceattachChance = varianceattachChance;
    }

    public HashMap<Double, Double> getVariancesymbAdv() {
        return variancesymbAdv;
    }

    public void setVariancesymbAdv(HashMap<Double, Double> variancesymbAdv) {
        this.variancesymbAdv = variancesymbAdv;
    }

    public HashMap<Double, Double> getMeanfitDaph() {
        return meanfitDaph;
    }

    public void setMeanfitDaph(HashMap<Double, Double> meanfitDaph) {
        this.meanfitDaph = meanfitDaph;
    }

    public HashMap<Double, Double> getMeanfitSymb() {
        return meanfitSymb;
    }

    public void setMeanfitSymb(HashMap<Double, Double> meanfitSymb) {
        this.meanfitSymb = meanfitSymb;
    }

    public HashMap<Double, Double> getVariancefitDaph() {
        return variancefitDaph;
    }

    public void setVariancefitDaph(HashMap<Double, Double> variancefitDaph) {
        this.variancefitDaph = variancefitDaph;
    }

    public HashMap<Double, Double> getVariancefitSymb() {
        return variancefitSymb;
    }

    public void setVariancefitSymb(HashMap<Double, Double> variancefitSymb) {
        this.variancefitSymb = variancefitSymb;
    }

    @Override
    public String toString() {
        return "MeanData{" +
                "meansusceptChance=" + meansusceptChance +
                ", meanhostAdv=" + meanhostAdv +
                ", meanattachChance=" + meanattachChance +
                ", meansymbAdv=" + meansymbAdv +
                ", variancesusceptChance=" + variancesusceptChance +
                ", variancehostAdv=" + variancehostAdv +
                ", varianceattachChance=" + varianceattachChance +
                ", variancesymbAdv=" + variancesymbAdv +
                ", meanfitDaph=" + meanfitDaph +
                ", meanfitSymb=" + meanfitSymb +
                ", variancefitDaph=" + variancefitDaph +
                ", variancefitSymb=" + variancefitSymb +
                '}';
    }
}
