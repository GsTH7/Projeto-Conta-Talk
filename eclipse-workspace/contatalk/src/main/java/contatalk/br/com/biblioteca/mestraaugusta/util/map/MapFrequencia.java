package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapFrequencia {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
		listagem.put("Sim, visito a biblioteca regularmente.", 1);
		listagem.put("Mais ou menos", 2);
		listagem.put("Não, raramente visito a biblioteca.", 3);
		listagem.put("Não, nunca visitei a biblioteca.", 4);
		return listagem;
	}

}
