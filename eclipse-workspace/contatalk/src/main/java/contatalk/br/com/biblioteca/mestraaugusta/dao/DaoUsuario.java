package contatalk.br.com.biblioteca.mestraaugusta.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import contatalk.br.com.biblioteca.mestraaugusta.entidade.Usuario;
import contatalk.br.com.biblioteca.mestraaugusta.repository.RepositoryUsuario;
import contatalk.br.com.biblioteca.mestraaugusta.util.VerificarUtil;


@Stateless
public class DaoUsuario {

	@EJB
	private RepositoryUsuario repositoryUsuario;
	
	public Usuario createOrUpdate(Usuario usuario) {
		if (VerificarUtil.verificarSeNulo(usuario.getId())) {
			return create(usuario);
		} else {
			return update(usuario);
		}
	}
	
	private Usuario create(Usuario usuario) {
		VerificarUtil.verificarSeNulo(usuario);
		return repositoryUsuario.store(usuario);
	}
	
	public void delete(Integer id) {
		repositoryUsuario.delete(id);
	}
	
	private Usuario update(Usuario usuario) {
		VerificarUtil.verificarSeNulo(usuario);
		VerificarUtil.verificarSeNulo(usuario.getId());
		return repositoryUsuario.update(usuario);
	}
	
	public Usuario findById(Integer id) {
		return repositoryUsuario.findById(id);
	}
	
	public List<Usuario> getList(String filtro){
		return repositoryUsuario.getList(filtro);
	}
	
	public Usuario findByLogin(String email, String senha) {
		return repositoryUsuario.findByLogin(email, senha);
	}
	
}
