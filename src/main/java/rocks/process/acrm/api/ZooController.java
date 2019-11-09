/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.process.acrm.business.service.AnimalService;
import rocks.process.acrm.business.service.KeeperService;
import rocks.process.acrm.business.service.SectorService;
import rocks.process.acrm.data.domain.Animal;
import rocks.process.acrm.data.domain.Keeper;
import rocks.process.acrm.data.domain.Sector;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ZooController {
    @Autowired
    private KeeperService keeperService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private SectorService sectorService;


    //Create a new animal
    @PostMapping(path = "/animal", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Animal> postAnimal(@RequestBody Animal animal) {
        try {
            animal = animalService.editAnimal(animal);
        } catch (ConstraintViolationException e) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(animal.getAnimalId()).toUri();

        return ResponseEntity.created(location).body(animal);
    }

    @PostMapping(path = "/keeper", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Keeper> registerKeeper(@RequestBody Keeper keeper) {
        try {
            keeperService.saveKeeper(keeper);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{keeperId}")
                .buildAndExpand(keeper.getKeeperId()).toUri();

        return ResponseEntity.created(location).body(keeper);
    }

    //Get a List of all animals
    @GetMapping(path = "/animal" ,produces ="application/json")
    public List<Animal> getAllAnimals() {
        return animalService.findAllAnimals();
    }

    //Get a List of all keepers
    @GetMapping(path = "/keeper" ,produces ="application/json")
    public List<Keeper> getAllKeepers() {
        return keeperService.getAllKeepers();
    }

    //Get a List of all sectors
    @GetMapping(path = "/sector" ,produces ="application/json")
    public List<Sector> getAllSectors() {
        return sectorService.getAllSectors();
    }

    //Get a specific animal
    @GetMapping(path = "/animal/{animalId}", produces = "application/json")
    public ResponseEntity<Animal> getSingleAnimal(@PathVariable(value = "animalId") String animalId) {
        Animal animal = null;
        try {
            animal = animalService.findAnimalById(Long.parseLong(animalId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(animal);
    }

    //Todo this doesnt work anymore
    //Get all animals that are handled by a specific keeper
    @GetMapping(path= "/keeper/{keeperId}", produces = "application/json")
    public ResponseEntity<Keeper> getAllAnimalsFromKeeper(@PathVariable(value = "keeperId")String keeperId) {
        Keeper keeper = null;
        try {
            keeper = keeperService.getKeeper(Long.parseLong(keeperId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(keeper);
    }

    //Get all animals that are situated in a specific sector
    @GetMapping(path = "/{sectorId}", produces = "application/json")
    public List<Animal> getAllAnimalsInSector(@PathVariable(value = "sectorId") String sectorId) {
        List<Animal>animalList = null;
        try {
            animalList = animalService.findAnimalBySectorId(Long.parseLong(sectorId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return animalList;
    }

    //Get a specific Sector to see its attributes
    @GetMapping(path = "/sector/{sectorId}", produces = "application/json")
    public ResponseEntity<Sector> getSector(@PathVariable(value = "sectorId")String sectorId){
        Sector sector = null;
        try {
            sector = sectorService.getSector(Long.parseLong(sectorId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(sector);
    }

    //I wanted to try to alter data without giving the whole object as parameter like i would in PUT
    //keeperId is wrongly used here i think, but it works. With more time and experience I would find a more elegant solution here
    @PatchMapping(path = "/animal/{animalId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Animal> patchAnimal(@PathVariable("animalId") String animalId, @RequestBody String sectorId, String keeperId) {
        Animal animal = null;
        try {
            animal = animalService.findAnimalById(Long.parseLong(animalId));
            animal.setSector(sectorService.getSector(Long.parseLong(sectorId)));
            animal.setKeeper(keeperService.getKeeper(Long.parseLong(keeperId)));
            animal = animalService.editAnimal(animal);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(animal);
    }

    //Delete an animal
	@DeleteMapping(path = "/animal/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable(value = "animalId") String animalId) {
        try {
			//TODO delete customer
            animalService.deleteAnimal(Long.parseLong(animalId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }


}