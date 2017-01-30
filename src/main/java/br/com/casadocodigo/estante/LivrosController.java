package br.com.casadocodigo.estante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.casadocodigo.registro.Usuario;
import br.com.casadocodigo.registro.Usuarios;
import br.com.casadocodigo.seguranca.UsuarioLogado;

@Controller
@RequestMapping("/livros")
public class LivrosController {

	@Autowired
	private Usuarios usuarios;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> livros() {

		Estante estante = usuarioLogado().getEstante();

		if (estante.temLivros()) {
			return new ResponseEntity<>(estante.todosLivros(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> adicionarLivro(@RequestBody Livro livro) {
		Usuario usuario = usuarioLogado();
		
		usuario.getEstante().adicionar(livro);

		usuarios.atualizar(usuario);
		
		return new ResponseEntity<>(livro, HttpStatus.CREATED);
	}

	private Usuario usuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();

		return usuarios.buscarPorID(usuarioLogado.getId());
	}
}
