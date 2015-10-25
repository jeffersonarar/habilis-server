package br.com.ws.resources;

import java.util.ArrayList;
import java.util.Date;
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
import br.com.Controller.ConteudoController;
import br.com.Controller.ContratoEstagioController;
import br.com.core.Model.Atividade;
import br.com.core.Model.AtividadesRealizadas;
import br.com.core.Model.Conteudo;
import br.com.core.Model.ContratoEstagio;
import br.com.core.Util.Retorno;

@XmlRootElement
@Path("/atividadesrealizadas")
public class AtividadesRealizadasWS {
	 private AtividadeController atiController = new AtividadeController();
	 private ConteudoController conController = new ConteudoController();
	 private AtividadesRealizadasController atiReaController = new AtividadesRealizadasController();
	 private ContratoEstagioController conEstController = new ContratoEstagioController();
	 
	 private ContratoEstagio contrato = new ContratoEstagio();
	 private List<?> atividadeList = new ArrayList<Atividade>();
	 private List<?> conteudoList = new ArrayList<Conteudo>();
	 private AtividadesRealizadas atividadesRealizadas = new AtividadesRealizadas();
	

	@POST
	@Path("salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(String json) {
	
			Retorno ret = new Retorno(false);

			JSONObject dados_array_json = new JSONObject(json);
			String nomeAtividade = null;
			String nomeConteudo = null;
			long idContrato = 0;

	
		
			if (!dados_array_json.isNull("conteudo")) {
				nomeConteudo = dados_array_json.getString("conteudo");
			}
			
			if (!dados_array_json.isNull("contrato")) {
				idContrato = dados_array_json.getLong("contrato");
			}
			
			if (!dados_array_json.isNull("atividade")) {
				nomeAtividade = dados_array_json.getString("atividade");
			}
	
			if (nomeAtividade != null && nomeConteudo != null && idContrato != 0) {

				Conteudo conteudo = new Conteudo();
				Atividade atividade = new Atividade();

				atividadeList = atiController.listarCriterioEqual(new Atividade(), nomeAtividade, true);
				conteudoList = conController.listarCriterioEqual(new Conteudo(), nomeConteudo, true);
				contrato = (ContratoEstagio) conEstController.buscarPorId(new ContratoEstagio(), idContrato);

				for (Object conteudo2 : conteudoList) {
					conteudo = (Conteudo) conteudo2;
				}
				
				for (Object atividade2 : atividadeList) {
					atividade = (Atividade) atividade2;
				}
				
				atividadesRealizadas.setData_cadastro(new Date());
				atividadesRealizadas.setAtividade(atividade);
				atividadesRealizadas.setConteudo(conteudo);
				atividadesRealizadas.setContratoEstagio(contrato);
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
