package ee.bcs.valiit;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<BankAccount> {
    @Override
    public BankAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getBigDecimal("id"));
        bankAccount.setAccountNr(resultSet.getString("accountnr"));
        bankAccount.setBankClientId(resultSet.getBigDecimal("bankclient_id"));
        bankAccount.setRegistered(resultSet.getString("registered"));
        bankAccount.setBalance(resultSet.getString("balance"));
        return bankAccount;
    }
}
