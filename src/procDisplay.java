/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class procDisplay {
    public static void processDisplay(Process dProc) {
        System.out.println("PID " + dProc.getPID() + " in state " + dProc.getCurrentState());
        System.out.println("Remaining Instructions: " + dProc.sizeOfProcess());
        for (int i = 0; i < dProc.sizeOfProcess(); i++) {
            System.out.println("     - " + dProc.getRegisterByIndex(i) + ": "
                    + dProc.getInstructionCyclesRemain(i) + " Cycles Remaining");
        }
    } // end processDisplay
} // end procDisplay