package br.com.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.Controller.AtividadeController;
import br.com.core.Model.Atividade;
import br.com.core.Model.Categoria;
import br.com.core.Model.Disciplina;


@Path("atividade")
public class AtividadeWS {
	
	private AtividadeController atiController = new AtividadeController();
	private List<?> atividadeList = new ArrayList<Atividade>();
	
	
	
	@POST
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(String json) {
		try{
			Categoria categoria = new Categoria();
			atividadeList = atiController.buscarForeign(new Atividade(), categoria);
			Gson gson = new Gson();
			String jsonResposta = gson.toJson(atividadeList);
			return Response.ok(jsonResposta, MediaType.APPLICATION_JSON).build();	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}

	
	public List<?> getAtividadeList() {
		return atividadeList;
	}
	public void setAtividadeList(List<?> atividadeList) {
		this.atividadeList = atividadeList;
	}
	

	
	
}
