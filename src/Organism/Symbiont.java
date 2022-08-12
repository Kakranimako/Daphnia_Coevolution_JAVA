package Organism;

public class Symbiont extends Organism{
    private String partner;
    public Symbiont(String name, double gene1, double gene2, double fitness) {
        super(name, gene1, gene2, fitness);
    }
    public Symbiont(String name, double gene1, double gene2, double fitness, String ouder) {
        super(name, gene1, gene2, fitness, ouder);
    }
    public Symbiont(String name, double gene1, double gene2, double fitness, String ouder, String partner) {
        super(name, gene1, gene2, fitness, ouder, partner);

    }

    public String getpartner() {
        return partner;
    }

    public void setpartner(String partner) {
        this.partner = partner;
    }
}
