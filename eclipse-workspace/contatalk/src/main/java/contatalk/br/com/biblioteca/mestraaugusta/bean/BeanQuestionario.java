package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import contatalk.br.com.biblioteca.mestraaugusta.dao.DaoAluno;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno.Sexo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapEscolas;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapFrequencia;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapGenero;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapHabitoLeitura;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapIncentivoPais;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoNegativo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoPositivo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapTurmas;


@Named()
@ViewScoped
public class BeanQuestionario extends BeanMain {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private DaoAluno daoAluno;
	
	//Primeiro Tab
	private Map<Object, Object> listaTurmas, listaEscolas;
	
	private String nome;
	private Sexo sexo;
	private Integer idade, turmaSelecionada, escolaSelecionada;
	
	//Segundo Tab
	private Map<Object, Object> listaHabitoLeitura, listaFrequencia, listaIncentivoPais, listaGenero,
			listaPontoPositivo, listaPontoNegativo;
	
	private Integer habitoLeitura, frequencia, incentivoPais, genero, pontoPositivo, pontoNegativo;
	
	//Auxiliar
	private Integer tabIndex = 0;
	
	@PostConstruct
	public void init() {
		iniciarListas();
	}
	
	private void iniciarListas() {
		listaTurmas = new MapTurmas().getMap();
		listaEscolas = new MapEscolas().getMap();
		listaHabitoLeitura = new MapHabitoLeitura().getMap();
		listaFrequencia = new MapFrequencia().getMap();
		listaIncentivoPais = new MapIncentivoPais().getMap();
		listaGenero = new MapGenero().getMap();
		listaPontoPositivo = new MapPontoPositivo().getMap();
		listaPontoNegativo = new MapPontoNegativo().getMap();
	}
	
	public String descricaoButtonNext() {
		return tabIndex == 0 ? "Próximo" : "Concluir";
	}
	
	public void voltar() {
		tabIndex--;
	}
	
