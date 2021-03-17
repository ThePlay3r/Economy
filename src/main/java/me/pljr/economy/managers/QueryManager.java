package me.pljr.economy.managers;

import me.pljr.economy.Economy;
import me.pljr.economy.objects.EconomyPlayer;
import me.pljr.pljrapispigot.database.DataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QueryManager {
    private final DataSource dataSource;

    public QueryManager(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public EconomyPlayer loadPlayer(UUID uuid){
        EconomyPlayer economyPlayer = new EconomyPlayer(uuid);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM economy_players WHERE uuid=?"
            );
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()){
                economyPlayer = new EconomyPlayer(uuid, results.getDouble("money"));
            }
            dataSource.close(connection, preparedStatement, results);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return economyPlayer;
    }

    public void savePlayer(EconomyPlayer economyPlayer){
        try {
            double money = economyPlayer.getMoney();

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "REPLACE INTO economy_players VALUES (?,?)"
            );
            preparedStatement.setString(1, economyPlayer.getUniqueId().toString());
            preparedStatement.setDouble(2, money);
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setupTables(){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS economy_players (" +
                            "uuid char(36) NOT NULL PRIMARY KEY," +
                            "money double NOT NULL);"
            );
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
