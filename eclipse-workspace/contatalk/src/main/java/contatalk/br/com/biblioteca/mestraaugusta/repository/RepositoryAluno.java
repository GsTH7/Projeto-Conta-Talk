package contatalk.br.com.biblioteca.mestraaugusta.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno;

@Stateless
public class RepositoryAluno {

	@PersistenceContext
	private EntityManager em;

	public Aluno store(Aluno aluno) {
		em.persist(aluno);
		return aluno;
	}

	public Aluno update(Aluno aluno) {
		em.merge(aluno);
		return aluno;
	}

	public Aluno findById(Integer id) {
		return em.find(Aluno.class, id);
	}

	public List<Aluno> getList(String filtro) {
		try {
			return em.createQuery("SELECT a FROM Aluno a WHERE 1 = 1 " + filtro,
					Aluno.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void delete(Integer id) {
		Aluno aluno = findById(id);

		if (aluno != null) {
			em.remove(aluno);
		}
	}
}
