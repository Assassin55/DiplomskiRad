package hr.tvz.bnemanic.logic;

import static org.simmetrics.builders.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.Jaccard;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.NeedlemanWunch;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

public class SimilarityMeasurement {
	
	public float levenstein(String description, String searchTerm) {
		StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.build();
		
		float result = metric.compare(description, searchTerm);
		
		return result;
	}
	
	public float needleman(String description, String searchTerm) {
		StringMetric metric = 
				with(new NeedlemanWunch())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.build();
		
		float result = metric.compare(description, searchTerm);

		return result;
	}
	
	public float jaroWinkler(String description, String searchTerm) {
		StringMetric metric = 
				with(new JaroWinkler())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.build();
		
		float result = metric.compare(description, searchTerm);
		
		return result;
	}
	
	public float cosine(String description, String searchTerm) {
		StringMetric metric =
				with(new CosineSimilarity<String>())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.replaceNonWord())
				.tokenize(Tokenizers.whitespace())
				.build();
		
		float result = metric.compare(description, searchTerm);
		
		return result;
	}
	
	public float jaccard(String description, String searchTerm) {
		StringMetric metric =
				with(new Jaccard<String>())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.replaceNonWord())
				.tokenize(Tokenizers.whitespace())
				.build();
		
		float result = metric.compare(description, searchTerm);
		
		return result;
	}

}
