package ojles.cursework.catalogue.domain;

import ojles.cursework.catalogue.exception.ForbiddenActionException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class CustomProperty {
    private static final String OPTIONS_SEPARATOR = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, length = 8192)
    private String value;

    // this property is only to add product_id column to the custom_property table
    // no getter or setter is present because this property is not intended to be used here
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public CustomProperty() {
        // required by JPA
    }

    public CustomProperty(String name, long number) {
        this(name, Type.NUMBER);
        value = Long.toString(number);
    }

    public CustomProperty(String name, String string) {
        this(name, Type.STRING);
        value = string;
    }

    public CustomProperty(String name, List<String> options) {
        this(name, Type.OPTIONS);

        for (String option : options) {
            if (option.contains(OPTIONS_SEPARATOR)) {
                throw new ForbiddenActionException(
                        String.format(
                                "Option '%s' contains special symbol '%s' that is not allowed",
                                option,
                                OPTIONS_SEPARATOR
                        ));
            }
        }

        value = String.join(OPTIONS_SEPARATOR, options);
    }

    private CustomProperty(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public long getNumber() {
        tryConvertToType(Type.NUMBER);
        return Long.valueOf(value);
    }

    public String getString() {
        tryConvertToType(Type.STRING);
        return value;
    }

    public List<String> getOptions() {
        tryConvertToType(Type.OPTIONS);
        return Arrays.asList(value.split(OPTIONS_SEPARATOR));
    }

    private void tryConvertToType(Type toType) {
        if (type != toType) {
            throw new DomainException("Can't convert value of type=" + type + " to " + toType);
        }
    }

    public enum Type {
        NUMBER,
        STRING,
        OPTIONS
    }
}
