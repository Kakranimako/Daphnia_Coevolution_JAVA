package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    

    public Collected_data simulator (HashMap<String, Double> variables, Collected_data bigdata, ArrayList<Double> datapoints, String variablePar, String mode, HashMap<String, Double> modeArgs){

        HashMap<String, Daphnia> daphniaPop = new HashMap<>();

        HashMap<String, Symbiont> symbiontPop = new HashMap<>();

        HashMap<String, Symbiont> gutSymbionts = new HashMap<>();

        HashMap<String, Symbiont> envSymbionts = new HashMap<>();


        Populations allPops = new Populations(daphniaPop,symbiontPop,gutSymbionts,envSymbionts);


        ArrayList<Double> varList = new ArrayList<>();

        if (mode.equals("sinus")) {
             varList = sinusfunc(variables, variables.get(variablePar), modeArgs.get("horizonShift"), modeArgs.get("period"), modeArgs.get("vertShift"));
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

        HashMap<String, Symbiont> symbiontPop = new OrganismFactory().CreateSymbiont("Symbiont", variables.get("initGene1"),
                variables.get("initVar1"), variables.get("initGene2"), variables.get("initVar2"), variables.get("symbPopSize"));
        allPops.setSymbiontPop(symbiontPop);



        HashMap<String, Daphnia> daphniaPop = new OrganismFactory().CreateDaphnias("Daphnia", variables.get("resistGene"),
                variables.get("resistVar"), variables.get("daphPopSize"));
        allPops.setDaphniaPop(daphniaPop);


        ArrayList<Symbiont> symbiontlist = new ArrayList<>(allPops.getSymbiontPop().values());
        Collections.shuffle(symbiontlist);

        for (Daphnia daphnia : allPops.getDaphniaPop().values()) {
            int symbInd = new Random().nextInt(allPops.getDaphniaPop().size());
            Symbiont symbiont = symbiontlist.get(symbInd);
            daphnia.setpartner(symbiont.getName());
            symbiont.setpartner(daphnia.getName());
            allPops.getGutSymbionts().put(symbiont.getName(), symbiont);
            symbiontlist.remove(symbInd);
        }


        for (Symbiont symbiont: symbiontlist) {
            allPops.getEnvSymbionts().put(symbiont.getName(), symbiont);
        }
        return allPops;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Populations reprod(Populations allPops, HashMap<String, Double> varis) {

        HashMap<String, Daphnia> Dpop = reprodDaph(allPops, varis);
        allPops.setDaphniaPop(Dpop);

        HashMap<String, Symbiont> Gpop = reprodSymb(allPops, varis, "Gut");
        allPops.setGutSymbionts(Gpop);



        HashMap<String, Symbiont> Epop = reprodSymb(allPops, varis, "Env");
        allPops.setEnvSymbionts(Epop);



        HashSet<String> whitelist = createWhitelist(allPops.getDaphniaPop());
        Coupled resultsCoupling = createCoupling(allPops.getDaphniaPop(),allPops.getGutSymbionts(),whitelist);

        allPops = assignNonCoupled(resultsCoupling, allPops);

        return allPops;

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations interaction(Populations allPops, HashMap<String, Double> varis) {

        HashMap<String, Double> virDict = cr_virDict(allPops, varis.get("scarcity"));

        for (Daphnia daph : allPops.getDaphniaPop().values()) {

            Symbiont symb = allPops.getGutSymbionts().get(daph.getpartner());

            daph.setFitness(1 - virDict.get(daph.getName()) * (1 - Math.min(1, daph.getGene1())) - varis.get("D_resistCoeff")* daph.getGene1());
            symb.setFitness(1 + varis.get("S_virCoeff") * virDict.get(daph.getName()) * varis.get("S_resistCoeff")*(1 - Math.min(1, daph.getGene1())));

            if (daph.getFitness() < varis.get("thresholdFit")) {
                daph.setFitness(0.001);
                symb.setFitness(varis.get("S_reducedFit"));

            }
        }


        return allPops;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Collected_data dataCollected (Populations allPops, Collected_data bigdata, HashMap<String, Double> varis, double genNum) {

        double virAvg = 0;
        double avgFitD = 0;
        double avgFitS = 0;
        double avgResist = 0;


        for (Symbiont symby: allPops.getSymbiontPop().values()){
            virAvg += 1 / (1 + Math.exp(-symby.getGene1() * (varis.get("scarcity") - symby.getGene2())));
        }
        virAvg = virAvg/allPops.getSymbiontPop().size();

        for (Symbiont symb: allPops.getSymbiontPop().values()) {
            avgFitS += symb.getFitness();
        }
        avgFitS = avgFitS/allPops.getSymbiontPop().size();


        for (Daphnia daph: allPops.getDaphniaPop().values()) {
            avgFitD += daph.getFitness();
            avgResist += daph.getGene1();
        }
        avgFitD = avgFitD/allPops.getDaphniaPop().size();
        avgResist = avgResist/allPops.getDaphniaPop().size();


        HashMap<String, Double> avgDataDict = new HashMap<>();
        avgDataDict.put("avgFitD", avgFitD);
        avgDataDict.put("avgFitS", avgFitS);
        avgDataDict.put("virulence", virAvg);
        avgDataDict.put("avgResist", avgResist);



    
        ArrayList<String> colHeadersParams = new ArrayList<>();
        colHeadersParams.add("scarcity");
        colHeadersParams.add("thresholdFit");
        colHeadersParams.add("S_reducedFit");
        colHeadersParams.add("D_resistCoeff");
        colHeadersParams.add("S_resistCoeff");
        colHeadersParams.add("S_virCoeff");
        colHeadersParams.add("mutStepSize");
        colHeadersParams.add("mut_chance");

        ArrayList<String> colHeadersData = new ArrayList<>();
        colHeadersData.add("avgFitD");
        colHeadersData.add("avgFitS");
        colHeadersData.add("virulence");
        colHeadersData.add("avgResist");

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

        ArrayList<Daphnia> Dpop = new ArrayList<>(allPops.getDaphniaPop().values());
        picksLists = makeCumulFitlistD(Dpop);
        picksLists = chooseParent(picksLists);


        return new OrganismFactory().CreateNewIndvsDaphnia("Daphnia", allPops.getDaphniaPop(), varis, varis.get("daphPopSize"), picksLists.getParentList());

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public HashMap<String, Symbiont> reprodSymb(Populations allPops, HashMap<String, Double> varis, String whichPop){

        HashMap<String, Symbiont> symbPop = allPops.getEnvSymbionts();
        double start = varis.get("daphPopSize") + 1;
        double stop = varis.get("symbPopSize");

        if (whichPop.equals("Gut")) {
            symbPop = allPops.getGutSymbionts();
            start = 1;
            stop = varis.get("daphPopSize");
        }
        Parentpicker picksList = new Parentpicker();
        picksList  = makeCumulFitlistS(allPops, whichPop);
        picksList = chooseParent(picksList);



        HashSet<String> parentSet = new HashSet<>(picksList.getParentList());


        symbPop = new OrganismFactory().CreateNewIndvsSymbiont("Symbiont", symbPop, varis, start, stop, picksList.getParentList());



        return symbPop;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              SMALL FUNCTIONS                                                //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Parentpicker makeCumulFitlistD(ArrayList<Daphnia> Dpop){

        double sumfit = 0;
        ArrayList<Double> cumulFitList = new ArrayList<>();

        ArrayList<String> parentCumulList = new ArrayList<>();

        for(Daphnia org : Dpop) {
            sumfit = sumfit + org.getFitness();
            cumulFitList.add(sumfit);
            parentCumulList.add(org.getName());
        }


        return new Parentpicker(cumulFitList, parentCumulList, new ArrayList<>());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Parentpicker makeCumulFitlistS(Populations allPops, String whichpop){

        ArrayList<Symbiont> Spop;

        if (whichpop == "Gut") {
            ArrayList<Symbiont> mypop = new ArrayList<>(allPops.getGutSymbionts().values());
            Spop = mypop;
        }
        else {
            ArrayList<Symbiont> mypop2 = new ArrayList<>(allPops.getEnvSymbionts().values());

            Spop = mypop2;
        }

        double sumfit = 0;
        ArrayList<Double> cumulFitList = new ArrayList<>();

        ArrayList<String> parentCumulList = new ArrayList<>();

        for(Symbiont org : Spop) {
            sumfit = sumfit + 1000*org.getFitness();
            cumulFitList.add(sumfit);
            parentCumulList.add(org.getName());
        }


        return new Parentpicker(cumulFitList, parentCumulList, new ArrayList<>());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Parentpicker chooseParent(Parentpicker picksList){

        ArrayList<String> parentList = new ArrayList<>();

        ArrayList<Double> cList = new ArrayList<>();
        cList.addAll(picksList.getCumulFitList());

        int sizelist = cList.size();

        Collections.sort(cList);
        double last_element = cList.get(sizelist-1);



        int i = 1;
        while ( i <= sizelist) {


            double target = ThreadLocalRandom.current().nextDouble(last_element);

            ArrayList<Double> aList = new ArrayList<>();
            aList.addAll(picksList.getCumulFitList());
            aList.add(target);

            Collections.sort(aList);

            int targIndex = aList.indexOf(target);
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

            double scaled = ThreadLocalRandom.current().nextDouble(-mutStep, mutStep);

            newgene = parentGene + scaled;
        }
        return newgene;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  HashSet<String> createWhitelist (HashMap<String, Daphnia> Dpop) {

        HashSet<String> whitelist = new HashSet<>();

        for (Daphnia daphnia: Dpop.values()) {
            whitelist.add(daphnia.getOuder());
        }

        return whitelist;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Coupled createCoupling (HashMap<String, Daphnia> Dpop, HashMap<String, Symbiont> gutSymbs, HashSet<String> whitelist) {


        HashMap<String, Daphnia> coupledDaphs = new HashMap<>();
        HashMap<String, Daphnia> nonCoupledDaphs = new HashMap<>();

        HashMap<String, Symbiont> coupledSymbs = new HashMap<>();
        HashMap<String, Symbiont> nonCoupledSymbs = new HashMap<>();


        HashMap<String, ArrayList<Daphnia>> parentChildMap = new HashMap<>();
        HashMap<String, ArrayList<Symbiont>> hostSymbMap = new HashMap<>();

        for (String item : whitelist) {
            parentChildMap.put(item + "key", new ArrayList<>());
        }

        for (String item : whitelist) {
            hostSymbMap.put(item + "key", new ArrayList<>());
        }

        for (Daphnia ind : Dpop.values()){
            parentChildMap.get(ind.getOuder() + "key").add(ind);
        }
        for (Symbiont ind : gutSymbs.values()) {
            if (whitelist.contains(ind.getpartner())) {
                hostSymbMap.get(ind.getpartner() + "key").add(ind);
            }
            else nonCoupledSymbs.put(ind.getName(), ind);
        }

        for (String item : parentChildMap.keySet()) {

            ArrayList<Daphnia> toBeCoupledDaph = parentChildMap.get(item);
            ArrayList<Symbiont> toBeCoupledSymb = hostSymbMap.get(item);

            Collections.shuffle(toBeCoupledDaph);
            Collections.shuffle(toBeCoupledSymb);

            while (toBeCoupledDaph.size() != 0 && toBeCoupledSymb.size() != 0) {
                Daphnia daph = toBeCoupledDaph.get(0);
                Symbiont symb = toBeCoupledSymb.get(0);

                daph.setpartner(symb.getName());
                symb.setpartner(daph.getName());

                coupledDaphs.put(daph.getName(), daph);
                coupledSymbs.put(symb.getName(), symb);

                toBeCoupledDaph.remove(daph);
                toBeCoupledSymb.remove(symb);

            }

            for (Daphnia ind : toBeCoupledDaph) {
                nonCoupledDaphs.put(ind.getName(), ind);
            }

            for (Symbiont ind : toBeCoupledSymb) {
                nonCoupledSymbs.put(ind.getName(), ind);
            }

        }

        return new Coupled(coupledDaphs, coupledSymbs, nonCoupledDaphs, nonCoupledSymbs);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Populations assignNonCoupled(Coupled resultCoupling, Populations allPops) {

        HashMap<String, Symbiont> newEnvSymbs = new HashMap<>();
        newEnvSymbs.putAll(allPops.getEnvSymbionts());
        newEnvSymbs.putAll(resultCoupling.getNonCoupledSymbs());
        ArrayList<Symbiont> newEnvSymbsList = new ArrayList<>(newEnvSymbs.values());
        Collections.shuffle(newEnvSymbsList);

        HashMap<String, Daphnia> newCoupledDaph = new HashMap<>();
        HashMap<String, Symbiont> newCoupledSymbs = new HashMap<>();

        for (Daphnia daph : resultCoupling.getNonCoupledDaphs().values()) {

            int symbInd = (int) ThreadLocalRandom.current().nextDouble(0, newEnvSymbs.size());
            Symbiont symb = newEnvSymbsList.get(symbInd);
            daph.setpartner(symb.getName());
            symb.setpartner(daph.getName());

            newEnvSymbs.remove(symb.getName());
            newEnvSymbsList.remove(symb);

            newCoupledSymbs.put(symb.getName(), symb);
            newCoupledDaph.put(daph.getName(), daph);

        }

        allPops.setEnvSymbionts(newEnvSymbs);
        for(Symbiont symb : allPops.getEnvSymbionts().values()) {
            symb.setpartner("Geen");
        }

        allPops.setDaphniaPop(newCoupledDaph);
        allPops.getDaphniaPop().putAll(resultCoupling.getCoupledDaphs());

        allPops.setGutSymbionts(resultCoupling.getCoupledSymbs());
        allPops.getGutSymbionts().putAll(newCoupledSymbs);

        HashMap<String, Symbiont> newSymbpop = new HashMap<>();
        newSymbpop.putAll(newEnvSymbs);
        newSymbpop.putAll(allPops.getGutSymbionts());

        allPops.setSymbiontPop(newSymbpop);





        return allPops;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public HashMap<String, Double> cr_virDict(Populations allPops, double scarcity) {

        HashMap<String, Double> virDict = new HashMap<>();

        for (Daphnia daph : allPops.getDaphniaPop().values()) {
            String symb = daph.getpartner();
            Symbiont symby = allPops.getGutSymbionts().get(symb);
            double virulence = 1 / (1 + Math.exp(symby.getGene1() * (scarcity - symby.getGene2())));
            virDict.put(daph.getName(), virulence);
        }
        return virDict;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean viabilityTest(Daphnia daph, HashMap<String, Double> virDict) {

        double c = ThreadLocalRandom.current().nextDouble(0, 1);

        double p = 1 / (1 + Math.exp(daph.getGene1() * (virDict.get(daph.getName()) - daph.getGene2())));

        boolean decision;
        decision = c < p;

        return decision;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations kick_and_Replace(Daphnia daph, Populations allPops) {

        String oldSymb = daph.getpartner();
        Symbiont oldSymby = allPops.getGutSymbionts().get(oldSymb);

        allPops.getGutSymbionts().remove(oldSymb);
        allPops.getEnvSymbionts().put(oldSymb, oldSymby);

        oldSymby.setpartner("Geen");

        int newSymbInd = (int) ThreadLocalRandom.current().nextDouble(0, allPops.getEnvSymbionts().size()-1);
        Collection<Symbiont> symbiontCollection = allPops.getEnvSymbionts().values();
        ArrayList<Symbiont> symbList = new ArrayList<>(symbiontCollection);

        Symbiont newSymb = symbList.get(newSymbInd);

        daph.setpartner(newSymb.getName());
        newSymb.setpartner(daph.getName());

        allPops.getEnvSymbionts().remove(newSymb.getName());
        allPops.getGutSymbionts().put(newSymb.getName(), newSymb);

        return allPops;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Double calcVir(Symbiont symb, HashMap<String, Double> varis) {

        return 1 / (1 + Math.exp(symb.getGene1() * (varis.get("scarcity") - symb.getGene2())));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void toTXT (Collected_data bigdata, MeanData meanie, HashMap<String, Double> varis, String mode, String varPar1, Double varParvalue1, String varPar2, Double varParvalue2, String foldername, String filename) throws IOException {

        foldername = "VT_" + foldername;
        filename = "OSC" +filename;
        File folder = new File("D:\\desktopp\\Daphnia_EXPS/" + foldername);
        folder.mkdir();
        FileWriter file = new FileWriter("D:\\desktopp\\Daphnia_EXPS/"+ foldername + "/" + filename+".csv");

        file.write(filename + "," + "\n"+
                "Runs" + "," + bigdata.getColumns().get("generations").get(0.0).size() + ",," +
                "InitGene1" + "," + varis.get("initGene1") + ",," + "InitGene2" + "," + varis.get("initGene2") + ",," +
                "Init_G1_StD" + "," + varis.get("initVar1")+ ",," + "Init_G2_StD" + "," + varis.get("initVar2")+ "\n" +
                "ResistGene," + varis.get("resistGene") + ",,resistVar," + varis.get("resistVar") + ",,generations" + "," + varis.get("num_of_gens") + "," + "," + "mode" + "," + mode + ",," +
                "varPar1" + "," + varPar1 + "," + varParvalue1+ ",," + "varPar2," + varPar2 + ",NA,"  +"\n" +
                "daphPopsize" + "," + varis.get("daphPopSize") + "\n" +
                "symbPopsize" + "," + varis.get("symbPopSize") + "\n" +
                "thresholdFit" + "," + varis.get("thresholdFit") + ",," + "S_reducedFit" + "," + varis.get("S_reducedFit") +"\n"+
                "mut_chance" + "," + varis.get("mut_chance") + "," + "," + "mutStepSize" + "," + varis.get("mutStepSize") + "\n\n");

        file.write("Generation" + "," + "scarcity" + ",resistGene,resG_SEM," + "virulence" + "," +"vir_SEM," + "avgFitD" + ",avgfitD_SEM,"
                + "avgFitS" + ",avgFitS_SEM," +  "D_resistCoeff" + "," + "S_resistCoeff,S_virCoeff" + "\n");

        for (double datapoint: bigdata.getColumns().get("generations").keySet()) {

            String dataline = String.valueOf(bigdata.getColumns().get("generations").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("scarcity").get(datapoint).get(0)) + "," +
                    String.valueOf(meanie.getMeanResist().get(datapoint)) + "," +
                    String.valueOf(meanie.getSem_Resist().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanVirulence().get(datapoint)) + "," +
                    String.valueOf(meanie.getSem_AvgFitD().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanAvgFitD().get(datapoint)) + "," +
                    String.valueOf(meanie.getSem_AvgFitD().get(datapoint)) + "," +
                    String.valueOf(meanie.getMeanAvgFitS().get(datapoint)) + "," +
                    String.valueOf(meanie.getSem_AvgFitS().get(datapoint)) + "," +
                    String.valueOf(bigdata.getColumns().get("D_resistCoeff").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("S_resistCoeff").get(datapoint).get(0)) + "," +
                    String.valueOf(bigdata.getColumns().get("S_virCoeff").get(datapoint).get(0));


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

    public ArrayList<Double> sinusfunc (HashMap<String, Double> varis, double specificParam, double horizonShift, double period, double vertShift) {

        ArrayList<Double> sinusList = new ArrayList<>();


        for (int i = 0; i < varis.get("num_of_gens")+1; i++) {

            sinusList.add(specificParam * Math.sin((2*Math.PI*i)/(period*varis.get("num_of_gens")) - horizonShift) +vertShift);

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
                stepList.add(multiplier*i);
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
            stocList.add(ThreadLocalRandom.current().nextDouble(mean, variance));
        }
        stocList.add(stocList.get(stocList.size()-1));
        return stocList;
    }
}

