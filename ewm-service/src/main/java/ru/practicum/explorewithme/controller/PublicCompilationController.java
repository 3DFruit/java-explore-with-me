package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.compilation.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@Validated
public class PublicCompilationController {
    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10") @Positive Integer size) {
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Integer compId) {
        return null;
    }
}
