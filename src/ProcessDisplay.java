/**
 * @author Sam Castle - 12/12/2022 - CMSC312 CPU Emulator
 */
public class ProcessDisplay {
    public static void display(Process display_process) {
        System.out.println("PID " + display_process.getPID() + " in state " + display_process.getCurrentState());
        int num_instructions_in_process = display_process.sizeOfProcess();
        System.out.println("Remaining Instructions: " + num_instructions_in_process);
        for (int i = 0; i < num_instructions_in_process; i++) {
            System.out.println("     - " + display_process.getCurrentInstruction(i).getRegister() + ": "
                    + display_process.getInstructionCyclesRemain(i) + " Cycles Remaining");
        }
    } // end processDisplay
} // end procDisplay