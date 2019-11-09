package rocks.process.acrm.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Keeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keeperId;
    @NotEmpty(message = "Please provide a name.")
    private String name;
    @Email(message = "Please provide a valid e-mail.")
    @NotEmpty(message = "Please provide an e-mail.")
    private String email;
    @org.springframework.data.annotation.Transient //will not be serialized
    private String password;
    @javax.persistence.Transient // will not be stored in DB
    private String remember;
    @OneToMany(mappedBy = "keeper")
    private List<Animal> animals;

    public Long getKeeperId() {
        return keeperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        String transientPassword = this.password;
        this.password = null;
        return transientPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}