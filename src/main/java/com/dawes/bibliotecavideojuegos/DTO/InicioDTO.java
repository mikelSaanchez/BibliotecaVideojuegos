package com.dawes.bibliotecavideojuegos.DTO;

public class InicioDTO {
    private int videojuegos;
    private int sociosActivos;
    private int prestamosAbiertos;
    private int prestamosVencidos;

    public InicioDTO(int videojuegos, int sociosActivos, int prestamosAbiertos, int prestamosVencidos) {
        this.videojuegos = videojuegos;
        this.sociosActivos = sociosActivos;
        this.prestamosAbiertos = prestamosAbiertos;
        this.prestamosVencidos = prestamosVencidos;
    }

    public int getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(int videojuegos) {
        this.videojuegos = videojuegos;
    }

    public int getSociosActivos() {
        return sociosActivos;
    }

    public void setSociosActivos(int sociosActivos) {
        this.sociosActivos = sociosActivos;
    }

    public int getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(int prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }

    public int getPrestamosVencidos() {
        return prestamosVencidos;
    }

    public void setPrestamosVencidos(int prestamosVencidos) {
        this.prestamosVencidos = prestamosVencidos;
    }
}
