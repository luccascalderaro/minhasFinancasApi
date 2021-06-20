package com.educandoweb.course.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.educandoweb.course.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
@Autowired	
UsuarioRepository repository;

@Autowired
TestEntityManager entityManager;
	
@Test
public void deveVerificarAExistenciaDeUmEmail() {
	//cenário
	Usuario usuario = criarUsuario();
	entityManager.persist(usuario);
	
	
	//ação
	
	boolean result = repository.existsByEmail(usuario.getEmail());
	
	//verificação
	
	Assertions.assertThat(result).isTrue();
}

	@Test
	public void deveRetornarFalsoQuandoNaoHouveUsuarioCadastradoComOEmail() {
		//cenario
	
		
		//acao
		boolean result = repository.existsByEmail("usuario@gmail.com");
		
		//Verificacao
		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//cenario
		
		Usuario usuario = criarUsuario();
		
		//acao
		
		Usuario usuarioSalvo = repository.save(usuario);
		
		
		//verificacaao
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();	
	}
	
	public static Usuario criarUsuario() {
		//cenario
		
			return	Usuario
						.builder()
						.nome("usuario")
						.email("usuario@gmail.com")
						.senha("123")
						.build();
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//verificacao
		 Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
		 
		 Assertions.assertThat(result.isPresent()).isTrue();
		 
		
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExistirNaBase() {
		
		//cenario
	
		//verificacao
		 Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
		 
		 Assertions.assertThat(result.isPresent()).isFalse();
		 
		
	}
	
}
