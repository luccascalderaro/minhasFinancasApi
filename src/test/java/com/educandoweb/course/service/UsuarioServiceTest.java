package com.educandoweb.course.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.educandoweb.course.exception.ErroAutenticacao;
import com.educandoweb.course.exception.RegraNegocioException;
import com.educandoweb.course.model.entity.Usuario;
import com.educandoweb.course.model.repository.UsuarioRepository;
import com.educandoweb.course.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@BeforeEach
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
		
	}
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenario
		String email = "email@gmail.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = service.autenticar(email, senha);
		
		//verificacao
		Assertions.assertThat(result).isNotNull();
				
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		//cenario
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		
		//service.autenticar("email@email.com", "senha");
		
		ErroAutenticacao thrown = assertThrows(
				ErroAutenticacao.class,
		           () -> service.autenticar("email@email.com", "senha"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	
	
	@Test
	public void deveValidarEmail() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		//acao
		service.validarEmail("email@gmail.com");
		
		
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenario
		
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "aaaa"));
		
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha invalida");
		
		
		ErroAutenticacao thrown = assertThrows(
				ErroAutenticacao.class,
		           () -> service.autenticar("email@email.com", "aaaa")
		    );
	
	}
	
	
	@Test()
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		   // assertTrue(thrown.getMessage().contains("Stuff"));
		//cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		
		RegraNegocioException thrown = assertThrows(
				RegraNegocioException.class,
		           () -> service.validarEmail("email@gmail.com")
		    );
		
	}
}
