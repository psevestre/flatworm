package domain.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassPeriod
{
    private String subject;
    private int period;
    private String teacher;
    private int gradeLevel;
    private String room;
    private List<Student> students = new ArrayList<Student>();

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public int getPeriod()
    {
        return period;
    }

    public void setPeriod(int period)
    {
        this.period = period;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public int getGradeLevel()
    {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel)
    {
        this.gradeLevel = gradeLevel;
    }

    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    public List<Student> getStudents()
    {
        return Collections.unmodifiableList(students);
    }

    public void setStudents(List<Student> students)
    {
        this.students.clear();
        this.students.addAll(students);
    }

    public void addStudent(Student _student)
    {
       students.add(_student);
    }
}
