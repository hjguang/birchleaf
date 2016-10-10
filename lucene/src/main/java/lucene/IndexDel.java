package lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexDel {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
       
//        creatIndex();
        
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("E:\\lucene")));
        System.out.println("索引文档列表:");
        for(int i = 0 ;i < reader.numDocs(); i ++) {
            System.out.println(reader.document(i));
        }
        IndexSearcher search = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("bookName", new IKAnalyzer());
        Term term = new Term("bookName", "*女*");
        Query query = parser.parse("儿女人");
//        Query query = new WildcardQuery(term);
        TopDocs docs = search.search(query, 10);
        System.out.println(docs.totalHits);
    }

    public static void creatIndex() throws Exception {
        Directory directory = FSDirectory.open(Paths.get("E:\\lucene"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory,config);
        writer.deleteAll();
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Field f1 = new Field("bookName", "钢铁是怎样炼成的", TextField.TYPE_STORED);
        Field f2 = new Field("bookName", "英雄儿女", TextField.TYPE_STORED);
        Field f3 = new Field("bookName", "篱笆女人和狗",TextField.TYPE_STORED);
        doc1.add(f1);
        doc2.add(f2);
        doc3.add(f3);
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.commit();
        writer.close();
    }
}
