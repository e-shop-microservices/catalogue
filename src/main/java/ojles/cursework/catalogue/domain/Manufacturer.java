package ojles.cursework.catalogue.domain;

import javax.persistence.*;

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String logoPath;

    public Manufacturer() {
        // required by JPA
    }

    public Manufacturer(String name, String logoPath) {
        this.name = name;
        this.logoPath = logoPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoPath() {
        return logoPath;
    }
}
