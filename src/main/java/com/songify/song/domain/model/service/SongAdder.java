package com.songify.song.domain.model.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song addSong(Song song) {
        log.info("Adding song: {}", song);
        songRepository.saveToDatabase(song);

        return song;
    }


}
