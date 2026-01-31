package com.songify.song.infastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongsRequest(
        @NotNull(message = "Song name cannot be null")
        @NotEmpty(message = "Song name cannot be empty")
        String songName,
        @NotNull(message = "Song artist cannot be null")
        @NotEmpty(message = "Song artist cannot be empty")
        String artist
) {
}
