package com.songify.song.infastructure.controller.dto.response;

import com.songify.song.domain.model.Song;

public record PartiallyUpdateSongResponseDto(Song updatedSong) {
}
