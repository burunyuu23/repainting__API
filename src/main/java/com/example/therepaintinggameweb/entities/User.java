package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_palettes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "palettes_id")
    )
    private List<Palettes> palettes = new ArrayList<>();

    @OneToMany(mappedBy = "whoAddUser", cascade = CascadeType.ALL)
    private List<Friendship> friendships = new ArrayList<>();

    public void addFriend(User friendUser) {
        Optional<Friendship> friendUserInListNotAccepted = findFriendship(friendUser, this);

        if (friendUserInListNotAccepted.isEmpty()) {
            friendUserInListNotAccepted = findFriendship(this, friendUser);

            if (friendUserInListNotAccepted.isEmpty()) {
                Friendship friendship = new Friendship();
                friendship.setWhoAddUser(this);
                friendship.setWhoShouldAcceptUser(friendUser);
                friendship.setAccept(false);
                friendships.add(friendship);
            }
        } else if (!friendUserInListNotAccepted.get().isAccept())
            friendUserInListNotAccepted.get().setAccept(true);
    }

    public void removeFriend(User friendUser) {
        Optional<Friendship> friendUserInListNotAccepted = findFriendship(friendUser, this);

        if (friendUserInListNotAccepted.isEmpty()) {
            friendUserInListNotAccepted = findFriendship(this, friendUser);

            friendUserInListNotAccepted
                    .ifPresent(friendship ->
                            removeFriendFromFriendshipList(friendship, this, friendUser));
        } else
            removeFriendFromFriendshipList(friendUserInListNotAccepted.get(), friendUser, this);

    }

    private Optional<Friendship> findFriendship(User whoAdd, User whoShouldAccept) {
        return friendships.stream()
                .filter(friendship -> friendship.getWhoShouldAcceptUser().equals(whoShouldAccept)
                        && friendship.getWhoAddUser().equals(whoAdd))
                .findFirst();
    }

    private void removeFriendFromFriendshipList(Friendship friendship, User friendUser, User me) {
        if (friendship.isAccept()) {
            friendship.setAccept(false);
            friendship.setWhoAddUser(me);
            friendship.setWhoShouldAcceptUser(friendUser);
        } else
            friendships.remove(friendship);
    }
}
