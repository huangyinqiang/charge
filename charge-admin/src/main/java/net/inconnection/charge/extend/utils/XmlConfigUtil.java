package net.inconnection.charge.extend.utils;

import com.alibaba.fastjson.JSON;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Created by zhengkun on 17-9-14.
 */
public class XmlConfigUtil {

    public XmlConfigUtil() {}

    //获取XML地址
    public static Document getXmlDoc(String filePath){

        File xmlFile = new File(filePath);

        Document doc = null;

        if (xmlFile.exists()) {
            try {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;

                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                //update Element value

                //write the updated document to file or console
                doc.getDocumentElement().normalize();
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else {
            System.out.println("XmlConfigUtil.getXmlDoc(): file: " + filePath + "did not exist!!!");
        }
        return doc;
    }


    private static Object parse(Element root) {
        List<?> elements = root.elements();
        if (elements.size() == 0) {
            // 没有子元素
            return root.getTextTrim();
        } else {
            // 有子元素
            String prev = null;
            boolean guess = true; // 默认按照数组处理

            Iterator<?> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                String name = elem.getName();
                if (prev == null) {
                    prev = name;
                } else {
                    guess = name.equals(prev);
                    break;
                }
            }
            iterator = elements.iterator();
            if (guess) {
                List<Object> data = new ArrayList<Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((List<Object>) data).add(parse(elem));
                }
                return data;
            } else {
                Map<String, Object> data = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((Map<String, Object>) data).put(elem.getName(), parse(elem));
                }
                return data;
            }
        }
    }

    public static Object getJson(String fileName) {
        String xmlFilePath = XmlConfigUtil.class.getResource("/") + fileName;
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = null;
        try {
            File f = new File(xmlFilePath.substring(5, xmlFilePath.length()));
            InputStream in = new FileInputStream(f);
            document = reader.read(in);
            in.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();

        Object obj = parse(root); // 返回类型未知，已知DOM结构的时候可以强制转换


        return JSON.toJSON(obj);
    }
}
