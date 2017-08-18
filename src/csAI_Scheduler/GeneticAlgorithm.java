/**
 * File: GeneticAlgorithm.java
 * Description: This file is used to create a new generation of chromosomes.
 */

package csAI_Scheduler;

import java.util.Random;


public class GeneticAlgorithm {

    private Population initialPopulation; //the last generation of chromosomes

    /**
     * Constructor. We only need the last's generation's data.
     * @param initPop last generation's data
     */
    GeneticAlgorithm(Population initPop){

        this.initialPopulation = initPop;
    }

    /**
     * This will create a new generation of chromosomes, replacing the old ones.
     */
    public void generate(){

        Chromosome one, two; //temp values
        Boolean probability; // if true then we mutate
        initialPopulation.putChromosomesInOrder(); //put every chromosome of the last generation into an ascending array using the fitness value.
        for(int i = 0; i < initialPopulation.getSize(); i += 2){ //we will ignore the last Chromosome if i is odd

            //since we have a sorted population the Chromosomes with index i closer to zero are the ones with the best quality.
            //get chromosomes
            one = initialPopulation.get(i);
            two = initialPopulation.get(i+1);

            //crossover them
            one.crossover(two);
            one.calculate_fitness();
            two.calculate_fitness();

            probability = new Random().nextInt(2) == 0; //we have 0,5 probability of a generated number which belongs in [0, 2) to be zero
            if(probability){
                one.mutate();
            }
            probability = new Random().nextInt(2) == 0;
            if(probability){
                two.mutate();
            }

            //get new fitness values
            one.calculate_fitness();
            two.calculate_fitness();
            initialPopulation.putChromosomesInOrder(); //put everything in order
        }
    }


}
