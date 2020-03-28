package guru.springframework.petclinic.services.springdatajpa;

import guru.springframework.petclinic.model.Owner;
import guru.springframework.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String TEST_LAST_NAME = "Kirk";
    public static final Long TEST_ID = 2L;

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    Owner testOwner;

    @BeforeEach
    void setUp() {
        testOwner = createTestOwner(TEST_ID, TEST_LAST_NAME);
    }

    @Test
    void findAll() {
        Set<Owner> returnOwners = new HashSet<>();
        returnOwners.add(createTestOwner(1L, "Daw"));
        returnOwners.add(createTestOwner(2L, "Avasarala"));

        when(ownerRepository.findAll()).thenReturn(returnOwners);

        Set<Owner> owners = ownerService.findAll();
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(testOwner));

        Owner owner = ownerService.findById(TEST_ID);

        assertEquals(TEST_LAST_NAME, owner.getLastName());
        assertEquals(TEST_ID, owner.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerService.findById(TEST_ID);

        assertNull(owner);
    }

    @Test
    void deleteById() {
        ownerService.deleteById(TEST_ID);

        verify(ownerRepository).deleteById(TEST_ID);
    }

    @Test
    void delete() {
        ownerService.delete(testOwner);

        verify(ownerRepository).delete(testOwner);
    }

    @Test
    void save() {
        when(ownerRepository.save(testOwner)).thenReturn(testOwner);

        Owner savedOwner = ownerService.save(testOwner);

        assertEquals(testOwner.getId(), savedOwner.getId());
        verify(ownerRepository).save(testOwner);
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(TEST_LAST_NAME)).thenReturn(testOwner);

        Owner owner = ownerService.findByLastName(TEST_LAST_NAME);

        assertEquals(TEST_LAST_NAME, owner.getLastName());
        verify(ownerRepository).findByLastName(TEST_LAST_NAME);
    }

    @Test
    void findByLastNameNotFound() {
        when(ownerRepository.findByLastName(any())).thenReturn(null);

        Owner owner = ownerService.findByLastName("foo");

        assertNull(owner);
        verify(ownerRepository).findByLastName(any());
    }

    private Owner createTestOwner(Long id, String lastName) {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setLastName(lastName);
        return owner;
    }
}