	public void proximo() {
		try {
			if(verificar()) {
				tabIndex++;
				
				if(tabIndex == 2) {
					salvarDados();
				}
			}
			
		} catch (Exception e) {
			messageError("Ocorreu um erro interno: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private boolean verificar() throws Exception{
		if(tabIndex == 0) {
			return verificarTab0();
		}else if(tabIndex == 1) {
			return verificarTab1();
		}
		return true;
	}
	
	private boolean verificarTab0() {
		if(!verificarCampoObrigatorio(":form1:nome", "Favor ir ao campo de Nome Completo e preenche-lo. Lembre-se que deve ser o nome completo.", nome.trim().length() < 10)) {
			return false;
		}
		
		if(!verificarCampoObrigatorio(":form1:idade", "Favor preencher uma idade válida. É aceito entre 4 e 12 anos de idade.", idade != null ? (idade < 4 || idade > 12) : null)) {
			return false;
		}
		
		if(!verificarCampoObrigatorio(":form1:turma", "Favor selecionar a turma.")) {
			return false;
		}
		
		if(!verificarCampoObrigatorio(":form1:escola", "Favor selecionar a escola.")) {
			return false;
		}
		
		return true;
	}
	
	private boolean verificarTab1() {
		Map<String, String> camposObrigatorios = new LinkedHashMap<>();
	    camposObrigatorios.put(":form1:habitoLeitura", "Favor selecionar opção para responder: Você lê bastante?");
	    camposObrigatorios.put(":form1:frequencia", "Favor selecionar opção para responder: Frequenta a biblioteca?");
	    camposObrigatorios.put(":form1:incentivoPais", "Favor selecionar opção para responder: Seus pais te incentivam a ler?");
	    camposObrigatorios.put(":form1:genero", "Favor selecionar opção para responder: Qual tipo de livro você mais gosta de ler?");
	    camposObrigatorios.put(":form1:pontoPositivo", "Favor selecionar opção para responder: O que te faz gostar de um livro?");
	    camposObrigatorios.put(":form1:pontoNegativo", "Favor selecionar opção para responder: E o que faz você não gostar?");
	    
	    for (Map.Entry<String, String> entry : camposObrigatorios.entrySet()) {
	        String idDoCampo = entry.getKey();
	        String mensagemErro = entry.getValue();
	        
	        if (!verificarCampoObrigatorio(idDoCampo, mensagemErro)) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
	private void salvarDados() throws Exception{
		Aluno aluno = new Aluno();
		salvarDadosAluno(aluno);
	}
	
	private void salvarDadosAluno(Aluno aluno) throws Exception{
		aluno.setDataHrCadastro(new Date());
		aluno.setNome(nome);
		aluno.setIdade(idade);
		aluno.setTurma(turmaSelecionada);
		aluno.setEscola(escolaSelecionada);
		aluno.setSexo(sexo);
		
		aluno = daoAluno.createOrUpdate(aluno);
	}
	
	public Map<Object, Object> getListaTurmas() {
		return listaTurmas;
	}

	public void setListaTurmas(Map<Object, Object> listaTurmas) {
		this.listaTurmas = listaTurmas;
	}

	public Map<Object, Object> getListaEscolas() {
		return listaEscolas;
	}

	public void setListaEscolas(Map<Object, Object> listaEscolas) {
		this.listaEscolas = listaEscolas;
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

	public Integer getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Integer turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public Integer getEscolaSelecionada() {
		return escolaSelecionada;
	}

	public void setEscolaSelecionada(Integer escolaSelecionada) {
		this.escolaSelecionada = escolaSelecionada;
	}
	
	public Integer getTabIndex() {
		return tabIndex;
	}
	
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Map<Object, Object> getListaHabitoLeitura() {
		return listaHabitoLeitura;
	}

	public void setListaHabitoLeitura(Map<Object, Object> listaHabitoLeitura) {
		this.listaHabitoLeitura = listaHabitoLeitura;
	}

	public Map<Object, Object> getListaFrequencia() {
		return listaFrequencia;
	}

	public void setListaFrequencia(Map<Object, Object> listaFrequencia) {
		this.listaFrequencia = listaFrequencia;
	}

	public Map<Object, Object> getListaIncentivoPais() {
		return listaIncentivoPais;
	}

	public void setListaIncentivoPais(Map<Object, Object> listaIncentivoPais) {
		this.listaIncentivoPais = listaIncentivoPais;
	}

	public Map<Object, Object> getListaGenero() {
		return listaGenero;
	}

	public void setListaGenero(Map<Object, Object> listaGenero) {
		this.listaGenero = listaGenero;
	}

	public Map<Object, Object> getListaPontoPositivo() {
		return listaPontoPositivo;
	}

	public void setListaPontoPositivo(Map<Object, Object> listaPontoPositivo) {
		this.listaPontoPositivo = listaPontoPositivo;
	}

	public Map<Object, Object> getListaPontoNegativo() {
		return listaPontoNegativo;
	}

	public void setListaPontoNegativo(Map<Object, Object> listaPontoNegativo) {
		this.listaPontoNegativo = listaPontoNegativo;
	}

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
	}

	public Integer getHabitoLeitura() {
		return habitoLeitura;
	}

	public void setHabitoLeitura(Integer habitoLeitura) {
		this.habitoLeitura = habitoLeitura;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getIncentivoPais() {
		return incentivoPais;
	}

	public void setIncentivoPais(Integer incentivoPais) {
		this.incentivoPais = incentivoPais;
	}

	public Integer getPontoPositivo() {
		return pontoPositivo;
	}

	public void setPontoPositivo(Integer pontoPositivo) {
		this.pontoPositivo = pontoPositivo;
	}

	public Integer getPontoNegativo() {
		return pontoNegativo;
	}

	public void setPontoNegativo(Integer pontoNegativo) {
		this.pontoNegativo = pontoNegativo;
	}
	
	public Sexo[] getSexos() {
		return Sexo.values();
	}
	
	public Sexo getSexo() {
		return sexo;
	}
	
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
}
