/**
 * File: SubjectFileReader.java
 * Description: This class is responsible of reading the input file witch contains
 * the details of the courses.
 */

package csAI_Scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SubjectFileReader {

	private String filename;
	private String[][] data; //data will end up here
	private static final int NUMBER_OF_COLUMNS = 4;

    /**
     * Constructor.
     * @param filename input
     */
	public SubjectFileReader(String filename){
		this.filename = filename;
	}

	/**
	 * Opens lessons.txt
	 * @return BufferedReader object
	 */
	private BufferedReader open(){
			File file = new File(filename);
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
	 * excluding comments , lines starting with #
	 * @return ArrayList<String[]>
	 */
	public ArrayList<String[]> getData(){

		BufferedReader br = open(); //file
		ArrayList<String[]> Aresult = new ArrayList<String[]>();//the result
		String line; //each line to be processed
		int checker;//checks if line contains 4 * ( | )
		try {
			while((line = br.readLine()) != null){ //get String to 'line' variable and check if null
				if(!line.startsWith("#")){
					//We want that line
					checker = line.length() - line.replace(" | ", "").length();
					if(checker==9){ // 3 * 3 ( | )
						//looks fine
						String[] temp = line.split("\\ \\|\\ ");

						Aresult.add(temp); //add to list

					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error parsing file.");//catch exception
		}
		close(br);// close file
		return Aresult;
	}

	/**
	 * Converts the ArrayList into a 2D array.
	 *
	 * ----------------------------------------------------------------------
	 * subject 1 code | subject 1 name | class (A, B or C) | Hours per week |
	 * ---------------+----------------+-------------------------------+-----
	 * subject 2 code | subject 2 name | class (A, B or C) | Hours per week |
	 * ---------------+----------------+-------------------------------+-----
	 * etc..
	 *
	 * @return String[][] with the data needed
	 */
	public String[][] read(){

		ArrayList<String[]> input = getData(); //get arraylist
		int size = input.size();
		data = new String[size][NUMBER_OF_COLUMNS];//create the final array
		for(int i = 0; i < size; i++){
			String[] insideArray = input.get(i);
			for(int j = 0; j < NUMBER_OF_COLUMNS; j++){
				data[i][j] = insideArray[j];
			}
		}
		return data;
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
	 * Returns Data without re-calculating.
	 * @return needed data.
	 */
	public String[][] returnData(){
		return data;
	}
}

