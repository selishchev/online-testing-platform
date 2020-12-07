package nsu.ui;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.ArrayList;

public class Tests {

    private Long id;

    public ArrayList<String> listOfQuestions = new ArrayList<>();

    private Teachers teacher;

    public void setListOfQuestions(ArrayList<String> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public ArrayList<String> getListOfQuestions() {
        return this.listOfQuestions;
    }

    private String question;

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return this.question;
    }

    public void setTeacher(Teachers teacher){
        this.teacher = teacher;
    }
    public Teachers getTeacher(){
        return this.teacher;
    }

    @NotEmpty(message = "Test Name is required.")
    private String testName;


    public void setQuestionToList(String question) {
        this.listOfQuestions.add(question);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }



}
