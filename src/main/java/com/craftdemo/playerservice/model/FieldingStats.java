package com.craftdemo.playerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int catches;
    private int stumpings;
    private int runOuts;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
