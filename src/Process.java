/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Subclass of PCB. Used to create the process object
 */
public class Process extends PCB
{
    /** constructor */
    public Process() {
        this.setPID();
        this.setCurrentState(IKernel.state.NEW);
    }

}