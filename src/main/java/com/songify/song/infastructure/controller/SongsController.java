package com.songify.song.infastructure.controller;

import com.songify.song.domain.model.service.SongAdder;
import com.songify.song.domain.model.service.SongMapper;
import com.songify.song.domain.model.service.SongRetriever;
import com.songify.song.infastructure.controller.dto.request.GetSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import com.songify.song.infastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.UpdateSongsRequest;
import com.songify.song.domain.model.SongNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/songs")
@RestController
public class SongsController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;

    public SongsController(SongAdder songAdder, SongRetriever songRetriever) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
    }

    private static final Logger log =
            LoggerFactory.getLogger(SongsController.class);

    @GetMapping()
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (limit != null) {
            Map<Integer, Song> limitedMap = songRetriever.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongRequestDto> getSongById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info("Received requestId: {}", requestId);
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " does not exist");
        }
        Song song = allSongs.get(id);
        GetSongRequestDto response = SongMapper.mapToGetSongRequestDto(song);
        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song songName = SongMapper.mapFromCreateSongResponseDtoToSong(request);
        songAdder.addSong(songName);
        CreateSongResponseDto response = SongMapper.mapFromSongToCreateSongResponseDto(songName);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " does not exist");
        }
        allSongs.remove(id);
        DeleteSongResponseDto body = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongsRequest request) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " does not exist");
        }
        Song newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(request);
        Song oldSong = allSongs.put(id, newSong);
        log.info("Updated song with id: " + id + " from " + oldSong + " to " + newSong);
        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        Map<Integer, Song> allSongs = songRetriever.findAll();
        if (!allSongs.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " does not exist");
        }
        Song song = allSongs.get(id);
        Song updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song.SongBuilder builder = Song.builder();
        if (request.songName() != null) {
            builder.songName(updatedSong.songName());
        } else {
            builder.songName(song.songName());
        }
        if (request.artist() != null) {
            builder.artist(updatedSong.artist());
        } else {
            builder.artist(song.artist());
        }
        allSongs.put(id, updatedSong);
        log.info("Updated song with id: " + id + " from " + song + " to " + updatedSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }
}
