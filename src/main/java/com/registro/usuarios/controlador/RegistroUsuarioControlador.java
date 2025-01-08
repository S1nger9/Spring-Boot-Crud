package com.registro.usuarios.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registro.usuarios.controlador.dto.UsuarioRegistroDTO;
import com.registro.usuarios.servicio.Servicios;

import excepcion.UsuarioExistenteException;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {

	private Servicios usuarioServicio;

	public RegistroUsuarioControlador(Servicios usuarioServicio) {
		super();
		this.usuarioServicio = usuarioServicio;
	}
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}

	@GetMapping
	public String mostrarFormularioDeRegistro() {
		return "registro";
	}
	
	//Este método maneja la validación al registrar una cuenta de usuario.
	@PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO,
                                           Model model) {
        try {
            usuarioServicio.guardar(registroDTO);
            return "redirect:/registro?exito";
        } catch (UsuarioExistenteException ex) {
            model.addAttribute("errorCorreoEnUso", true);
            return "registro";
        }
    }
}
