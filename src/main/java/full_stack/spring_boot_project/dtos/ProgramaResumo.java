package full_stack.spring_boot_project.dtos;

//O nome é o que a pessoa lê na tela (“Bolsa Família”).
//O codigo é o que o site usa por trás para pedir os detalhes: quando a pessoa clica em “Bolsa Família”, o site abre /api/programas/bolsa-familia.
public record ProgramaResumo(String codigo, String nome) {
}
