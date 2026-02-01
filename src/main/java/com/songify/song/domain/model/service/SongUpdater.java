package com.songify.song.domain.model.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.repository.SongRepository;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public void updateById(Long id, Song newSong) {
        Song songById = songRetriever.findSongById(id);
        songById.setName(newSong.getName());
        songById.setArtist(newSong.getArtist());
    }

    public Song updatePartiallyById(Long id, PartiallyUpdateSongRequestDto songFromRequest) {
        Song songFromDatabase = songRetriever.findSongById(id);
        if (songFromRequest.songName() != null) {
            songFromDatabase.setName(songFromRequest.songName());
        }
        if (songFromRequest.artist() != null) {
            songFromDatabase.setArtist(songFromRequest.artist());
        }
        return songFromDatabase;
    }
}
