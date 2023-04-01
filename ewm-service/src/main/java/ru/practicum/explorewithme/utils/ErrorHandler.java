package ru.practicum.explorewithme.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.model.exception.ObjectNotFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MethodArgumentNotValidException e) {
        log.error("Ошибка запроса: {}", e.getMessage(), e);
        return new ApiError(e.getMessage(),
                "Ошибка запроса",
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now().format(CommonUtils.DATE_TIME_FORMATTER),
                getErrors(e));
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleObjectNotFound(final Exception e) {
        log.error("Запрашиваемый объект не найден {}", e.getMessage(), e);
        return new ApiError(e.getMessage(),
                "Запрашиваемый объект не найден",
                HttpStatus.NOT_FOUND.name(),
                LocalDateTime.now().format(CommonUtils.DATE_TIME_FORMATTER),
                getErrors(e));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Exception e) {
        log.error("Необработанная ошибка: {}", e.getMessage(), e);
        return new ApiError(e.getMessage(),
                "Непредвиденная ошибка",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                LocalDateTime.now().format(CommonUtils.DATE_TIME_FORMATTER),
                getErrors(e));
    }

    private String getErrors(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
