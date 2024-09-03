package contatalk.br.com.biblioteca.mestraaugusta.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "aluno", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "escola"})})
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum Sexo {
		Masculino, Feminino;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "dataHrCadastro")
	private Date dataHrCadastro;
	
	@Column(name = "nome", length = 64)
	private String nome;
	
	@Column(name = "idade")
	private Integer idade;
	
	@Column(name = "turma")
	private Integer turma;
	
	@Column(name = "escola")
	private Integer escola;
	
	@Enumerated
	@Column(name = "sexo", nullable = false, length = 1)
	private Sexo sexo;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getTurma() {
		return turma;
	}

	public void setTurma(Integer turma) {
		this.turma = turma;
	}

	public Integer getEscola() {
		return escola;
	}

	public void setEscola(Integer escola) {
		this.escola = escola;
	}
	
	public Sexo getSexo() {
		return sexo;
	}
	
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
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
		Aluno other = (Aluno) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", dataHrCadastro=" + dataHrCadastro + ", nome=" + nome + ", idade=" + idade
				+ ", turma=" + turma + ", escola=" + escola + ", sexo=" + sexo + "]";
	}
	
}
