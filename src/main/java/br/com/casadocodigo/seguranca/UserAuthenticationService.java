package br.com.casadocodigo.seguranca;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.casadocodigo.registro.Usuario;
import br.com.casadocodigo.registro.Usuarios;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {

		Optional<Usuario> usuario = usuarios.buscarPorEmail(email);
		
		if (usuario.isPresent()) {
			return new UsuarioLogado(usuario.get());
		} else {
			throw new UsernameNotFoundException("usuario não autorizado");
		}
	}

}
