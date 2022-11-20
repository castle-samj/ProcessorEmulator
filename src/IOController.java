import java.util.ArrayList;

/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Class responsible for the handling of processes when they are needing I/O support
 * Emulates the WAITING state for a process
 */

public class IOController extends IHardware {
    private ArrayList<Instruction> waiting_queue = new ArrayList<>();

    /* Constructor */
    public IOController() {
        this.hid = IKernel.generateHID();
    }

    /* setters */
    public void addToWaiting(Instruction wait_instruction) {
        wait_instruction.changeParentProcessState(IKernel.state.WAITING);
        this.waiting_queue.add(wait_instruction);
    }

    /* getters */
    public Instruction getInstructionInWaiting() {
        return this.waiting_queue.get(0);
    }

    /* special */
    /** Returns true if WAITING_queue is empty */
    public boolean isEmpty(){
        return this.waiting_queue.isEmpty();
    }
    public void decrementWaiting(){
        this.waiting_queue.get(0).decrementInstructionCycles();
    }
}
