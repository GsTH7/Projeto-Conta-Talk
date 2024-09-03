package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapTurmas {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
		listagem.put("1° ano", 1);
		listagem.put("2° ano", 2);
		listagem.put("3° ano", 3);
		listagem.put("4° ano", 4);
		listagem.put("5° ano", 5);
		return listagem;
	}

}
