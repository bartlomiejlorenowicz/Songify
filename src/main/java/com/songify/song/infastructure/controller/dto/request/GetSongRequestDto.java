package com.songify.song.infastructure.controller.dto.request;

import com.songify.song.domain.model.Song;

public record GetSongRequestDto(Song songId) {
}
