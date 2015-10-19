package br.com.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.Controller.CategoriaController;
import br.com.core.Model.Categoria;

import com.google.gson.Gson;

@XmlRootElement
@Path("/categoria")
public class CategoriaWS {

	private CategoriaController catController = new CategoriaController();
	private List<?> categoriaList = new ArrayList<Categoria>();
	
	@POST
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		try{
			categoriaList = catController.listar(new Categoria());
			Gson gson = new Gson();
			String json = gson.toJson(categoriaList);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();	
		}catch(Exception e){
			 return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	public List<?> getCategoriaList() {
		return categoriaList;
	}
	public void setCategoriaList(List<?> categoriaList) {
		this.categoriaList = categoriaList;
	}

}
