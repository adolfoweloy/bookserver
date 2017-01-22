package br.com.casadocodigo.registro;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class Usuarios implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	public Usuario registrar(DadosDeRegistro dadosDeRegistro) {

		Credenciais credenciais = new Credenciais(
			dadosDeRegistro.getEmail(), 
			dadosDeRegistro.getSenha());

		Usuario novoUsuario = new Usuario(
			dadosDeRegistro.getNome(), credenciais);

		em.persist(novoUsuario);
		
		return novoUsuario;
	}

	public Usuario buscarPorID(long id) {
		return em.find(Usuario.class, id);
	}

	public void atualizar(Usuario usuario) {
		em.persist(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TypedQuery<Usuario> query = em.createQuery(
                "select u from Usuario u where u.credenciais.email = :email", Usuario.class);
        query.setParameter("email", email);

        return query.getSingleResult();
	}

}
