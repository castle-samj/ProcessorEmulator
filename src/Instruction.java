/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class Instruction {
    /**
     * The potential types of instructions. Currently limited to I/O, Calculate, and Fork
     */
    enum REGISTER_TYPES {
        IO,
        CALCULATION,
        FORK
    }

    /* variables */
    private final Process parent_process;
    private final short parent_pid;
    private final REGISTER_TYPES register;
    private final boolean is_io;
    private int cycles_remain;

    /* constructor */
    public Instruction(REGISTER_TYPES new_type, int new_cpu_cycles, Process new_parent) {
        this.parent_process = new_parent;
        this.parent_pid = new_parent.getPID();
        this.register = new_type;
        this.cycles_remain = new_cpu_cycles;
        this.is_io = (new_type.equals(REGISTER_TYPES.IO));
    }

    // reduce number of cycles by one
    public void decrementInstructionCycles() {
        this.cycles_remain--;
    }

    /* getters */
    public Process getParentProcess() {
        return this.parent_process;
    }
    public short getParentPID() {
        return this.parent_pid;
    }
    public REGISTER_TYPES getRegister() {
        return register;
    }
    public int getCyclesRemain() {
        return cycles_remain;
    }

    /* special */
    public boolean isIO() {
        return is_io;
    }
    public void changeParentProcessState(IKernel.state new_state) {
        this.parent_process.setCurrentState(new_state);
    }
} // end class process