package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

@Named
@ViewScoped
public class ScheduleView implements Serializable {

	private static final long serialVersionUID = -3749499299458463705L;
	private ScheduleModel eventModel;

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();

		// Adicionando reuniões para dias futuros
		 eventModel.addEvent(new DefaultScheduleEvent<Object>());
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	// Método auxiliar para adicionar dias a uma data
	private Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
}
