package br.com.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.Controller.ConteudoController;
import br.com.Controller.DisciplinaController;
import br.com.core.Model.Conteudo;
import br.com.core.Model.Disciplina;
import br.com.core.Model.Estagiario;
import br.com.core.Model.Matriz;

import com.google.gson.Gson;

@XmlRootElement
@Path("/conteudo")
public class ConteudoWS {
	private DisciplinaController disController = new DisciplinaController();
	private ConteudoController conController = new ConteudoController();
	private List<?> conteudoList = new ArrayList<Conteudo>();
	private List<?> disciplinaList = new ArrayList<Disciplina>();
	
	@POST
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listar(String jsonrecebido) {
		try{
			String nome = null;
			JSONObject dados_array_json = new JSONObject(jsonrecebido);
			 
			
			if(!dados_array_json.isNull("nome")){
				nome = dados_array_json.getString("nome");
			}
			
			
			if(nome != null){
				disciplinaList = disController.listarCriterioEqual(new Disciplina(), nome, true);
				Disciplina disciplina2 = null;
				
				for (Object disciplina : disciplinaList) {
					 disciplina2 = (Disciplina) disciplina;
				}
				conteudoList = conController.buscarForeign(new Conteudo(), disciplina2);
				
				 if(!conteudoList.isEmpty()){
					Gson gson = new Gson();
					String json = gson.toJson(conteudoList);
					return Response.ok(json, MediaType.APPLICATION_JSON).build();
				//	 String resposta = gerarJson(conteudoList);
					// return Response.ok(resposta, MediaType.APPLICATION_JSON).build();
				 }
			} else {
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}
	
		
			return Response.ok("", MediaType.APPLICATION_JSON).build();	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}

	




	public List<?> getConteudoList() {
		return conteudoList;
	}
	public void setConteudoList(List<?> conteudoList) {
		this.conteudoList = conteudoList;
	}
}
