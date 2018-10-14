package br.com.abraao.pa.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

	private T data;
	private List<String> errors;

	public Response() {

	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

}
