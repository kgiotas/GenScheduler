/**
 * File: DataReader.java
 * Description: This file organizes and returns the Subject array data.
 *              Every Subject object contains a Teacher object. This class
 *              makes sure that these objects are correctly linked together
 */

package csAI_Scheduler;

import java.util.ArrayList;

public class DataReader {

    private String _teacherdataFilename, _lessondataFilename; //filenames
    private Teacher[] _teacherData;
    private Subject[] _subjectData;

    public DataReader(String teacherFN, String subjectFN){

        this._lessondataFilename = subjectFN;
        this._teacherdataFilename = teacherFN;

        getData();//populates _teacherData, _subject_data
    }

    public Subject[] getSubjectData(){
        return _subjectData;
    }

    public Teacher[] getTeacherData(){
        return _teacherData;
    }

    /**
     * Populates _subject_data, _teacherData
     * and links ojects between Subject and Teacher data
     */
    private void getData(){

        //Populate teachers data
        int teacherCounter = 0; //array that stores indexes for _teacherData
        TeachersFileReader tfr = new TeachersFileReader(_teacherdataFilename);
        String[][] teacherData = tfr.read();
        int teacherDataSize = teacherData.length;
        _teacherData = new Teacher[teacherDataSize];

        for(int i = 0; i < teacherDataSize; i++){

            int exists = teacherExistsIn(teacherData[i][0]); //check if teacher has been added already
            if(exists > -1 ){ //if yes
                _teacherData[exists].addLesson(teacherData[i][2]);//add lesson only
            }else{
                ArrayList<String> tempArray = new ArrayList<String>();
                tempArray.add(teacherData[i][2]);
                _teacherData[teacherCounter] = new Teacher(teacherData[i][0], teacherData[i][1], tempArray, Integer.parseInt(teacherData[i][3]), Integer.parseInt(teacherData[i][4]));
                teacherCounter++;
            }
        }
        //Workaround for incorrect length. This happens if we have teachers teaching more than one subject
        Teacher[] workaround = new Teacher[teacherCounter];
        for(int i = 0; i < teacherCounter; i++){
            workaround[i] = _teacherData[i];
        }
        _teacherData = workaround;
        //end workaround

        //add subjects
        SubjectFileReader lfr = new SubjectFileReader(_lessondataFilename);
        String[][] lessonData = lfr.read();
        int lessonDataSize = lessonData.length;
        Subject tempSubject;
        Teacher tempTeacher;
        _subjectData = new Subject[lessonDataSize];
        for(int i = 0; i < lessonDataSize; i++){
            tempTeacher = _teacherData[getTeacherIndex(lessonData[i][0])]; //found teacher that teaches this lesson
            tempSubject = new Subject(lessonData[i][0], lessonData[i][1], lessonData[i][2], Integer.parseInt(lessonData[i][3].replaceAll("\\s","")), tempTeacher);
            _subjectData[i] = tempSubject;

        }
    }


    /**
     * Checks if teacher has been already added to _teacherData
     * @param teacherId
     * @return position if yes, else -1
     */
    private int teacherExistsIn(String teacherId){

        int result = -1;
        for(int i = 0; i < _teacherData.length; i++){
            if(_teacherData[i] != null) {
                if (_teacherData[i].getId().equals(teacherId)) {
                    result = i;
                }
            }
        }
        return result;
    }

    /**
     * Gets teacher's index number from _teacherData array
     * from teacher who teaches a subject with the specified subject_id
     * @param subject_id
     * @return index number for _teacherData
     */
    private int getTeacherIndex(String subject_id){

        int result = -1;
        ArrayList<String> temp;
        int d = _teacherData.length;
        for(int i = 0; i < _teacherData.length; i++){
            temp = _teacherData[i].getLessons();
            for(int j = 0; j < temp.size(); j++){
                if(temp.get(j).equals(subject_id)){
                    result = i;
                }
            }
        }
        return result;
    }

    /**
     * check the hours/week that each class have
     * @return true if every class has less than 36 hours/week
     */
    public boolean checkHours()
    {
        boolean result=false;
        int sumA = 0, sumB = 0, sumC = 0;
        for (int i = 0; i < _subjectData.length; i++) // check all the classes
        {
            if (_subjectData[i].getRoom().equals("A"))
                sumA += _subjectData[i].getHours_per_week();
            else  if (_subjectData[i].getRoom().equals("B"))
                sumB += _subjectData[i].getHours_per_week();
            else  if (_subjectData[i].getRoom().equals("C"))
                sumC += _subjectData[i].getHours_per_week();
        }
        if (sumA > 36 || sumB > 36 || sumC >36) // if hours are more than they should be
                result = true;
        return result;
    }
}
