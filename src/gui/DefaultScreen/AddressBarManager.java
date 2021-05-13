package gui.DefaultScreen;

public interface AddressBarManager {
    /**
     * set the address in the address bar
     * @param address the address to set
     */
    void setAddress(String address);

    /**
     * @return the address that is currently in the address bar
     */
    String getAddress();
}
