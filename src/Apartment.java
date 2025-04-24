class Apartment {
    private String id;
    private String type; // Eco, Semi-Luxury, Luxury
    private int floorLevel;
    private int serialNumber;
    private boolean isVacant;

    public Apartment(String id, String type, int floorLevel, int serialNumber) {
        this.id = id;
        this.type = type;
        this.floorLevel = floorLevel;
        this.serialNumber = serialNumber;
        this.isVacant = true;
    }

    // Getters
    public String getId() { return id; }
    public String getType() { return type; }
    public int getFloorLevel() { return floorLevel; }
    public int getSerialNumber() { return serialNumber; }
    public boolean isVacant() { return isVacant; }

    // Setters
    public void setVacant(boolean vacant) { isVacant = vacant; }
}