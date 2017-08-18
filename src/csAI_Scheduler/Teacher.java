/**
 * File: Teacher.java
 * Description: This file represents a teacher in the Schedule.
 */

package csAI_Scheduler;

import java.util.ArrayList;

public class Teacher {

	private String id;
	private String name;
	private ArrayList<String> courseCode;
	private int maxHoursPerWeek;
	private int maxHoursPerDay;

    /**
     * Constructor.
     * @param id
     * @param name
     * @param lessons
     * @param maxHoursPerWeek
     * @param maxHoursPerDay
     */
	public Teacher(String id, String name, ArrayList<String> lessons, int maxHoursPerWeek, int maxHoursPerDay) {
		super();
		this.id = id;
		this.name = name;
		this.courseCode = lessons;
		this.maxHoursPerWeek = maxHoursPerWeek;
		this.maxHoursPerDay = maxHoursPerDay;
	}

    //setters and getters
	public String getId() {
		return id;
	}

	public int getMaxHoursPerWeek() {
		return maxHoursPerWeek;
	}

	public String getName() {
		return name;
	}

	public void setLessons(ArrayList<String> lessons) {
		this.courseCode = lessons;
	}

	public void setMaxHoursPerWeek(int maxHoursPerWeek) {
		this.maxHoursPerWeek = maxHoursPerWeek;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addLesson(String lessonid){
		courseCode.add(lessonid);
	}

	public ArrayList<String> getLessons(){
		return courseCode;
	}
}