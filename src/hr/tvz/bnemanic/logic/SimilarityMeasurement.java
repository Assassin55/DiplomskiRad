package hr.tvz.bnemanic.logic;

import static org.simmetrics.builders.StringMetricBuilder.with;

import org.simmetrics.StringDistance;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.NeedlemanWunch;
import org.simmetrics.metrics.StringMetrics;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

public class SimilarityMeasurement {
	
	public float levenstein(String description, String searchTerm) {
//		String s1 = "dinamo zagreb";
//		String s2 = "zagreb";
		
//		StringMetric metric = StringMetrics.levenshtein();
//		StringDistance dist = new Levenshtein();
		StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.build();
		
//		float result = metric.compare(s1, s2);
//		float distance = dist.distance(s1, s2);
		float result = metric.compare(description, searchTerm);
		
//		System.out.println("Rezultat je: " + result);
//		System.out.println("Udaljenost je: " + distance);
//		System.out.println("Napredni rezultat je: " + advanced);
		
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

}
