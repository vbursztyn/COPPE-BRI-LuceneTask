package vsb.bri.task3;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QueriesXMLFile {
	
	private String QUERY_TAG_NAME = "QUERY";
	private String QUERYNUM_TAG_NAME = "QueryNumber";
	private String QUERYTEXT_TAG_NAME = "QueryText";
	private Map<String, String> mQueries = new HashMap<String, String>();

	public QueriesXMLFile(String path) throws Exception {
		File file = new File(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		document.getDocumentElement().normalize();
		
		NodeList queryList = document.getElementsByTagName(QUERY_TAG_NAME);
		
		for (int i=0; i<queryList.getLength(); i++) {
			Node queryNode = queryList.item(i);
			String queryId = "";
			String queryText = "";
			if (queryNode.getNodeType() == Node.ELEMENT_NODE) {
				Element queryElement = (Element) queryNode;
				
				if (queryElement.getElementsByTagName(QUERYNUM_TAG_NAME).getLength() > 0) {
					queryId = queryElement.getElementsByTagName(QUERYNUM_TAG_NAME).item(0).getTextContent();
				}
				if (queryElement.getElementsByTagName(QUERYTEXT_TAG_NAME).getLength() > 0) {
					queryText = queryElement.getElementsByTagName(QUERYTEXT_TAG_NAME).item(0).getTextContent();
				}
				
				if (queryId != "" && queryText != "") {
					mQueries.put(queryId, formatQuery(queryText));
				}		
			}
		}
	}
	
	private String formatQuery(String queryText) { // Removes special characters, keeping spaces, letters and numbers.
		return queryText.replaceAll("[^a-zA-Z0-9\\s]+","").replaceAll("\\s+", " ");
	}
	
	public Map<String,String> getQueries() {
		return mQueries;
	}
}
