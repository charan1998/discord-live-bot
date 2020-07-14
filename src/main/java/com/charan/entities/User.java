package com.charan.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discord_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String discordId;
    private String guildId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_streamer", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "streamer_id"))
    private Set<Streamer> following = new HashSet<>();

    public User() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public Set<Streamer> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Streamer> following) {
        this.following = following;
    }
}
