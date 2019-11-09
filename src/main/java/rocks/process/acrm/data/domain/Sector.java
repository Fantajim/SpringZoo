package rocks.process.acrm.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectorId;
    @NotEmpty(message = "Please provide a name.")
    private String sectorName;
    @NotEmpty(message = "Please provide the size of the sector in m2")
    private String sectorSize;
    @NotEmpty(message = "Please provide the ambient temperature of the sector climate")
    private String sectorTemp;
    @NotEmpty(message = "Please provide the location of the sector")
    private String sectorLoc;
    @OneToMany(mappedBy = "sector")
    private List<Animal> animals;

    public Long getSectorId() {
        return sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorSize() {
        return sectorSize;
    }

    public void setSectorSize(String sectorSize) {
        this.sectorSize = sectorSize;
    }

    public String getSectorTemp() {
        return sectorTemp;
    }

    public void setSectorTemp(String sectorTemp) {
        this.sectorTemp = sectorTemp;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public String getSectorLoc() {
        return sectorLoc;
    }

    public void setSectorLoc(String sectorLoc) {
        this.sectorLoc = sectorLoc;
    }
}