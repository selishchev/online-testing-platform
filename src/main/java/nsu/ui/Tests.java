package nsu.ui;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.ArrayList;

public class Tests {

    private Long id;

    private ArrayList<String> listOfQuestions = new ArrayList<>();

    private Teachers teacher;

    public void setTeacher(Teachers teacher){
        this.teacher = teacher;
    }
    public Teachers getTeacher(){
        return this.teacher;
    }

    @NotEmpty(message = "Test Name is required.")
    private String testName;

    public ArrayList<String> getListOfQuestions() {
        return this.listOfQuestions;
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }



}
