package Organism;



import Simulation.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class OrganismFactory {

    public HashMap<String, Daphnia> CreateDaphnias(String orgname, double initMeanGene1, double initVariance, double initMeanGene2, double initMeanGene3, double size){
        HashMap<String, Daphnia> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian(initMeanGene1, initVariance);
            double gene2 = new Random().nextGaussian(initMeanGene2, initVariance*0.1);
            double gene3 = new Random().nextGaussian(initMeanGene3, initVariance*0.1); //change
            double fitness = 1;
            Daphnia daphnia = new Daphnia(name, gene1, gene2, gene3, fitness);
            org_pop.put(name, daphnia);
        }
        return org_pop;
    }
    public HashMap<String, Symbiont> CreateSymbiont(String orgname, double initMeanGene1, double initVariance, double initMeanGene2, double initMeanGene3, double size){
        HashMap<String, Symbiont> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian(initMeanGene1, initVariance);
            double gene2 = new Random().nextGaussian(initMeanGene2, initVariance*0.1); //change
            double gene3 = new Random().nextGaussian(initMeanGene3, initVariance*0.1);
            double fitness = 1;
            Symbiont symbiont = new Symbiont(name, gene1, gene2, gene3, fitness);
            org_pop.put(name, symbiont);
        }
        return org_pop;
    }

    public  HashMap<String, Symbiont> CreateNewIndvsSymbiont(String orgname, HashMap<String, Symbiont> Symbiontpop, HashMap<String, Double> varis, double size, ArrayList<String> parentList){

        HashMap<String, Symbiont> org_pop = new HashMap<>();
        Collections.shuffle(parentList);


       for (int i = 1; i < size+1; i++) {
            String name = orgname + "_" + i;

            Symbiont parent = Symbiontpop.get(parentList.get(0));
            double gene1 = new Simulation().newGene(parent.getGene1(), varis.get("mut_chance"), varis.get("mutStepSize"));
            double gene2 = new Simulation().newGene(parent.getGene2(), varis.get("mut_chance"), 0.1*varis.get("mutStepSize"));
            double gene3 = new Simulation().newGene(parent.getGene3(), varis.get("mut_chance"), 0.1*varis.get("mutStepSize"));//change
            double fitness = 1;

            Symbiont newsymbiont = new Symbiont(name, gene1, gene2, gene3, fitness);
            org_pop.put(newsymbiont.getName(), newsymbiont);
            parentList.remove(0);
        }
        return org_pop;
    }

    public HashMap<String, Daphnia> CreateNewIndvsDaphnia(String orgname, HashMap<String, Daphnia> Daphniapop, HashMap<String, Double> varis, double size, ArrayList<String> parentList) {
        Collections.shuffle(parentList);
        HashMap<String, Daphnia> org_pop = new HashMap<>();

        for (int i = 1; i < size + 1; i++ ) {

            String name = orgname + "_" + i;

            Daphnia parent = Daphniapop.get(parentList.get(i-1));
            double gene1 = new Simulation().newGene(parent.getGene1(), varis.get("mut_chance"), varis.get("mutStepSize"));
            double gene2 = new Simulation().newGene(parent.getGene2(), varis.get("mut_chance"), 0.1*varis.get("mutStepSize")); //change
            double gene3 = new Simulation().newGene(parent.getGene3(), varis.get("mut_chance"), 0.1*varis.get("mutStepSize"));//change
            double fitness = 1;

            Daphnia daphnia = new Daphnia(name, gene1, gene2, gene3, fitness);
            org_pop.put(daphnia.getName(), daphnia);

        }

        return  org_pop;
    }



}
