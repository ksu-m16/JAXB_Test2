import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			 
			File file = new File("test2.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Abook.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Abook book = (Abook) jaxbUnmarshaller.unmarshal(file);
			
			System.out.println(book);
			System.out.println(book.contacts.size());
	 
		  } catch (JAXBException e) {
			e.printStackTrace();
		  }

	}

}


@XmlRootElement(name = "abook")
@XmlAccessorType(XmlAccessType.NONE) 
class Abook {
	
	public Abook(Map<Integer, Contact> map) {
        for( Map.Entry<Integer, Contact> e : map.entrySet() )
            contactsList.add(new Contact(e));
    }
    public Abook() {}
    
    @XmlElement (name="contact", type = Contact.class)
    public List<Contact> getContacts() {  
        List<Contact> list = new ArrayList<Contact>();  
        for (Map.Entry<Integer, Contact> entry : contacts.entrySet()) {  
        	Contact c =new Contact(entry);  
            list.add(c);  
        }  
        return list;  
    } 
    
//    @XmlElement (name="contact", type = Contact.class)
    public void setContacts(List<Contact> list) {  
        for(Contact c : list) {  
            this.contacts.put(c.id, c);  
        }  
    }  
    
    private List<Contact> contactsList = new ArrayList<Contact>();
	
//	@XmlElements(value = { @XmlElement(name = "contact", type = TreeMap.class)})
//	@XmlElement (name="contact")
	TreeMap<Integer, Contact> contacts = new TreeMap<Integer, Contact>();
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Contact> entry : contacts.entrySet()) {
			sb.append(entry.getKey() + ". " + entry.getValue() + "\n");
		}
		return sb.toString();
	}
	
	
//	public static class XmlXuMapAdapter<K, V> extends XmlAdapter<Abook, Map<K, V>> {
//		 
//	    @Override
//	    public Map<K, V> unmarshal(Abook book) throws Exception {
//	        TreeMap<Integer, Contact> map = new TreeMap<Integer, Contact>();
//	 
//	        for (Contact c : book.getContacts()) {
//	            map.put(c.id, c);
//	        }
//	        return (Map<K, V>) map;
//	    }
//	 
//	    @Override
//	    public Abook marshal(Map<K, V> v) throws Exception {
//	    	Abook book = new Abook((Map<Integer, Contact>) v);
//	 
////	        for (Map.Entry<K, V> entry : v.entrySet()) {
////	            MapEntryType<K, V> mapEntryType = new MapEntryType<K, V>();
////	            mapEntryType.setKey(entry.getKey());
////	            mapEntryType.setValue(entry.getValue());
////	            book.getEntry().add(mapEntryType);
////	        }
//	        return book;
//	    }
//	}
	
	
}

@XmlType(name="contact")
class Contact {
	
	public Contact() {}
    public Contact(Map.Entry<Integer, Contact> e) {
       id = e.getKey();
       name = e.getValue().name;
       phone = e.getValue().phone;
       address = e.getValue().address;
    }

	@XmlAttribute(name = "id", required = true)
	public int id;
	
	@XmlElement(required = true)
	public String name;
	
	@XmlElement
	public List<String> phone;
	
	@XmlElement
	public String address = "not set";
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\nPhone:");
		for (String ph : phone) {
			sb.append(ph + "\n");
		}
		sb.append("Address: " + address);
		return sb.toString();
		
	}
}

class Group {
	
	
}



