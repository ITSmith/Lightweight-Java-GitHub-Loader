package application.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Helper class to wrap a list of sources and destinations. This is used for saving the
 * lists to XML.
 */
public class DataWrapper {
	private List<Source> sources;
	private List<Destination> destinations;

    @XmlElement(name = "source")
    public List<Source> getSources() {
        return sources;
    }
    
    @XmlElement(name = "destination")
    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
    
    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
}