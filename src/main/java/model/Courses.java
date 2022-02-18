package main.java.model;

import java.util.List;
import java.util.Objects;

public class Courses {

    private Integer courseId;
    private String courseName;
    private String courseDescription;
    private List<Student> students;

    public Courses() {
    }

    public Courses(Integer courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Courses(Integer courseId, String courseName, String courseDescription, List<Student> students) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.students = students;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public List<Student> getStudents(){
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses courses = (Courses) o;
        return Objects.equals(courseId, courses.courseId) && Objects.equals(courseName, courses.courseName) && Objects.equals(courseDescription, courses.courseDescription) && Objects.equals(students, courses.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseDescription, students);
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }
}
