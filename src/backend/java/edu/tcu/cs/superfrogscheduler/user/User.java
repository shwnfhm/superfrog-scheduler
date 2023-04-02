package backend.java.edu.tcu.cs.superfrogscheduler.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = "Email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //user's email address
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    //user's phone number (without dashes)
    private String phoneNumber;

    //user's password
    @NotBlank
    @Size(max = 40)
    private String password;

    //user's first name
    @NotBlank
    private String firstName;

    //user's last name
    @NotBlank
    private String lastName;

    //used to indicate whether a SuperFrog Student, SpiritDirector
    //or customer. {"CUSTOMER", "SUPERFROG", "SPIRITDIRECTOR"}
    private String role;

    //true = activated, false = deactivated
    private boolean active;

    public User() {

    }

    public User(Long id, String email, String phoneNumber, String password, String firstName, String lastName, String role, boolean active) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
