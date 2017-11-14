package com.redsocial.persistencia;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.redsocial.modelo.Publicacion;
/**
 * 
 * @author Usuario
 *
 */
public class DAOPublicaciones {

	private final static String name = "name";
	private final static String emaill = "email";
	private final static String fechaa= "fecha";
	private final static String sms = "mensaje";
	private final static String img = "imagen";
	private final static String pub = "Publicaciones";
	private final static String idd = "_id";
	private final static String iid = "id";
	private final static String idp = "idPublicacion";
	
	public static ObjectId insert(Publicacion publicacion) {
		Document doc=new Document();
		doc.append(name, publicacion.getNombre());
		doc.append(emaill, publicacion.getEmail());
		doc.append(fechaa, publicacion.getFecha());
		doc.append(sms, publicacion.getMensaje());
		doc.append(img, publicacion.getImagen());
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>publicaciones=broker.getCollection(pub);
		publicaciones.insertOne(doc);
		
		 ObjectId id = (ObjectId)doc.get( idd );
		
		 return id;
	}
	

	
	public static void update (Publicacion publicacion) throws Exception {
		
		// Montamos la fecha actual para saber cuando se hizo la publicacion.
		 Calendar fecha = new GregorianCalendar();
		 String fechaPublicacion = "";
	     int year = fecha.get(Calendar.YEAR);
	     // Se le suma uno, porque calendar.month devuelve de 0-11
	     int month = fecha.get(Calendar.MONTH)+1;
	     int day = fecha.get(Calendar.DAY_OF_MONTH);
	     int hour = fecha.get(Calendar.HOUR_OF_DAY);
	     int minute = fecha.get(Calendar.MINUTE);
	     String monthS = (month<10)?"0"+month:""+month;
	     String dayS = (day<10)?"0"+day:""+day;
	     fechaPublicacion = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
		
		Document filter = new Document(idd, new ObjectId(publicacion.getIdPublicacion()));
		Document newValue = new Document();
		newValue.append(sms, publicacion.getMensaje());
		newValue.append(fechaa, fechaPublicacion);
		Document updateOperationDocument = new Document("$set", newValue);
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>publicaciones=broker.getCollection(pub);
		publicaciones.updateOne(filter, updateOperationDocument);
		
	}

	public static Publicacion select(String idPublicacion) throws Exception {
		Publicacion result = null;
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> publicaciones=broker.getCollection(pub);
		Document criterio=new Document();
		criterio.append(iid, idPublicacion);
		
		FindIterable<Document> resultado=publicaciones.find(criterio);
		Document publicacion=resultado.first();
		
		if (publicacion!=null) {
			result = new Publicacion(publicacion.getString(idp), publicacion.getString(emaill), 
			publicacion.getString(name), publicacion.getString(fechaa), publicacion.getString(img), publicacion.getString(sms));
		}
		
		return result;
	}
	
	public static ArrayList<Publicacion> selectAll () {
		
		ArrayList<Publicacion> result = new ArrayList<Publicacion>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> publicaciones=broker.getCollection(pub);
		FindIterable<Document> it = publicaciones.find().sort(new BasicDBObject(idd,-1));
		MongoCursor<Document> cursor = it.iterator();
	
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ObjectId id = (ObjectId)doc.get( idd );
			Publicacion publi = new Publicacion(id.toString(), doc.getString(emaill), doc.getString(name), doc.getString(fechaa), doc.getString(img), doc.getString(sms));
			result.add(publi);
		}

		return result;
	}
	
	public static void delete(String id) {
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>publicaciones=broker.getCollection(pub);
		publicaciones.deleteOne(new Document(idd, new ObjectId(id)));
		
	}
	

}
