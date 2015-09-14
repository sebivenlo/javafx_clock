package nl.fontys.sebivenlo.svgparser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ode
 */
class SVGcontentHandler extends DefaultHandler {

    private final Map<String, Map<String, String>> paths;
    private boolean inGelement = false;

    public SVGcontentHandler() {
        paths = new LinkedHashMap<>();
    }

    @Override
    public void startElement( String uri, String localName, String qName,
            Attributes attributes ) throws SAXException {
        if ( qName.equals( "g" ) ) {
            inGelement = true;
        }
        if ( inGelement && qName.equals( "path" ) ) {
            Map<String, String> content = new HashMap<>();
            for ( int a = 0; a < attributes.getLength(); a++ ) {
                String name = attributes.getLocalName( a );
                String value = attributes.getValue( a );
                content.put( name, value );
            }
            String key = attributes.getValue( "id" );
            paths.put( key, content );
        }
    }

    @Override
    public void endElement( String uri, String localName, String qName ) throws
            SAXException {
        if ( qName.equals( "g" ) ) {
            inGelement = false;
        }
    }

    Map<String, String> getPaths() {
        Map<String, String> result = new LinkedHashMap<>();
        for ( String n : this.paths.keySet() ) {
            result.put( n, this.paths.get( n ).get( "d" ) );
        }
        return result;
    }

    Set<String> getPathIds() {
        return this.paths.keySet();
    }

    Map<String, String> getPathInfo( String pathId ) {
        return this.paths.get( pathId );
    }
}
