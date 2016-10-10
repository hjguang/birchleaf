//package lucene;
//
//
//import java.nio.file.Paths;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig.OpenMode;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//public class CreateDoc {
//
//    public static void main(String[] args) throws Exception {
//        String path = "E:\\lucene";
//        create(path);
//        search(path);
//    }
//    
//    public static void create(String path) throws Exception {
//        Document doc = new Document();
//        Field title = new TextField("title", "ºî½¨¹â", Field.Store.YES);
//        
//        doc.add(title);
//        
//        Directory dir = FSDirectory.open(Paths.get(path));
//        Analyzer analyzer = new StandardAnalyzer();
//        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
//        cfg.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        IndexWriter writer = new IndexWriter(dir, cfg);
//        writer.deleteAll();
//        writer.addDocument(doc);
//        writer.commit();
//        writer.close();
//        
//    }
//
//    public static void search(String path) throws Exception {
//        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(path)));
//        IndexSearcher search = new IndexSearcher(reader);
//        Analyzer analyzer = new StandardAnalyzer();
//        QueryParser parser = new QueryParser("title", analyzer);
////        TopDocs docs = search.search(parser.parse("java"), 10);
//        TopDocs docs = search.search(new TermQuery(new Term("title", "¹â")), 10);
//        System.out.println(docs.scoreDocs.length);
//    }
//}
