/** Maintains some persistent information for the current instance */
public class IKernel {
    // environment variables
    private static int SIZE_OF_MAIN_MEMORY;
    private static int MAX_INSTRUCTIONS_PER_PROCESS;
    private static int SIZE_OF_FRAME;
    private static CPU LOCAL_CPU;
    private static MassStorage LOCAL_HDD;
    private static MainMemory LOCAL_RAM;
    private static IScheduler LOCAL_SCHEDULER;
    private static IOController LOCAL_IO;

    private static short PID_CONTROL;
    private static short HID_CONTROL;
    private int TIME_SLICE;

    /**
     * The states a process can be in
     */
    protected enum state {
        NEW,
        READY,
        RUNNING,
        WAITING,
        TERMINATED
    }

    /* setters */
    public static void setSize_of_main_memory(int num) {
        IKernel.SIZE_OF_MAIN_MEMORY = num;
    }
    public static void setMax_instructions_per_process(int num) {
        IKernel.MAX_INSTRUCTIONS_PER_PROCESS = num;
    }
    public static void setSizeOfFrame(int num){
        IKernel.SIZE_OF_FRAME = num;
    }
    protected void setLocalCPU(CPU new_cpu) {
        IKernel.LOCAL_CPU = new_cpu;
    }
    public void setLocalHdd(MassStorage new_hdd){
        IKernel.LOCAL_HDD = new_hdd;
    }
    public void setLocalRAM(MainMemory new_ram) {
        IKernel.LOCAL_RAM = new_ram;
    }
    public void setLocalScheduler(IScheduler new_scheduler) {
        IKernel.LOCAL_SCHEDULER = new_scheduler;
    }
    public void setLocalIO(IOController new_io) {
        IKernel.LOCAL_IO = new_io;
    }


    /* getters */
    public static int getSize_of_main_memory() {
        return SIZE_OF_MAIN_MEMORY;
    }
    public static int getMax_instructions_per_process() {
        return MAX_INSTRUCTIONS_PER_PROCESS;
    }
    public static int getSizeOfFrame() {
        return SIZE_OF_FRAME;
    }
    public static short generatePid() {
        return PID_CONTROL++;
    }
    public static short generateHID() {
        return HID_CONTROL++;
    }
    public static CPU getLocalCPU() {
        return LOCAL_CPU;
    }
    public static MassStorage getLocalHDD() {
        return LOCAL_HDD;
    }
    public static MainMemory getLocalRAM() {
        return LOCAL_RAM;
    }
    public static IScheduler getLocalScheduler() {
        return LOCAL_SCHEDULER;
    }
    public static IOController getLocalIO() {
        return LOCAL_IO;
    }

    /** Set the CPU's time slice parameter. Used for critical-sections */
    public void setTimeSlice(int seconds) {
        this.TIME_SLICE = seconds;
    }
    public void decrementTimeSlice() {
        this.TIME_SLICE--;
    }
    /** Returns the current value of time_slice (mostly just for the CPU to check) */
    public int getTimeSlice() {
        return TIME_SLICE;
    }


    /* state modifiers */
    // TODO
}
