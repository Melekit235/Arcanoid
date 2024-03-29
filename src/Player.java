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
        if(GameField.timer != null)
            GameField.timer.stop();

        if (statistics.lives <= 0) {

            //AnotherMessageBox anotherMessageBox = new AnotherMessageBox();

            //DisplayAll.displayObjects.add(anotherMessageBox);

            MessageBox.showMessageBox("Вы проиграли!");
            Game.timer.cancel();
        }
        else {
            Balls balls = new Balls();
            Platforms platforms = new Platforms();
            DisplayAll.displayObjects.set(0, balls.balls.get(0));
            DisplayAll.displayObjects.set(1, platforms.platforms.get(0));
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
