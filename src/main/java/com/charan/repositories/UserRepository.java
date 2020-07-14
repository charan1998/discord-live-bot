package com.charan.repositories;

import com.charan.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByDiscordIdAndGuildId(String discordId, String guildId);
}
