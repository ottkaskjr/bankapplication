package ee.bcs.valiit.forsiiri;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class RandomGameController {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // GET ALL GAMES (FULL OBJECTS)
    @GetMapping("games")
    public List<RandomGame> getGames(){
        String sql = "SELECT * FROM randomgame";
        Map<String, Object> paramMap = new HashMap<>();
        List<RandomGame> games = jdbcTemplate.query(sql, paramMap, new RandomGameRowMapper());
        return games;
    }

    // GET ALL UNFINISHED GAMES WITHOUT ANSWERS
    @GetMapping("unfinishedgames")
    public String[] getGamesWithoutAnswers(){
        String sql = "SELECT * FROM randomgame";
        Map<String, Object> paramMap = new HashMap<>();
        List<RandomGame> games = jdbcTemplate.query(sql, paramMap, new RandomGameRowMapper());
        String[] unfinishedGames = new String[games.size()];
        for(int i = 0; i < games.size(); i++){
            unfinishedGames[i] = "id: " + games.get(i).getId() + ", attempts: " + games.get(i).getAttempts();
        }
        return unfinishedGames;
    }

    // POST NEW GAME
    @PostMapping("postnewgame")
    public String createNewGame(){
        String sql = "INSERT INTO randomgame (answer, attempts) VALUES (:answer, :attempts)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("answer", randomNumber());
        paramMap.put("attempts", 0);
        jdbcTemplate.update(sql, paramMap);
        return "NEW GAME CREATED";
    }

    // GUESS THE NUMBER OF 1 GAME BY ID
    // url: localhost:8080/guessthenumber/1?guess=50
    @PostMapping("guessthenumber/{gameID}")
    public String makeAguess(@PathVariable("gameID") String gameID,
                             @RequestParam("guess") int guess){
        // get game by id
        String sql = "SELECT * FROM randomgame WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", new BigDecimal(gameID));
        RandomGame game = jdbcTemplate.queryForObject(sql, paramMap, new RandomGameRowMapper());


        // check guess and answer
        if(guess == game.getAnswer()){
            // delete the game from database
            String deleteSQL = "DELETE FROM randomgame WHERE id = :id";
            Map<String, Object> paramMapDelete = new HashMap<>();
            paramMapDelete.put("id", game.getId());
            jdbcTemplate.update(deleteSQL, paramMapDelete);

            int totalAttempts = game.getAttempts()+1;
            return "Congrats! You guessed " +  game.getAnswer() + ". Attempts: " + totalAttempts;
        }
        if(guess > 100 || guess < 1) {
            return "Please pick a number between 1 and 100";
        }

        String response = "";
        if(guess > game.getAnswer()){
            response = "Too big. Pick a new number";
        } else {
            response = "Too small. Pick a new number";
        }

        // update if wrong guess
        String updateSQL = "UPDATE randomgame SET attempts = :attempts WHERE id = :id";
        Map<String, Object> paramMapUpdate = new HashMap<>();
        paramMapUpdate.put("id", game.getId());
        paramMapUpdate.put("attempts", game.getAttempts() + 1);
        jdbcTemplate.update(updateSQL, paramMapUpdate);

        return response;
    }


    public int randomNumber(){
        double random = Math.random() * 100.0 + 1.0;
        int randomInt = (int) random;
        return randomInt;
    }
}
