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

import org.json.JSONObject;

import br.com.Controller.ContratoEstagioController;
import br.com.Controller.EstagiarioController;
import br.com.core.Model.ContratoEstagio;
import br.com.core.Model.Estagiario;

import com.google.gson.Gson;


@XmlRootElement
@Path("/contrato")
public class ContratoEstagioWS {

	private ContratoEstagioController conEstController = new ContratoEstagioController();
	private List<?> contratoEstagioList = new ArrayList<ContratoEstagio>();
	private EstagiarioController estController = new EstagiarioController();
	private List<?> estagiarioList = new ArrayList<Estagiario>();
	private ContratoEstagio contrato = new ContratoEstagio();
	

	@SuppressWarnings("unused")
	@POST
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listar(String jsonrecebido) {

		try{
			String nome = null;
			JSONObject dados_array_json = new JSONObject(jsonrecebido);
			String cpf = null; 
			
			if(!dados_array_json.isNull("cpf")){
				cpf = dados_array_json.getString("cpf");
			}

			if(cpf != null){
				estagiarioList = estController.findCriterioEstagiario(new Estagiario(), cpf, true);
				Estagiario estagiario = null;
				
				for (Object estagiarioObject : estagiarioList) {
					estagiario = (Estagiario) estagiarioObject;
				}
				contratoEstagioList = conEstController.buscarForeign(new ContratoEstagio(), estagiario);
				 if(!contratoEstagioList.isEmpty()){
						Gson gson = new Gson();
						String json = gson.toJson(contratoEstagioList);
						return Response.ok(json, MediaType.APPLICATION_JSON).build();
					 }
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Path("id")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buscarContrato(String jsonrecebido) {
		try{
			JSONObject dados_array_json = new JSONObject(jsonrecebido);
			String id = null; 
			
			if(!dados_array_json.isNull("id")){
				id = dados_array_json.getString("id");
			}

			if(id != null){
				
				
				contrato = (ContratoEstagio) conEstController.buscarPorId(new ContratoEstagio(), Long.parseLong(id));
		
				 if(contrato!=null){
						Gson gson = new Gson();
						String json = gson.toJson(contrato);
						return Response.ok(json, MediaType.APPLICATION_JSON).build();
				   }
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
