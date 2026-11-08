package full_stack.spring_boot_project.models;
import full_stack.spring_boot_project.exceptions.ProgramaNaoEncontradoException;

public enum ProgramaSocial {

    PROUNI(57, "ProUni"),
    SISU(546, "Sisu"),
    FIES(56, "Fies"),
    BOLSA_FAMILIA(12169, "Bolsa Família"),
    CADASTRO_UNICO(969, "Cadastro Único"),
    AUXILIO_GAS(24137, "Auxílio Gás"),
    BPC_IDOSO(2999, "BPC/LOAS Idoso"),
    BPC_DEFICIENCIA(2997, "BPC/LOAS Deficiência"),
    PE_DE_MEIA(13413, "Pé-de-Meia");

    private final int id;
    private final String nome;

    ProgramaSocial(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    //getCodigo converte do enum para o formato de URL: BOLSA_FAMILIA vira bolsa-familia.
    public String getCodigo() {
        return name().toLowerCase().replace('_', '-');
    }

    //porCodigo faz o caminho de volta: recebe o texto da URL e procura qual programa tem aquele código.
    public static ProgramaSocial porCodigo(String codigo) {
        for (ProgramaSocial programa : values()) {
            if (programa.getCodigo().equals(codigo.toLowerCase())) {
                return programa;
            }
        }
        throw new ProgramaNaoEncontradoException("Programa não encontrado: " + codigo);
    }
}
