/**
 * File: TeachersFileReader.java
 * Description: This class is responsible of reading the input file witch contains
 * the details of the teachers working at this school.
 */
package csAI_Scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TeachersFileReader {

	private String filename;
	private String[][] result; //the data will end up here
	private static final int NUMBER_OF_COLUMNS = 5;

	/**
	 * Constructor.
	 * @param filename
     */
	public TeachersFileReader(String filename){
		this.filename = filename;
	}

	/**
	 * Opens teachers.txt
	 * @return BufferedReader object
	 */
	private BufferedReader open(){

			File file = new File(filename);//open file
			BufferedReader br = null;
			try{
				br = new BufferedReader(new FileReader(file));
			}catch(FileNotFoundException fnfex){
				System.err.println("Error opening file.");
			}
			return br;
	}

	/**
	 * Reads file line by line
	 * excluding comments
	 * @return ArrayList<String[]>
	 */
	private ArrayList<String[]> getData(){

		BufferedReader br = open(); //file
		ArrayList<String[]> Tresult = new ArrayList<String[]>();//the result
		String line; //each line to be processed
		int checker;//checks if line contains 3 * ( | )
		try {
			while((line = br.readLine()) != null){ //get String to 'line' variable and check if null
				if(!line.startsWith("#")){
					//We want that line
					checker = line.length() - line.replace(" | ", "").length();
					if(checker == 12){ // 3 * 4 ( | )
						//looks fine
						Tresult.add(line.split("\\ \\|\\ ")); //add to list
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error parsing file.");//catch exception
		}
		close(br);// close file
		return Tresult;
	}

	/**
	 * Converts the ArrayList into a 2D array.
	 *
	 * ---------------+----------------+-------------+----------------------------+----------------------------
	 * teacher 1 code | teacher 1 name | course code | teacher's 1 max hours/week | teacher's 1 max hours/day |
	 * ---------------+----------------+-------------+---------------------------------------------------------
	 * teacher 2 code | teacher 2 name | course code | teacher's 2 max hours/week | teacher's 2 max hours/day |
	 * ---------------+----------------+-------------+----------------------------+----------------------------
	 * etc..
	 *
	 * @return String[][] with the data needed
	 */
	public String[][] read(){

		ArrayList<String[]> input = getData(); //get arraylist
		int size = input.size();
		result = new String[size][NUMBER_OF_COLUMNS];//create the final array
		for(int i = 0; i < size; i++){
			String[] insideArray = input.get(i);
			for(int j = 0; j < NUMBER_OF_COLUMNS; j++){
				result[i][j] = insideArray[j];
			}
		}
		return result;
	}

	/**
	 * Closes input file.
	 * @param BufferedReader
	 */
	private void close(BufferedReader br){
		try {
			br.close(); //close
		} catch (IOException e) {
			System.err.println("Could not close file."); //return error
		}
	}

	/**
	 * Prints calculated array.
	 * read() MUST be called first.
	 *
	 * @throws NullPointerException
	 */
	public void printArray() throws NullPointerException{

		if(result == null){//if read() has not been called
			throw new NullPointerException();//throw this
		}
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++){
				System.out.print(result[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Returns Data without re-calculating.
	 * @return needed data.
	 */
	public String[][] returnData(){

		return result;
	}
}

