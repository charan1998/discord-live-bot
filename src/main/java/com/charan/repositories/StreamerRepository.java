package com.charan.repositories;

import com.charan.entities.Streamer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreamerRepository extends CrudRepository<Streamer, Long> {
    Optional<Streamer> findByDiscordIdAndGuildId(String discordId, String guildId);
}
