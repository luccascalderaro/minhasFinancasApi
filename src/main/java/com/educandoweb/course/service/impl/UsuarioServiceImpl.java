package com.educandoweb.course.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.educandoweb.course.exception.ErroAutenticacao;
import com.educandoweb.course.exception.RegraNegocioException;
import com.educandoweb.course.model.entity.Usuario;
import com.educandoweb.course.model.repository.UsuarioRepository;
import com.educandoweb.course.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuario nao encontrado");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha invalida");
		}
		
		// TODO Auto-generated method stub
		return usuario.get();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		
		boolean existe = repository.existsByEmail(email);
		
		if(existe) {
			throw new RegraNegocioException("Já existe um usuario com esse email");
		}
		
	}

}
