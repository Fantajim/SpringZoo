package rocks.process.acrm.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;
    private String name;
    @NotEmpty(message = "Please provide a species name")
    private String species;
    @NotEmpty(message = "Please provide the age of the animal")
    private String age;
    @NotEmpty(message = "Please provide a gender (m/f)")
    private String gender;
    @ManyToOne
    @JsonBackReference(value = "keeper")
    private Keeper keeper;
    @ManyToOne
    @JsonManagedReference(value = "sector")
    private Sector sector;

    public Long getAnimalId() {
        return animalId;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Keeper getKeeper() {
        return keeper;
    }

    public void setKeeper(Keeper keeper) {
        this.keeper = keeper;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
