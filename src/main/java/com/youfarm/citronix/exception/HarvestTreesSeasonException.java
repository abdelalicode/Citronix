package com.youfarm.citronix.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class HarvestTreesSeasonException extends RuntimeException {

    private final Map<Long, LocalDate> treesConflicted;
    private final String message;

    public HarvestTreesSeasonException(Map<Long, LocalDate> treesConflicted, String message) {
        this.treesConflicted = treesConflicted;
        this.message = message;
    }

}
