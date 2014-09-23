package persistence.dataprovider;

import com.hp.hpl.jena.rdf.model.Model;

public interface DataProvider {

	Model getDataModel();
	
	void close();

}