package com.registro.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registro.usuarios.modelo.Cliente;

@Repository//Un repositorio es una clase que permite hacer consultas a la bbdd
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
	
	
}
