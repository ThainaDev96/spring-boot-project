package full_stack.spring_boot_project.exceptions;

public class ProgramaNaoEncontradoException extends RuntimeException {

    public ProgramaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}