package com.craftdemo.playerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PitchingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int wickets;
    private int innings;
    private double economy;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
