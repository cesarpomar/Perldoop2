package perldoop.generacion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.generacion.abrirbloque.*;
import perldoop.generacion.acceso.*;
import perldoop.generacion.aritmetica.*;
import perldoop.generacion.asignacion.*;
import perldoop.generacion.binario.*;
import perldoop.generacion.bloque.*;
import perldoop.generacion.bloqueelsif.*;
import perldoop.generacion.coleccion.*;
import perldoop.generacion.comparacion.*;
import perldoop.generacion.condicional.*;
import perldoop.generacion.constante.*;
import perldoop.generacion.cuerpo.*;
import perldoop.generacion.elsif.*;
import perldoop.generacion.expresion.*;
import perldoop.generacion.flujo.*;
import perldoop.generacion.funcion.*;
import perldoop.generacion.funciondef.*;
import perldoop.generacion.funcionsub.*;
import perldoop.generacion.lista.*;
import perldoop.generacion.logico.*;
import perldoop.generacion.modificador.*;
import perldoop.generacion.paquetes.*;
import perldoop.generacion.raiz.GenRaiz;
import perldoop.generacion.regulares.*;
import perldoop.generacion.sentencia.*;
import perldoop.generacion.variable.*;

/**
 * Fachada de los generadores
 *
 * @author César Pomar
 */
public final class FachadaGeneradores {

    private TablaGenerador tabla;

    /*Generadores*/
    private GenAbrirBloque genAbrirBloque;
    private GenAcceso genAcceso;
    private GenAritmetica genAritmetica;
    private GenAsignacion genAsignacion;
    private GenBinario genBinario;
    private GenBloque genBloque;
    private GenBloqueElsIf genBloqueElsIf;
    private GenColeccion genColeccion;
    private GenComparacion genComparacion;
    private GenCondicional genCondicional;
    private GenConstante genConstante;
    private GenCuerpo genCuerpo;
    private GenElsIf genElsIf;
    private GenExpresion genExpresion;
    private GenFlujo genFlujo;
    private GenFuncion genFuncion;
    private GenFuncionDef genFuncionDef;
    private GenFuncionSub genFuncionSub;
    private GenLista genLista;
    private GenLogico genLogico;
    private GenPaquetes genPaquetes;
    private GenRaiz genRaiz;
    private GenModificador genModificador;
    private GenRegulares genRegulares;
    private GenSentencia genSentencia;
    private GenVariable genVariable;

