package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Reservation;

public class ReservationDAO {

    public boolean insertReservation(Reservation r) {

        String sql =
                "INSERT INTO reservations " +
                "(pnr, passenger_name, train_number, train_name, " +
                "class_type, journey_date, source, destination) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getPnr());
            ps.setString(2, r.getPassengerName());
            ps.setInt(3, r.getTrainNumber());
            ps.setString(4, r.getTrainName());
            ps.setString(5, r.getClassType());
            ps.setString(6, r.getJourneyDate());
            ps.setString(7, r.getSource());
            ps.setString(8, r.getDestination());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public Reservation getReservation(String pnr) {

        String sql =
                "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Reservation(
                        rs.getString("pnr"),
                        rs.getString("passenger_name"),
                        rs.getInt("train_number"),
                        rs.getString("train_name"),
                        rs.getString("class_type"),
                        rs.getString("journey_date"),
                        rs.getString("source"),
                        rs.getString("destination")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public boolean cancelReservation(String pnr) {

        String sql =
                "DELETE FROM reservations WHERE pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}