package Organism;

public class Daphnia extends Organism{

    public Daphnia(String name, double gene1, double gene2, double fitness) {
        super(name, gene1, gene2, fitness);
    }
    public Daphnia(String name, double gene1, double gene2, double fitness, String ouder) {
        super(name, gene1, gene2, fitness, ouder);
    }
    public Daphnia(String name, double gene1, double gene2, double fitness, String ouder, String partner) {
        super(name, gene1, gene2, fitness, ouder, partner);

    }

}
