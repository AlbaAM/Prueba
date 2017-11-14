package com.redsocial.persistencia;

import java.util.ArrayList;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.redsocial.modelo.Respuesta;


/**
 * 
 * @author Usuario
 *
 */
public class DAORespuestas {

	private final static String nombre = "nombre";
	private final static String emaill = "email";
	private final static String fechaa= "fecha";
	private final static String sms = "mensaje";
	private final static String idp = "idPublicacion";
	private final static String rtas = "Respuestas";
	private final static String idd = "_id";
	
	
	public static ObjectId insert(Respuesta respuesta) {
		Document doc=new Document();
		doc.append(emaill, respuesta.getEmail());
		doc.append(fechaa, respuesta.getFecha());
		doc.append(idp, respuesta.getIdPublicacion());
		doc.append(sms, respuesta.getMensaje());
		doc.append(nombre, respuesta.getNombre());
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>publicaciones=broker.getCollection(rtas);
		publicaciones.insertOne(doc);
		
		 ObjectId id = (ObjectId)doc.get( idd );
		
		 return id;
	}
	
	public static ArrayList<Respuesta> select(String idPublicacion) throws Exception {
		
		ArrayList<Respuesta> result = new ArrayList<Respuesta>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> publicaciones=broker.getCollection(rtas);
		Document criterio=new Document();
		criterio.append(idp, idPublicacion);
		
		FindIterable<Document> resultado=publicaciones.find(criterio);
		MongoCursor<Document> cursor = resultado.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			Respuesta resp = new Respuesta(doc.getString(emaill), doc.getString(fechaa), doc.getString(idp), doc.getString(sms), doc.getString(nombre));
			result.add(resp);
		}
				
		return result;
	}
	
public static void delete(String id) {
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>respuestas=broker.getCollection(rtas);
		Document criterio=new Document();
		criterio.append(idp, id);
		respuestas.deleteMany(criterio);
		
	}
	
}
