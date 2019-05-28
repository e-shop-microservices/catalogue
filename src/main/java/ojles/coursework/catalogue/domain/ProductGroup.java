package ojles.coursework.catalogue.domain;

import ojles.coursework.catalogue.exception.ForbiddenActionException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imagePath;

    @JoinColumn(name = "parent_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductGroup parentGroup;

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private List<ProductGroup> children = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public ProductGroup() {
        // required by JPA
    }

    public ProductGroup(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<ProductGroup> getChildren() {
        return children;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        if (!children.isEmpty()) {
            throw new ForbiddenActionException("Can't add products to a node with subgroups, only leafs allowed");
        }

        products.add(product);
        product.setGroup(this);
    }

    public void addChildGroup(ProductGroup child) {
        // TODO: replace with equals()
        if (child == this) {
            throw new ForbiddenActionException("Can't add yourself as child");
        }

        // if node is a leaf
        if (children.isEmpty()) {
            if (!products.isEmpty() && !child.isEmpty()) {
                throw new ForbiddenActionException("Can't add non-empty group to non-empty parent group");
            }

            child.products.addAll(products);
            products.clear();
        }

        children.add(child);
        child.parentGroup = this;
    }

    private boolean isEmpty() {
        return products.isEmpty() && children.isEmpty();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public long getParentGroupId() {
        return parentGroup.getId();
    }
}
