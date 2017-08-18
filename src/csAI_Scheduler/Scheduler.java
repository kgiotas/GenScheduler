/**
 * File: Scheduler.java
 * Description: This file runs the program.
 */

package csAI_Scheduler;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Scheduler {

    static String _subjectFilename, _teacherFilename, _outputFilename;
    static int _numberOfGenerations = 1000; //if not given should be 100
    static int _numberOfChromosomes = 40;
    static Boolean inputIsGood = false;
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        //command is: scheduler -in teachersFile.txt subjectsFile.txt -out output.txt -g 1000
        if(args[0].equals("-in")){
            //args[2] = input teachers
            //args[3] = input subjects
            _teacherFilename = args[1];
            _subjectFilename = args[2];

            if(args[3].equals("-out")){
                //args[5] == output file
                _outputFilename = args[4];
                inputIsGood = true;
                if(args[5].equals("-g")) {//generations (not mandatory)
                    //args[6] == generations //should be above 1000
                    _numberOfGenerations = Integer.parseInt(args[6]);
                    if(args[7].equals("-ip")){
                        //args[7] == number of chromosomes in the initial population (Default: 40)
                        _numberOfChromosomes = Integer.parseInt(args[8]);
                    }
                }
            }
        }

        if(inputIsGood){

            //get file data
            DataReader dr = new DataReader(_teacherFilename, _subjectFilename);

            //check for more hours than we have
            if (dr.checkHours()) System.err.println("There are more than 36 hours per week");

            //we've got the data. Let's get the population now.
            Population population = new Population(_numberOfChromosomes, dr.getSubjectData());

            //initiate the GeneticAlgorithm object
            GeneticAlgorithm ga = new GeneticAlgorithm(population);

            //create generations
            for(int i = 0; i < _numberOfGenerations; i++){
                ga.generate();
            }
            population.print(); //print final schedule
            population.output(_outputFilename);//save output to file
        }
    }
}
