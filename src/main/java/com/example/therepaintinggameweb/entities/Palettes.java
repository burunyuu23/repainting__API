package com.example.therepaintinggameweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name = "palettes")
public class Palettes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "palettes_id")
    private Long palettesId;

    @Column(name = "palette", columnDefinition = "jsonb")
    private String palette;

    @JoinColumn(name = "created_user_id")
    private String createdUserId;

    @JsonIgnore
    @ManyToMany(mappedBy = "palettes")
    private List<User> users = new ArrayList<>();
}
