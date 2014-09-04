package persistance;

import util.Constants;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RDFModel {

	Model model;
	Bean2RDF writer;
	RDF2Bean reader;
	
	private static RDFModel INSTANCE;
	
	private RDFModel (){
		model = ModelFactory.createDefaultModel();
		
		model.setNsPrefix("recepti", Constants.NS);
		model.setNsPrefix("schema", Constants.SCHEMA);
		
		writer = new Bean2RDF(model);
		reader = new RDF2Bean(model);
	}
	
	public static RDFModel getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RDFModel();
		}
		return INSTANCE;
	}
	
	public void save(Object o) {
		writer.save(o);
	}
	
	public Object load(String uri) {
		return reader.load(uri);
	}
	
	public Model getModel() {
		return model;
	}

	public void printOut(){
		model.write(System.out, "TURTLE");
	}

    public void add(Model model) {
        this.model.add(model);
    }
}