package br.edu.devmedia.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/alo")
public class AloMundoService {
	
	@GET
	public String aloMundo(){
		return "Alô mundo do Marcão";
	}

}
