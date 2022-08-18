package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simulation {

    

    public Collected_data simulator (Populations allPops, Variables variables, Collected_data bigdata, int runNum){

        allPops = initialisation(allPops, variables);
        bigdata = dataCollected(allPops, bigdata, variables, runNum, 0);


        HashSet<Integer> genSet = new HashSet<>();
        int multiplier = variables.getNum_of_gen()/100;
        for (int i = 1; i <= 100; i++ ) {
            genSet.add(i*multiplier);
        }



        for (int genNum = 1; genNum < variables.getNum_of_gen()+1d; genNum++) {
            
            allPops = interaction(allPops, variables);
            allPops = reprod(allPops, variables);

            if (genSet.contains(genNum)) {
                bigdata = dataCollected(allPops, bigdata, variables, runNum, genNum);
            }
        }
        
        return bigdata;



    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              BIG FUNCTIONS                                                  //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Populations initialisation(Populations allPops, Variables variables){

        HashMap<String, Symbiont> symbiontPop = OrganismFactory.CreateSymbiont("Symbiont", variables.getMax_pop_num_Symb());
        allPops.setSymbiontPop(symbiontPop);

        HashMap<String, Daphnia> daphniaPop = OrganismFactory.CreateDaphnias("Daphnia", variables.getMax_pop_num_Daph());
        allPops.setDaphniaPop(daphniaPop);

        allPops.getEnvSymbionts().putAll(symbiontPop);

        ArrayList<Symbiont> symbiontlist = new ArrayList<>(allPops.getSymbiontPop().values());
        for (Daphnia daphnia : allPops.getDaphniaPop().values()) {
            Symbiont symbiont = symbiontlist.get(0);
            daphnia.setpartner(symbiont.getName());
            symbiont.setpartner(daphnia.getName());
            allPops.getGutSymbionts().put(symbiont.getName(), symbiont);
            allPops.getEnvSymbionts().remove(symbiont.getName());
            symbiontlist.remove(0);
        }
        return allPops;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Populations reprod(Populations allPops, Variables varis) {

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

    public Populations interaction(Populations allPops, Variables varis) {

        HashMap<String, Double> virDict = cr_virDict(allPops, varis.getScarcity());

        for (Daphnia daph : allPops.getDaphniaPop().values()) {
            boolean decision = kickDecision(daph, virDict);
            Symbiont symb = allPops.getGutSymbionts().get(daph.getpartner());

            if (decision) {
                allPops = kick_and_Replace(daph, allPops);

                Symbiont newSymb = allPops.getGutSymbionts().get(daph.getpartner());
                newSymb.setFitness(1 + (varis.getVir_parS() * calcVir(newSymb, varis)));

                daph.setFitness(varis.getFitnessPenalty());
            }
            else {
                symb.setFitness(1 + (varis.getVir_parS() * virDict.get(daph.getName())));
                daph.setFitness(1 - (varis.getVir_parD() * virDict.get(daph.getName())));
            }
        }

        return allPops;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Collected_data dataCollected (Populations allPops, Collected_data bigdata, Variables varis, int runNum, int genNum) {

        double daphSlopesAvg = 0;
        double daphIntsAvg = 0;
        double symbSlopesAvg = 0;
        double symbIntsAvg = 0;

        for (Symbiont symb: allPops.getSymbiontPop().values()) {
            symbSlopesAvg = (symbSlopesAvg + symb.getGene1())/2;
            symbIntsAvg = (symbIntsAvg + symb.getGene2())/2;
        }

        for (Daphnia daph: allPops.getDaphniaPop().values()) {
            daphSlopesAvg = (daphSlopesAvg + daph.getGene1())/2;
            daphIntsAvg = (daphIntsAvg + daph.getGene2())/2;
        }

        bigdata.getGeneration().get(runNum).add(genNum);
        bigdata.getSymbInts().get(runNum).add(symbIntsAvg);
        bigdata.getDaphInts().get(runNum).add(daphIntsAvg);
        bigdata.getMutStepSize().get(runNum).add(varis.getMutStepSize());
        bigdata.getMutation_chance().get(runNum).add(varis.getMutation_chance());
        bigdata.getVir_parS().get(runNum).add(varis.getVir_parS());
        bigdata.getDaphSlopes().get(runNum).add(daphSlopesAvg);
        bigdata.getVir_parD().get(runNum).add(varis.getVir_parD());
        bigdata.getFitnessPenalty().get(runNum).add(varis.getFitnessPenalty());
        bigdata.getSymbSlopes().get(runNum).add(symbSlopesAvg);
        bigdata.getScarcity().get(runNum).add(varis.getScarcity());
        
        
        return bigdata;
                
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public HashMap<String, Daphnia> reprodDaph(Populations allPops, Variables varis) {

        Parentpicker picksLists = makeCumulFitlist(allPops, "Daph", "none");
        picksLists = chooseParent(picksLists);


        // don't forget white list probleem

        return OrganismFactory.CreateNewIndvsDaphnia("Daphnia", allPops.getDaphniaPop(), varis, varis.getMax_pop_num_Daph(), picksLists.getParentList());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public HashMap<String, Symbiont> reprodSymb(Populations allPops, Variables varis, String whichPop){

        HashMap<String, Symbiont> symbPop = allPops.getEnvSymbionts();
        int start = varis.getMax_pop_num_Daph() + 1;
        int stop = varis.getMax_pop_num_Symb();

        if (whichPop.equals("Gut")) {
            symbPop = allPops.getGutSymbionts();
            start = 1;
            stop = varis.getMax_pop_num_Daph();
        }
        
        Parentpicker picksList  = makeCumulFitlist(allPops,"Symb", whichPop);
        picksList = chooseParent(picksList);


        return OrganismFactory.CreateNewIndvsSymbiont("Symbiont", symbPop, varis, start, stop, picksList.getParentList());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              SMALL FUNCTIONS                                                //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Parentpicker makeCumulFitlist(Populations allPops, String orgtype, String gut_or_env){

        ArrayList<Organism> testpoplist = new ArrayList<>(allPops.getGutSymbionts().values());

        if (orgtype.equals("Daph")) {
            testpoplist = new ArrayList<>(allPops.getDaphniaPop().values());
         }

        if (gut_or_env.equals("Env")) {
            testpoplist = new ArrayList<>(allPops.getEnvSymbionts().values());
        }

        double sumfit = 0;
        ArrayList<Double> cumulFitList = new ArrayList<>();

        ArrayList<String> parentCumulList = new ArrayList<>();

        for(Organism org : testpoplist) {
            sumfit = sumfit + org.getFitness();
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
            int oldtargIndex = 0;

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
    public static double newGene(double parentGene, double mutChance, double mutStep) {

        double c = new Random().nextDouble();


        double mutStepSize = 0;
        if (c < mutChance) {
            double min = -mutStep;
            double range = mutStep - min;
            double scaled = new Random().nextDouble() * range;
            mutStepSize = scaled + min;

            return mutStepSize;
        }
        return parentGene + mutStepSize;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  HashSet<String> createWhitelist (HashMap<String, Daphnia> Dpop) {

        HashSet<String> whitelist = new HashSet<String>();

        for (Daphnia daphnia: Dpop.values()) {
            whitelist.add(daphnia.getOuder());
        }

        return whitelist;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Coupled createCoupling (HashMap<String, Daphnia> Dpop, HashMap<String, Symbiont> gutSymbs, HashSet<String> whitelist) {




        HashMap<String, Daphnia> coupledDaphs = new HashMap<String, Daphnia>();
        HashMap<String, Daphnia> nonCoupledDaphs = new HashMap<String, Daphnia>();

        HashMap<String, Symbiont> coupledSymbs = new HashMap<String, Symbiont>();
        HashMap<String, Symbiont> nonCoupledSymbs = new HashMap<String, Symbiont>();


        HashMap<String, ArrayList<Daphnia>> parentChildMap = new HashMap<String, ArrayList<Daphnia>>();
        HashMap<String, ArrayList<Symbiont>> hostSymbMap = new HashMap<String, ArrayList<Symbiont>>();

        for (String item : whitelist) {
            parentChildMap.put(item + "key", new ArrayList<Daphnia>());
        }

        for (String item : whitelist) {
            hostSymbMap.put(item + "key", new ArrayList<Symbiont>());
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

                toBeCoupledDaph.remove(0);
                toBeCoupledSymb.remove(0);

            }

            for (Daphnia ind : toBeCoupledDaph) {
                nonCoupledDaphs.put(ind.getName(), ind);
            }

            for (Symbiont ind : toBeCoupledSymb) {
                nonCoupledSymbs.put(ind.getName(), ind);
            }

        }

        return new Coupled(coupledDaphs, coupledSymbs, nonCoupledDaphs, nonCoupledSymbs);

    };
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Populations assignNonCoupled(Coupled resultCoupling, Populations allPops) {

        HashMap<String, Symbiont> newEnvSymbs = new HashMap<String, Symbiont>();
        newEnvSymbs.putAll(allPops.getEnvSymbionts());
        newEnvSymbs.putAll(resultCoupling.getNonCoupledSymbs());
        ArrayList<Symbiont> newEnvSymbsList = new ArrayList<>(newEnvSymbs.values());

        HashMap<String, Daphnia> newCoupledDaph = new HashMap<String, Daphnia>();
        HashMap<String, Symbiont> newCoupledSymbs = new HashMap<String, Symbiont>();

        for (Daphnia daph : resultCoupling.getNonCoupledDaphs().values()) {

            int symbInd = new Random().nextInt(newEnvSymbs.size());
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

        return allPops;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public HashMap<String, Double> cr_virDict(Populations allPops, double scarcity) {

        HashMap<String, Double> virDict = new HashMap<>();

        for (Daphnia daph : allPops.getDaphniaPop().values()) {
            String symb = daph.getpartner();
            Symbiont symby = allPops.getGutSymbionts().get(symb);
            double virulence = 1 / (1 + Math.exp(-symby.getGene1() * (scarcity - symby.getGene2())));
            virDict.put(daph.getName(), virulence);
        }
        return virDict;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean kickDecision(Daphnia daph, HashMap<String, Double> virDict) {

        double c = new Random().nextDouble(0, 1);

        double p = 1 / (1 + Math.exp(-daph.getGene1() * (virDict.get(daph.getName()) - daph.getGene2())));

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

        int newSymbInd = new Random().nextInt(0, allPops.getEnvSymbionts().size()-1);
        Collection<Symbiont> symbiontCollection = allPops.getEnvSymbionts().values();
        ArrayList<Symbiont> symbList = new ArrayList<>(symbiontCollection);

        Symbiont newSymb = symbList.get(newSymbInd);

        daph.setpartner(newSymb.getName());
        newSymb.setpartner(daph.getName());

        allPops.getEnvSymbionts().remove(newSymb.getName());
        allPops.getGutSymbionts().put(newSymb.getName(), newSymb);

        return allPops;
    }

    public Double calcVir(Symbiont symb, Variables varis) {

        return 1 / (1 + Math.exp(-symb.getGene1() * (varis.getScarcity() - symb.getGene2())));
    }

    public void toTXT (Collected_data bigData, MeanData meanie) throws IOException {

        FileWriter file = new FileWriter("testrun2.csv");

        for (int i = 0; i < meanie.getMeanDaphInts().size(); i++) {

            String dataLine = String.valueOf((bigData.getGeneration().get(1).get(i))) + ","
                    + String.valueOf(bigData.getScarcity().get(1).get(i)) + "," + String.valueOf(bigData.getVir_parD().get(1).get(i)) + ","
                    + String.valueOf(bigData.getVir_parS().get(1).get(i)) + "," + String.valueOf(meanie.getMeanDaphSlopes().get(i)) + ","
                    + String.valueOf(meanie.getMeanDaphInts().get(i)) + "," + String.valueOf(meanie.getMeanSymbSLopes().get(i)) + ","
                    + String.valueOf(meanie.getMeanSymbInts().get(i));
            file.write(dataLine + "\n");
        }
        file.close();
    }
}

