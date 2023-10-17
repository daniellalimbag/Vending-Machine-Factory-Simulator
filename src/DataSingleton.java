/**
 * This class allows for data to be transferred between classes
 */
public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();
    private VendingMachine currentVM;
    private SpecialVendingMachine currentSVM;
    private VendingMachine customRVM;
    private SpecialVendingMachine customSVM;
    private VendingMachine predefinedRVM;
    private SpecialVendingMachine predefinedSVM;
    private boolean isRVMCurrent;
    private String lastRVMrestock;
    private String lastSVMrestock;

    /**
     * Retrieves the current instance of the DataSingleton data type
     * @return instance which allows for use of features of other classes
     */
    public static DataSingleton getInstance() {
        return instance;
    }

    /**
     * Passes the data from the VendingMachine object parameter to the VendingMachine object in the DataSingleton file
     * @param VM is the RVM from which data will be taken from
     */
    public void setCurrentVM(VendingMachine VM){this.currentVM = VM;}

    /**
     * Passes the data from the VendingMachine object parameter to the VendingMachine object in the DataSingleton file
     * @param specialVM is the SVM from which data will be taken from
     */
    public void setCurrentSVM(SpecialVendingMachine specialVM) {
        this.currentSVM = specialVM;
    }

    /**
     * Deletes the reference to the current vending machine.
     * Sets the current vending machine to null, indicating that there is no active vending machine
     */
    public void deleteCurrentVM(){this.currentVM = null;}

    /**
     * Retrieves the currently active VendingMachine object
     * @return The currently active VendingMachine object, or null if no vending machine is currently.
     */
    public VendingMachine getCurrentVM(){return currentVM;}

    /**
     * Retrieves the currently active VendingMachine object
     * @return The currently active VendingMachine object, or null if no vending machine is currently.
     */
    public VendingMachine getCustomRVM(){return customRVM;}

    /**
     * Retrieves the currently active SpecialVendingMachine object
     * @return The currently active SpecialVendingMachine object, or null if no vending machine is currently.
     */
    public SpecialVendingMachine getCustomSVM(){return customSVM;}

    /**
     * Sets a custom VendingMachine as the current custom vending machine
     * @param vm The custom VendingMachine object to be set as the current custom vending machine.
     */
    public void setCustomRVM(VendingMachine vm){this.customRVM = vm;}

    /**
     * Sets a custom VendingMachine as the current custom vending machine
     * @param svm The custom VendingMachine object to be set as the current custom vending machine.
     */
    public void setCustomSVM(SpecialVendingMachine svm){this.customSVM = svm;}

    /**
     * Sets a VendingMachine as the current pre-defined vending machine
     * @param vm The VendingMachine object to be set as the pre-defined  vending machine.
     */
    public void setPredefinedRVM(VendingMachine vm){this.predefinedRVM = vm;}

    /**
     *  Sets a SpecialVendingMachine as the current pre-defined special vending machine
     * @param svm The SpecialVendingMachine object to be set as the pre-defined special vending machine.
     */
    public void setPredefinedSVM(SpecialVendingMachine svm) {
        this.predefinedSVM = svm;
    }

    /**
     * Retrieves the pre-defined VendingMachine attribute
     * @return VendingMachine object that was set as the pre-defined vending machine
     */
    public VendingMachine getPredefinedRVM(){return this.predefinedRVM;}

    /**
     * Retrieves the pre-defined SpecialVendingMachine attribute
     * @return SpecialVendingMachine object that was set as the pre-defined vending machine
     */
    public SpecialVendingMachine getPredefinedSVM() {return this.predefinedSVM;}

    /**
     * Retrieves the currentSVM attribute
     * @return SpecialVendingMachine object that was set as the pre-defined vending machine
     */
    public SpecialVendingMachine getCurrentSVM() {return this.currentSVM;
    }

    /**
     * Sets a boolean that shows if the current vending machine being run is a RVM
     * @param b The boolean that equals true if the current VM being used is regular
     */
    public void setRVMCurrent(boolean b){this.isRVMCurrent = b;}

    /**
     * Checks if the current vending machine being used is an RVM
     * @return boolean true if the current VM being used is regular, false if it is special
     */
    public boolean getRVMCurrent(){return this.isRVMCurrent;}

    /**
     * Sets the date and time of the last restock of an RVM in string format
     * @param lastrestock the string format of the last restock for an RVM
     */
    public void setLastRVMrestock(String lastrestock){this.lastRVMrestock=lastrestock;}

    /**
     * Gets the date and time of the last restock of an RVM in string format
     * @return String the string format of the last restock for an RVM
     */
    public String getLastRVMrestock(){return this.lastRVMrestock;}

    /**
     * Sets the date and time of the last restock of an SVM in string format
     * @param lastrestock the string format of the last restock for an SVM
     */
    public void setLastSVMrestock(String lastrestock){this.lastSVMrestock=lastrestock;}

    /**
     * Gets the date and time of the last restock of an SVM in string format
     * @return String the string format of the last restock for an SVM
     */
    public String getLastSVMrestock(){return this.lastSVMrestock;}
}
