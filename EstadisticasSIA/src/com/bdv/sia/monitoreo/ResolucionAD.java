package com.bdv.sia.monitoreo;

public class ResolucionAD
{
  private long cantidadAprobadas;
  private long cantidadDenegadas;

  public ResolucionAD()
  {
    this.cantidadAprobadas = 0L;
    this.cantidadDenegadas = 0L;
  }

  public long getCantidadAprobadas() {
    return this.cantidadAprobadas;
  }

  public void setCantidadAprobadas(long cantidadAprobadas) {
    this.cantidadAprobadas = cantidadAprobadas;
  }

  public long getCantidadDenegadas() {
    return this.cantidadDenegadas;
  }

  public void setCantidadDenegadas(long cantidadDenegadas) {
    this.cantidadDenegadas = cantidadDenegadas;
  }
}