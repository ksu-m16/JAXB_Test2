import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
		
		FileOutputStream fos = null;
		try {
			File file = new File("test.xml");

	        JAXBContext jc = JAXBContext.newInstance(Abook.class);  
	        
	        Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
			Abook book = (Abook) jaxbUnmarshaller.unmarshal(file);
	        
			System.out.println(book);
			System.out.println("add contacts");
			
			Contact c3 = new Contact();
			c3.id = 3;
		    c3.name = "c3";
		    c3.phone = new ArrayList<String>();
		    c3.phone.add("121212");
		    c3.phone.add("212121");
 		    c3.address = "";
		    
		    Contact c4 = new Contact();
			c4.id = 4;
		    c4.name = "c4";
		    c4.address = "uiii";
			
		    book.contacts.put(c3.id, c3);  
		    book.contacts.put(c4.id, c4);  
			
	        Marshaller m = jc.createMarshaller();  
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        fos = new FileOutputStream(file);
	        m.marshal(book, fos);

			System.out.println(book);
	 
		  } catch (JAXBException e) {
			e.printStackTrace();
		  } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
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
    public Contact[] getContacts() {  
        List<Contact> list = new ArrayList<Contact>();  
        for (Map.Entry<Integer, Contact> entry : contacts.entrySet()) {  
        	Contact c =new Contact(entry);  
            list.add(c);  
        }  
        return list.toArray(new Contact[list.size()]);   
    } 
    
    public void setContacts(Contact[] arr) {  
        for(Contact c : arr) {  
            contacts.put(c.id, c);  
        }  
    }  
    
    private List<Contact> contactsList = new ArrayList<Contact>();
	
	TreeMap<Integer, Contact> contacts = new TreeMap<Integer, Contact>();
	
	@XmlElement(name = "group", type = Group.class)
	ArrayList<Group> groups = new ArrayList<Group>();
	
	public String toString(){
		StringBuilder sb = new StringBuilder("");
		for (Map.Entry<Integer, Contact> entry : contacts.entrySet()) {
			sb.append(entry.getKey() + ". " + entry.getValue() + "\n");
		}
		if (groups.size() > 0) {
			sb.append("\nGroups:\n");
			for (Group g : groups) {
				sb.append(g.id + ". " + g.name + "\n");
				
				for (Integer n : g.contacts) {
					sb.append(contacts.get(n).name + "\n");
				}
				
			}
			
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
	public List<String> phone = new ArrayList<String>();
	
	@XmlElement
	public String address = "not set";
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\n");
		if (phone.size() > 0) { 
			sb.append("Phone:\n");
			for (String ph : phone) {
				sb.append(ph + "\n");
			}
		}
		sb.append("Address: " + address);
		return sb.toString();
		
	}
}

@XmlType(name="group")
class Group {
	public Group() {}

	@XmlAttribute(name = "id", required = true)
	public int id;
	
	@XmlElement(required = true)
	public String name;
	
	@XmlElement(name = "contact")
	public List<Integer> contacts = new ArrayList<Integer>();
	
}



