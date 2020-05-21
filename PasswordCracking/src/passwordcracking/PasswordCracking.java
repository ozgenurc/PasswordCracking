/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordcracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ozgen
 */
public class PasswordCracking {

    public static final String GENES = ("abcçdefghıijklmnoöpqrsştuüvwxyzABCÇDEFGHIİJKLMNOÖPQRSŞTUÜVWXYZ 1234567890,.-;:_!\\\"#%&/()=?@${[]}");
    public static final String TARGET = ("İşlemsel Zeka_DL_20");

    public static int random_num(int start, int end) {
        Random rand = new Random();
        int randomNum = rand.nextInt((end - start) + 1) + start;
        return randomNum;
    }

    //Mutasyon için rastgele genleri oluşturur
    public static char mutated_genes() {
        int length = GENES.length();
        int r = random_num(0, length - 1);
        return GENES.charAt(r);
    }

    //Kromozom stringini oluşturur
    public static String create_gnome() {
        int len = TARGET.length();
        String gnome = "";
        for (int i = 0; i < len; i++) {
            gnome += mutated_genes();
        }
        return gnome;
    }

    final class DefineConstants {
        public static final int POPULATION_SIZE = 100;
    }


    public static void main(String[] args) {
        // TODO code application logic here

        long beginTime = 0, endTime = 0;
        // Şu anki nesil
        int generation = 0;

        List<Individual> population = new ArrayList<>();
        boolean found = false;

        beginTime = System.currentTimeMillis();
        // İlk popülasyonu yaratır
        for (int i = 0; i < DefineConstants.POPULATION_SIZE; i++) {
            String gnome = create_gnome();
            population.add(new Individual(gnome));
        }

        while (!found) {
            // popülasyonu artan fitness skoruna göre sıralar
            Collections.sort(population);
           
            //eğer fitness score 0 ise targeti bulduk ve sonuca ulaştık
            if (population.get(0).getFitness() <= 0) {
                found = true;
                break;
            }

            // Aksi takdirde yeni nesil için yeni child oluşturulur
            ArrayList<Individual> new_generation = new ArrayList<Individual>();

            // Elitizm uyguanır, yani en uygun nüfusun% 10'u bir sonraki nesle gider
            int s = (10 * DefineConstants.POPULATION_SIZE) / 100;
            for (int i = 0; i < s; i++) {
                new_generation.add(population.get(i));
            }

            // En uygun nüfusun% 50'sinden Bireyler child üretmek için çaprazlanacak
            s = (90 * DefineConstants.POPULATION_SIZE) / 100;
            for (int i = 0; i < s; i++) {
                int length = population.size();
                int r = random_num(0, 50);
                Individual parent1 = population.get(r);
                r = random_num(0, 50);
                Individual parent2 = population.get(r);
                Individual offspring = parent1.mate(parent2);
                new_generation.add(offspring);
            }
            population = new ArrayList<Individual>(new_generation);
            System.out.print("Generation: ");
            System.out.print(generation);
            System.out.print("\t");
            System.out.print("String: ");
            System.out.print(population.get(0).chromosome);
            System.out.print("\t");
            System.out.print("Fitness: ");
            System.out.print(population.get(0).getFitness());
            System.out.print("\n");
            generation++;
        }
        endTime = System.currentTimeMillis();
        System.out.print("Generation: ");
        System.out.print(generation);
        System.out.print("\t");
        System.out.print("String: ");
        System.out.print(population.get(0).chromosome);
        System.out.print("\t");
        System.out.print("Fitness: ");
        System.out.print(population.get(0).fitness);
        System.out.print("\n");
        System.out.println("Çalışma Süresi(Milisaniye): " + ((double) (endTime - beginTime)));
    }

}
