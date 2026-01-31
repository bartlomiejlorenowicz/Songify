package com.songify.song.domain.model.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("song1", "shawnmendes"),
            2, new Song("song2", "ariana grande"),
            3, new Song("song3", "jessy james"),
            4, new Song("song4", "paul morris")
    ));

    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll() {
        return database;
    }

}
