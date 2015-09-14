package nl.fontys.sebivenlo.svgparser;

import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import static nl.fontys.sebivenlo.svgparser.SVGSimplePath.getShape;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author ode
 */
public class SVGParser {

    private final String document;
    private final SVGcontentHandler handler;
    private boolean parsed = false;

    public SVGParser( String document ) throws IOException {
        this.document = document;
        this.handler = new SVGcontentHandler();
    }

    /**
     * This parse method parses an svg file.
     */
    private void parse() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware( true );
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler( handler );
            reader.parse( document );
            parsed = true;
        } catch ( SAXException | ParserConfigurationException | IOException ex ) {
            System.out.println( "Exception says: " + ex.getMessage() );
        }
    }

    /**
     * The method returns a map with the path elements of the svg file. The keys
     * is: id and the value is: d in the path element's attributes. The parse
     * method created this map.
     *
     * @return the key-value pairs as a map.
     */
    public Map<String, String> getPaths() {
        if ( !parsed ) {
            this.parse();
        }
        return handler.getPaths();
    }

    @Deprecated
    public static String convertToFileURL( String filename ) {
        String path = new File( filename ).getAbsolutePath();
        if ( File.separatorChar != '/' ) {
            path = path.replace( File.separatorChar, '/' );
        }

        if ( !path.startsWith( "/" ) ) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    public Set<String> getPathIds() {
        if ( !parsed ) {
            this.parse();
        }
        return handler.getPathIds();
    }

    public Map<String, String> getPathInfo( String pathId ) {
        if ( !parsed ) {
            this.parse();
        }
        return handler.getPathInfo( pathId );
    }

    public Map<String, Shape> getShapeMap(  ) {
        if ( !parsed ) {
            this.parse();
        }
        Map<String,Shape> result = new HashMap<>();
        for (String k : handler.getPathIds()) {
            result.put( k, getShape( handler.getPathInfo( k )) );
        }
        return result;
    }

}
