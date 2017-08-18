/**
 * File: Population.java
 * Description: This file is used to represent a population of chromosomes (or a generation).
 */

package csAI_Scheduler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class Population {

    ArrayList<Chromosome> pop;
    Subject[] tempSubject;
    Chromosome tempChromosome;
    private int number;

    /**
     * Contructor
     * @param number
     * @param subjectData
     */
    public Population(int number, Subject[] subjectData) {

        this.number = number;
        this.tempSubject = subjectData;
        pop = new ArrayList<Chromosome>(number);
        for(int i = 0; i < number; i++){
            tempChromosome = new Chromosome(1);//create an empty chromosome
            tempChromosome.setArray(generateRandomSchedule(subjectData));//get random data and put in the chromosome
            pop.add(tempChromosome);//add it to the population
        }
        putChromosomesInOrder(); //put chromosomes in order based on their fitness value.
    }

    /**
     * Gets population size
     * @return int size
     */
    public int getSize(){
        return number;
    }

    /**
     * Gets specific chromosome from population based on index number pos
     * @param pos index number
     * @return Chromosome object
     */
    public Chromosome get(int pos){
        return pop.get(pos);
    }

    /**
     * Puts chromosomes in an ascending order
     */
    public void putChromosomesInOrder(){

        Collections.sort(pop);
    }

    /**
     * Returns a random schedule for every class
     * @param data subject data
     * @return Subject[] array
     */
    private Subject[] generateRandomSchedule(Subject[] data){

        //get random schedule for each class ...
        Subject[] A1 = genereteRandomScheduleForClass("A", data);
        Subject[] A2 = genereteRandomScheduleForClass("A", data);
        Subject[] B1 = genereteRandomScheduleForClass("B", data);
        Subject[] B2 = genereteRandomScheduleForClass("B", data);
        Subject[] C1 = genereteRandomScheduleForClass("C", data);
        Subject[] C2 = genereteRandomScheduleForClass("C", data);

        // ... and then combine them in a single array
        Subject[] all = new Subject[210];
        for(int i = 0; i < A1.length; i++){
            all[i] = A1[i];
        }
        for(int i = 0; i < A2.length; i++){
            all[35 + i] = A2[i];
        }
        for(int i = 0; i < B1.length; i++){
            all[70 + i] = B1[i];
        }
        for(int i = 0; i < B2.length; i++){
            all[105 + i] = B2[i];
        }
        for(int i = 0; i < C1.length; i++){
            all[140 + i] = C1[i];
        }
        for(int i = 0; i < C2.length; i++){
            all[175 + i] = C2[i];
        }
        return all;
    }

    /**
     * Returns Subject data for specific class
     * @param room
     * @param arr all Subjects from input
     * @return Subject array for a specific class
     */
    public Subject[] getAllSubjectsFromClass(String room, Subject[] arr){

        Subject[] result;
        int counter = 0;

        //get how many subjects for a specific class we've got.
        for(int i = 0; i < arr.length; i++){
            if(arr[i].getRoom().equals(room)){
               for(int j = 0; j < arr[i].getHours_per_week(); j++){
                   counter++;
               }
            }
        }
        result = new Subject[counter];//create the final array
        int secondCounter = 0;
        //put Subjects in the final array
        for(int i = 0; i < arr.length; i++) {
            if (arr[i].getRoom().equals(room)) {
                for (int j = 0; j < arr[i].getHours_per_week(); j++) {
                    result[secondCounter] = arr[i];
                    secondCounter++;
                }
            }
        }
        return result;
    }

    /**
     * Generates random schedule for a specific class
     * @param room A1, A2, B1, B2...
     * @param arr input array
     * @return Subject[] array
     */
    private Subject[] genereteRandomScheduleForClass(String room, Subject[] arr){

        Subject[] data = getAllSubjectsFromClass(room, arr);//get data for this specific class.
        Subject[] result = new Subject[data.length];//data will end up here

        int[] randomArray = createRandomIntegerArray(0, 29);

        for(int i = 0; i < 30; i++){

                result[i] = data[randomArray[i]];

        }
        return result;
    }

    /**
     * Gets a random integer array which contains number from [min, max]
     * @param min minimum value
     * @param max maximum value
     * @return array with NO duplicates
     */
    private int[] createRandomIntegerArray(int min, int max){

        int index = 0;
        int[] result = new int[max - min + 1];
        //populate array with -1
        for(int i = 0; i < result.length; i++){
            result[i] = -1;
        }
        int random = min + (int) (Math.random() * ((max - min) + 1));//get the random number
        for(int i = 0; i < result.length; i++){
            while(contains(result, random)){//if it exists in array
                random = min + (int) (Math.random() * ((max - min) + 1));//generate another one
            }
            result[index] = random;//save it
            index++;
        }
        return result;
    }

    /**
     * Checks if array contains number
     * @param array
     * @param number the integer which we want to check
     * @return true if yes, else false
     */
    private Boolean contains(int[] array, int number){

        Boolean result = false;
        for(int i = 0; i < array.length; i++){
            if(array[i] == number){
                result = true;
            }
        }
        return result;
    }

    /**
     * initialize max_width variable for print method
     * @return size
     */
    private int initialize_width()
    {
        int max_length_teacher = 0; int max_length_subject = 0;
        Subject[] _schedule = pop.get(0).getArray();
        for (int i = 0; i < _schedule.length; i++)
        {
            if (_schedule[i] != null) {
                if (_schedule[i].getCourse_name().length() >= max_length_subject)
                    max_length_subject = _schedule[i].getCourse_name().length();
                if (_schedule[i].getTeacher().getName().length() >= max_length_teacher)
                    max_length_teacher = _schedule[i].getTeacher().getName().length();
            }
        }

        return max_length_subject + max_length_teacher + 4;
    }

    /**
     * Writes final schedule to output file
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void output(String outputFilename) throws FileNotFoundException, UnsupportedEncodingException
    {
        Subject[] _schedule = pop.get(0).getArray();
        int max_width = initialize_width();//width of every day in schedule
        String s; // string to print
        PrintWriter output = new PrintWriter(outputFilename, "UTF-8"); // open output file to write schedule
        int class_counter = 0; // count the classes (A1,A2...)
        int hour_counter = 0; // count the daily hours
        for (int room = 0; room < 6; room++) // print each class name
        {

            output.println("+----+");
            if (class_counter == 0){
                output.println("| A1 |");
                output.println("+----+");
            }
            if (class_counter == 1){

                output.println("| A2 |");

                output.println("+----+");
            }
            if (class_counter == 2){

                output.println("| B1 |");

                output.println("+----+");
            }
            if (class_counter == 3){

                output.println("| B2 |");

                output.println("+----+");
            }
            if (class_counter == 4){

                output.println("| C1 |");

                output.println("+----+");
            }
            if (class_counter == 5){

                output.println("| C2 |");

                output.println("+----+");
            }
            class_counter++;

            for (int day = 0; day < 5; day++) // print the days next to each other
            {
                switch (day) {

                    case 0:

                        s = "  Monday";
                        for (int i = 0; i < s.length(); i++) {

                            output.print(s.charAt(i));
                        }
                        for (int i = s.length(); i < max_width; i++) {

                            output.print(" ");
                        }

                        output.print("|");

                        break;
                    case 1:

                        s = "  Tuesday";
                        for (int i = 0; i < s.length(); i++) {

                            output.print(s.charAt(i));
                        }
                        for (int i = s.length(); i < max_width; i++) {

                            output.print(" ");
                        }

                        output.print("|");

                        break;
                    case 2:

                        s = "  Wednesday";
                        for (int i = 0; i < s.length(); i++) {

                            output.print(s.charAt(i));
                        }
                        for (int i = s.length(); i < max_width; i++) {

                            output.print(" ");
                        }

                        output.print("|");

                        break;
                    case 3:

                        s = "  Thursday";
                        for (int i = 0; i < s.length(); i++) {

                            output.print(s.charAt(i));
                        }
                        for (int i = s.length(); i < max_width; i++) {

                            output.print(" ");
                        }

                        output.print("|");

                        break;
                    case 4:

                        s = "  Friday";
                        for (int i = 0; i < s.length(); i++) {

                            output.print(s.charAt(i));
                        }
                        for (int i = s.length(); i < max_width; i++) {

                            output.print(" ");
                        }

                        output.println();
                        break;
                }
            }

            for (int yaw = 0; yaw < 5; yaw++) // underline days
            {
                for (int i = 0; i < max_width; i++)
                {

                    output.print("-");
                }
                if (yaw != 4) {

                    output.print("+");
                }
                else {

                    output.print("-");
                }
            }


            output.println();

            for (int hour=0; hour < 7 && hour_counter < 210; hour++)// print the hours of the day with lessons and teachers
            {
                if (_schedule[hour_counter] != null) s = hour + 1 + "." + _schedule[hour_counter + 0].getCourse_name() + "(" + _schedule[hour_counter + 0].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){

                    output.print(s.charAt(i));
                }
                for (int i = s.length(); i < max_width; i++){

                    output.print(" ");
                }

                output.print("|");

                if (_schedule[hour_counter+7] != null) s = hour + 1 + "." + _schedule[hour_counter + 7].getCourse_name() + "(" + _schedule[hour_counter + 7].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){

                    output.print(s.charAt(i));
                }
                for (int i = s.length(); i < max_width; i++){

                    output.print(" ");
                }

                output.print("|");

                if (_schedule[hour_counter+14] != null)	s = hour + 1 + "." + _schedule[hour_counter + 14].getCourse_name() + "(" + _schedule[hour_counter + 14].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){

                    output.print(s.charAt(i));
                }
                for (int i = s.length(); i < max_width; i++){

                    output.print(" ");
                }

                output.print("|");


                if (_schedule[hour_counter+21] != null)	s = hour + 1 + "." + _schedule[hour_counter + 21].getCourse_name() + "(" + _schedule[hour_counter + 21].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){

                    output.print(s.charAt(i));
                }
                for (int i = s.length(); i < max_width; i++){

                    output.print(" ");
                }

                output.print("|");

                if (_schedule[hour_counter+28] != null)	s = hour + 1 + "." + _schedule[hour_counter + 28].getCourse_name() + "(" + _schedule[hour_counter + 28].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){

                    output.print(s.charAt(i));
                }
                for (int i = s.length(); i < max_width; i++){

                    output.print(" ");
                }


                hour_counter++; // increase hours

                output.println();
            }
            hour_counter+=28;//change day

            output.println();
        }
        output.close(); // close output file

    }

    /**
     * print weekly schedule
     */
    public void print()
    {
        Subject[] _schedule = pop.get(0).getArray();
        int max_width = initialize_width();//width of every day in schedule
        String s; // string to print
        int class_counter = 0; // count the classes (A1,A2...)
        int hour_counter = 0; // count the daily hours
        for (int room = 0; room < 6; room++) // print each class name
        {
            System.out.println("+----+");
            if (class_counter == 0){
                System.out.println("| A1 |");
                System.out.println("+----+");
            }
            if (class_counter == 1){
                System.out.println("| A2 |");
                System.out.println("+----+");
            }
            if (class_counter == 2){
                System.out.println("| B1 |");
                System.out.println("+----+");
            }
            if (class_counter == 3){
                System.out.println("| B2 |");
                System.out.println("+----+");
            }
            if (class_counter == 4){
                System.out.println("| C1 |");
                System.out.println("+----+");
            }
            if (class_counter == 5){
                System.out.println("| C2 |");
                System.out.println("+----+");
            }
            class_counter++;
            for (int day = 0; day < 5; day++) // print the days next to each other
            {
                switch (day) {
                    case 0:
                        s = "  Monday";
                        for (int i = 0; i < s.length(); i++) {
                            System.out.print(s.charAt(i));

                        }
                        for (int i = s.length(); i < max_width; i++) {
                            System.out.print(" ");

                        }
                        System.out.print("|");
                        break;
                    case 1:
                        s = "  Tuesday";
                        for (int i = 0; i < s.length(); i++) {
                            System.out.print(s.charAt(i));

                        }
                        for (int i = s.length(); i < max_width; i++) {
                            System.out.print(" ");

                        }
                        System.out.print("|");
                        break;
                    case 2:
                        s = "  Wednesday";
                        for (int i = 0; i < s.length(); i++) {
                            System.out.print(s.charAt(i));

                        }
                        for (int i = s.length(); i < max_width; i++) {
                            System.out.print(" ");

                        }
                        System.out.print("|");
                        break;
                    case 3:

                        s = "  Thursday";
                        for (int i = 0; i < s.length(); i++) {
                            System.out.print(s.charAt(i));

                        }
                        for (int i = s.length(); i < max_width; i++) {
                            System.out.print(" ");

                        }
                        System.out.print("|");
                        break;
                    case 4:

                        s = "  Friday";
                        for (int i = 0; i < s.length(); i++) {
                            System.out.print(s.charAt(i));

                        }
                        for (int i = s.length(); i < max_width; i++) {
                            System.out.print(" ");

                        }
                        System.out.println();
                        break;
                }
            }

            for (int yaw = 0; yaw < 5; yaw++) // underline days
            {
                for (int i = 0; i < max_width; i++)
                {
                    System.out.print("-");
                }
                if (yaw != 4) {
                    System.out.print("+");
                }
                else {
                    System.out.print("-");
                }
            }

            System.out.println();
            for (int hour=0; hour < 7 && hour_counter < 210; hour++)// print the hours of the day with lessons and teachers
            {
                if (_schedule[hour_counter] != null) s = hour + 1 + "." + _schedule[hour_counter + 0].getCourse_name() + "(" + _schedule[hour_counter + 0].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){
                    System.out.print(s.charAt(i));

                }
                for (int i = s.length(); i < max_width; i++){
                    System.out.print(" ");

                }
                System.out.print("|");
                if (_schedule[hour_counter+7] != null) s = hour + 1 + "." + _schedule[hour_counter + 7].getCourse_name() + "(" + _schedule[hour_counter + 7].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){
                    System.out.print(s.charAt(i));

                }
                for (int i = s.length(); i < max_width; i++){
                    System.out.print(" ");

                }
                System.out.print("|");
                if (_schedule[hour_counter+14] != null)	s = hour + 1 + "." + _schedule[hour_counter + 14].getCourse_name() + "(" + _schedule[hour_counter + 14].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){
                    System.out.print(s.charAt(i));

                }
                for (int i = s.length(); i < max_width; i++){
                    System.out.print(" ");

                }
                System.out.print("|");
                if (_schedule[hour_counter+21] != null)	s = hour + 1 + "." + _schedule[hour_counter + 21].getCourse_name() + "(" + _schedule[hour_counter + 21].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){
                    System.out.print(s.charAt(i));

                }
                for (int i = s.length(); i < max_width; i++){
                    System.out.print(" ");

                }
                System.out.print("|");
                if (_schedule[hour_counter+28] != null)	s = hour + 1 + "." + _schedule[hour_counter + 28].getCourse_name() + "(" + _schedule[hour_counter + 28].getTeacher().getName() + ")";
                else s = hour + 1 + "." + "Free";
                for (int i = 0; i < s.length(); i++){
                    System.out.print(s.charAt(i));

                }
                for (int i = s.length(); i < max_width; i++){
                    System.out.print(" ");

                }
                hour_counter++; // increase hours
                System.out.println();

            }
            hour_counter+=28;//change day
            System.out.println();
        }
    }
}
