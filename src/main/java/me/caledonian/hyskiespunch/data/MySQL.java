package me.caledonian.hyskiespunch.data;

import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    // SQL Login Details
    private final String host = Files.config.getString("mysql.host");
    private final String db = Files.config.getString("mysql.database");
    private final String user = Files.config.getString("mysql.username");
    private final String paswd = Files.config.getString("mysql.password");
    private final Integer port = Files.config.getInt("mysql.port");

    private Connection con;

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()){ con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db + "?useSSL=false", this.user, this.paswd); }
    }


    // Getters
    public boolean isConnected(){return (con == null ? false : true);}
    public Connection getConnnection(){return con;}

    public void disconnect(){
        if(isConnected()){
            try{
                con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }//else{Logger.log(Logger.LogLevel.ERROR, "Plugin is not connected to a database");}
    }
}
