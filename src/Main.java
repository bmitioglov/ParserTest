
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

// SAX
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser;

        InputStream xmlData = null;
        try
        {
            xmlData = new FileInputStream("test.svg");

            parser = factory.newSAXParser();
            parser.parse(xmlData, new MyParser());

            
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // обработки ошибки, файл не найден
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            // обработка ошибки Parser
        } catch (SAXException e)
        {
            e.printStackTrace();
            // обработка ошибки SAX
        } catch (IOException e)
        {
            e.printStackTrace();
            // обработка ошибок ввода
        } 

    }
}

class MyParser extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if(qName.equals("path"))
            System.out.println("id отрезка "+attributes.getValue("id"));
            if (attributes.getValue("d")!=null){
                //System.out.println("Координаты "+attributes.getValue("d"));
                System.out.println("Длина отрезка "+segmLength(attributes.getValue("d")));
            }
            //
        super.startElement(uri, localName, qName, attributes);

        
    }
    
    public double segmLength(String str){
        //удаляем ненужные символы
        String str2;
        str2 = str.replace(',', ' ');
        String str3 = str2.replace('M', ' ');
        
        double[] coord = new double[4]; //массив координат
        
        int index = 0; //индекс символа в строке
        int indexCoord = 0; //индекс в массиве координат
        String token = "";
        
        //парсим строку
        while (index<str3.length())
        {
            if (Character.isWhitespace(str3.charAt(index))) {
                index++;
                if(token!=""){
                    coord[indexCoord] = Double.parseDouble(token);
                    indexCoord++;
                    token = "";
                }
            }
            else
            {
                token+=str3.charAt(index);
                index++;
            }
        }
        if (!Character.isWhitespace(str3.charAt(index-1)))  //записываем последний токен
            coord[indexCoord] = Double.parseDouble(token);
            

        return coord[3];
    }
}