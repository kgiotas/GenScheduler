/**
 * File: Chromosome.java
 * Description: This file represents one chromosome, used in the genetic algorithm
 *              It contains an array of Subject object plus it's fitness counter.
 */

package csAI_Scheduler;

import java.util.Comparator;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome>, Comparator<Chromosome> {

    private Subject[] sbj_arr;
    private int fitness;

    /**
     * Constructor 1.
     * @param i
     * We use i to determine if we need to create a NEW BLANK chromosome
     * and i != 0 if we intent to use setArray() later.
     * If we call calculate_fitness() when i == 0 the program WILL crash
     */
    public Chromosome(int i){

        if(i == 0){
            sbj_arr = new Subject[210];
            fitness = 0;
        }

    }

    /**
     * Constructor 2.
     * @param arr the array that we want to initialize the chromosome
     * fitness get calculated automatically
     */
    public Chromosome(Subject[] arr){

        sbj_arr = arr;
        calculate_fitness();
    }

    /**
     * Gets a specific Subject from the array
     * @param i index of Subject
     * @return Subject object
     */
    public Subject getSubject(int i){

        return sbj_arr[i];
    }

    /**
     * Changes internal array and calculates fitness
     * @param arr array
     */
    public void setArray(Subject[] arr){

        sbj_arr = arr;
        calculate_fitness();
    }

    /**
     * @return internal array
     */
    public Subject[] getArray(){

        return sbj_arr;
    }

    /**
     * @return fitness
     */
    public int getFitness(){
        return fitness;
    }

    /**
     * Performs the crossover function needed in the genetic algorithm
     * It 'cuts' in specified places in order to avoid messing with the school program
     * @param two second Chromosome object
     */
    public void crossover(Chromosome two){


        Subject[] tempTwo = two.getArray();
        Random random = new Random();
        int cut = random.nextInt(5); //0, 34
        if(cut == 0){
            cut = 35;
        }else if(cut == 1){
            cut = 70;
        }else if(cut == 2){
            cut = 105;
        }
        else if(cut == 3) {
            cut = 140;
        }
        else if(cut == 4){
            cut = 175;
        }

        for(int i = 0; i < cut; i++){
            sbj_arr[i] = tempTwo[i];
        }
        //one is Done!
        for(int i = 34; i >= cut; i--){
            tempTwo[i] = sbj_arr[i];
        }
        //two is done
        //Set them now
        two.setArray(tempTwo);
    }

    /**
     * Calculates the Chromosome's fitness value.
     * If any error is found the fitness value is getting bigger
     * The smaller the fitness value is, we have a better quality Chromosome object
     */
    public void calculate_fitness(){

        fitness = 0;
        int hour_counter1 = 0; int hour_counter3 = 1; int hour_counter5 = 0;// positions in array

        //check for consecutive hours of the same lesson
        for (int room = 0; room < 6; room++)
        {
          for (int day = 0; day < 5 && hour_counter3 < 210; day++)
          {
              if (sbj_arr[hour_counter3] != null && sbj_arr[hour_counter3+1] != null)
                  if (sbj_arr[hour_counter3].getCourseCode().equals(sbj_arr[hour_counter3+1].getCourseCode()))
                      fitness+=5;
              hour_counter3++;
          }
        }

        //check if there are empty classes during the day
        for (int room = 0; room < 6; room++)
        {
            for (int day = 0; day < 5;day++)
            {
                for (int hour = 0; hour < 6; hour++)
                {
                    if (sbj_arr[hour_counter1] == null)
                        fitness+=5;
                    hour_counter1++;
                }
                hour_counter1++;
            }
        }

        //check if there is the same teacher same hour in more than one classes
        for (int room = 0; room < 6; room++)
        {
            for (int hour = 0; hour < 7 && hour_counter5 < 35; hour++)
            {
                if (sbj_arr[hour_counter5] != null && sbj_arr[hour_counter5+35] != null)
                    if (sbj_arr[hour_counter5].getTeacher().getName().equals(sbj_arr[hour_counter5+35].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5] != null && sbj_arr[hour_counter5+70] != null)
                    if (sbj_arr[hour_counter5].getTeacher().getName().equals(sbj_arr[hour_counter5+70].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5] != null && sbj_arr[hour_counter5+105] != null)
                    if (sbj_arr[hour_counter5].getTeacher().getName().equals(sbj_arr[hour_counter5+105].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5] != null && sbj_arr[hour_counter5+140] != null)
                    if (sbj_arr[hour_counter5].getTeacher().getName().equals(sbj_arr[hour_counter5+140].getTeacher().getName()))
                        fitness +=150;


                if (sbj_arr[hour_counter5] != null && sbj_arr[hour_counter5+175] != null)
                    if (sbj_arr[hour_counter5].getTeacher().getName().equals(sbj_arr[hour_counter5+175].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+35] != null && sbj_arr[hour_counter5+70] != null)
                    if (sbj_arr[hour_counter5+35].getTeacher().getName().equals(sbj_arr[hour_counter5+70].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+35] != null && sbj_arr[hour_counter5+105] != null)
                    if (sbj_arr[hour_counter5+35].getTeacher().getName().equals(sbj_arr[hour_counter5+105].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+35] != null && sbj_arr[hour_counter5+140] != null)
                    if (sbj_arr[hour_counter5+35].getTeacher().getName().equals(sbj_arr[hour_counter5+140].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+35] != null && sbj_arr[hour_counter5+175] != null)
                    if (sbj_arr[hour_counter5+35].getTeacher().getName().equals(sbj_arr[hour_counter5+175].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+70] != null && sbj_arr[hour_counter5+105] != null)
                    if (sbj_arr[hour_counter5+70].getTeacher().getName().equals(sbj_arr[hour_counter5+105].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+70] != null && sbj_arr[hour_counter5+140] != null)
                    if (sbj_arr[hour_counter5+70].getTeacher().getName().equals(sbj_arr[hour_counter5+140].getTeacher().getName()))
                        fitness +=150;


                if (sbj_arr[hour_counter5+70] != null && sbj_arr[hour_counter5+175] != null)
                    if (sbj_arr[hour_counter5+70].getTeacher().getName().equals(sbj_arr[hour_counter5+175].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+105] != null && sbj_arr[hour_counter5+140] != null)
                    if (sbj_arr[hour_counter5+105].getTeacher().getName().equals(sbj_arr[hour_counter5+140].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+105] != null && sbj_arr[hour_counter5+175] != null)
                    if (sbj_arr[hour_counter5+105].getTeacher().getName().equals(sbj_arr[hour_counter5+175].getTeacher().getName()))
                        fitness +=150;

                if (sbj_arr[hour_counter5+140] != null && sbj_arr[hour_counter5+175] != null)
                    if (sbj_arr[hour_counter5+140].getTeacher().getName().equals(sbj_arr[hour_counter5+175].getTeacher().getName()))
                        fitness +=150;

                hour_counter5++;
            }

        }
    }

    /**
     * Performs the mutation procedure needed in the genetic algorithm
     * If a mutation is about to happen, this happens in a specific area,
     * more precisely in a specific class (A1, A2, B1, B2, ...) in order
     * to minimize errors.
     */
    public void mutate(){

        //area in witch the mutation will be performed.
        int min = 0;
        int max = 0;

        Random random = new Random();//init random object
        int num = random.nextInt(210); //0, 34

        //get the exact area
        if(num >= 0 && num <= 34){//A1
            min = 0;
            max = 34;
        }else if(num >= 35 && num <= 69){//A2
            min = 35;
            max = 69;
        }else if(num >= 70 && num <= 104){//B1
            min = 70;
            max = 104;
        }else if(num >= 105 && num <= 139){//B2
            min = 105;
            max = 139;
        }else if(num >= 140 && num <= 174){//C1
            min = 140;
            max = 174;
        }else if(num >= 175 && num <= 209){//C2
            min = 175;
            max = 209;
        }

        int swap = min + (int)(Math.random() * ((max - min) + 1)); //generate another number to swap subjects between classes
        Subject temp = sbj_arr[num];
        sbj_arr[num] = sbj_arr[swap];
        sbj_arr[swap] = temp;
    }

    //Compare functions
    public int compareTo(Chromosome o) {

        Integer fitA = new Integer(fitness);
        Integer fitB = new Integer(o.fitness);

        return fitA.compareTo(fitB);
    }

    @Override
    public int compare(Chromosome o1, Chromosome o2) {

        Integer fitA = new Integer(o1.fitness);
        Integer fitB = new Integer(o2.fitness);

        return Integer.compare(fitA, fitB);
    }
}
