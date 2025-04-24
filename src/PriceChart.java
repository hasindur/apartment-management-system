import java.util.HashMap;
import java.util.Map;

class PriceChart {
    private static Map<String, Double> deposit = new HashMap<>();
    private static Map<String, Double> rental = new HashMap<>();
    private static Map<String, Double> longTermDiscount = new HashMap<>();
    private static Map<String, Double> parkingCharges = new HashMap<>();
    private static Map<String, Double> earlyTerminationFine = new HashMap<>();

    private static double waterLongMidTermLow;
    private static double waterLongMidTermHigh;
    private static double waterShortTerm;
    private static double electricityLongMidTermLow;
    private static double electricityLongMidTermHigh;
    private static double electricityShortTerm;
    private static double acSemiLuxury;
    private static double acLuxury;
    private static double cableTv;

    public static void initializePrices() {
        // Deposits
        deposit.put("Eco", 5000.0);
        deposit.put("Semi-Luxury", 10000.0);
        deposit.put("Luxury", 45000.0);

        // Rentals per day
        rental.put("Eco", 5000.0);
        rental.put("Semi-Luxury", 6000.0);
        rental.put("Luxury", 8000.0);

        // Long-term discounts
        longTermDiscount.put("Eco", 182500.0);
        longTermDiscount.put("Semi-Luxury", 219000.0);
        longTermDiscount.put("Luxury", 292000.0);

        // Parking charges per day
        parkingCharges.put("Eco", 50.0);
        parkingCharges.put("Semi-Luxury", 10.0);
        parkingCharges.put("Luxury", 0.0);

        // Early termination fines per remaining day
        earlyTerminationFine.put("Eco", 100.0);
        earlyTerminationFine.put("Semi-Luxury", 200.0);
        earlyTerminationFine.put("Luxury", 300.0);

        // Water rates
        waterLongMidTermLow = 70.0;
        waterLongMidTermHigh = 120.0;
        waterShortTerm = 130.0;

        // Electricity rates
        electricityLongMidTermLow = 170.0;
        electricityLongMidTermHigh = 240.0;
        electricityShortTerm = 260.0;

        // AC rates
        acSemiLuxury = 0.0002 * 1600;
        acLuxury = 0.0002 * 2000;

        // Cable TV
        cableTv = 100.0;
    }

    // Getters for all price values
    public static double getDeposit(String type) { return deposit.get(type); }
    public static double getRental(String type) { return rental.get(type); }
    public static double getLongTermDiscount(String type) { return longTermDiscount.get(type); }
    public static double getParkingCharges(String type) { return parkingCharges.get(type); }
    public static double getEarlyTerminationFine(String type) { return earlyTerminationFine.get(type); }

    public static double getWaterLongMidTermLow() { return waterLongMidTermLow; }
    public static double getWaterLongMidTermHigh() { return waterLongMidTermHigh; }
    public static double getWaterShortTerm() { return waterShortTerm; }

    public static double getElectricityLongMidTermLow() { return electricityLongMidTermLow; }
    public static double getElectricityLongMidTermHigh() { return electricityLongMidTermHigh; }
    public static double getElectricityShortTerm() { return electricityShortTerm; }

    public static double getAcSemiLuxury() { return acSemiLuxury; }
    public static double getAcLuxury() { return acLuxury; }
    public static double getCableTv() { return cableTv; }
}