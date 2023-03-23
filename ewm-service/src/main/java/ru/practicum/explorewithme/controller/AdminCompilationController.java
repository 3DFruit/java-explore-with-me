package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.compilation.CompilationDto;
import ru.practicum.explorewithme.model.compilation.NewCompilationDto;
import ru.practicum.explorewithme.model.compilation.UpdateCompilationRequest;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return null;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {

    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable Long compId,
                                           @RequestBody @Valid UpdateCompilationRequest request) {
        return null;
    }
}
