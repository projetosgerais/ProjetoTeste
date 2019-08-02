package br.com.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.Pessoa;

@Named("meuMB")
@SessionScoped
public class PessoaMB implements Serializable {

	private static final long serialVersionUID = 2L;

	public PessoaMB() {

	}

	@Inject
	private Pessoa pessoa;

	private List<Pessoa> listaPessoa = new ArrayList<>();

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void calcular() {
		
		DecimalFormat oFormat = new DecimalFormat("#,##0.00");

		String classificacao = "";
		
		double resultado = 0.0;

		resultado = pessoa.getPeso() / (pessoa.getAltura() * pessoa.getAltura());
		
		if(resultado < 17 ) {
			classificacao = " (Muito Abaixo do peso)";
		}else if(resultado < 18.49) {
			classificacao = " (Abaixo do peso)";
		}else if(resultado < 24.99) {
			classificacao = " (Peso normal)";
		}else if(resultado < 29.99) {
			classificacao = " (Acima do peso)";
		}else if(resultado < 34.99) {
			classificacao = " (Obesidade I)";
		}else if(resultado < 39.99) {
			classificacao = " (Obesidade II - Severa)";
		}else {
			classificacao = " (Obesidade III - Mórbida)";
		}

		pessoa.setResultado(oFormat.format(resultado) + classificacao);
		
		refresh();

	}


	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	public void limparFormulario() {

		pessoa = new Pessoa();
		
		refresh();

	}
	
	public void refresh() {  
        FacesContext context = FacesContext.getCurrentInstance();  
        Application application = context.getApplication();  
        ViewHandler viewHandler = application.getViewHandler();  
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());  
        context.setViewRoot(viewRoot);  
        context.renderResponse();  
    }

}
