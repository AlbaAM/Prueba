package com.redsocial.persistencia;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.redsocial.modelo.MensajesPrivados;
/**
 * 
 * @author Usuario
 *
 */
public class DAOMensajesPrivados {
	
	private final static String idmensajee = "idmensaje";
	private final static String fechaa = "fecha";
	private final static String dto = "destinatario";
	private final static String rte = "emisor";
	private final static String edto = "emaildestinatario";
	private final static String erte = "emailemisor";
	private final static String sms = "mensaje";
	private final static String mp = "MensajesPrivados";
	private final static String idd = "_id";
	
	public static ObjectId insert(MensajesPrivados msg) {
		Document doc=new Document();
		doc.append(idmensajee, msg.getIdmensaje());
		doc.append(fechaa, msg.getFecha());
		doc.append(dto, msg.getDestinatario());
		doc.append(rte, msg.getEmisor());
		doc.append(sms, msg.getMensaje());
		
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>mensajes=broker.getCollection(mp);
		mensajes.insertOne(doc);
		
		 ObjectId id = (ObjectId)doc.get( idd );
		
		 return id;
	}
	

	
	public static void update (MensajesPrivados msg) throws Exception {
		
		// Montamos la fecha actual para saber cuando se hizo el mensaje.
		 Calendar fecha = new GregorianCalendar();
		 String fechaMensaje = "";
	     int year = fecha.get(Calendar.YEAR);
	     // Se le suma uno, porque calendar.month devuelve de 0-11
	     int month = fecha.get(Calendar.MONTH)+1;
	     int day = fecha.get(Calendar.DAY_OF_MONTH);
	     int hour = fecha.get(Calendar.HOUR_OF_DAY);
	     int minute = fecha.get(Calendar.MINUTE);
	     String monthS = (month<10)?"0"+month:""+month;
	     String dayS = (day<10)?"0"+day:""+day;
	     fechaMensaje = dayS+"/"+monthS+"/"+year+" "+hour+":"+minute;
		
		Document filter = new Document(idd, new ObjectId(msg.getIdmensaje()));
		Document newValue = new Document();
		newValue.append(sms, msg.getMensaje());
		newValue.append(fechaa, fechaMensaje);
		Document updateOperationDocument = new Document("$set", newValue);
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>mensajes=broker.getCollection(mp);
		mensajes.updateOne(filter, updateOperationDocument);
		
	}

	public static MensajesPrivados select(String idmensaje) throws Exception {
		MensajesPrivados result = null;
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> mensajes=broker.getCollection(mp);
		Document criterio=new Document();
		criterio.append(idd, idmensaje);
		
		FindIterable<Document> resultado=mensajes.find(criterio);
		Document mensaje=resultado.first();
		
		if (mensaje!=null) {
			result = new MensajesPrivados(mensaje.getString(idmensajee), mensaje.getString(fechaa), 
					mensaje.getString(edto), mensaje.getString(erte), mensaje.getString(sms));
			
		}
		
		return result;
	}
	
	public static ArrayList selectAll () {
		
		ArrayList<MensajesPrivados> result = new ArrayList<MensajesPrivados>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> mensajes=broker.getCollection(mp);
		FindIterable<Document> it = mensajes.find();
		MongoCursor<Document> cursor = it.iterator();
	
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ObjectId id = (ObjectId)doc.get( idd );
			MensajesPrivados msg = new MensajesPrivados(id.toString(), doc.getString(fechaa), doc.getString(dto), doc.getString(rte), doc.getString(sms));
			result.add(msg);
		}

		return result;
	}
	
	public static ArrayList selectMsgUser (String emailUser) {
		
		ArrayList<MensajesPrivados> result = new ArrayList<MensajesPrivados>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> mensajes=broker.getCollection(mp);
		FindIterable<Document> it = mensajes.find();
		MongoCursor<Document> cursor = it.iterator();
	
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ObjectId id = (ObjectId)doc.get( idd );
			MensajesPrivados msg = new MensajesPrivados(id.toString(), doc.getString(fechaa), doc.getString(dto), doc.getString(rte), doc.getString(sms));
			if(doc.getString(edto)==emailUser)
				result.add(msg);
		}

		return result;
	}
	
	public static void delete(String id) {
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>mensajes=broker.getCollection(mp);
		mensajes.deleteOne(new Document(idd, new ObjectId(id)));
		
	}

}
