package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import contatalk.br.com.biblioteca.mestraaugusta.dao.DaoUsuario;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Usuario;

@Named()
@ViewScoped
public class BeanCadastro extends BeanMain {

	private static final long serialVersionUID = 6473128796163533213L;

	@EJB
	private DaoUsuario daoUsuario;

	private Usuario us;

	// Filtros
	@Email
	private String email;
	private String confirmaEmail;
	private String senha;
	private String confirmaSenha;

	private String emailLogin;
	private String senhaLogin;

	@PostConstruct
	public void init() {
	}

	public void cadastrarUsuario() {
		try {
			if (email == null || confirmaEmail == null) {
				messageError("E-mail não pode ser nulo.");
				return;
			}

			if (!email.equals(confirmaEmail)) {
				messageError("Informe o e-mail corretamente.");
				return;
			}

			if (senha == null || confirmaSenha == null) {
				messageError("Senha não pode ser nula.");
				return;
			}

			if (!senha.equals(confirmaSenha)) {
				messageError("Informe senhas iguais.");
				return;
			}

			// Validando que os campos são preenchidos corretamente
			if (!email.isEmpty() && !senha.isEmpty()) {
				us = new Usuario();
				us.setDataHrCadastro(new Date());
				us.setEmail(email.trim().toLowerCase());
				us.setSenha(senha);
				us = daoUsuario.createOrUpdate(us);
				messageInfo("Salvo com sucesso!");
				FacesContext.getCurrentInstance().getExternalContext().redirect("/contatalk/sistema/acesso/login.xhtml");
			} else {
				messageError("Erro ao salvar! Campos vazios.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			messageError("Erro inesperado ao salvar usuário. Tente novamente.");
		}
	}

	public void doLogin() {
		try {

			us = daoUsuario.findByLogin(emailLogin.trim().toLowerCase(), senhaLogin);

			if (us != null) {

				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(true);
				session.setAttribute("usuarioLogado", true);
				session.setAttribute("Login", us);

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/contatalk/sistema/views/acesso.xhtml");

			} else {
				messageError("Login não existe!");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		// Redireciona para a página de login
		FacesContext.getCurrentInstance().getExternalContext().redirect("/contatalk/sistema/acesso/login.xhtml");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaEmail() {
		return confirmaEmail;
	}

	public void setConfirmaEmail(String confirmaEmail) {
		this.confirmaEmail = confirmaEmail;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getEmailLogin() {
		return emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}

	public String getSenhaLogin() {
		return senhaLogin;
	}

	public void setSenhaLogin(String senhaLogin) {
		this.senhaLogin = senhaLogin;
	}
}
