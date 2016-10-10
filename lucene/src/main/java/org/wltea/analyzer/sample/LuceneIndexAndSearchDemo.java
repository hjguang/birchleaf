/**
 * IK ���ķִ�  �汾 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Դ������������(linliangyi2005@gmail.com)�ṩ
 * ��Ȩ���� 2012�������蹤����
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package org.wltea.analyzer.sample;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;




/**
 * ʹ��IKAnalyzer����Lucene�����Ͳ�ѯ����ʾ
 * 2012-3-2
 * 
 * �����ǽ��Lucene4.0 API��д��
 *
 */
public class LuceneIndexAndSearchDemo {
	
	
	/**
	 * ģ�⣺
	 * ����һ��������¼���������������������
	 * @param args
	 */
	public static void main(String[] args){
		//Lucene Document������
		String fieldName = "text";
		 //��������
		String text = "IK Analyzer��һ����ϴʵ�ִʺ��ķ��ִʵ����ķִʿ�Դ���߰�����ʹ����ȫ�µ����������ϸ�����з��㷨��";
		
		//ʵ����IKAnalyzer�ִ���
		Analyzer analyzer = new IKAnalyzer(true);
		
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			//�����ڴ���������
			directory = new RAMDirectory();	 
			
			//����IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory , iwConfig);
			//д������
			Document doc = new Document();
			doc.add(new StringField("ID", "10000", Field.Store.YES));
			doc.add(new TextField(fieldName, text, Field.Store.YES));
			iwriter.addDocument(doc);
			iwriter.close();
			
			
			//��������**********************************
		    //ʵ����������   
			ireader = DirectoryReader.open(directory);
			isearcher = new IndexSearcher(ireader);			
			
			String keyword = "���ķִʹ��߰�";			
			//ʹ��QueryParser��ѯ����������Query����
			QueryParser qp = new QueryParser(fieldName,  analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);
			System.out.println("Query = " + query);
			
			//�������ƶ���ߵ�5����¼
			TopDocs topDocs = isearcher.search(query , 5);
			System.out.println("���У�" + topDocs.totalHits);
			//������
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("���ݣ�" + targetDoc.toString());
			}			
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally{
			if(ireader != null){
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(directory != null){
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
