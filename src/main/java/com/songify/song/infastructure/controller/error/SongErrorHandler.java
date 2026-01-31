package com.songify.song.infastructure.controller.error;

import com.songify.song.infastructure.controller.SongsController;
import com.songify.song.infastructure.controller.dto.response.ErrorSongResponseDto;
import com.songify.song.domain.model.SongNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = SongsController.class)
public class SongErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(SongErrorHandler.class);

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorSongResponseDto handleSongNotFoundException(SongNotFoundException exception) {
        log.warn("SongNotFoundException while accessing song");
        return new ErrorSongResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

