package academy.devdojo.springboot2essentials.Handler;

import academy.devdojo.springboot2essentials.Exception.BadRequestException;
import academy.devdojo.springboot2essentials.Exception.BadRequestExceptionDetails;
import academy.devdojo.springboot2essentials.Exception.ExceptionDetails;
import academy.devdojo.springboot2essentials.Exception.ValidationExceptionDetails;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.header;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException){
    return new ResponseEntity<> (BadRequestExceptionDetails.builder().
            timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception")
            .details(badRequestException.getMessage())
            .developerMessage(badRequestException.getClass().getName())
            .build(), HttpStatus.BAD_REQUEST);
    }
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining( ", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining( ", "));
        
        return new ResponseEntity<> (ValidationExceptionDetails.builder().
            timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request InvalidFields")
            .details(exception.getMessage())
            .developerMessage(exception.getClass().getName())
            .fields(fields)
            .fieldMessage(fieldsMessage)
            .build(), HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
    Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request){
    ExceptionDetails exceptionDetails =  ExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .title(ex.getCause().getMessage())
            .details(ex.getMessage())
            .developerMessage(ex.getClass().getName())
            .build();

    return new ResponseEntity<>(exceptionDetails, headers, status);
}
}    

