package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapHabitoLeitura {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
		listagem.put("Sim, leio batante.", 1);
		listagem.put("Mais ou menos", 2);
		listagem.put("Não, raramente leio.", 3);
		return listagem;
	}

}
