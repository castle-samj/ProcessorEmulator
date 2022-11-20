/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class ProcessDisplay {
    public static void display(Process display_process) {
        System.out.println("PID " + display_process.getPID() + " in state " + display_process.getCurrentState());
        System.out.println("Remaining Instructions: " + display_process.getProcessCyclesRemain());
        for (int i = 0; i < display_process.sizeOfProcess(); i++) {
            System.out.println("     - " + display_process.getCurrentInstruction(i) + ": "
                    + display_process.getInstructionCyclesRemain(i) + " Cycles Remaining");
        }
    } // end processDisplay
} // end procDisplay