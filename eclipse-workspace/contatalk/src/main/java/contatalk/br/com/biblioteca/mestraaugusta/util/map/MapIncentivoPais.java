package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapIncentivoPais {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
		listagem.put("Sim, meus pais me incentivam muito a ler e sempre compram livros e me acompanham nas leituras.", 1);
		listagem.put("Sim, meus pais sempre falam da importãncia da leitura", 2);
		listagem.put("Não, meus pais não me incentivam a ler, mas também não me desencorajam.", 3);
		listagem.put("Não, meus pais não me incentivam a ler e preferem que eu faça outras atividades.", 4);
		return listagem;
	}

}
