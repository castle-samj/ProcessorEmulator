import java.util.ArrayList;

/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Class responsible for the handling of processes when they are needing I/O support
 * Emulates the WAITING state for a process
 */

public class IODevice extends IHardware {
    private final ArrayList<Instruction> waiting_queue = new ArrayList<>();

    /* Constructor */
    public IODevice() {
        this.hid = IKernel.generateHID();
    }

    /* setters */
    public void addToWaiting(Instruction wait_instruction) {
        wait_instruction.changeParentProcessState(IKernel.state.WAITING);
        wait_instruction.setLocation(this.io_device_location, 0);
        this.waiting_queue.add(wait_instruction);
    }

    /* getters */
    public Instruction getInstructionInWaiting() {
        Instruction temporary_instruction = this.waiting_queue.get(0);
        this.waiting_queue.remove(0);
        temporary_instruction.setLocation(0,0);
        return temporary_instruction;
    }

    /* special */
    /** Returns true if WAITING_queue is empty */
    public boolean isEmpty(){
        return this.waiting_queue.isEmpty();
    }
    public byte decrementWaiting(){
        this.waiting_queue.get(0).decrementInstructionCycles();
        if (this.waiting_queue.get(0).getCyclesRemain() < 1) {
            return 2;
        } else {
            return 1;
        }
    }
}
