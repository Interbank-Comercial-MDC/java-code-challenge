package pe.interbank.util;

public enum TypeTransaction {
  CREDIT(1, "CREDITO"),
  DEBIT(2, "DEBITO");

  private int type;
  private String description;

  TypeTransaction(int type, String description) {
    this.type = type;
    this.description = description;
  }

  public int getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }
}
