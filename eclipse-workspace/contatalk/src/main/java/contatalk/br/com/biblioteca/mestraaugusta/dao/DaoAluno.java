package contatalk.br.com.biblioteca.mestraaugusta.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno;
import contatalk.br.com.biblioteca.mestraaugusta.repository.RepositoryAluno;
import contatalk.br.com.biblioteca.mestraaugusta.util.VerificarUtil;


@Stateless
public class DaoAluno {

	@EJB
	private RepositoryAluno repositoryAluno;
	
	public Aluno createOrUpdate(Aluno aluno) {
		if (VerificarUtil.verificarSeNulo(aluno.getId())) {
			return create(aluno);
		} else {
			return update(aluno);
		}
	}
	
	private Aluno create(Aluno aluno) {
		VerificarUtil.verificarSeNulo(aluno);
		return repositoryAluno.store(aluno);
	}
	
	public void delete(Integer id) {
		repositoryAluno.delete(id);
	}
	
	private Aluno update(Aluno aluno) {
		VerificarUtil.verificarSeNulo(aluno);
		VerificarUtil.verificarSeNulo(aluno.getId());
		return repositoryAluno.update(aluno);
	}
	
	public Aluno findById(Integer id) {
		return repositoryAluno.findById(id);
	}
	
	public List<Aluno> getList(String filtro){
		return repositoryAluno.getList(filtro);
	}
	
}
