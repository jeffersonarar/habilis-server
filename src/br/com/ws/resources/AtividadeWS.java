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

import br.com.Controller.AtividadeController;
import br.com.Controller.CategoriaController;
import br.com.Controller.DisciplinaController;
import br.com.core.Model.Atividade;
import br.com.core.Model.Categoria;
import br.com.core.Model.Disciplina;
import br.com.core.Model.Estagiario;
import br.com.core.Model.Matriz;

import com.google.gson.Gson;

@XmlRootElement
@Path("/atividade")
public class AtividadeWS {
	private CategoriaController catController = new CategoriaController();
	private AtividadeController conController = new AtividadeController();
	private List<?> atividadeList = new ArrayList<Atividade>();
	private List<?> categoriaList = new ArrayList<Categoria>();
	
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
				categoriaList = catController.listarCriterioEqual(new Categoria(), nome, true);
				Categoria categoria2 = null;
				
				for (Object categoria : categoriaList) {
					categoria2 = (Categoria) categoria;
				}
				atividadeList = conController.buscarForeign(new Atividade(), categoria2);
				
				 if(!atividadeList.isEmpty()){
					Gson gson = new Gson();
					String json = gson.toJson(atividadeList);
					return Response.ok(json, MediaType.APPLICATION_JSON).build();
				//	 String resposta = gerarJson(AtividadeList);
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

	




	public List<?> getAtividadeList() {
		return atividadeList;
	}
	public void setAtividadeList(List<?> atividadeList) {
		this.atividadeList = atividadeList;
	}
}
