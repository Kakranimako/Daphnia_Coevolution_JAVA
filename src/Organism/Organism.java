package Organism;

public class Organism {

    private  String partner;
    private String name;
    private double gene1;
    private double gene2;
    private double fitness;
    private String ouder;



    public Organism(String name, double gene1, double gene2, double fitness) {
        this.name = name;
        this.gene1 = gene1;
        this.gene2 = gene2;
        this.fitness = fitness;
    }
    public Organism(String name, double gene1, double gene2, double fitness, String ouder) {
        this(name, gene1, gene2,fitness);
        this.ouder = ouder;
    }

    public Organism(String name, double gene1, double gene2, double fitness, String ouder, String partner) {
        this(name, gene1, gene2,fitness, ouder);
        this.partner = partner;
    }




    @Override
    public String toString() {
        return "Organism{" +
                "partner='" + partner + '\'' +
                ", name='" + name + '\'' +
                ", gene1=" + gene1 +
                ", gene2=" + gene2 +
                ", fitness=" + fitness +
                ", ouder='" + ouder + '\'' +
                ", partner='" + partner + '\'' +
                '}';
    }



    public String getpartner() {
        return partner;
    }

    public void setpartner(String partner) {
        this.partner = partner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGene1() {
        return gene1;
    }

    public void setGene1(double gene1) {
        this.gene1 = gene1;
    }

    public double getGene2() {
        return gene2;
    }

    public void setGene2(double gene2) {
        this.gene2 = gene2;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String getOuder() {
        return ouder;
    }

    public void setOuder(String ouder) {
        this.ouder = ouder;
    }

}
