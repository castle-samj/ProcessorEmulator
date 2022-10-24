import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class procBuilder {
    public static void processBuilder(SchedulerRR currentSchedule, Dispatcher currentDispatcher, int num)
            throws IOException, SAXException, ParserConfigurationException {
        File templateXML = new File("src/templates.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document templates = builder.parse(templateXML);

        // generate random processes, up to num number of processes will be added to ArrayList
        for (int i = 0; i < num; i++) {
            process newProc = new process(procCount.x++, currentDispatcher); // temp var for new process to be added

            //random number to select which template will be used
            int templateNumber = (int) ((Math.random() * (5)) + 1);
            String tempToUse = "type" + templateNumber;
            NodeList tempNodeList = templates.getElementsByTagName(tempToUse);

            // poll the selected template for each operation, generate random cycles from min/max,
            // and create new operation.
            for (int j = 0; j < tempNodeList.getLength(); j++) { // should be 5 times
                Node tempNode = tempNodeList.item(j); // the ith element that matches tempToUse
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) { // ELEMENT_NODE means has sub-elements
                    Element templateElement = (Element) tempNode;
                    String tempType = templateElement.getElementsByTagName("action").item(0).getTextContent().trim();
                    int tempMin = Integer.parseInt(templateElement.getElementsByTagName("min").item(0).getTextContent().trim());
                    int tempMax = Integer.parseInt(templateElement.getElementsByTagName("max").item(0).getTextContent().trim());
                    int tempCycles = (int) (Math.random() * (tempMax - tempMin + 1) + tempMin);

                    //create operation
                    operation tempOp = new operation(tempType, tempCycles);
                    //add operation to process
                    newProc.addOperations(tempOp);
                }
            }
            // set new process to NEW
            newProc.setNew(state.NEW);
            // add process to schedule (will change to READY on add)
            currentSchedule.addToSchedule(newProc, currentDispatcher);
        }
    } // end buildProcesses
}
