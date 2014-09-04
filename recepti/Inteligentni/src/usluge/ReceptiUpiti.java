package rs.fon.is.creativeWork.services;

import java.util.ArrayList;
import java.util.Collection;

import persistance.RDFModel;
import util.Constants;

import com.hp.hpl.jena.rdf.model.Model;
import usluge.IzvrsilacUpita;

public class ReceptiUpiti {
	
	private IzvrsilacUpita queryExecutor = new IzvrsilacUpita();
	
	public Model describeBooksAboutShakespeare() {
		String query = 
			"PREFIX schema: <"+Constants.SCHEMA+"> " + 
			"DESCRIBE ?recipe " +
			"WHERE { " +
				"?recipe a schema; " +
			"}";
		
		return queryExecutor
				.executeDescribeSparqlQuery(query,
						RDFModel.getInstance().getModel());
	}

	public Collection<String> getAllPulitzerWinners() {
		String query = 
			"PREFIX schema: <"+Constants.SCHEMA+"> " + 
			"PREFIX xsd: <"+Constants.XSD+"> " + 
			"SELECT ?name " +
			"WHERE { " +
				"?bookName a schema:CreativeWork ; " +
					"schema:provider ?writer ;" +
					"schema:award \"Pulitzer Prize\"^^xsd:string ." +
						
				"?writer schema:name ?name ." +
			"}";

		Collection<String> result = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "name",
						RDFModel.getInstance().getModel());
		
		if (result != null && !result.isEmpty()) {
			return result;
		}
		
		return new ArrayList<String>();
	}
}
