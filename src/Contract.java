import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class Contract {
    private String contractId;
    private String contractType; // L, M, S
    private Customer customer;
    private List<Apartment> apartments;
    private Date startDate;
    private Date endDate;
    private int duration; // in days
    private String status; // OPENED, ENDED, CLOSED, TERMINATED_DUE, TERMINATED

    public Contract(String contractId, String contractType, Customer customer, List<Apartment> apartments,
                    Date startDate, Date endDate, int duration) {
        this.contractId = contractId;
        this.contractType = contractType;
        this.customer = customer;
        this.apartments = apartments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.status = "OPENED";
    }

    // Getters
    public String getContractId() { return contractId; }
    public String getContractType() { return contractType; }
    public Customer getCustomer() { return customer; }
    public List<Apartment> getApartments() { return apartments; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getDuration() { return duration; }
    public String getStatus() { return status; }
    public int getRemainingDays() {
        long diff = endDate.getTime() - new Date().getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    // Setters
    public void setStatus(String status) { this.status = status; }

    public String getStartDate(String m) {
        return ("dd/MM /yyyy");
    }

    public String getEndDate(String m) {
        return  ("dd/MM /yyyy");
    }
}
