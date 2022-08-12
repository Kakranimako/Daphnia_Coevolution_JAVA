package Simulation;
public class Variables {

    private int scarcity;
    private int num_of_gen;
    private int max_pop_num_Daph;
    private int max_pop_num_Symb;
    private double mutation_chance;
    private double vir_parD;
    private double vir_parS;

    public Variables(int scarcity, int num_of_gen, int max_pop_num_Daph, int max_pop_num_Symb, int mutation_chance, int vir_parD, int vir_parS) {
        this.scarcity = scarcity;
        this.num_of_gen = num_of_gen;
        this.max_pop_num_Daph = max_pop_num_Daph;
        this.max_pop_num_Symb = max_pop_num_Symb;
        this.mutation_chance = mutation_chance;
        this.vir_parD = vir_parD;
        this.vir_parS = vir_parS;
    }

    public int getScarcity() {
        return scarcity;
    }

    public void setScarcity(int scarcity) {
        this.scarcity = scarcity;
    }

    public int getNum_of_gen() {
        return num_of_gen;
    }

    public void setNum_of_gen(int num_of_gen) {
        this.num_of_gen = num_of_gen;
    }

    public int getMax_pop_num_Daph() {
        return max_pop_num_Daph;
    }

    public void setMax_pop_num_Daph(int max_pop_num_Daph) {
        this.max_pop_num_Daph = max_pop_num_Daph;
    }

    public int getMax_pop_num_Symb() {
        return max_pop_num_Symb;
    }

    public void setMax_pop_num_Symb(int max_pop_num_Symb) {
        this.max_pop_num_Symb = max_pop_num_Symb;
    }

    public double getMutation_chance() {
        return mutation_chance;
    }

    public void setMutation_chance(double mutation_chance) {
        this.mutation_chance = mutation_chance;
    }

    public double getVir_parD() {
        return vir_parD;
    }

    public void setVir_parD(double vir_parD) {
        this.vir_parD = vir_parD;
    }

    public double getVir_parS() {
        return vir_parS;
    }

    public void setVir_parS(double vir_parS) {
        this.vir_parS = vir_parS;
    }

    @Override
    public String toString() {
        return "Simulation.Variables{" +
                "scarcity=" + scarcity +
                ", num_of_gen=" + num_of_gen +
                ", max_pop_num_Daph=" + max_pop_num_Daph +
                ", max_pop_num_Symb=" + max_pop_num_Symb +
                ", mutation_chance=" + mutation_chance +
                ", vir_parD=" + vir_parD +
                ", vir_parS=" + vir_parS +
                '}';
    }
}

