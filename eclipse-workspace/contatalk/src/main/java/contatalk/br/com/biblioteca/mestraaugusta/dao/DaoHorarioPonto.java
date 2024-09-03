package contatalk.br.com.biblioteca.mestraaugusta.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.HorariosPonto;
import contatalk.br.com.biblioteca.mestraaugusta.repository.RepositoryHorariosPonto;
import contatalk.br.com.biblioteca.mestraaugusta.util.VerificarUtil;


@Stateless
public class DaoHorarioPonto {

	@EJB
	private RepositoryHorariosPonto repositoryHorariosPonto;
	
	public HorariosPonto createOrUpdate(HorariosPonto horarioPonto) {
		if (VerificarUtil.verificarSeNulo(horarioPonto.getId())) {
			return create(horarioPonto);
		} else {
			return update(horarioPonto);
		}
	}
	
	private HorariosPonto create(HorariosPonto horarioPonto) {
		VerificarUtil.verificarSeNulo(horarioPonto);
		return repositoryHorariosPonto.store(horarioPonto);
	}
	
	public void delete(Integer id) {
		repositoryHorariosPonto.delete(id);
	}
	
	private HorariosPonto update(HorariosPonto horarioPonto) {
		VerificarUtil.verificarSeNulo(horarioPonto);
		VerificarUtil.verificarSeNulo(horarioPonto.getId());
		return repositoryHorariosPonto.update(horarioPonto);
	}
	
	public HorariosPonto findById(Integer id) {
		return repositoryHorariosPonto.findById(id);
	}
	
	public List<HorariosPonto> getList(Integer id){
		return repositoryHorariosPonto.getList(id);
	}
	
//	public HorariosPonto getByUsuario() {
//		
//	}
}
