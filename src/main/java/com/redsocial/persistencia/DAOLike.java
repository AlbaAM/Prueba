package com.redsocial.persistencia;


import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.redsocial.modelo.Like;


/**
 * 
 * @author Usuario
 *
 */
public class DAOLike {
  
	private final static String eu = "emailUsuario";
	private final static String idp = "idPublicacion";
	private final static String likess = "Likes";
	
  public static void insert(Like like) throws Exception {

    
    Document doc=new Document();
    doc.append(eu, like.getEmailUsuario());
    doc.append(idp, like.getIdPublicacion());    
    
    MongoBroker broker= MongoBroker.get();
    MongoCollection<Document>likes=broker.getCollection(likess);
    likes.insertOne(doc);
  }
  

  
  public static void update (Like like) throws Exception {
    Document doc=new Document();
    doc.append(idp, like.getIdPublicacion());
    
    MongoBroker broker= MongoBroker.get();
    MongoCollection<Document>likes=broker.getCollection(likess);
    likes.deleteOne(doc);
    
    Document doc2=new Document();
    doc2.append(eu, like.getEmailUsuario());
    doc2.append(idp, like.getIdPublicacion());
    
    likes.insertOne(doc2);
    
  }

  
  public static void delete(Like like) {
    Document doc=new Document();
    doc.append(idp, like.getIdPublicacion());
    doc.append(eu, like.getEmailUsuario());
    
    MongoBroker broker= MongoBroker.get();
    MongoCollection<Document>likes=broker.getCollection(likess);
    likes.findOneAndDelete(doc);
    
  }
  
  public static void deleteAll(String idPublicacion) {
	    Document doc=new Document();
	    doc.append(idp, idPublicacion);
	    
	    MongoBroker broker= MongoBroker.get();
	    MongoCollection<Document>likes=broker.getCollection(likess);
	    likes.deleteMany(doc);
	    
	  }
  
  public static ArrayList<Like> select(String idPublicacion) throws Exception {
		
		ArrayList<Like> result = new ArrayList<Like>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> publicaciones=broker.getCollection(likess);
		Document criterio=new Document();
		criterio.append(idp, idPublicacion);
		
		FindIterable<Document> resultado=publicaciones.find(criterio);
		MongoCursor<Document> cursor = resultado.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			Like like = new Like(doc.getString(eu), doc.getString(idp));
			result.add(like);
		}
				
		return result;
	}
  	
  public static Like checkLike(String idPublicacion,String emailUsuario) throws Exception {
		Like result = null;
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> likes=broker.getCollection(likess);
		Document criterio=new Document();
		criterio.append(idp, idPublicacion);
		criterio.append(eu, emailUsuario);
		
		FindIterable<Document> resultado=likes.find(criterio);
		Document doc=resultado.first();
		
		if (doc!=null) {
			result = new Like (doc.getString(idp), doc.getString(eu));
		}
		
		return result;
	}

}