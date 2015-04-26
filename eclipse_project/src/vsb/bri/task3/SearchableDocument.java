package vsb.bri.task3;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.Element;

public class SearchableDocument {
	
	private String RECORDNUM_TAG_NAME = "RECORDNUM";
	private String ABSTRACT_TAG_NAME = "ABSTRACT";
	private String EXTRACT_TAG_NAME = "EXTRACT";
	private String mRecordText;
	private String mRecordNum;
	IndexWriter mWriter;
	
	public SearchableDocument(Element recElement, IndexWriter w) throws Exception {
		mWriter = w;
		mRecordNum = recElement.getElementsByTagName(RECORDNUM_TAG_NAME).item(0).getTextContent();
		mRecordText = "";
		if (recElement.getElementsByTagName(ABSTRACT_TAG_NAME).getLength() > 0) {
			mRecordText = recElement.getElementsByTagName(ABSTRACT_TAG_NAME).item(0).getTextContent();
		} 
		else {
			if (recElement.getElementsByTagName(EXTRACT_TAG_NAME).getLength() > 0) {
				mRecordText = recElement.getElementsByTagName(EXTRACT_TAG_NAME).item(0).getTextContent();
			}
		}
	}
	
	public void commit() throws IOException {
		if (mRecordText == "") {
			return; // Skips empty records (found at least one without ABSTRACT nor EXTRACT).
		}
		Document document = new Document();
		document.add(new TextField("text", mRecordText, Field.Store.YES));
		document.add(new StringField("id", mRecordNum, Field.Store.YES));
		mWriter.addDocument(document);
		System.out.println("Successfully committed record: " + mRecordNum);
	}
}
