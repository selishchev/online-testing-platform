package nsu.ui;
import org.hibernate.validator.constraints.NotEmpty;

public class Teachers {

    private Long id;

    @NotEmpty(message = "Name is required.")
    private String firstName;

    @NotEmpty(message = "Second Name is required.")
    private String secondName;

    @NotEmpty(message = "Last Name is required.")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
