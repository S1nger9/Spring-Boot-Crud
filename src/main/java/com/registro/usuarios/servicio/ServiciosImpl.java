package com.registro.usuarios.servicio;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registro.usuarios.controlador.dto.UsuarioRegistroDTO;
import com.registro.usuarios.modelo.Cliente;
import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.ClienteRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;

import excepcion.UsuarioExistenteException;


@Service
public class ServiciosImpl implements Servicios {

	    private UsuarioRepositorio usuarioRepositorio;
	    private ClienteRepositorio clienteRepositorio;

	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;

	    @Autowired//Qualifier sirve para indicar que bean se ha de inyectar
	    public ServiciosImpl(@Qualifier("usuarioRepositorio") UsuarioRepositorio usuarioRepositorio,
	                               @Qualifier("clienteRepositorio") ClienteRepositorio clienteRepositorio) {
	        this.usuarioRepositorio = usuarioRepositorio;
	        this.clienteRepositorio = clienteRepositorio;
	    }
	    @Override
	    public List<Cliente> listarClientes() {
	        // Implementación para listar clientes
	        return clienteRepositorio.findAll();
	    }
	    
	    @Override
	    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
	        Usuario usuarioExistente = usuarioRepositorio.findByEmail(registroDTO.getEmail());

	        if (usuarioExistente != null) {
	            throw new UsuarioExistenteException("El correo electrónico ya está en uso. Introduce uno diferente.");
	        } else {
	            Usuario usuario = new Usuario(registroDTO.getNombre(),
	                    registroDTO.getApellido(), registroDTO.getEmail(),
	                    passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(new Rol("ROLE_USER")));
	            return usuarioRepositorio.save(usuario);
	        }
	    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(usuario.getEmail(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}
	
	//
	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
	
	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepositorio.findAll();
	}
	
	 @Override
	    public Cliente guardarCliente(Cliente cliente) {
	        return clienteRepositorio.save(cliente);
	    }
	 
	 @Override
	    public void eliminarClientePorId(Long id) {
	        clienteRepositorio.deleteById(id);
	    }
	 @Override
	    public Cliente encontrarClientePorId(Long id) {
	        return clienteRepositorio.findById(id)
	                .orElseThrow();
	    }
	 @Override
	 public Cliente editarCliente(Cliente cliente) {
	     // Optional es una clase que sirve para representar un contenedor que puede estar vacio.
	     Optional<Cliente> clienteExistente = clienteRepositorio.findById(cliente.getId());
	     
	     if (clienteExistente.isPresent()) {
	         Cliente clienteActualizado = clienteExistente.get();
	         
	         // Actualizar los campos del cliente con la información recibida
	         clienteActualizado.setNombre(cliente.getNombre());
	         clienteActualizado.setApellido(cliente.getApellido());
	         clienteActualizado.setEmail(cliente.getEmail());
	         clienteActualizado.setImagen(cliente.getImagen());
	         // Guardar los cambios realizados en el cliente
	         return clienteRepositorio.save(clienteActualizado);
	     } else {
	         // Si no se encuentra el cliente, retorna null o realiza alguna acción según tu lógica de negocio
	         return null; // O realiza alguna otra acción
	     }
	 }
}
