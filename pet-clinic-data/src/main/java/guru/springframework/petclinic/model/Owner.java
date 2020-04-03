package guru.springframework.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "owners")
public class Owner extends Person {

    private String address;
    private String city;
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Builder
    public Owner(String firstName, String lastName, String address, String city, String telephone) {
        super(firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public Pet getPet(String name) {
        return getPet(name, false);
    }


    public Pet getPet(String name, boolean ignoreNew) {
        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String petName = pet.getName();
                if (petName.toLowerCase().equals(name.toLowerCase())) {
                    return pet;
                }
            }
        }
        return null;
    }
}
