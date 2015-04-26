package vsb.bri.task3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class LuceneSearchRunner {
	
	private static String[] RECORDS_FILES = { "src/vsb/bri/task3/data/cf74.xml",
	                                "src/vsb/bri/task3/data/cf75.xml",
	                                "src/vsb/bri/task3/data/cf76.xml",
	                                "src/vsb/bri/task3/data/cf77.xml",
	                                "src/vsb/bri/task3/data/cf78.xml",
	                                "src/vsb/bri/task3/data/cf79.xml" };
	
	private static String QUERIES_FILE = "src/vsb/bri/task3/data/cfquery.xml";
	
	private static List<String> mOutputLines = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		
		EnglishAnalyzer analyzer = new EnglishAnalyzer();
		Directory index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter w = new IndexWriter(index, config);
		
		for (String file : RECORDS_FILES) {
			RecordsXMLFile recordsFile = new RecordsXMLFile(file, w);
		}	
		w.close();
		
		QueriesXMLFile queries = new QueriesXMLFile(QUERIES_FILE);
		for (Map.Entry<String,String> query : queries.getQueries().entrySet()) {
			String queryStr = query.getValue();
			QueryParser parser = new QueryParser("text", analyzer);
			Query q = parser.parse(queryStr);
			
			int hitsPerPage = 10;
			DirectoryReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			
			SearchResults results = new SearchResults(query.getKey());
			for (int i=0; i<hits.length; i++) {
				int docId = hits[i].doc;
				Document document = searcher.doc(docId); 
				results.add(i+1, document.get("id"), hits[i].score);
			}
			mOutputLines.add(results.toString());
			System.out.println(results.toString());
			
			reader.close();
		}
		
		save("LCN_results_output.csv");
	}
	
	private static void save(String outputPath) throws IOException {
		FileWriter fw = new FileWriter(outputPath);
		for (String line : mOutputLines) {
			fw.write(line + "\n");
		}
		fw.close();
	}

}
