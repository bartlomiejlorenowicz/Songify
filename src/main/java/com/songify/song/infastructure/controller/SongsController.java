package com.songify.song.infastructure.controller;

import com.songify.song.domain.model.service.*;
import com.songify.song.infastructure.controller.dto.request.GetSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import com.songify.song.infastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.UpdateSongsRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static com.songify.song.domain.model.service.SongMapper.*;

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
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, value = 10) Pageable pageable) {
        List<Song> allSongs = songRetriever.findAll(pageable);
        GetAllSongsResponseDto response = mapFromSongsToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongRequestDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info("Received requestId: {}", requestId);
        Song song = songRetriever.findSongById(id);
        GetSongRequestDto response = mapToGetSongRequestDto(song);
        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = mapFromCreateSongResponseDtoToSong(request);
        Song savedSong = songAdder.addSong(song);
        CreateSongResponseDto response = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songDeleter.deleteById(id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongsRequest request) {
        Song newSong = mapFromUpdateSongRequestDtoToSong(request);
        songUpdater.updateById(id, newSong);

        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        Song updatedSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song savedSong = songUpdater.updatePartiallyById(id, request);
        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }
}
