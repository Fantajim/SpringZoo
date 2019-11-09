package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Keeper;
import rocks.process.acrm.data.domain.Sector;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Sector findBySectorId(Long sectorId);
    Sector findBySectorName(String sectorName);
    Sector findBySectorNameAndSectorIdNot(String sectorName, Long sectorId);
    List<Sector> findAll();
}