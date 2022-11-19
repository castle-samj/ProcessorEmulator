import java.util.ArrayList;

/**
 * Emulates Long-Term Storage solution for virtual computer
 * Includes both mass-storage and virtual-memory spaces
 */
public class MassStorage extends IHardware
{
    private ArrayList<Process> mass_storage = new ArrayList<>();
    private ArrayList<Instruction> virtual_memory = new ArrayList<>();

    public MassStorage(){
        this.hid = IKernel.generateHID();
    }

    /* setters */
    /** Add a process to the process_bank on Long-Term storage */
    public void addProcess(Process new_process){
        this.mass_storage.add(new_process);
    }

    /* getters */
    /** Returns the first Process in the bank */
    public Process getProcess(){
        return this.mass_storage.get(0);
    }
    public Process getProcessByID(short pid) {
        Process toReturn = getProcess();
        for (Process proc : this.mass_storage) {
            if (proc.getPID() == pid) {
                toReturn = proc;
            }
        }
        return toReturn;
    }

    /** Move a process Instruction from mass_storage to virtual_memory */
    public void loadInstruction(short process_id) {

    }
}