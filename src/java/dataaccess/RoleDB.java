/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author awarsyle
 */
public class RoleDB {
    public Role getRole(int roleID) throws SQLException 
    {

        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            Role role = null;
            String preparedQuery = "SELECT RoleID, RoleName FROM role_table WHERE RoleID=?";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString(2);
                role = new Role(roleID, roleName);
            }

            return role;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
    public Role getRoleName(String roleName) throws SQLException 
    {

        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            Role role = null;
            String preparedQuery = "SELECT RoleID, RoleName FROM role_table WHERE roleName=?";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                int roleID = rs.getInt(1);
                role = new Role(roleID, roleName);
            }

            return role;
        } finally {
            connectionPool .freeConnection(connection);
        }
    }
    
    public List<Role> getAll() throws SQLException
    {
         ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            Role role;
            ArrayList<Role> roles = new ArrayList<>();
            //ArrayList<User> users = new ArrayList<>();

            String preparedQuery = "SELECT roleID, roleName FROM role_table";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int roleID = rs.getInt(1);
                String roleName = rs.getString(2);
                
               // RoleDB roleDB = new RoleDB();
                role = new Role(roleID, roleName);
                roles.add(role);
            }
            
            return roles;
        } finally 
        {
            connectionPool.freeConnection(connection);
        }
        
    }

    public int insert(Role role) throws SQLException
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;

        int rows = 0;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            String preparedQuery
                    = "INSERT INTO role_Table "
                    + "(roleID, roleName) "
                    + "VALUES "
                    + "(?, ?)";

            PreparedStatement ps = connection.prepareStatement(preparedQuery);

            ps.setInt(1, role.getRoleID());
            ps.setString(2, role.getRoleName());

            rows = ps.executeUpdate();
            ps.close();
            return rows;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
    
     public boolean delete(Role role) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String DELETE_STMT = "DELETE FROM role_Table where roldID = ?";
            PreparedStatement prepare = connection.prepareStatement(DELETE_STMT);
            prepare.setInt(1, role.getRoleID());

            int rowCount = prepare.executeUpdate();
            prepare.close();
            return rowCount == 1;

        } finally {
            connectionPool.freeConnection(connection);
        }
     }
     
     public int update(Role role) throws SQLException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String preparedQuery = "UPDATE role_Table set roleID = ?, set roleName = ? ";
            int successCount = 0;

            PreparedStatement statement = connection.prepareStatement(preparedQuery);
            statement.setString(1, String.valueOf(role.getRoleID()));
            statement.setString(2, role.getRoleName());


            successCount = statement.executeUpdate();
            statement.close();
            return successCount;
        } finally {
            connectionPool.freeConnection(connection);
        }

    }
}
