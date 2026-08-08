package model;

public class Reservation {

    private String pnr;
    private String passengerName;
    private int trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String source;
    private String destination;

    public Reservation(
            String pnr,
            String passengerName,
            int trainNumber,
            String trainName,
            String classType,
            String journeyDate,
            String source,
            String destination) {

        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
    }

    public String getPnr() {
        return pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getClassType() {
        return classType;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}