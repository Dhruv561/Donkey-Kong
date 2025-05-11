public abstract class GameScreen {
    protected Mario mario;
    protected Hammer hammer;
    protected DonkeyKong donkeyKong;
    protected Platform[] platforms;
    protected Barrel[] barrels;
    protected Ladder[] ladders;
    protected NormalMonkey[] normalMonkeys;
    protected IntelligentMonkey[] intelligentMonkeys;
    protected Blaster[] blasters;

    public abstract void loadLevel();

    public void createPlatforms() {

    }

    public void createBarrels() {

    }

    public void createLadders() {

    }

    public void createBlasters() {

    }

    public void createMonkeys() {

    }


    boolean gameOver = false;
    boolean gameWon = false;


}
