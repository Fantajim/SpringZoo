package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Sector;
import rocks.process.acrm.data.repository.SectorRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
@Validated
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    Validator validator;

    public void saveSector(@Valid Sector sector) throws Exception {
        if (sector.getSectorId() == null) {
            if (sectorRepository.findBySectorName(sector.getSectorName()) != null) {
                throw new Exception("Sector Name: " + sector.getSectorName() + " is already assigned to another Sector");
            }
        } else if (sectorRepository.findBySectorNameAndSectorIdNot(sector.getSectorName(), sector.getSectorId()) != null) {
            throw new Exception("Sector Name " + sector.getSectorName() + " is already assigned to a Sector.");
        }
        sectorRepository.save(sector);
    }

    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public Sector getSector(Long sectorId) {
        return sectorRepository.findBySectorId(sectorId);
    }

    @PostConstruct
    private void init() throws Exception {
        if (sectorRepository.findAll().size() < 4) {
            Sector sector = new Sector();
            sector.setSectorName("Pre-release/Nursing area");
            sector.setSectorSize("200m2");
            sector.setSectorTemp("28°C");
            sector.setSectorLoc("Center");
            this.saveSector(sector);

            Sector sector2 = new Sector();
            sector2.setSectorName("Small mammal house");
            sector2.setSectorSize("100m2");
            sector2.setSectorTemp("32°C");
            sector2.setSectorLoc("East");
            this.saveSector(sector2);

            Sector sector3 = new Sector();
            sector3.setSectorName("The Fridge");
            sector3.setSectorSize("234m2");
            sector3.setSectorTemp("20°C");
            sector3.setSectorLoc("West");
            this.saveSector(sector3);

            Sector sector4 = new Sector();
            sector4.setSectorName("Jungle habitat");
            sector4.setSectorSize("400m2");
            sector4.setSectorTemp("32°C");
            sector4.setSectorLoc("North");
            this.saveSector(sector4);
        }
    }
}