package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Animal findByName(String animalName);
    Animal findByNameAndAnimalIdNot(String animalName, Long animalId);
    List<Animal> findBySector_SectorId(Long sectorId);
    List<Animal> findByAnimalIdAndKeeper_KeeperId(Long animalId, Long keeperId);
    List<Animal> findAll();
}
