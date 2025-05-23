package com.toporkov.automobileapp.util;

import com.toporkov.automobileapp.web.dto.domain.coordinate.CreateCoordinateDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GpxParser {
    public static List<CreateCoordinateDTO> parseGpx(MultipartFile file, String vehicleId) throws BadRequestException {
        List<CreateCoordinateDTO> coordinates = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList trkptList = doc.getElementsByTagName("trkpt");
            for (int i = 0; i < trkptList.getLength(); i++) {
                Node trkptNode = trkptList.item(i);
                if (trkptNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element trkptElement = (Element) trkptNode;
                    double lat = Double.parseDouble(trkptElement.getAttribute("lat"));
                    double lon = Double.parseDouble(trkptElement.getAttribute("lon"));
                    Double ele = null;
                    Instant time = null;
                    NodeList children = trkptElement.getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        Node child = children.item(j);
                        if (child.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = child.getNodeName();
                            if ("ele".equals(nodeName)) {
                                ele = Double.parseDouble(child.getTextContent());
                            } else if ("time".equals(nodeName)) {
                                time = Instant.parse(child.getTextContent());
                            }
                        }
                    }
                    CreateCoordinateDTO dto = new CreateCoordinateDTO();
                    dto.setLatitude(lat);
                    dto.setLongitude(lon);
                    dto.setVehicleId(vehicleId);
                    dto.setCreateAt(time);
                    coordinates.add(dto);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new BadRequestException("Ошибка парсинга GPX: " + e.getMessage());
        }

        return coordinates;
    }
}
