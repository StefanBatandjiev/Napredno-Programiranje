package src.ZadachiZaVtorKolokvium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

class Game {

    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;

    public Game(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getResult() {
        return Integer.compare(homeGoals, awayGoals);
    }
}

class Team {

    private String name;
    private int wins;
    private int draws;
    private int loses;
    private int goalsScored;
    private int goalsConceded;
    static int i = 0;

    public Team(String name) {
        this.name = name;
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    public int numMatches() {
        return wins + loses + draws;
    }

    public int getPoints() {
        return wins * 3 + draws;
    }

    public void incrementWins() {
        ++this.wins;
    }

    public void incrementDraws() {
        ++this.draws;
    }

    public void incrementLoses() {
        ++this.loses;
    }

    public void increaseGoals(int scored, int conceded) {
        this.goalsScored += scored;
        this.goalsConceded += conceded;
    }

    public int getGoalDiff() {
        return this.goalsScored - this.goalsConceded;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5s%5s%5s%5s%5s",
                this.name,
                this.numMatches(),
                this.wins,
                this.draws,
                this.loses,
                this.getPoints());
    }
}

class FootballTable {

    private Map<String, Team> tableTeams;
    private static final Comparator<Team> comparator = Comparator.comparing(Team::getPoints)
            .thenComparing(Team::getGoalDiff).reversed()
            .thenComparing(Team::getName);

    public FootballTable() {
        this.tableTeams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Game game = new Game(homeTeam, awayTeam, homeGoals, awayGoals);
        Team team1 = new Team(homeTeam);
        Team team2 = new Team(awayTeam);

        this.tableTeams.putIfAbsent(homeTeam, team1);
        this.tableTeams.putIfAbsent(awayTeam, team2);

        if (game.getResult() == 1) {
            this.tableTeams.get(homeTeam).incrementWins();
            this.tableTeams.get(homeTeam).increaseGoals(homeGoals, awayGoals);

            this.tableTeams.get(awayTeam).incrementLoses();
            this.tableTeams.get(awayTeam).increaseGoals(awayGoals, homeGoals);
        } else if (game.getResult() == -1) {
            this.tableTeams.get(awayTeam).incrementWins();
            this.tableTeams.get(awayTeam).increaseGoals(awayGoals, homeGoals);

            this.tableTeams.get(homeTeam).incrementLoses();
            this.tableTeams.get(homeTeam).increaseGoals(homeGoals, awayGoals);
        } else {
            this.tableTeams.get(homeTeam).incrementDraws();
            this.tableTeams.get(homeTeam).increaseGoals(homeGoals, homeGoals);

            this.tableTeams.get(awayTeam).incrementDraws();
            this.tableTeams.get(awayTeam).increaseGoals(homeGoals, homeGoals);
        }
    }

    public void printTable() {
        List<String> teams = this.tableTeams.values()
                .stream()
                .sorted(comparator)
                .map(Team::toString)
                .collect(Collectors.toList());
        for (int i = 0; i < teams.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, teams.get(i));
        }
    }
}