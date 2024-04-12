/**
 * @author Sam Castle - 12/12/2022 - CMSC312 CPU Emulator
 */
abstract class IHardware {
    protected short hid;
    protected byte cpu_location = 1;
    protected byte main_memory_location = 2;
    protected byte hard_drive_location = 3;
    protected byte virtual_memory_location = 4;
    protected byte io_device_location = 5;
    protected int max_hard_drive_size = IKernel.getSizeOfHardDrive()+1;
    protected int max_memory_address = (IKernel.getSizeOfMemory()/IKernel.getSizeOfFrame())+1;
}
