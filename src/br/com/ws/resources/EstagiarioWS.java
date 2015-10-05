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

import org.json.JSONObject;

import com.google.gson.JsonObject;

import br.com.Controller.EstagiarioController;
import br.com.core.Model.Estagiario;
import br.com.core.Util.Retorno;

@XmlRootElement
@Path("/estagiarios")
public class EstagiarioWS {
	
	private EstagiarioController estController = new EstagiarioController();
	private List<?> estagiarioList = new ArrayList<Estagiario>();

	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String json){
		try{
			JSONObject dados_array_json = new JSONObject(json);
	
			String senha = null; 
			String cpf = null; 
			
			if(!dados_array_json.isNull("cpf")){
				cpf = dados_array_json.getString("cpf");
			}
			if(!dados_array_json.isNull("senha")){
				senha = dados_array_json.getString("senha");
			}
			if(cpf != null && senha != null){
				 Estagiario estagiario = new Estagiario();
				 estagiario = (Estagiario) estController.validarLogin(estagiario, cpf, senha);
				 if(estagiario != null){
					 String resposta = gerarJsonUsuario(estagiario);
					 return Response.ok(resposta, MediaType.APPLICATION_JSON).build();
				 }
			} else {
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}
	
			return Response.ok("", MediaType.APPLICATION_JSON).build();
	}catch(Exception e){
		   e.printStackTrace();
		   return Response.serverError().entity(e.getMessage()).build();
	}
	}
	
	
	private String gerarJsonUsuario(Estagiario e) {
		JsonObject json  = new JsonObject();
		json.addProperty("id", e.getId());
		json.addProperty("nome", e.getNome());
		json.addProperty("email", e.getEmail());
		json.addProperty("matriz", e.getMatriz().getId());
		json.addProperty("telefone", e.getTelefone());
		json.addProperty("cpf", e.getCpf());
		json.addProperty("periodo", e.getPeriodo());
		json.addProperty("ativo", e.getAtivo());
		return json.toString();
	}

	public List<?> getEstagiarioList() {
		return estagiarioList;
	}

	public void setEstagiarioList(List<Estagiario> estagiarioList) {
		this.estagiarioList = estagiarioList;
	}
	
	

	public List<?> listar() {
		return estagiarioList = estController.listar(new Estagiario());
	}
	
	

}
