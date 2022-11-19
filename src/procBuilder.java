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
    public static void processBuilder(int num_proc_to_make, MassStorage hdd)
            throws IOException, SAXException, ParserConfigurationException {
        File templateXML = new File("src/templates.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document templates = builder.parse(templateXML);

        // generate random processes, up to num number of processes will be added to ArrayList
        for (int i = 0; i < num_proc_to_make; i++) {
            Process newProc = new Process(); // temp var for new process to be added

            //random number to select which template will be used
            int templateNumber = (int) ((Math.random() * (IKernel.getMax_instructions_per_process())) + 1);
            String tempToUse = "type" + templateNumber;
            NodeList tempNodeList = templates.getElementsByTagName(tempToUse);

            // poll the selected template for each instruction, generate random cycles from min/max,
            // and create new Instruction.
            for (int j = 0; j < tempNodeList.getLength(); j++) { // should be 5 times
                Node tempNode = tempNodeList.item(j); // the ith element that matches tempToUse
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) { // ELEMENT_NODE means has sub-elements
                    Element templateElement = (Element) tempNode;

                    Instruction.register_types temp_type;
                    String temp_type_str = templateElement.getElementsByTagName("action").item(0).getTextContent().trim();
                    if (temp_type_str.contains(Instruction.register_types.FORK.name())){
                        temp_type = Instruction.register_types.FORK;
                    }
                    else if (temp_type_str.contains(Instruction.register_types.IO.toString())){
                        temp_type = Instruction.register_types.IO;
                    }
                    else if (temp_type_str.contains(Instruction.register_types.CALCULATION.name())){
                        temp_type = Instruction.register_types.CALCULATION;
                    }
                    else {
                        System.out.println("failed to read process instruction type");
                        break;
                    }

                    int tempMin = Integer.parseInt(templateElement.getElementsByTagName("min").item(0).getTextContent().trim());
                    int tempMax = Integer.parseInt(templateElement.getElementsByTagName("max").item(0).getTextContent().trim());
                    int tempCycles = (int) (Math.random() * (tempMax - tempMin + 1) + tempMin);

                    //create Instruction
                    Instruction tempOp = new Instruction(temp_type, tempCycles, newProc);
                    //add Instruction to Process
                    newProc.addInstruction(j, tempOp);
                }
            }
            // add process to schedule (will change to READY on add)
            hdd.addProcess(newProc);
        }
    } // end buildProcesses
}
