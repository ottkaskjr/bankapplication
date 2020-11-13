package ee.bcs.valiit;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryRowMapper implements RowMapper<Record> {
    @Override
    public Record mapRow(ResultSet resultSet, int i) throws SQLException {
        Record history = new Record();
        history.setDate(resultSet.getString("date"));
        history.setTransaction(resultSet.getString("transaction"));
        return history;
    }
}
