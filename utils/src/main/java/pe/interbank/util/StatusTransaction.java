package pe.interbank.util;

public enum StatusTransaction {
  PENDIENTE("PENDIENTE"),
  APROBADO("APROBADO"),
  RECHAZADO("RECHAZADO");

  private String description;

  StatusTransaction(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
