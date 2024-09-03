package contatalk.br.com.biblioteca.mestraaugusta.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.HorariosPonto;

@Stateless
public class RepositoryHorariosPonto {

	@PersistenceContext
	private EntityManager em;

	public HorariosPonto store(HorariosPonto horariosPonto) {
		em.persist(horariosPonto);
		return horariosPonto;
	}

	public HorariosPonto update(HorariosPonto horariosPonto) {
		em.merge(horariosPonto);
		return horariosPonto;
	}

	public HorariosPonto findById(Integer id) {
		return em.find(HorariosPonto.class, id);
	}

	public List<HorariosPonto> getList(Integer id) {
		try {
			return em.createQuery("SELECT a FROM HorariosPonto a WHERE a.usuario.id= " + id, HorariosPonto.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void delete(Integer id) {
		HorariosPonto horariosPonto = findById(id);

		if (horariosPonto != null) {
			em.remove(horariosPonto);
		}
	}
}
