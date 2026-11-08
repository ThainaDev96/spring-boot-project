package full_stack.spring_boot_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProgramaNaoEncontradoException.class)
    public ProblemDetail programaNaoEncontrado(ProgramaNaoEncontradoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({GovBrIndisponivelException.class, RestClientException.class})
    public ProblemDetail govBrIndisponivel(Exception ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_GATEWAY,
                "Não foi possível consultar o gov.br no momento. Tente novamente mais tarde.");
    }
}