package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simulation {

    

    public Collected_data simulator (HashMap<String, Double> variables, Collected_data bigdata, ArrayList<Double> datapoints, String variablePar1, String variablePar2, String mode, HashMap<String, Double> modeArgs){

        HashMap<String, Daphnia> daphniaPop = new HashMap<>();

        HashMap<String, Symbiont> symbiontPop = new HashMap<>();



        Populations allPops = new Populations(daphniaPop,symbiontPop);


        ArrayList<Double> varList = new ArrayList<>();

        if (mode.equals("sinus")) {
             varList = sinusfunc(variables, variables.get(variablePar1), modeArgs.get("horizonShift"), modeArgs.get("period"));
        }
        else if (mode.equals("linear")) {
            varList = linear(variables, modeArgs.get("slope"), modeArgs.get("vertShift"));
        }
        else if (mode.equals("step")) {
            varList = stepfunc(variables, variables.get(variablePar1), modeArgs.get("phases"));
        }
        else if (mode.equals("random")) {
            varList = stochasticfunc(variables, modeArgs.get("mean"), modeArgs.get("variance"));
        }


        if (!mode.equals("static")) {
            variables.put(variablePar1, varList.get(0));
        }

        allPops = initialisation(allPops, variables);
        bigdata = dataCollected(allPops, bigdata, variables,0.0);

        for (double genNum = 1.0; genNum < variables.get("num_of_gens")+1; genNum++) {

           if (!mode.equals("static")) {
               variables.put(variablePar1, varList.get((int) genNum));
           }

            allPops = interaction(allPops, variables);

            if (datapoints.contains(genNum)) {
                bigdata = dataCollected(allPops, bigdata, variables, genNum);
            }
            allPops = reprod(allPops, variables);



        }
        
        return bigdata;



    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              BIG FUNCTIONS                                                  //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations initialisation(Populations allPops, HashMap<String, Double> variables){

        HashMap<String, Symbiont> symbiontPop = new OrganismFactory().CreateSymbiont("Symbiont", variables.get("initMeanGene1"),
                variables.get("initVariance"), variables.get("initMeanGene2"), variables.get("initMeanGene3"), variables.get("symbPopSize"));
        allPops.setSymbiontPop(symbiontPop);

        HashMap<String, Daphnia> daphniaPop = new OrganismFactory().CreateDaphnias("Daphnia", variables.get("initMeanGene1"),
                variables.get("initVariance"), variables.get("initMeanGene2"), variables.get("initMeanGene3"), variables.get("daphPopSize"));
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

        double susceptChanceAvg = 0;
        double attachChanceAvg = 0;
        double hostAdvAvg = 0;
        double symbAdvAvg = 0;
        double fitAvgDaph = 0;
        double fitAvgSymb = 0;

        for (Symbiont symb: allPops.getSymbiontPop().values()) {
            attachChanceAvg += 1 / (1 + Math.exp(symb.getGene1() * 0.5 - symb.getGene2())); //HARDCODE
            symbAdvAvg += symb.getGene3();
            fitAvgSymb += symb.getFitness();
        }
        attachChanceAvg = attachChanceAvg/allPops.getSymbiontPop().size();
        symbAdvAvg = symbAdvAvg/allPops.getSymbiontPop().size();
        fitAvgSymb = fitAvgSymb/allPops.getSymbiontPop().size();

        for (Daphnia daph: allPops.getDaphniaPop().values()) {
            susceptChanceAvg += 1 / (1 + Math.exp(daph.getGene1() * 0.5 - daph.getGene2())); //HARDCODE
            hostAdvAvg += daph.getGene3();
            fitAvgDaph += daph.getFitness();
        }
        susceptChanceAvg = susceptChanceAvg/allPops.getDaphniaPop().size();
        hostAdvAvg = hostAdvAvg/allPops.getDaphniaPop().size();
        fitAvgDaph = fitAvgDaph/allPops.getDaphniaPop().size();

        HashMap<String, Double> avgDataDict = new HashMap<>();
        avgDataDict.put("susceptChance", susceptChanceAvg);
        avgDataDict.put("attachChance", attachChanceAvg);
        avgDataDict.put("hostAdv", hostAdvAvg);
        avgDataDict.put("symbAdv", symbAdvAvg);
        avgDataDict.put("fitDaph", fitAvgDaph);
        avgDataDict.put("fitSymb", fitAvgSymb);



        ArrayList<String> colHeadersParams = new ArrayList<>();
        colHeadersParams.add("scarcity");
        colHeadersParams.add("mutStepSize");
        colHeadersParams.add("mut_chance");

        ArrayList<String> colHeadersData = new ArrayList<>();
        colHeadersData.add("susceptChance");
        colHeadersData.add("attachChance");
        colHeadersData.add("hostAdv");
        colHeadersData.add("symbAdv");
        colHeadersData.add("fitDaph");
        colHeadersData.add("fitSymb");

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


        double size = varis.get("symbPopSize");


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

        double p = 1 / (1 + Math.exp(symb.getGene1() * 0.5 - symb.getGene2())); //HARDCODE

        boolean decision;
        decision = c < p;

        return decision;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean susceptibility(Daphnia daph) {

        double c = new Random().nextDouble(0, 1);

        double p = 1 / (1 + Math.exp(daph.getGene1() * 0.5 - daph.getGene2())); //HARDCODE

        boolean decision;
        decision = c < p;

        return decision;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void toTXT (Collected_data bigdata, MeanData meanie, HashMap<String, Double> varis, String mode, String variablePar1,
                       Double varParValue1, String variablePar2, Double varParValue2, String filename) throws IOException {

        FileWriter file = new FileWriter(filename +".csv");

        file.write(filename + "," + "\n"+
                "Runs" + "," + bigdata.getColumns().get("generations").get(0.0).size() + ",," +
                "Init_meanG1" + "," + varis.get("initMeanGene1") + ",," + "Init_meanG2" + "," + varis.get("initMeanGene2") + ",," +
                "Init_StD" + "," + varis.get("initVariance")+ "\n" +
                "generations" + "," + varis.get("num_of_gens") + "," + "," + "mode" + "," + mode + ",," +
                "variableParam1" + "," + variablePar1 + ",," + "varParValue1" + "," + varParValue1 + ",," +
                "variableParam2" + "," + variablePar2 + ",," + "varParValue1" + "," + varParValue1 +"\n" +
                "daphPopsize" + "," + varis.get("daphPopSize") + "\n" +
                "symbPopsize" + "," + varis.get("symbPopSize") + "\n" +
                "mut_chance" + "," + varis.get("mut_chance") + "," + "," + "mutStepSize" + "," + varis.get("mutStepSize") + "\n\n\n");

        file.write("Generation" + "," + "scarcity" + "," + "suscept %" + "," + "attach %" + ","
                + "host Adv" + "," +  "symb factor" + "," + "fitDaph" + "," + "fitSymb" + "," +
                "susceptErr" + "," + "attachErr" + "," + "hostAdvErr" + "," + "symbFactorErr" + "," +
                 "fitDaphErr" + "," + "fitSymbErr" + "\n");

        for (double datapoint: bigdata.getColumns().get("generations").keySet()) {

            String dataline = String.valueOf(bigdata.getColumns().get("generations").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("scarcity").get(datapoint).get(0)) + "," +
                    String.valueOf(meanie.getMeansusceptChance().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanattachChance().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanhostAdv().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeansymbAdv().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanfitDaph().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanfitSymb().get(datapoint)) + "," +
                    String.valueOf(meanie.getVariancesusceptChance().get(datapoint)) + "," +
                    String.valueOf(meanie.getVarianceattachChance().get(datapoint)) + "," +
                    String.valueOf(meanie.getVariancehostAdv().get(datapoint)) + "," +
                    String.valueOf(meanie.getVariancesymbAdv().get(datapoint)) + "," +
                    String.valueOf(meanie.getVariancefitDaph().get(datapoint)) + "," +
                    String.valueOf(meanie.getVariancefitSymb().get(datapoint));

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

