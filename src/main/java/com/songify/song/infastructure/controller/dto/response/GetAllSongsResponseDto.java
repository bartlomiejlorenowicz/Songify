package com.songify.song.infastructure.controller.dto.response;


import java.util.List;

public record GetAllSongsResponseDto(List<SongDto> songs) {
}
