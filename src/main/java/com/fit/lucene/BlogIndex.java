package com.fit.lucene;

import com.fit.entity.TBlog;
import com.fit.util.DateUtils;
import com.fit.util.RequestUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客索引类
 *
 * @author Administrator
 */
public class BlogIndex {

    private Directory dir = null;


    /**
     * 获取IndexWriter实例
     *
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws Exception {
        dir = FSDirectory.open(Paths.get("C://lucene"));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加博客索引
     *
     * @param blog
     */
    public void addIndex(TBlog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtils.getDateSmall(), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentnotag(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    /**
     * 更新博客索引
     *
     * @param blog
     * @throws Exception
     */
    public void updateIndex(TBlog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtils.getDateSmall(), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentnotag(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    /**
     * 删除指定博客的索引
     *
     * @param blogId
     * @throws Exception
     */
    public void deleteIndex(String blogId) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", blogId));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

    /**
     * 查询博客信息
     *
     * @param q 查询关键字
     * @return
     * @throws Exception
     */
    public List<TBlog> searchBlog(String q) throws Exception {
        dir = FSDirectory.open(Paths.get("C://lucene"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<TBlog> blogList = new LinkedList<TBlog>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            TBlog blog = new TBlog();
            blog.setId(Integer.parseInt(doc.get(("id"))));
            blog.setReleasedatestr(doc.get(("releaseDate")));
            String title = doc.get("title");
            String content = doc.get("content");
            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (RequestUtil.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (RequestUtil.isEmpty(hContent)) {
                    if (content.length() <= 200) {
                        blog.setContent(content);
                    } else {
                        blog.setContent(content.substring(0, 200));
                    }
                } else {
                    blog.setContent(hContent);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }
}
