package com.registro.usuarios.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.registro.usuarios.modelo.Cliente;
import com.registro.usuarios.servicio.Servicios;

@Controller
public class PaginasControlador {
	 @Autowired
	    private Servicios servicio; 
	 
	    //Estos métodos manejan el inicio de sesion, pagina de inicio y el formulario del cliente.
		@GetMapping("/login")
		public String iniciarSesion() {
			return "login";
		}
		
		@GetMapping("/")
		public String verPaginaDeInicio(Model modelo) {
			modelo.addAttribute("usuarios", servicio.listarUsuarios());
			return "index";
		}
		
		 @GetMapping("/cliente/new")
			public String nuevoUsuarioForm(Model model) {
				model.addAttribute("clienteForm", new Cliente());
				return "form";
			}

}
