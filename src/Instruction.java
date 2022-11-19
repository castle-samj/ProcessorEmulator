/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Instruction {
    /**
     * The potential types of instructions. Currently limited to I/O, Calculate, and Fork
     */
    enum register_types {
        IO,
        CALCULATION,
        FORK
    }

    /* variables */
    private final Process parent_process;
    private final short parent_pid;
    private final register_types register;
    private final boolean is_io;
    private int cycles_remain;

    /* constructor */
    public Instruction(register_types new_type, int new_cpu_cycles, Process new_parent) {
        this.parent_process = new_parent;
        this.parent_pid = new_parent.getPID();
        this.register = new_type;
        this.cycles_remain = new_cpu_cycles;
        this.is_io = (new_type.equals(register_types.IO));
    }

    // reduce number of cycles by one
    public void decrementInstructionCycles() {
        this.cycles_remain--;
    }

    /* getters */
    public short getParentPID() {
        return this.parent_pid;
    }
    public register_types getRegister() {
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