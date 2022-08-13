package Organism;


import Simulation.Variables;
import Simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class OrganismFactory {

    public static HashMap<String, Daphnia> CreateDaphnias(String orgname, int size){
        HashMap<String, Daphnia> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian()*0.5;
            double gene2 = new Random().nextGaussian()*0.5+0.5;
            double fitness = 1;
            Daphnia daphnia = new Daphnia(name, gene1, gene2, fitness);
            org_pop.put(name, daphnia);
        }
        return org_pop;
    }
    public static HashMap<String, Symbiont> CreateSymbiont(String orgname, int size){
        HashMap<String, Symbiont> org_pop = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            String name = orgname + "_" + i;
            double gene1 = new Random().nextGaussian()*0.5;
            double gene2 = new Random().nextGaussian()*0.5+0.5;
            double fitness = 1;
            Symbiont symbiont = new Symbiont(name, gene1, gene2, fitness);
            org_pop.put(name, symbiont);
        }
        return org_pop;
    }

    public static HashMap<String, Symbiont> CreateNewIndvsSymbiont(String orgname, HashMap<String, Symbiont> Symbiontpop, Variables varis, int start, int size, ArrayList<String> parentList){

        HashMap<String, Symbiont> org_pop = new HashMap<>();

        // probleem if (Symbiontpop.equals())
        while (start <= size) {
            String name = orgname + " " + start;

            Symbiont parent = Symbiontpop.get(parentList.get(start));
            double gene1 = Simulation.newGene(parent.getGene1(), varis.getMutation_chance());
            double gene2 = Simulation.newGene(parent.getGene2(), varis.getMutation_chance());
            double fitness = 1;
            String ouder = parent.getName();
            String host = parent.getpartner();

            Symbiont symbiont = new Symbiont(name, gene1, gene2, fitness, ouder, host);
            org_pop.put(symbiont.getName(), symbiont);
            start = start + 1;
        }
        return org_pop;
    }

    public static HashMap<String, Daphnia> CreateNewIndvsDaphnia(String orgname, HashMap<String, Daphnia> Daphniapop, Variables varis, int size, ArrayList<String> parentList) {

        HashMap<String, Daphnia> org_pop = new HashMap<>();

        for (int i  = 1; i <= size; i ++) {
            String name = orgname + " " + i;

            Daphnia parent = Daphniapop.get(parentList.get(i));
            double gene1 = Simulation.newGene(parent.getGene1(), varis.getMutation_chance());
            double gene2 = Simulation.newGene(parent.getGene2(), varis.getMutation_chance());
            double fitness = 1;
            String ouder = parent.getName();
            String symb = parent.getpartner();

            Daphnia daphnia = new Daphnia(name, gene1, gene2, fitness, ouder, symb);
            org_pop.put(daphnia.getName(), daphnia);


        }

        return  org_pop;
    }



}
