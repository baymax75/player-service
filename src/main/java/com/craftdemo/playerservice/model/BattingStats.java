package com.craftdemo.playerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int runs;
    private int matches;
    private double average;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
