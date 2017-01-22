package br.com.casadocodigo.registro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private Usuarios usuarios;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> registrar(@RequestBody DadosDeRegistro dadosDeRegistro) {

		Usuario usuario = usuarios.registrar(dadosDeRegistro);
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);

	}

}
