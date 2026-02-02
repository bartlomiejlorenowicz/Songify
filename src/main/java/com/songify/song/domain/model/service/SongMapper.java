package com.songify.song.domain.model.service;

import com.songify.song.infastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.GetSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.UpdateSongsRequest;
import com.songify.song.infastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {


    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

    public static Song mapFromCreateSongResponseDtoToSong(CreateSongRequestDto request) {
        return new Song(request.songName(), request.artist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        SongDto savedSongDto = SongMapper.mapFromSongToSongDto(song);
       return new CreateSongResponseDto(savedSongDto);
    }

    public static GetSongRequestDto mapToGetSongRequestDto(Song song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongRequestDto(songDto);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("Song with id: " + id + " deleted successfully", HttpStatus.OK);
    }

    public static Song mapFromUpdateSongRequestDtoToSong(UpdateSongsRequest request) {
        return new Song(request.songName(), request.artist());
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.getName(), newSong.getArtist());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song updatedSong) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(updatedSong);
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto request) {
    return new Song(request.songName(), request.artist());
    }

    public static GetAllSongsResponseDto mapFromSongsToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songDto = songs.stream().map(SongMapper::mapFromSongToSongDto).toList();
        return new GetAllSongsResponseDto(songDto);
    }
}
