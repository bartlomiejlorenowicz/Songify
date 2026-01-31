package com.songify.song.infastructure.controller.dto.response;

import com.songify.song.domain.model.Song;

import java.util.Map;

public record SongsResponseDto(Map<Integer, Song> songs) {
}
