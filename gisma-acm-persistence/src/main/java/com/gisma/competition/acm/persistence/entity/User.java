package com.gisma.competition.acm.persistence.entity;

import com.gisma.competition.acm.persistence.enumeration.UserRoleModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_username", columnNames = "username"),
                @UniqueConstraint(name = "unique_user_email", columnNames = "email")
        })
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final UserRoleModel userRole;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private final Long registrationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRole == UserRoleModel.ADMIN) {
            return Arrays.asList(
                    new SimpleGrantedAuthority(decorateRole(UserRoleModel.ADMIN)),
                    new SimpleGrantedAuthority(decorateRole(UserRoleModel.STANDARD)));
        } else {
            return List.of(new SimpleGrantedAuthority(decorateRole(UserRoleModel.STANDARD)));
        }
    }

    private String decorateRole(UserRoleModel userRole) {
        return "ROLE_" + userRole.name();
    }
}
