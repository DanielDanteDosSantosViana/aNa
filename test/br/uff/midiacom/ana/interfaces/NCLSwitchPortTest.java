package br.uff.midiacom.ana.interfaces;

import br.uff.midiacom.ana.NCLDoc;
import br.uff.midiacom.ana.NCLInvalidIdentifierException;
import br.uff.midiacom.ana.node.NCLMedia;
import br.uff.midiacom.ana.node.NCLSwitch;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.StringReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class NCLSwitchPortTest {

    @Test
    public void test1() throws NCLInvalidIdentifierException {
        NCLSwitchPort port = new NCLSwitchPort("pinit");
        NCLMapping map = new NCLMapping();
        map.setComponent(new NCLMedia("med1"));
        map.setInterface(new NCLArea("trac1"));
        port.addMapping(map);

        String expResult = "<switchPort id='pinit'>\n\t<mapping component='med1' interface='trac1'/>\n</switchPort>\n";
        String result = port.parse(0);
        assertEquals(expResult, result);
    }

    @Test
    public void test2() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLSwitchPort instance = new NCLSwitchPort(reader, null);
            String expResult = "<switchPort id='pinit'>\n\t<mapping component='med1' interface='trac1'/>\n</switchPort>\n";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(expResult)));

            String result = instance.parse(0);
            //System.out.println(result);
            assertEquals(expResult, result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test3() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLDoc instance = new NCLDoc();
            instance.setReader(reader);
            String xml = "<ncl><body>"+
                    "<switch id='sw'><switchPort><mapping component='m1' interface='a1'/></switchPort>"+
                    "<media id='m1' src='media.png'><area id='a1' label='teste'/></media>"+
                    "</switch></body></ncl>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            String expResult = "media.png";
            String result = ((NCLMedia) ((NCLMapping) ((NCLSwitchPort) ((NCLSwitch) instance.getBody().getNodes().iterator().next()).getPorts().iterator().next()).getMappings().iterator().next()).getComponent()).getSrc();
            //System.out.println(result);
            assertEquals(expResult, result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test4() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLDoc instance = new NCLDoc();
            instance.setReader(reader);
            String xml = "<ncl><body>"+
                    "<switch id='sw'><switchPort><mapping component='m1' interface='a1'/></switchPort>"+
                    "<media id='m1' src='media.png'><area id='a1' label='teste'/></media>"+
                    "</switch></body></ncl>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            String expResult = "teste";
            String result = ((NCLArea) ((NCLMapping) ((NCLSwitchPort) ((NCLSwitch) instance.getBody().getNodes().iterator().next()).getPorts().iterator().next()).getMappings().iterator().next()).getInterface()).getLabel();
            //System.out.println(result);
            assertEquals(expResult, result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao1() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLSwitchPort instance = new NCLSwitchPort(reader, null);
            String xml = "<switchPort/>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao2() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLSwitchPort instance = new NCLSwitchPort(reader, null);
            String xml = "<switchPort id='start'/>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao3() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLSwitchPort instance = new NCLSwitchPort(reader, null);
            String xml = "<switchPort id='start'>"+
                    "<mapping/>"+
                    "</switchPort>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            assertFalse(instance.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao4() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLDoc instance = new NCLDoc();
            instance.setReader(reader);
            String xml = "<ncl><body><switch id='sw'>"+
                    "<switchPort><mapping component='m1' interface='a2'/></switchPort>"+
                    "<media id='m1'><area id='a1'/></media>"+
                    "<media id='m2'><area id='a2'/></media>"+
                    "</switch></body></ncl>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            NCLSwitch swt = (NCLSwitch) instance.getBody().getNodes().iterator().next();
            NCLSwitchPort swtp = (NCLSwitchPort) swt.getPorts().iterator().next();
            NCLMapping map = (NCLMapping) swtp.getMappings().iterator().next();

            assertFalse(map.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao5() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLDoc instance = new NCLDoc();
            instance.setReader(reader);
            String xml = "<ncl><body><switch id='sw'>"+
                    "<switchPort><mapping component='m4'/></switchPort>"+
                    "<media id='m1'><area id='a1'/></media>"+
                    "<media id='m2'><area id='a2'/></media>"+
                    "</switch></body></ncl>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            NCLSwitch swt = (NCLSwitch) instance.getBody().getNodes().iterator().next();
            NCLSwitchPort swtp = (NCLSwitchPort) swt.getPorts().iterator().next();
            NCLMapping map = (NCLMapping) swtp.getMappings().iterator().next();

            assertFalse(map.validate());
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void test_validacao6() {
        try{
            XMLReader reader = XMLReaderFactory.createXMLReader();

            NCLDoc instance = new NCLDoc();
            instance.setReader(reader);
            String xml = "<ncl><body><switch id='sw'>"+
                    "<switchPort><mapping component='m1'/></switchPort>"+
                    "<media id='m1'><area id='a1'/></media>"+
                    "<media id='m2'><area id='a2'/></media>"+
                    "</switch></body></ncl>";

            reader.setContentHandler(instance);
            reader.parse(new InputSource(new StringReader(xml)));

            NCLSwitch swt = (NCLSwitch) instance.getBody().getNodes().iterator().next();
            NCLSwitchPort swtp = (NCLSwitchPort) swt.getPorts().iterator().next();
            NCLMapping map = (NCLMapping) swtp.getMappings().iterator().next();

            boolean result = map.validate();

            for(String msg : map.getWarnings())
                System.out.println(msg);
            for(String msg : map.getErrors())
                System.out.println(msg);

            assertTrue(result);
        }
        catch(SAXException ex){
            fail(ex.getMessage());
        }
        catch(IOException ex){
            fail(ex.getMessage());
        }
    }
}