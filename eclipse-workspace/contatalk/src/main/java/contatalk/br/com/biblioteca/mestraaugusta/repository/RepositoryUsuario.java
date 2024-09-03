package contatalk.br.com.biblioteca.mestraaugusta.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.Usuario;

@Stateless
public class RepositoryUsuario {

	@PersistenceContext
	private EntityManager em;

	public Usuario store(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}

	public Usuario update(Usuario usuario) {
		em.merge(usuario);
		return usuario;
	}

	public Usuario findById(Integer id) {
		return em.find(Usuario.class, id);
	}

	public List<Usuario> getList(String filtro) {
		try {
			return em.createQuery("SELECT a FROM Usuario a WHERE 1 = 1 " + filtro, Usuario.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void delete(Integer id) {
		Usuario usuario = findById(id);

		if (usuario != null) {
			em.remove(usuario);
		}
	}

	public Usuario findByLogin(String email, String senha) {
		try {
			String sql = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha";
			return em.createQuery(sql, Usuario.class).setParameter("email", email).setParameter("senha", senha)
					.getSingleResult();
		} catch (NoResultException e) {
			return null; // Retorna null se nenhum resultado for encontrado
		}
	}
}
