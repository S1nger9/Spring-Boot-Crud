package com.registro.usuarios.servicio;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registro.usuarios.controlador.dto.UsuarioRegistroDTO;
import com.registro.usuarios.modelo.Cliente;
import com.registro.usuarios.modelo.Usuario;

//Aqui UserDetailsService  proporciona funcionalidad relacionada con la gestión de usuarios y clientes y detalles de autenticación.
public interface Servicios extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<Usuario> listarUsuarios();

	Cliente guardarCliente(Cliente cliente);
	
	List<Cliente> listarClientes();

	void eliminarClientePorId(Long id);

	Cliente encontrarClientePorId(Long id);

	Cliente editarCliente(Cliente cliente);
}
