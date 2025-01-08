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
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.servicio.Servicios;

@Controller
public class ClienteControlador {
	 @Autowired
	    private Servicios servicio; 
	 
	 @Autowired
	    public ClienteControlador(Servicios servicio) {
	        this.servicio = servicio;
	    }
	 @PostMapping("/new/submit")
	 public String nuevoClienteSubmit(Cliente cliente) {
	     servicio.guardarCliente(cliente);
	     return "redirect:/list";
	 }
	 
	 //Este método devuelve la lista de clientes.
	 @GetMapping("/list")
	    public String listado(Model model) {
	        List<Cliente> listaClientes = servicio.listarClientes();
	        model.addAttribute("listaClientes", listaClientes);
	        return "list"; // Devuelve el nombre del archivo HTML (list.html)
	    }
	 @PostMapping("/cliente/{id}/eliminar")
	    public String eliminarCliente(@PathVariable("id") Long id) {
	        servicio.eliminarClientePorId(id);
	        return "redirect:/list";
	    }
	 
	 	@GetMapping("/cliente/{id}/editar")
	    public String editarCliente(@PathVariable("id") Long id, Model model) {
	        Cliente cliente = servicio.encontrarClientePorId(id);
	        model.addAttribute("cliente", cliente);
	        return "editar";
	    }
	    
	    @PostMapping("/cliente/{id}/editar")
	    public String editarClienteSubmit(@PathVariable("id") Long id, @ModelAttribute("cliente") Cliente clienteForm) {
	        Cliente cliente = servicio.encontrarClientePorId(id);
	        cliente.setApellido(clienteForm.getApellido());
	        cliente.setNombre(clienteForm.getNombre());
	        cliente.setEmail(clienteForm.getEmail());
	        cliente.setImagen(clienteForm.getImagen());
	        
	        
	        servicio.guardarCliente(cliente);
	        return "redirect:/list";
	    }

	    

}
