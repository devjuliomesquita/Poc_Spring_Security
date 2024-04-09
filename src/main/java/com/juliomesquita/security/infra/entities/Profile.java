package com.juliomesquita.security.infra.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id", nullable = false)
    private UUID id;

    @Column(name = "profile_name")
    private String name;

    @Column(name = "profile_description")
    private String description;

    @OneToMany(mappedBy = "profile")
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(name, profile.name) && Objects.equals(description, profile.description) && Objects.equals(users, profile.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, users);
    }
}
