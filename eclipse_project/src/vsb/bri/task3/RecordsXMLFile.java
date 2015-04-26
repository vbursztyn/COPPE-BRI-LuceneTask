package vsb.bri.task3;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecordsXMLFile {
	
	private String RECORD_TAG_NAME = "RECORD";
	
	public RecordsXMLFile(String path, IndexWriter w) throws Exception {
		File file = new File(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		document.getDocumentElement().normalize();
		
		NodeList recList = document.getElementsByTagName(RECORD_TAG_NAME);
		
		for (int i=0; i<recList.getLength(); i++) {
			Node recNode = recList.item(i);
			if (recNode.getNodeType() == Node.ELEMENT_NODE) {
				Element recElement = (Element) recNode;
				SearchableDocument searchableDoc = new SearchableDocument(recElement, w);
				searchableDoc.commit();
			}
		}
	}
}
