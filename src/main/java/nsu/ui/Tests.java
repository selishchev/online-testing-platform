package nsu.ui;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.ArrayList;

public class Tests {

    private Long id;

    private String teacher;

    @NotEmpty(message = "Question is required.")
    private String question;

    @NotEmpty(message = "Test Name is required.")
    private String testName;

    public ArrayList<String> getListOfQuestions() {
        return listOfQuestions;
    }

    private ArrayList<String> listOfQuestions;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.listOfQuestions.add(question);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }



}
