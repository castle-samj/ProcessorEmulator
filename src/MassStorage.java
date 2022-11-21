/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Emulates Long-Term Storage solution for virtual computer
 * Includes both hard_drive and virtual_memory spaces
 */
public class MassStorage extends IHardware {
    private final Process[] hard_drive;
    /** Stores PIDs of Processes in hard_drive */
    private final short[] hard_drive_meta;

    private final Instruction[] virtual_memory;
    /** Stores PIDs of Instructions in virtual_memory */
    private final short[] virtual_memory_meta;
    /** Stores usage information of frames in virtual_memory */
    private final boolean[] virtual_memory_used;


    /* Constructor */
    public MassStorage(){
        this.hid = IKernel.generateHID();
        this.hard_drive = new Process[max_hard_drive_size];
        this.hard_drive_meta = new short[max_hard_drive_size];
        this.virtual_memory = new Instruction[max_memory_address];
        this.virtual_memory_meta = new short[max_memory_address];
        this.virtual_memory_used = new boolean[max_memory_address];
    }

    /* setters */
    public void addProcessToHardDrive(Process new_process){
        // since this uses a modulo, the given address used will restart at 0 after the max_hard_drive_size is exceeded
        // in IKernel.PID_CONTROLLER. Given more time/interest, this could include a check against the existing process
        // in hard_drive to ensure it is TERMINATED before overwriting
        int temp_address = (new_process.getPID() % (max_hard_drive_size-1));
        new_process.setAllInstructionLocation(this.hard_drive_location, temp_address);
        this.hard_drive[temp_address] = new_process;
        this.hard_drive_meta[temp_address] = new_process.getPID();
    }
    public boolean addInstructionToVirtualMemory(Instruction extra_instruction) {
        if (isVirtualMemoryFull()) {
            return false;
        } else {
            int temp_address = getFreeAddress();
            extra_instruction.setLocation(this.virtual_memory_location, temp_address);
            this.virtual_memory[temp_address] = extra_instruction;
            this.virtual_memory_meta[temp_address] = extra_instruction.getParentPID();
            this.virtual_memory_used[temp_address] = true;
            return true;
        }
    }

    /* getters */
    public Instruction getProcessFromHardDriveByState(IKernel.state test_state) {
        int solution = 0;
        for (int index = 1; index < (max_hard_drive_size); index++) {
            if (this.hard_drive[index].getCurrentState() == test_state) {
                solution = index;
                break;
            }
        }
        return hard_drive[solution].getCurrentInstruction();
    }
    public Instruction removeInstructionFromVirtualMemory(short instruction_pid) {
        int solution = 0;
        for (int index = 1; index < max_memory_address; index++){
            if (virtual_memory_meta[index] == instruction_pid) {
                solution = index;
            }
        }
        return this.virtual_memory[solution];
    }
    public boolean isProcessInHardDriveByState(IKernel.state test_state) {
        boolean conclusion = false;
        for (int index = 1; index < (this.hard_drive.length-1); index++) {
            if ((hard_drive[index] != null) && (hard_drive[index].getCurrentState() == test_state)) {
                conclusion = true;
                break;
            }
        }
        return conclusion;
    }
    public boolean isPIDInVirtualMemory(short test_pid) {
        boolean conclusion = false;
        for (short virtual_memory_pid : virtual_memory_meta) {
            if (test_pid == virtual_memory_pid) {
                conclusion = true;
                break;
            }
        }
        return conclusion;
    }
    /** Returns true if no frame in virtual_memory is in use */
    public boolean isVirtualMemoryEmpty() {
        boolean conclusion = true;
        for (boolean bool : virtual_memory_used) {
            if (bool) {
                conclusion = false;
                break;
            }
        }
        return conclusion;
    }
    /** Returns true if all frames in virtual_memory are used */
    public boolean isVirtualMemoryFull() {
        boolean conclusion = true;
        for (boolean bool : virtual_memory_used) {
            if (!bool) {
                conclusion = false;
                break;
            }
        }
        return conclusion;
    }
    public void dumpHardDriveInfo() {
        for (Process process : this.hard_drive) {
            if (process == null) {
                break;
            }
            ProcessDisplay.display(process);
        }
    }


    // finds an address in virtual_memory that is not being used
    private int getFreeAddress() {
        int new_address = -1;
        for (int index = 0; index < max_memory_address; index++) {
            if (!virtual_memory_used[index]) {
                new_address = index;
            }
        }
        return new_address;
    }
}