package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Keeper;

import java.util.List;

@Repository
public interface KeeperRepository extends JpaRepository<Keeper, Long> {
    Keeper findByKeeperId(Long keeperId);
    Keeper findByEmail(String email);
    Keeper findByEmailAndKeeperIdNot(String email, Long keeperId);
    List<Keeper>findAll();
}

