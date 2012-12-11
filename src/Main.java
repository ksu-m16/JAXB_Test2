import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			 
			File file = new File("test.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Abook.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Abook book = (Abook) jaxbUnmarshaller.unmarshal(file);
			
			System.out.println(book);
	 
		  } catch (JAXBException e) {
			e.printStackTrace();
		  }

	}

}

@XmlRootElement(name = "abook")
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

