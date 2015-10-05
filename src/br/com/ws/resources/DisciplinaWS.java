package br.com.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

import br.com.Controller.DisciplinaController;
import br.com.core.Model.Disciplina;



@XmlRootElement
@Path("/disciplina")
public class DisciplinaWS {
	
	private DisciplinaController disController = new DisciplinaController();
	private List<?> disciplinaList = new ArrayList<Disciplina>();
	
	@POST
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listar() {
		try{
			disciplinaList = disController.listar(new Disciplina());
			Gson gson = new Gson();
			String json = gson.toJson(disciplinaList);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}

	
	
	public List<?> getDisciplinaList() {
		return disciplinaList;
	}
	public void setDisciplinaList(List<?> disciplinaList) {
		this.disciplinaList = disciplinaList;
	}

	
	

}
