/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perldoop.semantica.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica para generar bloques especiales
 *
 * @author César Pomar
 */
public abstract class SemEspecial{

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspecial(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita un bloque
     *
     * @param s Bloque
     */
    public abstract void visitar(Bloque s);
}
