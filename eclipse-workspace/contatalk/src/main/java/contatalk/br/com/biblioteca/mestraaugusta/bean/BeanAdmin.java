package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import contatalk.br.com.biblioteca.mestraaugusta.dao.DaoAluno;
import contatalk.br.com.biblioteca.mestraaugusta.dao.DaoHorarioPonto;
import contatalk.br.com.biblioteca.mestraaugusta.dao.DaoUsuario;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Aluno.Sexo;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.HorariosPonto;
import contatalk.br.com.biblioteca.mestraaugusta.entidade.Usuario;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapFrequencia;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapGenero;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapHabitoLeitura;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapIncentivoPais;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoNegativo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoPositivo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapTurmas;

@Named()
@ViewScoped
public class BeanAdmin extends BeanMain {

	private static final long serialVersionUID = 1L;

	@EJB
	private DaoAluno daoAluno;
	@EJB
	private DaoUsuario daoUsuario;
	@EJB
	private DaoHorarioPonto daoHorarioPonto;

	private Usuario u;
	private HorariosPonto hp;

	// Filtros
	private Map<Object, Object> listaHabitoLeitura, listaFrequencia, listaIncentivoPais, listaGenero,
			listaPontoPositivo, listaPontoNegativo;

	private Sexo sexo;
	private Integer idadeInicial, idadeFinal, habitoLeitura, frequencia, incentivoPais, genero, pontoPositivo,
			pontoNegativo;
	private Date dataInicial, dataFinal;
	private Integer valores;
	private String mensagem;
	private Boolean renderizaMensagem = false;
	private Date data = new Date();
	private String selecionado;
	private String value;
	private String horario;
	private String horarioSaida;
	private Integer radio = 0;
	private Boolean localizacao = false;

	// Tabs
	private BarChartModel graficoPorEscolas, graficoTotal;

	private List<Aluno> listaAlunos;

	// Auxiliar
	private StringBuilder filtro = new StringBuilder("");

	@PostConstruct
	public void init() {
		iniciarListas();
		iniciarGraficos();
		carregarPessoa();
	}

	private void iniciarListas() {
		listaHabitoLeitura = new MapHabitoLeitura().getMap();
		listaFrequencia = new MapFrequencia().getMap();
		listaIncentivoPais = new MapIncentivoPais().getMap();
		listaGenero = new MapGenero().getMap();
		listaPontoPositivo = new MapPontoPositivo().getMap();
		listaPontoNegativo = new MapPontoNegativo().getMap();

		listaAlunos = daoAluno.getList("");
	}

