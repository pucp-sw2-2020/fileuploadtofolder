package sw2.fileuploadtofolder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sw2.fileuploadtofolder.entity.Empleado;
import sw2.fileuploadtofolder.repository.EmpleadoRepository;
import sw2.fileuploadtofolder.util.StorageService;

import java.util.HashMap;

@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    StorageService storageService;

    @GetMapping({"/", "lista"})
    public String listaEmpleados(Model model) {
        model.addAttribute("listaEmpleados", empleadoRepository.findAll());
        return "lista";
    }

    @GetMapping("/nuevo")
    public String nuevoEmpleado() {
        return "nuevoForm";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@RequestParam("archivo") MultipartFile file,
                                  Empleado empleado, Model model) {

        HashMap<String, String> map = storageService.store(file);
        if (map.get("estado").equals("exito")) {
            empleado.setFoto(map.get("fileName"));
            empleadoRepository.save(empleado);
            return "redirect:/";
        } else {
            //existe error
            model.addAttribute("msg", map.get("msg"));
            return "nuevoForm";
        }
    }

}
