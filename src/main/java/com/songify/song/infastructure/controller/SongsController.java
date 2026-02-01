package com.songify.song.infastructure.controller;

import com.songify.song.domain.model.service.*;
import com.songify.song.infastructure.controller.dto.request.GetSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import com.songify.song.infastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.UpdateSongsRequest;
import com.songify.song.domain.model.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/songs")
@RestController
@AllArgsConstructor
public class SongsController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    private static final Logger log =
            LoggerFactory.getLogger(SongsController.class);

    @GetMapping()
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Long limit) {
        List<Song> allSongs = songRetriever.findAll();
        if (limit != null) {
            List<Song> limitedMap = songRetriever.findAllLimitedBy(limit);
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongRequestDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info("Received requestId: {}", requestId);
        Song song = songRetriever.findSongById(id);
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
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songDeleter.deleteById(id);
        DeleteSongResponseDto body = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongsRequest request) {
        Song newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(request);
        songUpdater.updateById(id, newSong);

        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        Song updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song savedSong = songUpdater.updatePartiallyById(id, request);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }
}