	private void iniciarGraficos() {
		try {
			iniciarPorEscolas();
			iniciarTotal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Usuario carregarPessoa() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			Object ob = session.getAttribute("Login");

			if (ob != null && ob instanceof Usuario) {
				Usuario us = (Usuario) ob;
				u = us;
			} else {
				messageError("Erro ao carregar usuário, faça login novamente!");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void iniciarPorEscolas() throws Exception {
		graficoPorEscolas = new BarChartModel();
		ChartData data = new ChartData();

		data.addChartDataSet(getDataSetEscola("Escola Estadual Lauro Machado", 1));
		data.addChartDataSet(getDataSetEscola("Escola Estadual Badaró Junior", 2));
		data.addChartDataSet(getDataSetEscola("Escola Municipal São João Batista", 3));
		data.setLabels(getLabels());

		graficoPorEscolas.setData(data);
		graficoPorEscolas.setOptions(getOptions(true));
	}

	private BarChartDataSet getDataSetEscola(String nomeEscola, Integer escola) throws Exception {
		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel(nomeEscola);

		List<String> colors = getColors(escola);
		barDataSet.setBackgroundColor(colors);
		barDataSet.setBorderColor(colors);
		barDataSet.setBorderWidth(1);

		return barDataSet;
	}

	private List<String> getColors(Integer escola) {
		List<String> listColors = new ArrayList<>();
		switch (escola) {
		case 1:
			listColors.add("rgba(255, 99, 132, 0.2)");
			break;
		case 2:
			listColors.add("rgba(255, 159, 64, 0.2)");
			break;
		case 3:
			listColors.add("rgba(54, 162, 235, 0.2)");
			break;
		default:
			listColors.add("rgba(75, 192, 192, 0.2)");
			break;
		}
		return listColors;
	}

	private List<String> getLabels() {
		Map<Object, Object> turmas = new MapTurmas().getMap();
		List<String> labels = new ArrayList<>();
		turmas.forEach((key, value) -> {
			if (value != null)
				labels.add(key.toString());
		});
		return labels;
	}

	private BarChartOptions getOptions(boolean mostrarLegend) {
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setOffset(true);
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setPrecision(0);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Quantidade de Alunos que preencheram o questionário");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(mostrarLegend);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontColor("#333");
		legendLabels.setFontSize(18);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		return options;
	}

	private void iniciarTotal() throws Exception {
		graficoTotal = new BarChartModel();
		ChartData data = new ChartData();

		data.addChartDataSet(getDataSetTotal());
		data.setLabels(getLabels());

		graficoTotal.setData(data);
		graficoTotal.setOptions(getOptions(false));
	}

	private BarChartDataSet getDataSetTotal() throws Exception {
		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Total");

		List<String> colors = getColors(0);
		barDataSet.setBackgroundColor(colors);
		barDataSet.setBorderColor(colors);
		barDataSet.setBorderWidth(1);

		return barDataSet;
	}

	public void limpar() {
		habitoLeitura = null;
		frequencia = null;
		incentivoPais = null;
		genero = null;
		pontoPositivo = null;
		pontoNegativo = null;
		sexo = null;
		idadeInicial = null;
		idadeFinal = null;
		dataInicial = null;
		dataFinal = null;

		pesquisar();
	}

	public void pesquisar() {
		filtro = new StringBuilder("");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (sexo != null) {
			filtro.append(" AND q.aluno.sexo = " + sexo.ordinal());
		}

		if (idadeInicial != null) {
			filtro.append(" AND q.aluno.idade >= " + idadeInicial);
		}

		if (idadeFinal != null) {
			filtro.append(" AND q.aluno.idade <= " + idadeFinal);
		}

		if (dataInicial != null) {
			filtro.append(" AND to_char(q.aluno.dataHrCadastro, 'dd/MM/yyyy') >= '" + sdf.format(dataInicial) + "'");
		}

		if (dataFinal != null) {
			filtro.append(" AND to_char(q.aluno.dataHrCadastro, 'dd/MM/yyyy') <= '" + sdf.format(dataFinal) + "'");
		}

		if (habitoLeitura != null) {
			filtro.append(" AND q.habitoLeitura = ").append(habitoLeitura);
		}
		if (frequencia != null) {
			filtro.append(" AND q.frequencia = ").append(frequencia);
		}
		if (incentivoPais != null) {
			filtro.append(" AND q.incentivoPais = ").append(incentivoPais);
		}
		if (genero != null) {
			filtro.append(" AND q.genero = ").append(genero);
		}
		if (pontoPositivo != null) {
			filtro.append(" AND q.pontoPositivo = ").append(pontoPositivo);
		}
		if (pontoNegativo != null) {
			filtro.append(" AND q.pontoPositivo = ").append(pontoNegativo);
		}

		iniciarGraficos();
		listaAlunos = daoAluno.getList(filtro.toString());
	}

	public void mensagens() {
		try {

			renderizaMensagem = true;

			if (valores != null && valores == 1) {
				mensagem = "Para anexar arquivos no aplicativo, vá até a aba \"Arquivos\" e clique na opção \"Anexar\". Em seguida, selecione o arquivo que deseja enviar.";
			} else if (valores != null && valores == 2) {
				mensagem = "Para dar feedback, clique no perfil do funcionário. Isso abrirá uma opção onde você poderá escrever o feedback diretamente.";
			} else if (valores != null && valores == 3) {
				mensagem = "Para realizar o ponto, basta clicar no dia em que você está no calendário, depois selecionar o horário correspondente logo abaixo. Em seguida, insira seu e-mail (que já estará salvo) e pronto, o ponto será registrado.";
			} else {
				mensagem = "Informe os valores corretamente!";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String formataData() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");

			if (data != null) {
				return sdf.format(data) + " " + horario;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public void salvar() {

		try {

			hp = new HorariosPonto();

			hp.setDataHrCadastro(new Date());
			hp.setHorario(horario);
			hp.setDataPonto(data);
			hp.setUsuario(u);
			hp.setEntradaOuSaida(radio);

			hp = daoHorarioPonto.createOrUpdate(hp);
			messageInfo("Horário salvo com sucesso!");
			localizacao = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String sumTime() {

		if (u == null) {
			messageError("Faça login novamente!");
			return "";
		} else {

			List<HorariosPonto> list = daoHorarioPonto.getList(u.getId());

			// Soma as horas e os minutos
			int totalHoursEntrada = 0;
			int totalMinutesEntrada = 0;

			int totalHoursSaida = 0;
			int totalMinutesSaida = 0;

			for (HorariosPonto horariosPonto : list) {
				// Divide as strings em horas e minutos
				String[] parts1 = horariosPonto.getHorario().split(":");

				int hours1 = Integer.parseInt(parts1[0]);
				int minutes1 = Integer.parseInt(parts1[1]);

				if (horariosPonto.getEntradaOuSaida() == 0) {

					totalHoursEntrada += hours1;
					totalMinutesEntrada += minutes1;

					// Se os minutos somados forem 60 ou mais, ajusta as horas
					if (totalMinutesEntrada >= 60) {
						totalHoursEntrada += totalMinutesEntrada / 60;
						totalMinutesEntrada = totalMinutesEntrada % 60;
					}
				} else {
					totalHoursSaida += hours1;
					totalMinutesSaida += minutes1;

					// Se os minutos somados forem 60 ou mais, ajusta as horas
					if (totalMinutesSaida >= 60) {
						totalHoursSaida += totalMinutesSaida / 60;
						totalMinutesSaida = totalMinutesSaida % 60;
					}
				}

				// Retorna no formato "HH:MM", garantindo dois dígitos para horas e minutos
			}

			int totalHours = 0, totalMinutes = 0;

			totalHours = totalHoursSaida - totalHoursEntrada;

			totalMinutes = totalMinutesSaida - totalMinutesEntrada;

			return String.format("%02d:%02d", totalHours, totalMinutes);
		}
	}

	public Date bloqueiaDataPosterior() {
		return new Date();
	}

	public BarChartModel getGraficoPorEscolas() {
		return graficoPorEscolas;
	}

	public void setGraficoPorEscolas(BarChartModel graficoPorEscolas) {
		this.graficoPorEscolas = graficoPorEscolas;
	}

	public BarChartModel getGraficoTotal() {
		return graficoTotal;
	}

	public void setGraficoTotal(BarChartModel graficoTotal) {
		this.graficoTotal = graficoTotal;
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

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
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

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Integer getIdadeInicial() {
		return idadeInicial;
	}

	public void setIdadeInicial(Integer idadeInicial) {
		this.idadeInicial = idadeInicial;
	}

	public Integer getIdadeFinal() {
		return idadeFinal;
	}

	public void setIdadeFinal(Integer idadeFinal) {
		this.idadeFinal = idadeFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getValores() {
		return valores;
	}

	public void setValores(Integer valores) {
		this.valores = valores;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Boolean getRenderizaMensagem() {
		return renderizaMensagem;
	}

	public void setRenderizaMensagem(Boolean renderizaMensagem) {
		this.renderizaMensagem = renderizaMensagem;
	}

	public String getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(String selecionado) {
		this.selecionado = selecionado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getHorarioSaida() {
		return horarioSaida;
	}

	public void setHorarioSaida(String horarioSaida) {
		this.horarioSaida = horarioSaida;
	}

	public Integer getRadio() {
		return radio;
	}

	public void setRadio(Integer radio) {
		this.radio = radio;
	}

	public Boolean getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Boolean localizacao) {
		this.localizacao = localizacao;
	}
}
