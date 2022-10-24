/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class operation
{
    // variables
    public String operationType;
    public int cyclesRemain;

    // constructor
    public operation(String type, int cpuCycles)
    {
        this.operationType = type;
        this.cyclesRemain = cpuCycles;
    }

    // reduce number of cycles by one
    public void decrementCycles()
    {
        this.cyclesRemain = (this.cyclesRemain - 1);
    }
    public String getOperationType() {
        return operationType;
    }
} // end class process