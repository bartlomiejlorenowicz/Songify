package com.songify.song.domain.model.service;

import com.songify.song.domain.model.repository.SongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SongDeleter {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public void deleteById(Long id) {
        songRetriever.findSongById(id);
        log.info("Delete by id {}", id);
        songRepository.deleteById(id);
    }
}
