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
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class ProcessBuilder {
    public static void build(int num_proc_to_make, MassStorage current_hdd)
            throws IOException, SAXException, ParserConfigurationException {
        File templateXML = new File("src/templates.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document templates = builder.parse(templateXML);

        // generate random processes, up to num number of processes will be added to ArrayList
        for (int i = 0; i < num_proc_to_make; i++) {
            Process new_process = new Process(); // temp var for new process to be added

            //random number to select which template will be used
            int template_number = (int) ((Math.random() * (IKernel.getMax_instructions_per_process())) + 1);
            String temp_to_use = "type" + template_number;
            NodeList temp_node_list = templates.getElementsByTagName(temp_to_use);

            // poll the selected template for each instruction, generate random cycles from min/max,
            // and create new Instruction.
            for (int j = 0; j < temp_node_list.getLength(); j++) { // should be 5 times
                Node temp_node = temp_node_list.item(j); // the ith element that matches tempToUse
                if (temp_node.getNodeType() == Node.ELEMENT_NODE) { // ELEMENT_NODE means has sub-elements
                    Element template_element = (Element) temp_node;

                    Instruction.REGISTER_TYPES temp_type;
                    String temp_type_str = template_element.getElementsByTagName("action").item(0).getTextContent().trim();
                    if (temp_type_str.contains("fork")){
                        temp_type = Instruction.REGISTER_TYPES.FORK;
                    }
                    else if (temp_type_str.contains("I/O")){
                        temp_type = Instruction.REGISTER_TYPES.IO;
                    }
                    else if (temp_type_str.contains("calculate")){
                        temp_type = Instruction.REGISTER_TYPES.CALCULATION;
                    }
                    else {
                        System.out.println("ProcessBuilder failed to read process instruction type");
                        break;
                    }

                    int temp_min = Integer.parseInt(template_element.getElementsByTagName("min").item(0).getTextContent().trim());
                    int temp_max = Integer.parseInt(template_element.getElementsByTagName("max").item(0).getTextContent().trim());
                    int temp_cycles = (int) (Math.random() * (temp_max - temp_min + 1) + temp_min);

                    //create Instruction
                    Instruction temp_instruction = new Instruction(temp_type, temp_cycles, new_process);
                    //add Instruction to Process
                    new_process.addInstruction(j, temp_instruction);
                }
            }
            // add process to schedule (will change to READY on add)
            current_hdd.addProcess(new_process);
        }
    } // end buildProcesses
}
