package full_stack.spring_boot_project.controllers;

import full_stack.spring_boot_project.services.ProgramaService;
import full_stack.spring_boot_project.dtos.ServicoGovResponse;
import full_stack.spring_boot_project.models.ProgramaSocial;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import full_stack.spring_boot_project.dtos.ProgramaResumo;
import java.util.List;

@RestController
@RequestMapping("/api/programas")
public class ProgramaController {

    private final ProgramaService programaService;

    public ProgramaController(ProgramaService programaService) {
        this.programaService = programaService;
    }

    @GetMapping
    public List<ProgramaResumo> listar() {
        return programaService.listar();
    }

    //Monta a URL no navegador, liga um endereço da internet a um métod Java.
    @GetMapping("/{codigo}")
    public ServicoGovResponse.Servico buscar(@PathVariable String codigo) {
        ProgramaSocial programa = ProgramaSocial.porCodigo(codigo);
        return programaService.buscar(programa);
    }
}