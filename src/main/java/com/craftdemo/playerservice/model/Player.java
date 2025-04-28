// Player.java
package com.craftdemo.playerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private BattingStats battingStats;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private FieldingStats fieldingStats;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PitchingStats pitchingStats;
}
