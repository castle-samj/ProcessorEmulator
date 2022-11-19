import java.util.ArrayList;

/**
 * Process Control Block class for each process generated
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
abstract class PCB {
    /* variables */
    private IKernel.state current_state;
    private short pid;
    // pointer to current instruction
    private int program_counter = 0;
    private ArrayList<Instruction> instructions_in_process = new ArrayList<>();
    private ArrayList<Integer> instruction_location_in_memory = new ArrayList<>();

    // TODO vars not implemented
    // List of open files (parent/child?)
    // Priority
    // Memory Management Information
    private int memory_location;
    // I/O Status
    // Accounting
    // Fork() [child?]


    /* PCB setters */
    public void setCurrentState(IKernel.state new_state) {
        this.current_state = new_state;
    }
    public void setPID() {
        this.pid = IKernel.generatePid();
    }
    public void updateProgramCounter() {
        this.program_counter++;
    }
    public void setMemoryLocation(int instruction_index, int new_location) {
        this.instruction_location_in_memory.set(instruction_index, new_location);
    }

    /* PCB getters */
    public IKernel.state getCurrentState() {
        return current_state;
    }
    public short getPID() {
        return this.pid;
    }


    /* process-specific getters */
    public int getProcessCyclesRemain() {
        int sum = 0;
        for (int i = this.instructions_in_process.size()-1; i > this.sizeOfProcess(); i--){
            sum = sum + this.getInstructionCyclesRemain(i);
        }
        return sum;
    }
    public int sizeOfProcess() {
        return (this.instructions_in_process.size() - program_counter)-1;
    }
    public boolean isEmpty() {
        return this.instructions_in_process.isEmpty();
    }


    /* instruction-specific setters */
    public void addInstruction(int num, Instruction new_instruction) {
        this.instructions_in_process.add(num, new_instruction);
    }

    /* instruction-specific getters */
    public Instruction getCurrentInstruction() {
        return this.instructions_in_process.get(program_counter);
    }
    public int getInstructionCyclesRemain(int i) {
        return this.instructions_in_process.get(i).getCyclesRemain();
    }
    /** Returns true if current Instruction is of type I/O */
    public boolean isIO() {
        return this.getCurrentInstruction().isIO();
    }
} // end class pcb