package com.example.inmo.models;

public class Pago {
    private int idPago;
    private String fechaPago;
    private double monto;
    private String detalle;
    private boolean estado;
    private int idContrato;
    public Pago() {
    }
    public Pago(int idPago, String fechaPago, double monto, String detalle, boolean estado, int idContrato) {
        this.idPago = idPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.detalle = detalle;
        this.estado = estado;
        this.idContrato = idContrato;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
}