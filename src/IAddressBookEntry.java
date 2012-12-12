import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public abstract class IAddressBookEntry {
	@XmlAttribute(name = "id", required = true)
	public int id;
	
	@XmlElement(name = "name", required = true)
	public String name;
}
