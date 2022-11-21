/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Round Robin style scheduler, where each process is added to the end of a queue. Process at
 * front of queue is selected for processing on CPU and, if more time is needed, returned to end of schedule list.
 */
public class sRoundRobin extends IScheduler {

    /* setters */
    @Override
    public void addToReadyQueue(Instruction new_instruction) {
        new_instruction.changeParentProcessState(state.READY);
        this.ready_queue.add(new_instruction.getParentPID());
    }


    /* getters */
    @Override
    public short getNextInstructionPID() {
        short instruction_pid = this.ready_queue.get(0);
        this.remove();
        return instruction_pid;
    }
    @Override
    public short referenceInstruction(int index) {
        return this.ready_queue.get(index);
    }


    /* special */
    @Override
    public void ensureNextIsInMainMemory() {
        while (true) {
            short instruction_pid = this.ready_queue.get(0);
            // if Instruction is already in main_memory, exit
            if (getLocalRAM().hasPID(instruction_pid)) {
                break;
            }
            // check in virtual_memory
            else if (getLocalHDD().isPIDInVirtualMemory(instruction_pid)) {
                Instruction instruction_in_virtual_memory = getLocalHDD().removeInstructionFromVirtualMemory(instruction_pid);
                // try to move to main_memory (there may be free space)
                if (getLocalRAM().moveInstructionIntoMainMemory(instruction_in_virtual_memory)) {
                    break;
                }
                // no free space in main_memory, victim selection
                else {
                    victimSelection();
                    // after victimSelection, try again to fit into main_memory
                }
            }
        }
    }
    @Override
    public int size() {
        return this.ready_queue.size();
    }
    @Override
    public boolean isEmpty() {
        return this.ready_queue.isEmpty();
    }
    @Override
    public void scheduleSomething() {
        // if memory is full, do not schedule something
        if (getLocalRAM().isFull() && getLocalHDD().isVirtualMemoryFull()) {
            return;
        }
        // if hard_drive has any NEW process, get instruction
        if (getLocalHDD().isProcessInHardDriveByState(state.NEW)) {
            Instruction temp_instruction = getLocalHDD().getProcessFromHardDriveByState(state.NEW);
            temp_instruction.changeParentProcessState(state.READY);
            // try to move instruction to main_memory
            if (getLocalRAM().moveInstructionIntoMainMemory(temp_instruction)){
                addToReadyQueue(temp_instruction);
            } else {
                // main_memory did not accept the instruction, try virtual_memory
                if (getLocalHDD().isVirtualMemoryFull()) {
                    // if virtual_memory is full, return instruction and reset state to NEW
                    temp_instruction.changeParentProcessState(state.NEW);
                    System.out.println("Memory full. Nothing scheduled at this time");
                } else {
                    getLocalHDD().addInstructionToVirtualMemory(temp_instruction);
                    addToReadyQueue(temp_instruction);
                }
            }
        }
        // else, no process is scheduled
    }
    @Override
    public boolean scheduleInstruction(Instruction temp_instruction) {
        boolean conclusion = false;
        // if memory is full, do not schedule something
        if (getLocalRAM().isFull() && getLocalHDD().isVirtualMemoryFull()) {
            return false;
        }
        temp_instruction.changeParentProcessState(state.READY);
        // try to move instruction to main_memory
        if (getLocalRAM().moveInstructionIntoMainMemory(temp_instruction)){
            addToReadyQueue(temp_instruction);
            conclusion = true;
        } else {
            // main_memory did not accept the instruction, try virtual_memory
            if (getLocalHDD().isVirtualMemoryFull()) {
                // if virtual_memory is full, return instruction and reset state to NEW
                temp_instruction.changeParentProcessState(state.NEW);
                System.out.println("Memory full. Nothing scheduled at this time");
            } else {
                getLocalHDD().addInstructionToVirtualMemory(temp_instruction);
                addToReadyQueue(temp_instruction);
                conclusion = true;
            }
        }
        // else, no process is scheduled
        return conclusion;
    }
    public void remove() {
        this.ready_queue.remove(0);
    }
    public void clear() {
        this.ready_queue.clear();
    }
    public void victimSelection() {
        try {
            // for each pid in ready_queue, check that it is in main_memory
            short victim = (short)(this.ready_queue.size()-1);
            for (short test_pid = (short)ready_queue.size(); test_pid > 0; test_pid--) {
                if (getLocalRAM().hasPID(test_pid)) {
                    victim = test_pid;
                }
            }
            // with Instruction in main_memory, swap it to virtual_memory
            Instruction temporary_instruction = getLocalRAM().getInstructionByPID(victim);
            if (getLocalHDD().addInstructionToVirtualMemory(temporary_instruction)) {
                return;
            }
            // virtual_memory is full, also. Move victim to hard_drive and wear cone of shame
            else {
                // instruction never left hard_drive, so its instance will be destroyed but retain meta-data
                temporary_instruction.changeParentProcessState(state.NEW);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Problem while trying victim-selection");
        }
    }
}
