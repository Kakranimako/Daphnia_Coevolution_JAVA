package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;

import java.lang.reflect.Array;
import java.util.*;

public class Simulation {

    

    public Simulation (Populations allPops, Variables variables){

        
        HashMap<String, Symbiont> symbiontPop = OrganismFactory.CreateSymbiont("Symbiont", variables.getMax_pop_num_Symb());
        allPops.setSymbiontPop(symbiontPop);
        
        HashMap<String, Daphnia> daphniaPop = OrganismFactory.CreateDaphnias("Daphnia", variables.getMax_pop_num_Daph());
        allPops.setDaphniaPop(daphniaPop);

        allPops.getEnvSymbionts().putAll(symbiontPop);

        ArrayList<Symbiont> symbiontlist = new ArrayList<>(allPops.getSymbiontPop().values());
        for (Daphnia daphnia: allPops.getDaphniaPop().values()) {
            Symbiont symbiont = symbiontlist.get(0);
            daphnia.setpartner(symbiont.getName());
            symbiont.setpartner(daphnia.getName());
            allPops.getGutSymbionts().put(symbiont.getName(), symbiont);
            allPops.getEnvSymbionts().remove(symbiont.getName());
            symbiontlist.remove(0);

        }

        allPops = reprod(allPops, variables);



    }

    public HashMap<String, Daphnia> reprodDaph(Populations allPops, Variables varis) {

        Parentpicker picksLists = makeCumulFitlist(allPops, "Daph", "none");
        picksLists = chooseParent(picksLists);
        

        // don't forget white list probleem

        return OrganismFactory.CreateNewIndvsDaphnia("Daphnia", allPops.getDaphniaPop(), varis, varis.getMax_pop_num_Daph(), picksLists.getParentList());
    }
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
        

        HashMap<String, Symbiont> newSymbpop = OrganismFactory.CreateNewIndvsSymbiont("Symbiont", symbPop, varis, start, stop, picksList.getParentList());

        return newSymbpop;


    }

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

        Parentpicker picksList = new Parentpicker(cumulFitList, parentCumulList, new ArrayList<>());

        return picksList;

    }
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

    

    public static double newGene(double parentGene, double mutChance) {

        double c = new Random().nextDouble();


        double mutStepSize = 0;
        if (c < mutChance) {
            double min = -0.03;
            double max = 0.03;
            double range = max - min;
            double scaled = new Random().nextDouble() * range;
            mutStepSize = scaled + min;

            return mutStepSize;
        }
        double mutGene = parentGene + mutStepSize;
        return mutGene;
    }

    public  HashSet<String> createWhitelist (HashMap<String, Daphnia> Dpop) {

        HashSet<String> whitelist = new HashSet<String>();

        for (Daphnia daphnia: Dpop.values()) {
            whitelist.add(daphnia.getOuder());
        }

        return whitelist;
    }

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

        Coupled resultsCoupling = new Coupled(coupledDaphs, coupledSymbs, nonCoupledDaphs, nonCoupledSymbs);

        return resultsCoupling;

    };

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


}

