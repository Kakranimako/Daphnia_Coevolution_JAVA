package Organism;



import Simulation.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class OrganismFactory {

    public HashMap<String, Daphnia> CreateDaphnias(String orgname, double resistGene, double resistGeneVar, double size){
        HashMap<String, Daphnia> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian(resistGene, resistGeneVar);
            double gene2 = 0.5;
            //new Random().nextGaussian(1, initVariance*0.1); //HARDCODE
            double fitness = 1;
            Daphnia daphnia = new Daphnia(name, gene1, gene2, fitness);
            org_pop.put(name, daphnia);
        }
        return org_pop;
    }
    public HashMap<String, Symbiont> CreateSymbiont(String orgname, double initGene1, double initVar1, double initGene2, double initVar2, double size){
        HashMap<String, Symbiont> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian(initGene1, initVar1);
            double gene2 = new Random().nextGaussian(initGene2, initVar2);
            double fitness = 1;
            Symbiont symbiont = new Symbiont(name, gene1, gene2, fitness);
            org_pop.put(name, symbiont);
        }
        return org_pop;
    }

    public  HashMap<String, Symbiont> CreateNewIndvsSymbiont(String orgname, HashMap<String, Symbiont> Symbiontpop, HashMap<String, Double> varis, double start, double size, ArrayList<String> parentList){

        HashMap<String, Symbiont> org_pop = new HashMap<>();
        Collections.shuffle(parentList);

        // probleem if (Symbiontpop.equals())
        while (start <= size) {
            String name = orgname + "_" + start;

            Symbiont parent = Symbiontpop.get(parentList.get(0));
            double gene1 = new Simulation().newGene(parent.getGene1(), varis.get("mut_chance"), varis.get("mutStepSize"));
            double gene2 = new Simulation().newGene(parent.getGene2(), varis.get("mut_chance"), varis.get("mutStepSize"));
            double fitness = 1;

            String ouder = parent.getName();
            String host = parent.getpartner();

            Symbiont newsymbiont = new Symbiont(name, gene1, gene2, fitness, ouder, host);
            org_pop.put(newsymbiont.getName(), newsymbiont);
            start = start + 1;
            parentList.remove(0);
        }
        return org_pop;
    }

    public HashMap<String, Daphnia> CreateNewIndvsDaphnia(String orgname, HashMap<String, Daphnia> Daphniapop, HashMap<String, Double> varis, double size, ArrayList<String> parentList) {
        Collections.shuffle(parentList);
        HashMap<String, Daphnia> org_pop = new HashMap<>();
        int i = 0;
        while (i < size) {
            int nameNum = i+1;
            String name = orgname + "_" + nameNum;

            Daphnia parent = Daphniapop.get(parentList.get(i));
            double gene1 = new Simulation().newGene(parent.getGene1(), varis.get("mut_chance"), varis.get("mutStepSize"));
            double gene2 = 0.5;
            //new Simulation().newGene(parent.getGene2(), varis.get("mut_chance"), 0.2*varis.get("mutStepSize")); //HARDCODE
            double fitness = 1;
            String ouder = parent.getName();
            String symb = parent.getpartner();

            Daphnia daphnia = new Daphnia(name, gene1, gene2, fitness, ouder, symb);
            org_pop.put(daphnia.getName(), daphnia);

            i += 1;

        }

        return  org_pop;
    }



}
