import java.util.ArrayList;

/** Base class of scheduler, to be inherited by each variation of scheduler */
abstract class IScheduler extends IKernel {
    /** List of Instructions in the order of priority */
    ArrayList<Instruction> ready_queue = new ArrayList<>();

    /* setters */
    abstract public void addToReadyQueue(Instruction new_instruction);

    /* getters */
    abstract public Instruction getNextInstruction();

    abstract int size();
    abstract boolean isEmpty();
}
