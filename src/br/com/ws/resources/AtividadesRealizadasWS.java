package br.com.ws.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import br.com.Controller.AtividadeController;
import br.com.Controller.AtividadesRealizadasController;
import br.com.Controller.CategoriaController;
import br.com.Controller.ConteudoController;
import br.com.Controller.DisciplinaController;
import br.com.core.Model.Atividade;
import br.com.core.Model.AtividadesRealizadas;
import br.com.core.Model.Categoria;
import br.com.core.Model.Conteudo;
import br.com.core.Model.Disciplina;
import br.com.core.Util.Retorno;

@XmlRootElement
@Path("/atividadesrealizadas")
public class AtividadesRealizadasWS {
	 private CategoriaController catController = new CategoriaController();
	 private AtividadeController atiController = new AtividadeController();
	 private DisciplinaController disController = new DisciplinaController();
	 private ConteudoController conController = new ConteudoController();
	 private AtividadesRealizadasController atiReaController = new AtividadesRealizadasController();
	 
	 private List<?> atividadeList = new ArrayList<Atividade>();
	 private List<?> categoriaList = new ArrayList<Categoria>();
	 private List<?> conteudoList = new ArrayList<Conteudo>();
	 private List<?> disciplinaList = new ArrayList<Disciplina>();
	 private AtividadesRealizadas atividadesRealizadas = new AtividadesRealizadas();
	

	@POST
	@Path("salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(String json) {
	
			Retorno ret = new Retorno(false);

			JSONObject dados_array_json = new JSONObject(json);
	
		//	String nomeDisciplina = null;
			String nomeAtividade = null;
			String nomeConteudo = null;
		//	String nomeCategoria = null;
	
		
			if (!dados_array_json.isNull("conteudo")) {
				nomeConteudo = dados_array_json.getString("conteudo");
			}
			/*if (!dados_array_json.isNull("categoria")) {
				nomeCategoria = dados_array_json.getString("categoria");
			}
			if (!dados_array_json.isNull("disciplina")) {
				nomeConteudo = dados_array_json.getString("disciplina");
			}*/
			
			if (!dados_array_json.isNull("atividade")) {
				nomeAtividade = dados_array_json.getString("atividade");
			}
	
			if (nomeAtividade != null && nomeConteudo != null) {
			/*	Disciplina disciplina = new Disciplina();
				Categoria categoria = new Categoria();*/
				Conteudo conteudo = new Conteudo();
				Atividade atividade = new Atividade();
				
			//	categoriaList = catController.listarCriterioEqual(new Categoria(), nomeCategoria, true);
				atividadeList = atiController.listarCriterioEqual(new Atividade(), nomeAtividade, true);
				conteudoList = conController.listarCriterioEqual(new Conteudo(), nomeConteudo, true);
			//	disciplinaList = disController.listarCriterioEqual(new Disciplina(), nomeDisciplina, true);
				
			/*	for (Object categoria2 : categoriaList) {
					categoria2 = categoria;
				}
				
				for (Object disciplina2 : disciplinaList) {
					disciplina2 = disciplina;
				}
				*/
				for (Object conteudo2 : conteudoList) {
					conteudo2 = conteudo;
				}
				
				for (Object atividade2 : atividadeList) {
					atividade2 = atividade;
				}
				
				
				atividadesRealizadas.setAtividade(atividade);
				atividadesRealizadas.setConteudo(conteudo);
				//atividadesRealizadas.setContratoEstagio(contratoEstagio);
				ret = atiReaController.salvar(atividadesRealizadas);
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}else{
				return Response.ok("", MediaType.APPLICATION_JSON).build();
			}

	}

	public AtividadesRealizadas getAtividadesRealizadas() {
		return atividadesRealizadas;
	}

	public void setAtividadesRealizadas(AtividadesRealizadas atividadesRealizadas) {
		this.atividadesRealizadas = atividadesRealizadas;
	}
}
