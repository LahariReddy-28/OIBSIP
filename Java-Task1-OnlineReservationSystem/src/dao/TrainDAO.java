package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainDAO {

    public String getTrainName(int trainNumber) {

        String sql =
                "SELECT train_name FROM trains " +
                "WHERE train_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("train_name");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}