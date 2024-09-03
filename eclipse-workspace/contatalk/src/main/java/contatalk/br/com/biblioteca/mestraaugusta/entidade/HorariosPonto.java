package contatalk.br.com.biblioteca.mestraaugusta.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "horarioPonto")
public class HorariosPonto implements Serializable {

	private static final long serialVersionUID = -4788504827181247070L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "dataHrCadastro")
	private Date dataHrCadastro;

	@Column(name = "horario", length = 64, nullable = false)
	private String horario;

	@Column(name = "dataPonto",nullable = false)
	private Date dataPonto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id" , nullable = false)
	private Usuario usuario;
	
	@Column(name = "entradaOuSaida" , nullable = false)
	private Integer entradaOuSaida; // 0 - ENTRADA , 1 - SAIDA

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

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Date getDataPonto() {
		return dataPonto;
	}

	public void setDataPonto(Date dataPonto) {
		this.dataPonto = dataPonto;
	}
	
	public Integer getEntradaOuSaida() {
		return entradaOuSaida;
	}

	public void setEntradaOuSaida(Integer entradaOuSaida) {
		this.entradaOuSaida = entradaOuSaida;
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
		HorariosPonto other = (HorariosPonto) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", dataHrCadastro=" + dataHrCadastro + ", horario=" + horario + "]";
	}
}
