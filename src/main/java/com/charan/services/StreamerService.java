package com.charan.services;

import com.charan.entities.Streamer;
import com.charan.repositories.StreamerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StreamerService {
    private final StreamerRepository streamerRepository;

    StreamerService(StreamerRepository streamerRepository) {
        this.streamerRepository = streamerRepository;
    }

    public Optional<Streamer> findById(Long id) {
        return streamerRepository.findById(id);
    }

    public Optional<Streamer> findByDiscordIdAndGuildId(String discordId, String guildId) {
        return streamerRepository.findByDiscordIdAndGuildId(discordId, guildId);
    }

    public Streamer save(Streamer streamer) {
        return streamerRepository.save(streamer);
    }

    public Streamer update(Streamer streamer) {
        return streamerRepository.save(streamer);
    }
}
