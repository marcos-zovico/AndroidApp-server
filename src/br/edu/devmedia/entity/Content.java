package br.edu.devmedia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content implements Serializable {

	private static final long serialVersionUID = 3581301361067115944L;

	private List<String> registration_ids;
	private Map<String, String> data;

	public void addRegId(String regId) {
		if (registration_ids == null) {
			registration_ids = new ArrayList<>();
		}
		registration_ids.add(regId);
	}

	public void addData(String chave, String valor) {
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(chave, valor);
	}

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public Map<String, String> getData() {
		return data;
	}

}
