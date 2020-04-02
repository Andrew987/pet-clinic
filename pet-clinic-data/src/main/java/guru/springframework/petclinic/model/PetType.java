package guru.springframework.petclinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter

@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
