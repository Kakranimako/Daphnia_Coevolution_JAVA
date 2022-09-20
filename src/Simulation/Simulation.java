package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simulation {

    

    public Collected_data simulator (HashMap<String, Double> variables, Collected_data bigdata, ArrayList<Double> datapoints, String variablePar, String mode, HashMap<String, Double> modeArgs){

        HashMap<String, Daphnia> daphniaPop = new HashMap<>();

        HashMap<String, Symbiont> symbiontPop = new HashMap<>();



        Populations allPops = new Populations(daphniaPop,symbiontPop);


        ArrayList<Double> varList = new ArrayList<>();

        if (mode.equals("sinus")) {
             varList = sinusfunc(variables, variables.get(variablePar), modeArgs.get("horizonShift"), modeArgs.get("period"));
        }
        else if (mode.equals("linear")) {
            varList = linear(variables, modeArgs.get("slope"), modeArgs.get("vertShift"));
        }
        else if (mode.equals("step")) {
            varList = stepfunc(variables, variables.get(variablePar), modeArgs.get("phases"));
        }
        else if (mode.equals("random")) {
            varList = stochasticfunc(variables, modeArgs.get("mean"), modeArgs.get("variance"));
        }


        if (!mode.equals("static")) {
            variables.put(variablePar, varList.get(0));
        }

        allPops = initialisation(allPops, variables);
        bigdata = dataCollected(allPops, bigdata, variables,0.0);

        for (double genNum = 1.0; genNum < variables.get("num_of_gens")+1; genNum++) {

           if (!mode.equals("static")) {
               variables.put(variablePar, varList.get((int) genNum));
           }

            allPops = interaction(allPops, variables);

            allPops = reprod(allPops, variables);


            if (datapoints.contains(genNum)) {
                bigdata = dataCollected(allPops, bigdata, variables, genNum);
            }
        }
        
        return bigdata;



    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              BIG FUNCTIONS                                                  //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations initialisation(Populations allPops, HashMap<String, Double> variables){

        HashMap<String, Symbiont> symbiontPop = new OrganismFactory().CreateSymbiont("Symbiont", variables.get("initMeanGene1"),
                variables.get("initVariance"), variables.get("initMeanGene2"), variables.get("initMeanGene1"), variables.get("symbPopSize"));
        allPops.setSymbiontPop(symbiontPop);

        HashMap<String, Daphnia> daphniaPop = new OrganismFactory().CreateDaphnias("Daphnia", variables.get("initMeanGene1"),
                variables.get("initVariance"), variables.get("initMeanGene2"), variables.get("initMeanGene1"), variables.get("daphPopSize"));
        allPops.setDaphniaPop(daphniaPop);

        return allPops;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Populations reprod(Populations allPops, HashMap<String, Double> varis) {

        HashMap<String, Daphnia> Dpop = reprodDaph(allPops, varis);
        allPops.setDaphniaPop(Dpop);

        HashMap<String, Symbiont> Spop = reprodSymb(allPops, varis);
        allPops.setSymbiontPop(Spop);


        return allPops;

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations interaction(Populations allPops, HashMap<String, Double> varis) {

        ArrayList<Symbiont> symblist = new ArrayList<>(allPops.getSymbiontPop().values());

        for (Symbiont symb: symblist) {
            symb.setFitness(1 - varis.get("scarcity"));
        }

        for (Daphnia daph: allPops.getDaphniaPop().values()) {

            daph.setFitness(1- varis.get("scarcity"));
            for (int i = 0; i < 2; i++) {
                boolean susceptible = susceptibility(daph);
                if (susceptible) {
                    Symbiont symb = symblist.get(new Random().nextInt(symblist.size()-1));
                    boolean attachment = attachability(symb);

                    if (attachment) {
                        daph.setFitness(daph.getFitness() + symb.getGene3());
                        symb.setFitness(1 - varis.get("scarcity") + daph.getGene3());

                        symblist.remove(symb);
                    }
                }
            }
        }

        return allPops;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Collected_data dataCollected (Populations allPops, Collected_data bigdata, HashMap<String, Double> varis, double genNum) {

        double daphSlopesAvg = 0;
        double daphIntsAvg = 0;
        double symbSlopesAvg = 0;
        double symbIntsAvg = 0;

        for (Symbiont symb: allPops.getSymbiontPop().values()) {
            symbSlopesAvg += symb.getGene1();
            symbIntsAvg += symb.getGene2();
        }
        symbSlopesAvg = symbSlopesAvg/allPops.getSymbiontPop().size();
        symbIntsAvg = symbIntsAvg/allPops.getSymbiontPop().size();

        for (Daphnia daph: allPops.getDaphniaPop().values()) {
            daphSlopesAvg += daph.getGene1();
            daphIntsAvg += daph.getGene2();
        }
        daphSlopesAvg = daphSlopesAvg/allPops.getDaphniaPop().size();
        daphIntsAvg = daphIntsAvg/allPops.getDaphniaPop().size();

        HashMap<String, Double> avgDataDict = new HashMap<>();
        avgDataDict.put("daphSlopes", daphSlopesAvg);
        avgDataDict.put("daphInts", daphIntsAvg);
        avgDataDict.put("symbSlopes", symbSlopesAvg);
        avgDataDict.put("symbInts", symbIntsAvg);


    
        ArrayList<String> colHeadersParams = new ArrayList<>();
        colHeadersParams.add("scarcity");
        colHeadersParams.add("vir_parD");
        colHeadersParams.add("vir_parS");
        colHeadersParams.add("fitPen");
        colHeadersParams.add("mutStepSize");
        colHeadersParams.add("mut_chance");

        ArrayList<String> colHeadersData = new ArrayList<>();
        colHeadersData.add("daphSlopes");
        colHeadersData.add("daphInts");
        colHeadersData.add("symbSlopes");
        colHeadersData.add("symbInts");

        bigdata.getColumns().get("generations").get(genNum).add(genNum);

        for (String columnname : colHeadersParams) {
            
            HashMap<Double, ArrayList<Double>> column = bigdata.getColumns().get(columnname);
            column.get(genNum).add(varis.get(columnname));

        }

        for (String columnname2 : colHeadersData) {

            HashMap<Double, ArrayList<Double>> column = bigdata.getColumns().get(columnname2);
            column.get(genNum).add(avgDataDict.get(columnname2));
        }



        
        return bigdata;
                
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public HashMap<String, Daphnia> reprodDaph(Populations allPops, HashMap<String, Double> varis) {

        Parentpicker picksLists = new Parentpicker();
        picksLists = makeCumulFitlist(allPops, "Daph");
        picksLists = chooseParent(picksLists);



        return new OrganismFactory().CreateNewIndvsDaphnia("Daphnia", allPops.getDaphniaPop(), varis, varis.get("daphPopSize"), picksLists.getParentList());

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public HashMap<String, Symbiont> reprodSymb(Populations allPops, HashMap<String, Double> varis){


        double size = varis.get("symbPopSize") + 1;


        Parentpicker picksList = new Parentpicker();
        picksList  = makeCumulFitlist(allPops, "Symb");
        picksList = chooseParent(picksList);


        return new OrganismFactory().CreateNewIndvsSymbiont("Symbiont", allPops.getSymbiontPop(), varis, size, picksList.getParentList());
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              SMALL FUNCTIONS                                                //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Parentpicker makeCumulFitlist(Populations allPops, String orgtype){

        ArrayList<Organism> testpoplist = new ArrayList<>(allPops.getSymbiontPop().values());

        if (orgtype.equals("Daph")) {
            testpoplist = new ArrayList<>(allPops.getDaphniaPop().values());
         }


        double sumfit = 0;
        ArrayList<Double> cumulFitList = new ArrayList<>();

        ArrayList<String> parentCumulList = new ArrayList<>();

        for(Organism org : testpoplist) {
            sumfit = sumfit + 1000*org.getFitness();
            cumulFitList.add(sumfit);
            parentCumulList.add(org.getName());
        }

        return new Parentpicker(cumulFitList, parentCumulList, new ArrayList<>());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Parentpicker chooseParent(Parentpicker picksList){

        ArrayList<String> parentList = new ArrayList<>();

        int sizelist = picksList.getCumulFitList().size();


        int i = 1;
        while ( i <= sizelist) {

            double target = new Random().nextDouble(0, picksList.getCumulFitList().get(sizelist-1));

            int linkergrens = 0;
            int rechtergrens = picksList.getCumulFitList().size()-1;
            int targIndex = (linkergrens + rechtergrens)/2;
            int oldtargIndex = -2;

            while(targIndex != 0 && !(picksList.getCumulFitList().get(targIndex) >= target && picksList.getCumulFitList().get(targIndex-1) < target)) {


                if (picksList.getCumulFitList().get(targIndex) > target) {
                    rechtergrens = targIndex;
                }
                else linkergrens = targIndex;

                targIndex = (linkergrens + rechtergrens)/2;

                if (oldtargIndex == linkergrens) {
                    targIndex += 1;
                }
                oldtargIndex = targIndex;
            }

            parentList.add(picksList.getParentCumulList().get(targIndex));
            i = i+1;
        }
        
        picksList.setParentList(parentList);



        return picksList;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Double newGene(double parentGene, double mutChance, double mutStep) {

        double c = new Random().nextDouble();
        double newgene = parentGene;


        if (c < mutChance) {

            double scaled = new Random().nextDouble(-mutStep, mutStep);

            newgene = parentGene + scaled;
        }
        return newgene;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Boolean attachability(Symbiont symb) {

        double c = new Random().nextDouble(0, 1);

        double p = 1 / (1 + Math.exp(symb.getGene1() * 0.5 - symb.getGene2()));

        boolean decision;
        decision = c < p;

        return decision;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean susceptibility(Daphnia daph) {

        double c = new Random().nextDouble(0, 1);

        double p = 1 / (1 + Math.exp(daph.getGene1() * 0.5 - daph.getGene2()));

        boolean decision;
        decision = c < p;

        return decision;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void toTXT (Collected_data bigdata, MeanData meanie, HashMap<String, Double> varis, String mode, String variablePar, Double varParvalue, String filename) throws IOException {

        FileWriter file = new FileWriter(filename +".csv");

        file.write(filename + "," + "\n"+
                "Runs" + "," + bigdata.getColumns().get("generations").get(0.0).size() + ",," +
                "Init_meanG1" + "," + varis.get("initMeanGene1") + ",," + "Init_meanG2" + "," + varis.get("initMeanGene2") + ",," +
                "Init_StD" + "," + varis.get("initVariance")+ "\n" +
                "generations" + "," + varis.get("num_of_gens") + "," + "," + "mode" + "," + mode + ",," +
                "variableParam" + "," + variablePar + ",," + "varParValue" + "," + varParvalue + ",," +"\n" +
                "daphPopsize" + "," + varis.get("daphPopSize") + "\n" +
                "symbPopsize" + "," + varis.get("symbPopSize") + "\n" +
                "fitPenDaph" + "," + varis.get("fitPen") + "," + "," + "fitPenSymb" + "," + varis.get("fitPenSymb") +"\n"+
                "mut_chance" + "," + varis.get("mut_chance") + "," + "," + "mutStepSize" + "," + varis.get("mutStepSize") + "\n\n");

        file.write("Generation" + "," + "scarcity" + "," + "vir_parD" + "," + "vir_parS" + ","
                + "daphSlopes" + "," +  "dSlopesStD" + "," + "daphInts" + "," + "dIntsStd" + "," +
                "symbSlopes" + "," + "sSlopesStD" + "," + "symbInts" + "," + "sIntsStD" + "\n");

        for (double datapoint: bigdata.getColumns().get("generations").keySet()) {

            String dataline = String.valueOf(bigdata.getColumns().get("generations").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("scarcity").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("vir_parD").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("vir_parS").get(datapoint).get(0)) + "," +
                    String.valueOf(meanie.getMeanDaphSlopes().get(datapoint)) + "," +
                    String.valueOf(meanie.getVarianceDaphSlopes().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanDaphInts().get(datapoint)) + "," +
                    String.valueOf(meanie.getVarianceDaphInts().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanSymbSlopes().get(datapoint)) + "," +
                    String.valueOf(meanie.getVarianceSymbSlopes().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanSymbInts().get(datapoint)) + "," +
                    String.valueOf(meanie.getVarianceDaphInts().get(datapoint));

            file.write(dataline + "\n");
        }

        file.close();
        }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Double> linear (HashMap<String, Double> varis, double slope, double vertShift) {

        ArrayList<Double> linearList = new ArrayList<>();

        for (int i = 0; i < varis.get("num_of_gens"); i++) {
            linearList.add(slope*i + vertShift);
        }
        
        linearList.add(linearList.get(linearList.size()-1));
        return linearList;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Double> sinusfunc (HashMap<String, Double> varis, double specificParam, double horizonShift, double period) {

        ArrayList<Double> sinusList = new ArrayList<>();
        double a = specificParam/2;
        double c = specificParam/2;

        for (int i = 0; i < varis.get("num_of_gens")+1; i++) {

            sinusList.add(a * Math.sin((2*Math.PI*i)/(period*varis.get("num_of_gens")) - horizonShift) +c);

        }

        sinusList.add(sinusList.get(sinusList.size()-1));
        return sinusList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Double> stepfunc (HashMap<String, Double> varis, double specificParam, double phases) {

        ArrayList<Double> stepList = new ArrayList<>();

        double length = varis.get("num_of_gens")+1;
        double multiplier = specificParam/phases;
        double blocks = length/phases;

        for (int i = 1; i <= phases; i++) {
            for (int u = 1; u <= blocks; u++) {
                stepList.add(1 - multiplier*i);
            }
        }
        stepList.add(stepList.get(stepList.size()-1));
        return stepList;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Double> stochasticfunc (HashMap<String, Double> varis, double mean, double variance) {

        ArrayList<Double> stocList = new ArrayList<>();
        double length = varis.get("num_of_gens")+1;

        for (int i = 1; i < length; i++) {
            stocList.add(new Random().nextDouble(mean, variance));
        }
        stocList.add(stocList.get(stocList.size()-1));
        return stocList;
    }
}

