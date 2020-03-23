package guru.springframework.petclinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter

@Entity
@Table(name = "specialties")
public class Specialty extends BaseEntity {

    private String description;

}
