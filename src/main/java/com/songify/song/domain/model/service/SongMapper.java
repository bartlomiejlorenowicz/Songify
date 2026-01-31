package com.songify.song.domain.model.service;

import com.songify.song.infastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.GetSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infastructure.controller.dto.request.UpdateSongsRequest;
import com.songify.song.infastructure.controller.dto.response.CreateSongResponseDto;
import com.songify.song.domain.model.Song;
import com.songify.song.infastructure.controller.dto.response.DeleteSongResponseDto;
import com.songify.song.infastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.song.infastructure.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

public class SongMapper {

    public static Song mapFromCreateSongResponseDtoToSong(CreateSongRequestDto request) {
        return new Song(request.songName(), request.artist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song songName) {
       return new CreateSongResponseDto(songName);
    }

    public static GetSongRequestDto mapToGetSongRequestDto(Song song) {
        return new GetSongRequestDto(song);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Integer id) {
        return new DeleteSongResponseDto("Song with id: " + id + " deleted successfully", HttpStatus.OK);
    }

    public static Song mapFromUpdateSongRequestDtoToSong(UpdateSongsRequest request) {
        return new Song(request.songName(), request.artist());
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.songName(), newSong.artist());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song updatedSong) {
        return new PartiallyUpdateSongResponseDto(updatedSong);
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto request) {
    return new Song(request.songName(), request.artist());
    }
}
