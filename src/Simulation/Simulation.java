package Simulation;

import Organism.Daphnia;
import Organism.Organism;
import Organism.OrganismFactory;
import Organism.Symbiont;

import java.lang.reflect.Array;
import java.util.*;

public class Simulation {

    private HashMap<String, Daphnia> daphniapop;
    private HashMap<String, Symbiont> symbiontpop;
    private HashMap<String, Symbiont> gut_Symbionts;
    private HashMap<String, Symbiont> env_Symbionts;
    private Variables variables;

    public Simulation(Variables variables){
        this.variables = variables;
        this.symbiontpop = OrganismFactory.CreateSymbiont("Symbiont", this.variables.getMax_pop_num_Symb());
        this.daphniapop = OrganismFactory.CreateDaphnias("Daphnia", this.variables.getMax_pop_num_Symb());
        this.env_Symbionts.putAll(this.symbiontpop);

        Stack<Symbiont> symbiontlist = (Stack<Symbiont>) this.symbiontpop.values();
        for (Daphnia daphnia: this.daphniapop.values()) {
            Symbiont symbiont = symbiontlist.pop();
            daphnia.setpartner(symbiont.getName());
            symbiont.setpartner(daphnia.getName());
            this.gut_Symbionts.put(symbiont.getName(), symbiont);
            this.env_Symbionts.remove(symbiont.getName());

        }

    }

    public HashMap<String, Daphnia> reprodDaph(HashMap<String, Daphnia> daphniapop, Variables varis) {

        ArrayList<Integer> cumulFitList = makeCumulFitlist(daphniapop);
        ArrayList<Integer> daphParentIndList = chooseParent(cumulFitList);
        ArrayList<String> daphParentList = findParent(daphParentIndList);

        HashMap<String, Daphnia> newDaphpop = OrganismFactory.CreateNewIndvsDaphnia("Daphnia", daphniapop, varis, varis.getMax_pop_num_Daph(), daphParentList);
        // dont forget white list probleem

        return newDaphpop;
    }
    public void reprod() {
        this.daphniapop = reprodDaph(this.daphniapop, this.variables);
        this.gut_Symbionts = reprodSymb(this.gut_Symbionts, this.variables);
        this.env_Symbionts = reprodSymb(this.env_Symbionts, this.variables);
        HashSet<String> whitelist = createWhitelist(this.daphniapop);
        Coupled resultsCoupling = createCoupling(this.daphniapop,this.gut_Symbionts,whitelist);





    }

    public HashMap<String, Symbiont> reprodSymb(HashMap<String, Symbiont> symbpop, Variables varis){



        // probleem
        ArrayList<Integer> cumulFitList = makeCumulFitlist(symbpop);
        ArrayList<Integer> symbParentIndList = chooseParent(cumulFitList);
        ArrayList<String> symbParentList = findParent(symbParentIndList);

        HashMap<String, Symbiont> newSymbpop = OrganismFactory.CreateNewIndvsSymbiont("Symbiont", symbpop, varis, varis.getMax_pop_num_Daph(), symbParentList);

        return newSymbpop;


    }

    public ArrayList<Integer> makeCumulFitlist(HashMap<String, Daphnia> org){
        return null;

    }
    public ArrayList<Integer> chooseParent(ArrayList<Integer> cumulFitlist){
        return null;
    }

    public ArrayList<String> findParent(ArrayList<Integer> parentIndList) {
        return null;

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
            hostSymbMap.get(ind.getpartner() + "key").add(ind);
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

}

