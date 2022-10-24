/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class procDisplay {
    public static void processDisplay(process dProc) {
        System.out.println("PID " + dProc.getPID() + " in state " + dProc.getState());
        System.out.println("Remaining Operations: " + dProc.getOperationsRemain());
        for (int i = 0; i < dProc.size(); i++) {
            System.out.println("     - " + dProc.getRegisterType(i) + ": "
                    + dProc.getRegisterCycles(i) + " Cycles Remaining");
        }
    } // end processDisplay
} // end procDisplay