    /**
     * Constructor de la fachada
     *
     * @param tabla tablaGenerador
     */
    public FachadaGeneradores(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene el generador de abrirBloque
     *
     * @return Generador de abrirBloque
     */
    public GenAbrirBloque getGenAbrirBloque() {
        if (genAbrirBloque == null) {
            genAbrirBloque = new GenAbrirBloque(tabla);
        }
        return genAbrirBloque;
    }

    /**
     * Establece el generador de abrirBloque
     *
     * @param genAbrirBloque Generador de abrirBloque
     */
    public void setGenAbrirBloque(GenAbrirBloque genAbrirBloque) {
        this.genAbrirBloque = genAbrirBloque;
    }

    /**
     * Obtiene el generador de acceso
     *
     * @return Generador de acceso
     */
    public GenAcceso getGenAcceso() {
        if (genAcceso == null) {
            genAcceso = new GenAcceso(tabla);
        }
        return genAcceso;
    }

    /**
     * Establece el generador de acceso
     *
     * @param genAcceso Generador de acceso
     */
    public void setGenAcceso(GenAcceso genAcceso) {
        this.genAcceso = genAcceso;
    }

    /**
     * Obtiene el generador de aritmetica
     *
     * @return Generador de aritmetica
     */
    public GenAritmetica getGenAritmetica() {
        if (genAritmetica == null) {
            genAritmetica = new GenAritmetica(tabla);
        }
        return genAritmetica;
    }

    /**
     * Establece el generador de aritmetica
     *
     * @param genAritmetica Generador de aritmetica
     */
    public void setGenAritmetica(GenAritmetica genAritmetica) {
        this.genAritmetica = genAritmetica;
    }

    /**
     * Obtiene el generador de asignacion
     *
     * @return Generador de asignacion
     */
    public GenAsignacion getGenAsignacion() {
        if (genAsignacion == null) {
            genAsignacion = new GenAsignacion(tabla);
        }
        return genAsignacion;
    }

    /**
     * Establece el generador de asignacion
     *
     * @param genAsignacion Generador de asignacion
     */
    public void setGenAsignacion(GenAsignacion genAsignacion) {
        this.genAsignacion = genAsignacion;
    }

    /**
     * Obtiene el generador de binario
     *
     * @return Generador de binario
     */
    public GenBinario getGenBinario() {
        if (genBinario == null) {
            genBinario = new GenBinario(tabla);
        }
        return genBinario;
    }

    /**
     * Establece el generador de binario
     *
     * @param genBinario Generador de binario
     */
    public void setGenBinario(GenBinario genBinario) {
        this.genBinario = genBinario;
    }

    /**
     * Obtiene el generador de bloque
     *
     * @return Generador de bloque
     */
    public GenBloque getGenBloque() {
        if (genBloque == null) {
            genBloque = new GenBloque(tabla);
        }
        return genBloque;
    }

    /**
     * Establece el generador de bloque
     *
     * @param genBloque Generador de bloque
     */
    public void setGenBloque(GenBloque genBloque) {
        this.genBloque = genBloque;
    }

    /**
     * Obtiene el generador de elsif
     *
     * @return Generador de elsif
     */
    public GenBloqueElsIf getGenBloqueElsIf() {
        if (genBloqueElsIf == null) {
            genBloqueElsIf = new GenBloqueElsIf(tabla);
        }
        return genBloqueElsIf;
    }

    /**
     * Establece el generador de elsif
     *
     * @param genBloqueElsIf Generador de elsif
     */
    public void setGenBloqueElsIf(GenBloqueElsIf genBloqueElsIf) {
        this.genBloqueElsIf = genBloqueElsIf;
    }

    /**
     * Obtiene el generador de coleccion
     *
     * @return Generador de coleccion
     */
    public GenColeccion getGenColeccion() {
        if (genColeccion == null) {
            genColeccion = new GenColeccion(tabla);
        }
        return genColeccion;
    }

    /**
     * Establece el generador de coleccion
     *
     * @param genColeccion Generador de coleccion
     */
    public void setGenColeccion(GenColeccion genColeccion) {
        this.genColeccion = genColeccion;
    }

    /**
     * Obtiene el generador de comparacion
     *
     * @return Generador de comparacion
     */
    public GenComparacion getGenComparacion() {
        if (genComparacion == null) {
            genComparacion = new GenComparacion(tabla);
        }
        return genComparacion;
    }

    /**
     * Establece el generador de comparacion
     *
     * @param genComparacion Generador de comparacion
     */
    public void setGenComparacion(GenComparacion genComparacion) {
        this.genComparacion = genComparacion;
    }

    /**
     * Obtiene el generador de condicional
     *
     * @return Generador de condicional
     */
    public GenCondicional getGenCondicional() {
        if (genCondicional == null) {
            genCondicional = new GenCondicional(tabla);
        }
        return genCondicional;
    }

    /**
     * Establece el generador de condicional
     *
     * @param genCondicional Generador de condicional
     */
    public void setGenCondicional(GenCondicional genCondicional) {
        this.genCondicional = genCondicional;
    }

    /**
     * Obtiene el generador de constante
     *
     * @return Generador de constante
     */
    public GenConstante getGenConstante() {
        if (genConstante == null) {
            genConstante = new GenConstante(tabla);
        }
        return genConstante;
    }

    /**
     * Establece el generador de constante
     *
     * @param genConstante Generador de constante
     */
    public void setGenConstante(GenConstante genConstante) {
        this.genConstante = genConstante;
    }

    /**
     * Obtiene el generador de cuerpo
     *
     * @return Generador de cuerpo
     */
    public GenCuerpo getGenCuerpo() {
        if (genCuerpo == null) {
            genCuerpo = new GenCuerpo(tabla);
        }
        return genCuerpo;
    }

    /**
     * Establece el generador de cuerpo
     *
     * @param genCuerpo Generador de cuerpo
     */
    public void setGenCuerpo(GenCuerpo genCuerpo) {
        this.genCuerpo = genCuerpo;
    }

    /**
     * Obtiene el generador de elsif
     *
     * @return Generador de elsif
     */
    public GenElsIf getGenElsIf() {
        if (genElsIf == null) {
            genElsIf = new GenElsIf(tabla);
        }
        return genElsIf;
    }

    /**
     * Establece el generador de elsif
     *
     * @param genElsIf Generador de elsif
     */
    public void setGenElsIf(GenElsIf genElsIf) {
        this.genElsIf = genElsIf;
    }

    /**
     * Obtiene el generador de expresion
     *
     * @return Generador de expresion
     */
    public GenExpresion getGenExpresion() {
        if (genExpresion == null) {
            genExpresion = new GenExpresion(tabla);
        }
        return genExpresion;
    }

    /**
     * Establece el generador de expresion
     *
     * @param genExpresion Generador de expresion
     */
    public void setGenExpresion(GenExpresion genExpresion) {
        this.genExpresion = genExpresion;
    }

    /**
     * Obtiene el generador de flujo
     *
     * @return Generador de flujo
     */
    public GenFlujo getGenFlujo() {
        if (genFlujo == null) {
            genFlujo = new GenFlujo(tabla);
        }
        return genFlujo;
    }

    /**
     * Establece el generador de flujo
     *
     * @param genFlujo Generador de flujo
     */
    public void setGenFlujo(GenFlujo genFlujo) {
        this.genFlujo = genFlujo;
    }

    /**
     * Obtiene el generador de funcion
     *
     * @return Generador de funcion
     */
    public GenFuncion getGenFuncion() {
        if (genFuncion == null) {
            genFuncion = new GenFuncion(tabla);
        }
        return genFuncion;
    }

    /**
     * Establece el generador de funcion
     *
     * @param genFuncion Generador de funcion
     */
    public void setGenFuncion(GenFuncion genFuncion) {
        this.genFuncion = genFuncion;
    }

    /**
     * Obtiene el generador de funciondef
     *
     * @return Generador de funciondef
     */
    public GenFuncionDef getGenFuncionDef() {
        if (genFuncionDef == null) {
            genFuncionDef = new GenFuncionDef(tabla);
        }
        return genFuncionDef;
    }

    /**
     * Establece el generador de funciondef
     *
     * @param genFuncionDef Generador de funciondef
     */
    public void setGenFuncionDef(GenFuncionDef genFuncionDef) {
        this.genFuncionDef = genFuncionDef;
    }

    /**
     * Obtiene el generador de funcionsub
     *
     * @return Generador de funcionsub
     */
    public GenFuncionSub getGenFuncionSub() {
        if (genFuncionSub == null) {
            genFuncionSub = new GenFuncionSub(tabla);
        }
        return genFuncionSub;
    }

    /**
     * Establece el generador de funcionsub
     *
     * @param genFuncionSub Generador de funcionsub
     */
    public void setGenFuncionSub(GenFuncionSub genFuncionSub) {
        this.genFuncionSub = genFuncionSub;
    }

    /**
     * Obtiene el generador de lista
     *
     * @return Generador de lista
     */
    public GenLista getGenLista() {
        if (genLista == null) {
            genLista = new GenLista(tabla);
        }
        return genLista;
    }

    /**
     * Establece el generador de lista
     *
     * @param genLista Generador de lista
     */
    public void setGenLista(GenLista genLista) {
        this.genLista = genLista;
    }

    /**
     * Obtiene el generador de logico
     *
     * @return Generador de logico
     */
    public GenLogico getGenLogico() {
        if (genLogico == null) {
            genLogico = new GenLogico(tabla);
        }
        return genLogico;
    }

    /**
     * Establece el generador de logico
     *
     * @param genLogico Generador de logico
     */
    public void setGenLogico(GenLogico genLogico) {
        this.genLogico = genLogico;
    }

    /**
     * Obtiene el generador de raiz
     *
     * @return Generador de raiz
     */
    public GenRaiz getGenRaiz() {
        if (genRaiz == null) {
            genRaiz = new GenRaiz(tabla);
        }
        return genRaiz;
    }

    /**
     * Establece el generador de raiz
     *
     * @param genRaiz Generador de raiz
     */
    public void setGenRaiz(GenRaiz genRaiz) {
        this.genRaiz = genRaiz;
    }

    /**
     * Obtiene el generador de paquetes
     *
     * @return Generador de paquetes
     */
    public GenPaquetes getGenPaquetes() {
        if (genPaquetes == null) {
            genPaquetes = new GenPaquetes(tabla);
        }
        return genPaquetes;
    }

    /**
     * Establece el generador de paquetes
     *
     * @param genPaquetes Generador de paquetes
     */
    public void setGenPaquetes(GenPaquetes genPaquetes) {
        this.genPaquetes = genPaquetes;
    }

    /**
     * Obtiene el generador de modificador
     *
     * @return Generador de modificador
     */
    public GenModificador getGenModificador() {
        if (genModificador == null) {
            genModificador = new GenModificador(tabla);
        }
        return genModificador;
    }

    /**
     * Establece el generador de modificador
     *
     * @param genModificador Generador de modificador
     */
    public void setGenModificador(GenModificador genModificador) {
        this.genModificador = genModificador;
    }

    /**
     * Obtiene el generador de regulares
     *
     * @return Generador de regulares
     */
    public GenRegulares getGenRegulares() {
        if (genRegulares == null) {
            genRegulares = new GenRegulares(tabla);
        }
        return genRegulares;
    }

    /**
     * Establece el generador de regulares
     *
     * @param genRegulares Generador de regulares
     */
    public void setGenRegulares(GenRegulares genRegulares) {
        this.genRegulares = genRegulares;
    }

    /**
     * Obtiene el generador de sentencia
     *
     * @return Generador de sentencia
     */
    public GenSentencia getGenSentencia() {
        if (genSentencia == null) {
            genSentencia = new GenSentencia(tabla);
        }
        return genSentencia;
    }

    /**
     * Establece el generador de sentencia
     *
     * @param genSentencia Generador de sentencia
     */
    public void setGenSentencia(GenSentencia genSentencia) {
        this.genSentencia = genSentencia;
    }

    /**
     * Obtiene el generador de variable
     *
     * @return Generador de variable
     */
    public GenVariable getGenVariable() {
        if (genVariable == null) {
            genVariable = new GenVariable(tabla);
        }
        return genVariable;
    }

    /**
     * Establece el generador de variable
     *
     * @param genVariable Generador de variable
     */
    public void setGenVariable(GenVariable genVariable) {
        this.genVariable = genVariable;
    }

}
