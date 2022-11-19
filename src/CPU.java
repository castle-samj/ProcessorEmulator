/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class CPU extends IHardware {

    /* Constructor */
    public CPU(){
        this.hid = IKernel.generateHID();
    }

    /** Returns an _byte_ to signify exit case.
     <ul>
        <li>Case 0: Process was type calculate and has cycles remaining.</li>
        <li>Case 1: Process is type I/O and needs to be moved to WAITING state.</li>
        <li>Case 2: Process has no cycles remaining and timeSlice is still valid. Send to TERMINATED state.</li>
        <li>Case 3: Process requires a Fork call, and spawns a child. </li>
     </ul>
     */
    public byte cpuTime(Dispatcher current_dispatcher, Instruction current_instruction) {
        byte exit_condition;

        // check if I/O
        if (current_instruction.isIO()) {
            exit_condition = 1;
        } else if (current_instruction.getRegister() == Instruction.register_types.FORK) {
            Fork(current_instruction);
            exit_condition = 3;
        } else {
            while (current_dispatcher.getTimeSlice() > 0 && (current_instruction.getCyclesRemain() > 0)) {
                // remove one cycle from instruction
                current_instruction.decrementInstructionCycles();

                // process operations in WAITING are decremented concurrently with CPU cycles
                current_dispatcher.decrementIfInWaiting();

                current_dispatcher.decrementTimeSlice();
            }

            if (current_instruction.getCyclesRemain() == 0) {
                // handle TERMINATE first
                exit_condition = 2;
            } else {
                // default exit because more cycles remain
                exit_condition = 0;
            }
        } // end calculate section

        return exit_condition;
    } // end cpuTime

    public void Fork(Instruction toFork) {
        // TODO implement fork()
    }
}
