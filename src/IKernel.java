/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Maintains some persistent information for the current instance
 */
public class IKernel implements Runnable {
    // environment variables
    private static int SIZE_OF_MEMORY;
    private static int MAX_INSTRUCTIONS_PER_PROCESS;
    private static int SIZE_OF_FRAME;
    private static int SIZE_OF_HARD_DRIVE;
    private static CPU LOCAL_CPU;
    private static MassStorage LOCAL_HDD;
    private static MainMemory LOCAL_RAM;
    private static IScheduler LOCAL_SCHEDULER;
    private static IODevice LOCAL_IO;

    private static short PID_CONTROL = 1;
    private static short HID_CONTROL;
    private static int TIME_SLICE_DEFAULT;
    private static int TIME_SLICE;

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
    public static void setSizeOfMemory(int num) {
        SIZE_OF_MEMORY = num;
    }
    public static void setMaxInstructionsPerProcess(int num) {
        MAX_INSTRUCTIONS_PER_PROCESS = num;
    }
    public static void setSizeOfFrame(int num){
        SIZE_OF_FRAME = num;
    }
    public static void setSizeOfHardDrive(int num) {
        SIZE_OF_HARD_DRIVE = num;
    }
    protected void setLocalCPU(CPU new_cpu) {
        LOCAL_CPU = new_cpu;
    }
    public void setLocalHdd(MassStorage new_hdd){
        LOCAL_HDD = new_hdd;
    }
    public void setLocalRAM(MainMemory new_ram) {
        LOCAL_RAM = new_ram;
    }
    public void setLocalScheduler(IScheduler new_scheduler) {
        LOCAL_SCHEDULER = new_scheduler;
    }
    public void setLocalIO(IODevice new_io) {
        LOCAL_IO = new_io;
    }


    /* getters */
    public static int getSizeOfMemory() {
        return SIZE_OF_MEMORY;
    }
    public static int getMaxInstructionsPerProcess() {
        return MAX_INSTRUCTIONS_PER_PROCESS;
    }
    public static int getSizeOfFrame() {
        return SIZE_OF_FRAME;
    }
    public static int getSizeOfHardDrive() {
        return SIZE_OF_HARD_DRIVE;
    }
    public static short generatePid() {
        return PID_CONTROL++;
    }
    public static short generateHID() {
        return HID_CONTROL++;
    }
    public CPU getLocalCPU() {
        return LOCAL_CPU;
    }
    public MassStorage getLocalHDD() {
        return LOCAL_HDD;
    }
    public MainMemory getLocalRAM() {
        return LOCAL_RAM;
    }
    public IScheduler getLocalScheduler() {
        return LOCAL_SCHEDULER;
    }
    public IODevice getLocalIO() {
        return LOCAL_IO;
    }

    /** Set the CPU's time slice parameter. Used for critical-sections */
    public static void setTimeSliceDefault(int seconds) {
        TIME_SLICE_DEFAULT = seconds;
    }
    public static int getTimeSliceDefault() {
        return TIME_SLICE_DEFAULT;
    }
    public static void setTimeSlice() {
        TIME_SLICE = getTimeSliceDefault();
    }
    public static void setTimeSlice(int new_time) {
        TIME_SLICE = new_time;
    }
    public void decrementTimeSlice() {
        TIME_SLICE--;
    }
    /** Returns the current value of time_slice (mostly just for the CPU to check) */
    public int getTimeSlice() {
        return TIME_SLICE;
    }

    /**
     * Threads
     */
    public void run() {

    }
}
