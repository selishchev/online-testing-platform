package nsu.ui.mvc;
import org.hibernate.validator.constraints.NotEmpty;

public class Tests {

    private Long id;

    @NotEmpty(message = "Id is required.")
    private Long teacherID;

    @NotEmpty(message = "Question is required.")
    private String question;

    @NotEmpty(message = "Test Name is required.")
    private String testName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }



}
