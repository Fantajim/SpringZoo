package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Keeper;
import rocks.process.acrm.data.repository.KeeperRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
@Validated
public class KeeperService {

    @Autowired
    private KeeperRepository keeperRepository;
    @Autowired
    Validator validator;

    public void saveKeeper(@Valid Keeper keeper) throws Exception {
        if (keeper.getKeeperId() == null) {
            if (keeperRepository.findByEmail(keeper.getEmail()) != null) {
                throw new Exception("Email address " + keeper.getEmail() + " already assigned another animal.");
            }
        } else if (keeperRepository.findByEmailAndKeeperIdNot(keeper.getEmail(), keeper.getKeeperId()) != null) {
            throw new Exception("Email address " + keeper.getEmail() + " already assigned another animal.");
        }
        keeperRepository.save(keeper);
    }

    public Keeper getKeeper(Long keeperId) {
        return keeperRepository.findByKeeperId(keeperId);
    }

    public Keeper getCurrentKeeper() {
        String userEmail = "boss@zoo.ch";
        return keeperRepository.findByEmail(userEmail);
    }

    public List<Keeper> getAllKeepers() {
        return keeperRepository.findAll();
    }

    @PostConstruct
    private void init() throws Exception {
        Keeper keeper = new Keeper();
        keeper.setName("Boss");
        keeper.setEmail("boss@zoo.ch");
        keeper.setPassword("password1");
        keeper.setRemember("default");
        this.saveKeeper(keeper);

        Keeper keeper2 = new Keeper();
        keeper2.setName("Trainee");
        keeper2.setEmail("trainee@zoo.ch");
        keeper2.setPassword("password1");
        keeper2.setRemember("default");
        this.saveKeeper(keeper2);
    }
}
