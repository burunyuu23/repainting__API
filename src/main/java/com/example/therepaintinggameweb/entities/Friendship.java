package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "friends")
public class Friendship {

    @Id
    @ManyToOne
    @JoinColumn(name = "who_add_user_id", referencedColumnName = "user_id")
    private User whoAddUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "who_should_accept_user_id", referencedColumnName = "user_id")
    private User whoShouldAcceptUser;

    @Column(name = "is_accept")
    private boolean isAccept;
}
