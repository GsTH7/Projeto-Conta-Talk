package contatalk.br.com.biblioteca.mestraaugusta.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Usuario implements Serializable {

	private static final long serialVersionUID = -4788504827181247070L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "dataHrCadastro")
	private Date dataHrCadastro;

	@Column(name = "email", length = 64, nullable = false)
	private String email;

	@Column(name = "senha", length = 20, nullable = false)
	private String senha;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataHrCadastro() {
		return dataHrCadastro;
	}

	public void setDataHrCadastro(Date dataHrCadastro) {
		this.dataHrCadastro = dataHrCadastro;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", dataHrCadastro=" + dataHrCadastro + ", email=" + email + ", senha=" + senha
				+ "]";
	}

}
