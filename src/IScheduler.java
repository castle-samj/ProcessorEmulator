import java.util.ArrayList;
/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Base class of scheduler, to be inherited by each variation of scheduler
 */
abstract class IScheduler extends IKernel {
    /** List of Instructions in the order of priority */
    ArrayList<Short> ready_queue = new ArrayList<>();

    /* setters */
    abstract public void addToReadyQueue(Instruction new_instruction);

    /* getters */
    abstract public short getNextInstructionPID();
    abstract public short referenceInstruction(int index);

    /* special */
    abstract void ensureNextIsInMainMemory();
    abstract void scheduleSomething();
    abstract boolean scheduleInstruction(Instruction temp_instruction);
    abstract int size();
    abstract boolean isEmpty();
}
