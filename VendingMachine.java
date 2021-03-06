import java.util.*;
public class VendingMachine {

  HashMap<String, Integer> inventory = new HashMap<String, Integer>();
  HashMap<String, Double> drinkList = new HashMap<String, Double>();
  HashMap<Double, Integer> changeInventory = new HashMap<Double, Integer>();
  HashMap<Integer, String> drinkMenu = new HashMap<Integer, String>();

  double amountReceived = 0.00;
  double amountOwed = 0.00; 
  public int selection;

  public Scanner userInput = new Scanner(System.in);

  protected void restock(Integer amount, String type){
    switch(type){
      case "Coca-cola":
        inventory.put(type, amount);
      case "Sprite":
        inventory.put(type, amount);
      case "Pepsi":
        inventory.put(type, amount);
      case "Fanta":
        inventory.put(type, amount);
      case "Sparkling Water":
        inventory.put(type, amount);
      default:
        System.out.println("We do not carry " + type);
    }
  }

  protected void select(Integer selection){
    if(amountReceived >= drinkList.get(drinkMenu.get(selection))){
      inventory.put(drinkMenu.get(selection), inventory.get(drinkMenu.get(selection)) - 1);
      amountOwed += (amountReceived - drinkList.get(drinkMenu.get(selection)));
    } else {
      displayAmount(drinkList.get(drinkMenu.get(selection)));
    };
  }

  protected void stockMoneyAndBeverages(){
    inventory.put("Coca-cola", 20);
    inventory.put("Sprite", 20);
    inventory.put("Pepsi", 20);
    inventory.put("Fanta", 20);
    inventory.put("Sparkling Water", 20);
    drinkList.put("Coca-cola", 1.50);
    drinkList.put("Sprite", 1.00);
    drinkList.put("Pepsi", 1.50);
    drinkList.put("Fanta", 1.50);
    drinkList.put("Sparkling Water", 2.00);
    changeInventory.put(1.0, 100);
    changeInventory.put(.25, 101);
    changeInventory.put(.10, 102);
    changeInventory.put(.05, 103);
    changeInventory.put(.01, 104);
    drinkMenu.put(1, "Coca-cola");
    drinkMenu.put(2, "Sprite");
    drinkMenu.put(3, "Pepsi");
    drinkMenu.put(4, "Fanta");
    drinkMenu.put(5, "Sparkling Water");
  }

  protected void receiveMoney(Double amount){
    amountReceived += amount;
    changeInventory.put(amount, changeInventory.get(amount) + 1);
    if(amountReceived >= drinkList.get(drinkMenu.get(selection))){
      System.out.println(selection);
    };
    System.out.println(amountReceived);
  }

  protected void displayAmount(Double amount){
    while(amountReceived < amount){
      System.out.println("This drink costs $ " + (amount - amountReceived));
      System.out.println("                 ");
      System.out.println("What would you like to do?");
      System.out.println("1       Insert Money");
      System.out.println("2       Exit");
      selection = userInput.nextInt();
      if (selection == 1){
        this.insertMoney();
        break;
      } else {
        this.thankYouMessage();
        break;
      }
    }
  }

  protected void insertMoney(){
    System.out.println("How much would you like to insert?");
    System.out.println("1          $1.00");
    System.out.println("2          $.25");
    System.out.println("3          $.10");
    System.out.println("4          $.05");
    System.out.println("5          $.01");
    selection = userInput.nextInt();
    if(selection == 1){
      this.receiveMoney(1.00);
    } else if (selection == 2){
      this.receiveMoney(.25);
    } else if (selection == 3) {
      this.receiveMoney(.10);
    } else if (selection == 4){
      this.receiveMoney(.05);
    } else if (selection == 5){
      this.receiveMoney(.01);
    }
  }

  protected void thankYouMessage(){
    System.out.println("Thanks for checking this vending machine out!");
  }

  protected void dispenseMoney(){
    System.out.println("You received " + amountOwed);
    changeInventory.forEach((k,v) -> {
      while(amountOwed > 0){
        amountOwed -= k;
        changeInventory.put(k, changeInventory.get(k) -1);
      };
    });
    this.thankYouMessage();
  }

  protected void dispenseDrink(){
    inventory.put(drinkMenu.get(selection), inventory.get(drinkMenu.get(selection)) -1);
  }

  public void mainMenu(){
    System.out.println("Select an option");
    System.out.println("_________________");
    System.out.println("1       Coca-cola");
    System.out.println("2       Sprite");
    System.out.println("3       Pepsi");
    System.out.println("4       Fanta");
    System.out.println("5       Sparkling Water");
    System.out.println("                 ");
    selection = userInput.nextInt();
    this.select(selection);
  }

  public static void main(String[] args){
    VendingMachine newVendingMachine = new VendingMachine();
    newVendingMachine.stockMoneyAndBeverages();
    Scanner userInput = new Scanner(System.in);
    boolean exit = false;
    newVendingMachine.mainMenu();
  }
}