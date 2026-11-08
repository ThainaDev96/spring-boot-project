package full_stack.spring_boot_project.exceptions;

public class GovBrIndisponivelException extends RuntimeException {

    public GovBrIndisponivelException(String mensagem) {
        super(mensagem);
    }
}