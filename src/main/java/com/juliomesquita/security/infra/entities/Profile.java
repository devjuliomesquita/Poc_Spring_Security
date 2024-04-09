package com.juliomesquita.security.infra.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Profile {

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
}
