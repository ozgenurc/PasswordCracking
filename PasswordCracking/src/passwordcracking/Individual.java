/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordcracking;

/**
 *
 * @author ozgen
 */
// Bireyleri nüfus içinde temsil eden sınıf
public class Individual implements Comparable<Individual> {

    public String chromosome;
    int fitness;

    public Individual(String chromosome) {
        this.chromosome = chromosome;
        fitness = cal_fitness();
    }
    //copy constructor
    public Individual (Individual parent2){
        chromosome=parent2.chromosome;
        fitness=parent2.fitness;
    }

    //fitness skorunu hesaplar: target stringinden farklı karakterlerin sayısını hesaplar
    public final int cal_fitness() {
        int len = PasswordCracking.TARGET.length();
        int fitness = 0;
        for (int i = 0; i < len; i++) {
            if (chromosome.charAt(i) != PasswordCracking.TARGET.charAt(i)) {
                fitness++;
            }
        }
        return fitness;
    }

    //yeni yavrular üret
    public final Individual mate(Individual par2) {
        //yavruları tutan kromozom
        String child_chromosome = "";

        int length = chromosome.length();
        for (int i = 0; i < length; i++) {
            // random olasılık
            float p = PasswordCracking.random_num(0, 100) / 100;

            // olasılık 0.45'den küçükse, parent 1'den gen ekler
            if (p < 0.45F) {
                child_chromosome += chromosome.charAt(i);
            } // prob 0.45 ila 0.90 arasındaysa, parent 2'den gen yerleştirir
            else if (p < 0.90F) {
                child_chromosome += par2.chromosome.charAt(i);
            } // aksi takdirde çeşitliliği korumak için rastgele gen (mutasyon) ekler
            else {
                child_chromosome += PasswordCracking.mutated_genes();
            }
        }
        // yavrular için üretilen kromozomu kullanarak yeni Bireysel (yavrular) yaratır
        return new Individual(child_chromosome);
    }

    @Override
    public int compareTo(Individual o ) {
        return new Integer(this.getFitness()).compareTo(o.getFitness()); 
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

 

}
