package ee.bcs.valiit;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        Client bankclient = new Client();
        bankclient.setId(resultSet.getBigDecimal("id"));
        bankclient.setFirstName(resultSet.getString("firstname"));
        bankclient.setLastName(resultSet.getString("lastname"));
        bankclient.setRegistered(resultSet.getString("registered"));
        bankclient.setTotalBalance(resultSet.getString("totalbalance"));
        bankclient.setNumberofaccounts(resultSet.getInt("numberofaccounts"));
        return bankclient;
    }
}
