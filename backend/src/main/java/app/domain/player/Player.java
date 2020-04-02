package app.domain.player;

public class Player {

  private final int id;
  private final String name;
  private int balance;
  private boolean isActive;



  Player(String name) {
    id = 1;
    this.name = name;
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }



  //generateId


}
