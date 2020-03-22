package guru.springframework.petclinic.bootstrap;

import guru.springframework.petclinic.model.*;
import guru.springframework.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean petTypesLoaded = (0 != petTypeService.findAll().size());

        if (!petTypesLoaded) {
            loadData();
        }
    }

    private void loadData() {
        PetType dogType = new PetType();
        dogType.setName("Dog");
        petTypeService.save(dogType);

        PetType parrotType = new PetType();
        dogType.setName("Parrot");
        petTypeService.save(parrotType);

        System.out.println("Loaded Pet Types ...");

        Pet dog = new Pet();
        dog.setName("Barky");
        dog.setPetType(dogType);
        dog.setBirthDate(LocalDate.now());

        Pet parrot = new Pet();
        parrot.setName("Raaa");
        parrot.setPetType(parrotType);
        parrot.setBirthDate(LocalDate.now());

        System.out.println("Created Pets ...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("12345689");
        owner1.getPets().add(dog);
        dog.setOwner(owner1);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("Gemuse Strasse 7");
        owner2.setCity("Berlin");
        owner2.setTelephone("98765455");
        owner2.getPets().add(parrot);
        parrot.setOwner(owner2);
        ownerService.save(owner2);

        System.out.println("Loaded Owners with their Pets...");

        Specialty exoticBirds = new Specialty();
        exoticBirds.setDescription("Exotic Birds");
        specialtyService.save(exoticBirds);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        specialtyService.save(surgery);

        System.out.println("Loaded specialties ...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialties().add(exoticBirds);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialties().add(surgery);
        vetService.save(vet2);

        System.out.println("Loaded Vets ...");

        Visit parrotVisit = new Visit();
        parrotVisit.setPet(parrot);
        parrotVisit.setDate(LocalDate.now());
        parrotVisit.setDescription("Sneezy parrot");

        visitService.save(parrotVisit);

        System.out.println("Loaded Visits ...");
    }

}
