package nl.fontys.sebivenlo.svgparser;

import java.io.IOException;
import java.net.URL;
import nl.fontys.sebivenlo.svgparser.SVGParser;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ode
 */
public class SVGParserTest {

    private static final String val1363 = "M 235.086,31.4489 L 234.91,33.2228 L 236.602,33.2228 L 236.016,39.0938 L 236.039,39.1408 "
            + "L 235.984,39.1169 C 233.844,38.9369 231.883,38.5078 230.086,37.8358 "
            + "C 227.934,36.9958 225.559,35.5659 224.949,33.4759 C 224.184,30.5508 226.137,28.2928 227.523,27.2108 "
            + "L 227.484,29.7379 C 228.227,27.3239 230.086,25.1089 230.086,25.1089 C 225.469,27.6519 224.055,30.1288 223.527,31.9138 "
            + "C 223.312,32.7148 223.348,33.3549 223.426,34.0548 C 223.719,35.6718 224.695,36.8279 225.863,37.7928 "
            + "C 228.527,39.9178 231.93,40.9958 235.738,41.4998 L 235.797,41.4839 L 235.773,41.5309 L 235.324,46.0349 "
            + "L 233.691,46.0349 L 233.516,47.8079 L 240.949,47.8079 L 241.125,46.0349 L 239.453,46.0349 L 239.875,41.7649 "
            + "L 239.848,41.7068 L 239.914,41.7339 C 241.387,41.7498 243.57,41.7068 243.57,41.7068 L 243.922,37.8199 L 242.926,37.8199 "
            + "L 242.457,39.1678 C 241.586,39.2498 240.41,39.2339 240.168,39.2299 L 240.098,39.2618 L 240.141,39.1909 "
            + "L 240.184,38.7148 L 240.73,33.2228 L 245.02,33.2228 L 244.805,35.3788 L 246.633,35.3788 L 247.027,31.4449 "
            + "L 235.09,31.4449";
    
    private static final String val1375 = "M 242.082,61.9608 C 240.66,61.9608 239.574,62.7888 239.574,64.2228 "
            + "C 239.574,65.6439 240.656,66.4728 242.082,66.4728 C 243.504,66.4728 244.59,65.6479 244.59,64.2228 "
            + "C 244.59,62.7928 243.508,61.9608 242.082,61.9608 z M 242.082,65.4329 C 241.398,65.4329 241.113,64.8629 241.113,64.2259 "
            + "C 241.113,63.5818 241.395,63.0118 242.082,63.0118 C 242.77,63.0118 243.051,63.5818 243.051,64.2259 "
            + "C 243.051,64.8629 242.773,65.4329 242.082,65.4329";

    public SVGParserTest() {
    }

    @Test
    public void fontysSVGtest() throws IOException {
        SVGParser svgParser = new SVGParser("files/fontys.svg");
        Map<String, String> paths = svgParser.getPaths();
        String value = paths.get("path1330");
        assertNull(value);
        value = paths.get("path1363");
        assertEquals(val1363,value);
        value = paths.get("path1375");
        assertEquals(val1375,value);
    }
    
    @Test 
    public void testResourceLoading(){
        ClassLoader cl = SVGParser.class.getClassLoader();
        URL url = getClass().getResource( "althands.svg");
        System.out.println( "url ex" + url.toExternalForm() +" url tostring " + url.toString());
        assertNotNull( url );
    
    }
}
