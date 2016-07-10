package perldoop.semantica.aritmetica;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.semantica.util.Tipos;

/**
 * Clase para la semantica de aritmetica
 *
 * @author César Pomar
 */
public class SemAritmetica {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAritmetica(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(AritSuma s) {
        Tipo ti = Tipos.numero(s.getIzquierda().getTipo());
        Tipo td = Tipos.numero(s.getDerecha().getTipo());
        if (ti.isDouble() || td.isDouble()) {
            s.setTipo(new Tipo(Tipo.DOUBLE));
        } else if (ti.isFloat() || td.isFloat()) {
            s.setTipo(new Tipo(Tipo.FLOAT));
        } else if (ti.isLong() || td.isLong()) {
            s.setTipo(new Tipo(Tipo.LONG));
        } else if (ti.isInteger() || td.isInteger()) {
            s.setTipo(new Tipo(Tipo.INTEGER));
        }
    }

    public void visitar(AritResta s) {
        Tipo ti = Tipos.numero(s.getIzquierda().getTipo());
        Tipo td = Tipos.numero(s.getDerecha().getTipo());
        if (ti.isDouble() || td.isDouble()) {
            s.setTipo(new Tipo(Tipo.DOUBLE));
        } else if (ti.isFloat() || td.isFloat()) {
            s.setTipo(new Tipo(Tipo.FLOAT));
        } else if (ti.isLong() || td.isLong()) {
            s.setTipo(new Tipo(Tipo.LONG));
        } else if (ti.isInteger() || td.isInteger()) {
            s.setTipo(new Tipo(Tipo.INTEGER));
        }
    }

    public void visitar(AritMulti s) {
        Tipo ti = Tipos.numero(s.getIzquierda().getTipo());
        Tipo td = Tipos.numero(s.getDerecha().getTipo());
        if (ti.isDouble() || td.isDouble()) {
            s.setTipo(new Tipo(Tipo.DOUBLE));
        } else if (ti.isFloat() || td.isFloat()) {
            s.setTipo(new Tipo(Tipo.FLOAT));
        } else if (ti.isLong() || td.isLong()) {
            s.setTipo(new Tipo(Tipo.LONG));
        } else if (ti.isInteger() || td.isInteger()) {
            s.setTipo(new Tipo(Tipo.INTEGER));
        }
    }

    public void visitar(AritDiv s) {
        s.setTipo(new Tipo(Tipo.DOUBLE));
    }

    public void visitar(AritPow s) {
        s.setTipo(new Tipo(Tipo.DOUBLE));
    }

    public void visitar(AritX s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(AritConcat s) {
        s.setTipo(new Tipo(Tipo.STRING));
    }

    public void visitar(AritMod s) {
        Tipo t = Tipos.numero(s.getIzquierda().getTipo());
        if (t.isInteger() || t.isLong()) {
            s.setTipo(new Tipo(t));
        } else {
            s.setTipo(new Tipo(Tipo.LONG));
        }
    }

    public void visitar(AritPositivo s) {
        s.setTipo(new Tipo(s.getExpresion().getTipo()));
    }

    public void visitar(AritNegativo s) {
        s.setTipo(new Tipo(s.getExpresion().getTipo()));
    }

    public void visitar(AritPreIncremento s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isVariable()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        if (!t.isSimple()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_DEFERENCIACION, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        s.setTipo(t);
    }

    public void visitar(AritPreDecremento s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isVariable()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        if (!t.isSimple()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_DEFERENCIACION, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        s.setTipo(t);
    }

    public void visitar(AritPostIncremento s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isVariable()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        if (!t.isSimple()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_DEFERENCIACION, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        s.setTipo(t);
    }

    public void visitar(AritPostDecremento s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isVariable()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        if (!t.isSimple()) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_DEFERENCIACION, Buscar.tokenFin(s), s.getOperador().toString());
            throw new ExcepcionSemantica();
        }
        s.setTipo(t);
    }
}
