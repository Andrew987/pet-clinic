package guru.springframework.petclinic.services.map;

import guru.springframework.petclinic.model.Owner;
import guru.springframework.petclinic.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    @Mock
    PetService petService;
    OwnerServiceMap ownerServiceMap;

    final Long testId = 2l;
    final String testLastName = "Kirk";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerServiceMap = new OwnerServiceMap(petService);
        ownerServiceMap.save(createTestOwner(testId, testLastName));
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(testId);

        assertEquals(testLastName, owner.getLastName());
        assertEquals(testId, owner.getId());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(testId);

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(testId));

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void saveStoresId() {
        Long id = 3L;
        Owner owner2 = createTestOwner(id, "Daw");
        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveCreatesId() {
        Owner savedOwner = ownerServiceMap.save(new Owner());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(testLastName);

        assertEquals(testLastName, owner.getLastName());
        assertEquals(testId, owner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("foo");

        assertNull(owner);
    }

    private Owner createTestOwner(Long id, String lastName) {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setLastName(lastName);
        return owner;
    }
}