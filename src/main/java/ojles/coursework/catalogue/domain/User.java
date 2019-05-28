package ojles.coursework.catalogue.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean admin = false;

    public static User createCustomer() {
        return new User();
    }

    public static User createAdmin() {
        User user = new User();
        user.admin = true;
        return user;
    }
}
