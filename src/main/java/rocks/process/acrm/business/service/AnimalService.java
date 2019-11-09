package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Animal;
import rocks.process.acrm.data.repository.AnimalRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private KeeperService keeperService;
    @Autowired
    private SectorService sectorService;

    public Animal editAnimal(@Valid Animal animal) throws Exception {
        if (animal.getAnimalId() == null) {
            if (animalRepository.findByName(animal.getName()) == null) {
                if (animal.getKeeper() == null) {
                    animal.setKeeper(keeperService.getCurrentKeeper());
                }
                return animalRepository.save(animal);
            }
            throw new Exception("Animal: " + animal.getName() + " is already in the zoo.");
        }
        if (animalRepository.findByNameAndAnimalIdNot(animal.getName(), animal.getAnimalId()) == null) {
            if (animal.getKeeper() == null) {
                animal.setKeeper(keeperService.getCurrentKeeper());
            }
            return animalRepository.save(animal);
        }
        throw new Exception("Animal: " + animal.getName() + " is already in the zoo.");
    }

    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> findAnimalBySectorId(Long sectorId) throws Exception {
        List<Animal> animalList = animalRepository.findBySector_SectorId(sectorId);
        if(animalList.isEmpty()) {
            throw new Exception("No Animals are situated in this sector");
        }
        return animalList;
    }

    public Animal findAnimalById(Long animalId) throws Exception {
        List<Animal> animalList = animalRepository.findByAnimalIdAndKeeper_KeeperId(animalId, keeperService.getCurrentKeeper().getKeeperId());
        if(animalList.isEmpty()){
            throw new Exception("No Animal with ID "+animalId+" found.");
        }
        return animalList.get(0);
    }

    public void deleteAnimal(Long animalId) { animalRepository.deleteById(animalId);}

    @PostConstruct
    private void init() throws Exception {
        Animal animal = new Animal();
        animal.setName("Caesar");
        animal.setSpecies("Ape");
        animal.setAge("20");
        animal.setGender("male");
        animal.setSector(sectorService.getSector(4L));
        this.editAnimal(animal);

        Animal animal2 = new Animal();
        animal2.setName("King Kong");
        animal2.setSpecies("Ape");
        animal2.setAge("21");
        animal2.setGender("male");
        animal2.setSector(sectorService.getSector(4L));
        this.editAnimal(animal2);

        Animal animal3 = new Animal();
        animal3.setName("Ginny");
        animal3.setSpecies("Rat");
        animal3.setAge("3");
        animal3.setGender("female");
        animal3.setSector(sectorService.getSector(2L));
        this.editAnimal(animal3);

        Animal animal4 = new Animal();
        animal4.setName("Chester");
        animal4.setSpecies("Penguin");
        animal4.setAge("5");
        animal4.setGender("male");
        animal4.setKeeper(keeperService.getKeeper(2L));
        animal4.setSector(sectorService.getSector(3L));
        this.editAnimal(animal4);

        Animal animal5 = new Animal();
        animal5.setName("Jessica");
        animal5.setSpecies("Penguin");
        animal5.setAge("6");
        animal5.setGender("female");
        animal5.setKeeper(keeperService.getKeeper(2L));
        animal5.setSector(sectorService.getSector(3L));
        this.editAnimal(animal5);


    }



}
