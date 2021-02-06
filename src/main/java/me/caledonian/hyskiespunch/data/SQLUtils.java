package me.caledonian.hyskiespunch.data;

import me.caledonian.hyskiespunch.HyskiesPunch;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLUtils {

    private HyskiesPunch plugin;
    public SQLUtils(HyskiesPunch plugin){this.plugin = plugin;}

    // Create SQL Table
    public void createTable(){
        PreparedStatement ps;
        try{
            ps = HyskiesPunch.SQL.getConnnection().prepareStatement("CREATE TABLE IF NOT EXISTS info "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),BEPUNCHED INT(2),CANPUNCH INT(2),PRIMARY KEY(NAME))");
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Setup player
    public void createPlayer(Player p){
        try{
            String name = p.getName();
            UUID uuid = p.getUniqueId();

            if(!exists(uuid)){
                PreparedStatement ps2 = HyskiesPunch.SQL.getConnnection().prepareStatement("INSERT IGNORE INTO info (NAME,UUID) VALUES (?,?)");

                ps2.setString(1, name);
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();

                setBePunched(p, 1);
                setCanPunch(p, 0);
                return;
            }
        }catch (SQLException e){e.printStackTrace();}
    }

    // Player exists boolean
    public boolean exists(UUID uuid){
        try{
            PreparedStatement ps = HyskiesPunch.SQL.getConnnection().prepareStatement("SELECT * FROM info WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            if(results.next()){
                // player exists
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Can other people punch them?
    public void setBePunched(Player p, int points){
        try{
            PreparedStatement ps = HyskiesPunch.SQL.getConnnection().prepareStatement("UPDATE info SET BEPUNCHED=? WHERE UUID=?");
            ps.setInt(1, points);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
    }

    public int canBePunched(Player p){
        try{
            PreparedStatement ps = HyskiesPunch.SQL.getConnnection().prepareStatement("SELECT BEPUNCHED FROM info WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            int data = 0;
            if(rs.next()){
                data = rs.getInt("BEPUNCHED");
                return data;
            }
        }catch (SQLException e){e.printStackTrace();}
        return 0;
    }

    // Can they punch other people
    public void setCanPunch(Player p, int points){
        try{
            PreparedStatement ps = HyskiesPunch.SQL.getConnnection().prepareStatement("UPDATE info SET CANPUNCH=? WHERE UUID=?");
            ps.setInt(1, points);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch(SQLException e){e.printStackTrace();}
    }

    public int canPunch(Player p){
        try{
            PreparedStatement ps = HyskiesPunch.SQL.getConnnection().prepareStatement("SELECT CANPUNCH FROM info WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            int data = 0;
            if(rs.next()){
                data = rs.getInt("CANPUNCH");
                return data;
            }
        }catch (SQLException e){e.printStackTrace();}
        return 0;
    }

}
