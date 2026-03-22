package in.org.project.EcommercePlatform.entity;

import in.org.project.EcommercePlatform.type.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "APP_USER", uniqueConstraints = {@UniqueConstraint(name = "unique_userName", columnNames = {"userName"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private Long age;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<RoleType>roles=new ArrayList<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<Address> listOfAddress=new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority>authorities=new HashSet<>();
        return roles.stream()
                .map(roleType -> new SimpleGrantedAuthority(roleType.toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
}
