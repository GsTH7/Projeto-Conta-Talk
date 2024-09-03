package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputnumber.InputNumber;

import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapEscolas;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapFrequencia;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapGenero;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapHabitoLeitura;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapIncentivoPais;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoNegativo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapPontoPositivo;
import contatalk.br.com.biblioteca.mestraaugusta.util.map.MapTurmas;


public class BeanMain implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public void messageInfo(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
	
	public void messageWarn(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}
	
	public void messageError(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public void showInfo() {
        addMessage(FacesMessage.SEVERITY_INFO, "Info", "Arquivo Enviado!");
    }
	
	public boolean verificarCampoObrigatorio(String idDoCampo, String mensagemErro, Boolean... condicoes) {
		FacesContext context = FacesContext.getCurrentInstance();

		UIComponent component = context.getViewRoot().findComponent(idDoCampo);
		
		boolean campoValido = true;
		
		String bordaVermelha = "ui-state-error";
		if (component != null) {
			if(component instanceof InputNumber) {	//Para campos <p:inputNumber>
				InputNumber inputNumber = (InputNumber) component;
				Object value = inputNumber.getValue();
				
				if (value == null || value instanceof Number && ((Number) value).doubleValue() == 0.0 || !verificarCondicoes(condicoes)) {
					if (inputNumber.getInputStyleClass() == null) {
						inputNumber.setInputStyleClass(bordaVermelha);
					} else if(!inputNumber.getInputStyleClass().contains(bordaVermelha)){
						inputNumber.setInputStyleClass(inputNumber.getInputStyleClass() + " " + bordaVermelha);
					}
					
					messageWarn(mensagemErro);
					campoValido = false;

				} else {
					inputNumber.setInputStyleClass(getText(inputNumber.getInputStyleClass())
							.replace(" " + bordaVermelha, "").replace(bordaVermelha, ""));
				}
				
			} else {//Para campos <p:inputText>, <p:inputTextArea>, <p:selectOneMenu>
				String styleClass = (String) component.getAttributes().get("styleClass");
				String value = getTextOB(((ValueHolder) component).getValue());

				if (value == null || value.trim().equals("") || !verificarCondicoes(condicoes)) {
					if (styleClass == null) {
						styleClass = bordaVermelha;
					} else if (!styleClass.contains(bordaVermelha)) {
						styleClass += " " + bordaVermelha;
					}
					
					messageWarn(mensagemErro);
					campoValido = false;

				} else {
					styleClass = getText(styleClass).replace(" " + bordaVermelha, "").replace(bordaVermelha, "");
				}
				component.getAttributes().put("styleClass", styleClass);
			}
		}
		return campoValido;
	}
	
	private boolean verificarCondicoes(Boolean... condicoes) {
		boolean condicoesAtendidas = true;
        for (Boolean condicao : condicoes) {	// Verifica todas as condições
            if (condicao != null && condicao) {
                condicoesAtendidas = false; // Se uma condição não for atendida, define como false e para de verificar
                break;
            }
        }
        return condicoesAtendidas;
	}

	private String getText(String valor) {
		if(valor == null) {
			return "";
		}
		return valor;
	}
	
	private String getTextOB(Object valor) {
		if(valor == null) {
			return "";
		}
		return valor.toString();
	}
	
	public String getDescricaoEscola(Integer escola) {
        Map<Object, Object> escolas = new MapEscolas().getMap();
        for (Entry<Object, Object> entry : escolas.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(escola)) {
                return entry.getKey().toString();
            }
        }
        return null;
    }
	
	public String getDescricaoTurma(Integer turma) {
		Map<Object, Object> turmas = new MapTurmas().getMap();
		for (Entry<Object, Object> entry : turmas.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(turma)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoHabito(Integer habito) {
		Map<Object, Object> habitos = new MapHabitoLeitura().getMap();
		for (Entry<Object, Object> entry : habitos.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(habito)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoFrequencia(Integer frequencia) {
		Map<Object, Object> frequencias = new MapFrequencia().getMap();
		for (Entry<Object, Object> entry : frequencias.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(frequencia)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoIncentivo(Integer incentivo) {
		Map<Object, Object> incentivos = new MapIncentivoPais().getMap();
		for (Entry<Object, Object> entry : incentivos.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(incentivo)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoGenero(Integer genero) {
		Map<Object, Object> generos = new MapGenero().getMap();
		for (Entry<Object, Object> entry : generos.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(genero)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoPontoPositivo(Integer pontoPositivo) {
		Map<Object, Object> positivos = new MapPontoPositivo().getMap();
		for (Entry<Object, Object> entry : positivos.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(pontoPositivo)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
	
	public String getDescricaoPontoNegativo(Integer pontoNegativo) {
		Map<Object, Object> negativos = new MapPontoNegativo().getMap();
		for (Entry<Object, Object> entry : negativos.entrySet()) {
			if (entry.getValue() != null && entry.getValue().equals(pontoNegativo)) {
				return entry.getKey().toString();
			}
		}
		return null;
	}
}
