import java.util.HashMap;
import java.util.Map;

class Customer {
    private String nic;
    private String name;
    private String address;
    private String vehicleLicensePlate;
    private String contactNumber;

    public Customer(String nic, String name, String address, String vehicleLicensePlate, String contactNumber) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getNic() { return nic; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getVehicleLicensePlate() { return vehicleLicensePlate; }
    public String getContactNumber() { return contactNumber; }
}

// Class to manage customers
