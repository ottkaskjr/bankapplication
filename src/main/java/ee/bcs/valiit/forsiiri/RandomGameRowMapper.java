package ee.bcs.valiit.forsiiri;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RandomGameRowMapper implements RowMapper<RandomGame>{
    @Override
    public RandomGame mapRow(ResultSet resultSet, int i) throws SQLException{
        RandomGame game = new RandomGame();
        game.setId(resultSet.getBigDecimal("id"));
        game.setAnswer(resultSet.getInt("answer"));
        game.setAttempts(resultSet.getInt("attempts"));
        return game;
    }
}
