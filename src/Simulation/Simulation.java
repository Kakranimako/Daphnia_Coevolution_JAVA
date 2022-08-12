package Simulation;

import Organism.Daphnia;
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
        this.symbiontpop = reprodSymb(this.symbiontpop, this.variables);
        HashSet<String> whitelist = createWhitelist(this.daphniapop);


    }

    public HashMap<String, Symbiont> reprodSymb(HashMap<String, Symbiont> symbpop, Variables varis){



        // probleem
        ArrayList<Integer> cumulFitList = makeCumulFitlist(symbpop);
        ArrayList<Integer> symbParentIndList = chooseParent(cumulFitList);
        ArrayList<String> symbParentList = findParent(symbParentIndList);

        HashMap<String, Symbiont> newSymbpop = OrganismFactory.CreateNewIndvsSymbiont("Symbiont", symbpop, varis, varis.getMax_pop_num_Daph(), symbParentList);

        return newSymbpop;


    }

    public ArrayList<Integer> makeCumulFitlist(HashMap<String, > org){
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

        HashSet<String> whitelist = new HashSet<>(String)

        for (Daphnia daphnia: Dpop.values()) {
            whitelist.add(daphnia.getOuder());
        }


        return whitelist;
    }

    public Object[] coupleDicts createCoupling (HashMap<String, Daphnia> Dpop, HashMap<String, Symbiont> gutSymbs, HashSet whitelist) {


    };
}

