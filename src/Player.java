

public class Player {
    public static Statistic statistics;
    public static boolean isGameFailed = false;
    public Player(){
        statistics = new Statistic();
    }

    public int getLives() {
        return statistics.lives;
    }

    public void fail() {
        statistics.lives--;
        TableRecords.update();
        if (statistics.lives <= 0) {
            MessageBox.showMessageBox("Вы проиграли!");
            Game.timer.cancel();
        }
        else {
            Balls balls = new Balls();
            Platforms platforms = new Platforms();
            Game.gameField.allObjects.displayObjects.set(0, balls.balls.get(0));
            Game.gameField.allObjects.displayObjects.set(1, platforms.platforms.get(0));
            Bonuses.resetVisibility();
            Game.gameField.platforms = platforms;
            Game.gameField.balls = balls;
            isGameFailed = true;
        }
    }
    public static String getPlayerStatistic(){
        return "         Уровень:  " + statistics.level + "        Имя игрока:  " + statistics.name + "         Счёт:  " + statistics.score + "         Жизни:  " + statistics.lives + "         Сложность:  " + statistics.complexity + "        ";
    }
}
