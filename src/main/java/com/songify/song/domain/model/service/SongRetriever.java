package com.songify.song.domain.model.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.model.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SongRetriever {

    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findAll() {
        log.info("retrieving all songs");
        return songRepository.findAll();
    }

    public List<Song> findAllLimitedBy(Long limit) {
        return songRepository.findAll()
                .stream()
                .limit(limit)
                .toList();
    }

    public Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    public void existsById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
    }
}
