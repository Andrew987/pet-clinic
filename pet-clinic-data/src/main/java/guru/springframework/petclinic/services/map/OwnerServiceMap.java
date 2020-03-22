package guru.springframework.petclinic.services.map;

import guru.springframework.petclinic.model.Owner;
import guru.springframework.petclinic.services.OwnerService;
import guru.springframework.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner object) {
        if (object != null) {
            object.getPets().forEach(pet -> {
                if (pet.getId() == null) {
                    petService.save(pet);
                }
            });
        }
        return super.save(object);
    }

    @Override
    public Owner findByLastName(String lastName) {
        for (Owner owner : map.values()) {
            if (owner.getLastName().equals(lastName)) {
                return owner;
            }
        }
        return null;
    }
}
