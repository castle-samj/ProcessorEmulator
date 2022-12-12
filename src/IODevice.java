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
    public void decrementWaiting(Dispatcher current_dispatcher){
        // remove the IO Instruction from waiting_queue to decrement its cycles
        Instruction temp_inst = getInstructionInWaiting();
        temp_inst.decrementInstructionCycles();

        if (temp_inst.getCyclesRemain() < 1) {
        // if there are not more instruction cycles remaining..
            if (temp_inst.getParentProcess().getProcessCyclesRemain() > 0) {
                // and there are more Cycles on the Process, reschedule Process
                Process temp_process = temp_inst.getParentProcess();
                temp_process.updateProgramCounter();
                // this is normally retuning a bool for successful scheduling; if there is no way to schedule this
                // Process' next instruction yet, it will be "returned to HDD" and have to wait to be scheduled at random
                current_dispatcher.getLocalScheduler().scheduleInstruction(temp_process.getCurrentInstruction());
            } else {
                // there are no more cycles on Process, it is terminated
                temp_inst.changeParentProcessState(IKernel.state.TERMINATED);
            }
        }
        else {
        // there are more cycles on the IO Instruction, return the IO Instruction to waiting_queue
            this.addToWaiting(temp_inst);
        }
    }
}
