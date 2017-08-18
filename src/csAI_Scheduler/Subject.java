/**
 * File: Subject.java
 * Description: This file represents a course in the Schedule.
 */
package csAI_Scheduler;

public class Subject {

	private String course_code;
	private String course_name;
	private String room;
	private int hours_per_week;
	private Teacher teacher; //we link this object in the DataReader class.

    /**
     * Constructor
     * @param course_code code
     * @param course_name title
     * @param room room
     * @param hours_per_week hours
     * @param teacher teacher.
     */
	public Subject(String course_code, String course_name, String room, int hours_per_week, Teacher teacher) {
		super();
		this.course_code = course_code;
		this.course_name = course_name;
		this.room = room;
		this.hours_per_week = hours_per_week;
		this.teacher = teacher;
	}

    //Setters and getters

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getCourseCode() {
		return course_code;
	}

	public void setCourseCode(String code) {
		this.course_code = code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getHours_per_week() {
		return hours_per_week;
	}

	public void setHours_per_week(int hours_per_week) {
		this.hours_per_week = hours_per_week;
	}
}
