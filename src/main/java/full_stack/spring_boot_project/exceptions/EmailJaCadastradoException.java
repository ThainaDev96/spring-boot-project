package full_stack.spring_boot_project.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}