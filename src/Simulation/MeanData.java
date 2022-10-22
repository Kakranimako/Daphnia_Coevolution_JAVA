package Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.DoubleToLongFunction;

public class MeanData {

    private HashMap<Double, Double> meanVirulence;
    private HashMap<Double, Double> meanResist;
    
    private HashMap<Double, Double> meanAvgFitD;
    
    private HashMap<Double, Double> meanAvgFitS;

    private HashMap<Double, Double> sem_Virulence;

    private HashMap<Double, Double> sem_AvgFitD;

    private HashMap<Double, Double> sem_AvgFitS;

    private HashMap<Double, Double> sem_Resist;


    public MeanData() {

    }

    public MeanData(HashMap<Double, Double> meanResist, HashMap<Double, Double> meanVirulence, HashMap<Double, Double> meanAvgFitD, HashMap<Double,
            Double> meanAvgFitS, HashMap<Double, Double> sem_Virulence, HashMap<Double, Double> sem_AvgFitD, HashMap<Double, Double> sem_AvgFitS,
                    HashMap<Double, Double> sem_Resist) {
        this.meanVirulence = meanVirulence;
        this.meanResist = meanResist;
        this.meanAvgFitD = meanAvgFitD;
        this.meanAvgFitS = meanAvgFitS;
        this.sem_Virulence = sem_Virulence;
        this.sem_AvgFitD = sem_AvgFitD;
        this.sem_AvgFitS = sem_AvgFitS;
        this.sem_Resist = sem_Resist;
    }

    public MeanData calcMeansVariance (Collected_data bigdata) {

        HashMap<Double, Double> meanVirulence = new HashMap<>();
        HashMap<Double, Double> meanAvgFitD = new HashMap<>();
        HashMap<Double, Double> meanAvgFitS = new HashMap<>();
        HashMap<Double, Double> sem_Virulence = new HashMap<>();
        HashMap<Double, Double> sem_AvgFitD= new HashMap<>();
        HashMap<Double, Double> sem_AvgFitS = new HashMap<>();
        HashMap<Double, Double> sem_resistGene = new HashMap<>();
        HashMap<Double, Double> resistGene = new HashMap<>();

        for (double datapoint : bigdata.getColumns().get("generations").keySet()) {

            double meanResistGene = calcAvg(bigdata.getColumns().get("avgResist").get(datapoint));
            resistGene.put(datapoint, meanResistGene);

            sem_resistGene.put(datapoint, calcVariance(bigdata.getColumns().get("avgResist").get(datapoint), meanResistGene));

            double meanVir = calcAvg(bigdata.getColumns().get("virulence").get(datapoint));
            meanVirulence.put(datapoint, meanVir);

            sem_Virulence.put(datapoint, calcVariance(bigdata.getColumns().get("virulence").get(datapoint), meanVir));

            double mean_avgFitD = calcAvg(bigdata.getColumns().get("avgFitD").get(datapoint));
            meanAvgFitD.put(datapoint, mean_avgFitD);

            sem_AvgFitD.put(datapoint, calcVariance(bigdata.getColumns().get("avgFitD").get(datapoint), mean_avgFitD));


            double mean_avgFitS = calcAvg(bigdata.getColumns().get("avgFitS").get(datapoint));
            meanAvgFitS.put(datapoint, mean_avgFitS);

            sem_AvgFitS.put(datapoint, calcVariance(bigdata.getColumns().get("avgFitS").get(datapoint), mean_avgFitS));



        }


        return new MeanData(resistGene, meanVirulence, meanAvgFitD, meanAvgFitS,
                sem_Virulence, sem_AvgFitD, sem_AvgFitS, sem_resistGene);
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

    public HashMap<Double, Double> getMeanVirulence() {
        return meanVirulence;
    }

    public void setMeanVirulence(HashMap<Double, Double> meanVirulence) {
        this.meanVirulence = meanVirulence;
    }

    public HashMap<Double, Double> getMeanAvgFitD() {
        return meanAvgFitD;
    }

    public void setMeanAvgFitD(HashMap<Double, Double> meanAvgFitD) {
        this.meanAvgFitD = meanAvgFitD;
    }

    public HashMap<Double, Double> getMeanAvgFitS() {
        return meanAvgFitS;
    }

    public void setMeanAvgFitS(HashMap<Double, Double> meanAvgFitS) {
        this.meanAvgFitS = meanAvgFitS;
    }

    public HashMap<Double, Double> getSem_Virulence() {
        return sem_Virulence;
    }

    public void setSem_Virulence(HashMap<Double, Double> sem_Virulence) {
        this.sem_Virulence = sem_Virulence;
    }

    public HashMap<Double, Double> getSem_AvgFitD() {
        return sem_AvgFitD;
    }

    public void setSem_AvgFitD(HashMap<Double, Double> sem_AvgFitD) {
        this.sem_AvgFitD = sem_AvgFitD;
    }

    public HashMap<Double, Double> getSem_AvgFitS() {
        return sem_AvgFitS;
    }

    public void setSem_AvgFitS(HashMap<Double, Double> sem_AvgFitS) {
        this.sem_AvgFitS = sem_AvgFitS;
    }

    public HashMap<Double, Double> getMeanResist() {
        return meanResist;
    }

    public void setMeanResist(HashMap<Double, Double> meanResist) {
        this.meanResist = meanResist;
    }

    public HashMap<Double, Double> getSem_Resist() {
        return sem_Resist;
    }

    public void setSem_Resist(HashMap<Double, Double> sem_Resist) {
        this.sem_Resist = sem_Resist;
    }
}
