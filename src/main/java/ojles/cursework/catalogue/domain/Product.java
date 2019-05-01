package ojles.cursework.catalogue.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    // TODO: change to TEXT or LARGETEXT
    @Column(nullable = false, length = 2048)
    private String description;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private String imagePath;

    // this property is only to add group_id column to the product table
    // no getter or setter is present because this property is not intended to be used here
    @JoinColumn(name = "group_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProductGroup group;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CustomProperty> customProperties = new ArrayList<>();

    public Product() {
        // required by JPA
    }

    public Product(String name, String description, Long price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<CustomProperty> getCustomProperties() {
        return customProperties;
    }
}
