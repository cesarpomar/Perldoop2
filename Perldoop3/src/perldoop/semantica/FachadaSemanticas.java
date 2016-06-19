package perldoop.semantica;

import perldoop.semantica.abrirbloque.*;
import perldoop.semantica.acceso.*;
import perldoop.semantica.aritmetica.*;
import perldoop.semantica.asignacion.*;
import perldoop.semantica.binario.*;
import perldoop.semantica.bloque.*;
import perldoop.semantica.bloqueelsif.*;
import perldoop.semantica.coleccion.*;
import perldoop.semantica.comparacion.*;
import perldoop.semantica.condicional.*;
import perldoop.semantica.constante.*;
import perldoop.semantica.cuerpo.*;
import perldoop.semantica.elsif.*;
import perldoop.semantica.expresion.*;
import perldoop.semantica.flujo.*;
import perldoop.semantica.funcion.*;
import perldoop.semantica.funciondef.*;
import perldoop.semantica.funcionsub.*;
import perldoop.semantica.lista.*;
import perldoop.semantica.logico.*;
import perldoop.semantica.modificador.*;
import perldoop.semantica.regulares.*;
import perldoop.semantica.sentencia.*;
import perldoop.semantica.variable.*;

/**
 * Fachada de las semanticas
 *
 * @author César Pomar
 */
public final class FachadaSemanticas {

    private TablaSemantica tabla;

    /*Semanticaes*/
    private SemAbrirBloque semAbrirBloque;
    private SemAcceso semAcceso;
    private SemAritmetica semAritmetica;
    private SemAsignacion semAsignacion;
    private SemBinario semBinario;
    private SemBloque semBloque;
    private SemBloqueElsIf semBloqueElsIf;
    private SemColeccion semColeccion;
    private SemComparacion semComparacion;
    private SemCondicional semCondicional;
    private SemConstante semConstante;
    private SemCuerpo semCuerpo;
    private SemElsIf semElsIf;
    private SemExpresion semExpresion;
    private SemFlujo semFlujo;
    private SemFuncion semFuncion;
    private SemFuncionDef semFuncionDef;
    private SemFuncionSub semFuncionSub;
    private SemLista semLista;
    private SemLogico semLogico;
    private SemModificador semModificador;
    private SemRegulares semRegulares;
    private SemSentencia semSentencia;
    private SemVariable semVariable;

    /**
     * Constructor de la fachada
     *
     * @param tabla tablaSemantica
     */
    public FachadaSemanticas(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene la semantica de abrirBloque
     *
     * @return Semantica de abrirBloque
     */
    public SemAbrirBloque getSemAbrirBloque() {
        if (semAbrirBloque == null) {
            semAbrirBloque = new SemAbrirBloque(tabla);
        }
        return semAbrirBloque;
    }

    /**
     * Establece la semantica de abrirBloque
     *
     * @param semAbrirBloque Semantica de abrirBloque
     */
    public void setSemAbrirBloque(SemAbrirBloque semAbrirBloque) {
        this.semAbrirBloque = semAbrirBloque;
    }

    /**
     * Obtiene la semantica de acceso
     *
     * @return Semantica de acceso
     */
    public SemAcceso getSemAcceso() {
        if (semAcceso == null) {
            semAcceso = new SemAcceso(tabla);
        }
        return semAcceso;
    }

    /**
     * Establece la semantica de acceso
     *
     * @param semAcceso Semantica de acceso
     */
    public void setSemAcceso(SemAcceso semAcceso) {
        this.semAcceso = semAcceso;
    }

    /**
     * Obtiene la semantica de aritmetica
     *
     * @return Semantica de aritmetica
     */
    public SemAritmetica getSemAritmetica() {
        if (semAritmetica ==null){
            semAritmetica = new SemAritmetica(tabla);
        }
        return semAritmetica;
    }

    /**
     * Establece la semantica de aritmetica
     *
     * @param semAritmetica Semantica de aritmetica
     */
    public void setSemAritmetica(SemAritmetica semAritmetica) {
        this.semAritmetica = semAritmetica;
    }

    /**
     * Obtiene la semantica de asignacion
     *
     * @return Semantica de asignacion
     */
    public SemAsignacion getSemAsignacion() {
        if (semAsignacion ==null){
            semAsignacion = new SemAsignacion(tabla);
        }
        return semAsignacion;
    }

    /**
     * Establece la semantica de asignacion
     *
     * @param semAsignacion Semantica de asignacion
     */
    public void setSemAsignacion(SemAsignacion semAsignacion) {
        this.semAsignacion = semAsignacion;
    }

    /**
     * Obtiene la semantica de binario
     *
     * @return Semantica de binario
     */
    public SemBinario getSemBinario() {
        if (semBinario ==null){
            semBinario = new SemBinario(tabla);
        }
        return semBinario;
    }

    /**
     * Establece la semantica de binario
     *
     * @param semBinario Semantica de binario
     */
    public void setSemBinario(SemBinario semBinario) {
        this.semBinario = semBinario;
    }

    /**
     * Obtiene la semantica de bloque
     *
     * @return Semantica de bloque
     */
    public SemBloque getSemBloque() {
        if (semBloque ==null){
            semBloque = new SemBloque(tabla);
        }
        return semBloque;
    }

    /**
     * Establece la semantica de bloque
     *
     * @param semBloque Semantica de bloque
     */
    public void setSemBloque(SemBloque semBloque) {
        this.semBloque = semBloque;
    }

    /**
     * Obtiene la semantica de elsif
     *
     * @return Semantica de elsif
     */
    public SemBloqueElsIf getSemBloqueElsIf() {
        if (semBloqueElsIf ==null){
            semBloqueElsIf = new SemBloqueElsIf(tabla);
        }
        return semBloqueElsIf;
    }

    /**
     * Establece la semantica de elsif
     *
     * @param semBloqueElsIf Semantica de elsif
     */
    public void setSemBloqueElsIf(SemBloqueElsIf semBloqueElsIf) {
        this.semBloqueElsIf = semBloqueElsIf;
    }

    /**
     * Obtiene la semantica de coleccion
     *
     * @return Semantica de coleccion
     */
    public SemColeccion getSemColeccion() {
        if (semColeccion ==null){
            semColeccion = new SemColeccion(tabla);
        }
        return semColeccion;
    }

    /**
     * Establece la semantica de coleccion
     *
     * @param semColeccion Semantica de coleccion
     */
    public void setSemColeccion(SemColeccion semColeccion) {
        this.semColeccion = semColeccion;
    }

    /**
     * Obtiene la semantica de comparacion
     *
     * @return Semantica de comparacion
     */
    public SemComparacion getSemComparacion() {
        if (semComparacion ==null){
            semComparacion = new SemComparacion(tabla);
        }
        return semComparacion;
    }

    /**
     * Establece la semantica de comparacion
     *
     * @param semComparacion Semantica de comparacion
     */
    public void setSemComparacion(SemComparacion semComparacion) {
        this.semComparacion = semComparacion;
    }

    /**
     * Obtiene la semantica de condicional
     *
     * @return Semantica de condicional
     */
    public SemCondicional getSemCondicional() {
        if (semCondicional ==null){
            semCondicional = new SemCondicional(tabla);
        }
        return semCondicional;
    }

    /**
     * Establece la semantica de condicional
     *
     * @param semCondicional Semantica de condicional
     */
    public void setSemCondicional(SemCondicional semCondicional) {
        this.semCondicional = semCondicional;
    }

    /**
     * Obtiene la semantica de constante
     *
     * @return Semantica de constante
     */
    public SemConstante getSemConstante() {
        if (semConstante ==null){
            semConstante = new SemConstante(tabla);
        }
        return semConstante;
    }

    /**
     * Establece la semantica de constante
     *
     * @param semConstante Semantica de constante
     */
    public void setSemConstante(SemConstante semConstante) {
        this.semConstante = semConstante;
    }

    /**
     * Obtiene la semantica de cuerpo
     *
     * @return Semantica de cuerpo
     */
    public SemCuerpo getSemCuerpo() {
        if (semCuerpo ==null){
            semCuerpo = new SemCuerpo(tabla);
        }
        return semCuerpo;
    }

    /**
     * Establece la semantica de cuerpo
     *
     * @param semCuerpo Semantica de cuerpo
     */
    public void setSemCuerpo(SemCuerpo semCuerpo) {
        this.semCuerpo = semCuerpo;
    }

    /**
     * Obtiene la semantica de elsif
     *
     * @return Semantica de elsif
     */
    public SemElsIf getSemElsIf() {
        if (semElsIf ==null){
            semElsIf = new SemElsIf(tabla);
        }
        return semElsIf;
    }

    /**
     * Establece la semantica de elsif
     *
     * @param semElsIf Semantica de elsif
     */
    public void setSemElsIf(SemElsIf semElsIf) {
        this.semElsIf = semElsIf;
    }

    /**
     * Obtiene la semantica de expresion
     *
     * @return Semantica de expresion
     */
    public SemExpresion getSemExpresion() {
        if (semExpresion ==null){
            semExpresion = new SemExpresion(tabla);
        }
        return semExpresion;
    }

    /**
     * Establece la semantica de expresion
     *
     * @param semExpresion Semantica de expresion
     */
    public void setSemExpresion(SemExpresion semExpresion) {
        this.semExpresion = semExpresion;
    }

    /**
     * Obtiene la semantica de flujo
     *
     * @return Semantica de flujo
     */
    public SemFlujo getSemFlujo() {
        if (semFlujo ==null){
            semFlujo = new SemFlujo(tabla);
        }
        return semFlujo;
    }

    /**
     * Establece la semantica de flujo
     *
     * @param semFlujo Semantica de flujo
     */
    public void setSemFlujo(SemFlujo semFlujo) {
        this.semFlujo = semFlujo;
    }

    /**
     * Obtiene la semantica de funcion
     *
     * @return Semantica de funcion
     */
    public SemFuncion getSemFuncion() {
        if (semFuncion ==null){
            semFuncion = new SemFuncion(tabla);
        }
        return semFuncion;
    }

    /**
     * Establece la semantica de funcion
     *
     * @param semFuncion Semantica de funcion
     */
    public void setSemFuncion(SemFuncion semFuncion) {
        this.semFuncion = semFuncion;
    }

    /**
     * Obtiene la semantica de funciondef
     *
     * @return Semantica de funciondef
     */
    public SemFuncionDef getSemFuncionDef() {
        if (semFuncionDef ==null){
            semFuncionDef = new SemFuncionDef(tabla);
        }
        return semFuncionDef;
    }

    /**
     * Establece la semantica de funciondef
     *
     * @param semFuncionDef Semantica de funciondef
     */
    public void setSemFuncionDef(SemFuncionDef semFuncionDef) {
        this.semFuncionDef = semFuncionDef;
    }

    /**
     * Obtiene la semantica de funcionsub
     *
     * @return Semantica de funcionsub
     */
    public SemFuncionSub getSemFuncionSub() {
        if (semFuncionSub ==null){
            semFuncionSub = new SemFuncionSub(tabla);
        }
        return semFuncionSub;
    }

    /**
     * Establece la semantica de funcionsub
     *
     * @param semFuncionSub Semantica de funcionsub
     */
    public void setSemFuncionSub(SemFuncionSub semFuncionSub) {
        this.semFuncionSub = semFuncionSub;
    }

    /**
     * Obtiene la semantica de lista
     *
     * @return Semantica de lista
     */
    public SemLista getSemLista() {
        if (semLista ==null){
            semLista = new SemLista(tabla);
        }
        return semLista;
    }

    /**
     * Establece la semantica de lista
     *
     * @param semLista Semantica de lista
     */
    public void setSemLista(SemLista semLista) {
        this.semLista = semLista;
    }

    /**
     * Obtiene la semantica de logico
     *
     * @return Semantica de logico
     */
    public SemLogico getSemLogico() {
        if (semLogico ==null){
            semLogico = new SemLogico(tabla);
        }
        return semLogico;
    }

    /**
     * Establece la semantica de logico
     *
     * @param semLogico Semantica de logico
     */
    public void setSemLogico(SemLogico semLogico) {
        this.semLogico = semLogico;
    }

    /**
     * Obtiene la semantica de modificador
     *
     * @return Semantica de modificador
     */
    public SemModificador getSemModificador() {
        if (semModificador ==null){
            semModificador = new SemModificador(tabla);
        }
        return semModificador;
    }

    /**
     * Establece la semantica de modificador
     *
     * @param semModificador Semantica de modificador
     */
    public void setSemModificador(SemModificador semModificador) {
        this.semModificador = semModificador;
    }

    /**
     * Obtiene la semantica de regulares
     *
     * @return Semantica de regulares
     */
    public SemRegulares getSemRegulares() {
        if (semRegulares ==null){
            semRegulares = new SemRegulares(tabla);
        }
        return semRegulares;
    }

    /**
     * Establece la semantica de regulares
     *
     * @param semRegulares Semantica de regulares
     */
    public void setSemRegulares(SemRegulares semRegulares) {
        this.semRegulares = semRegulares;
    }

    /**
     * Obtiene la semantica de sentencia
     *
     * @return Semantica de sentencia
     */
    public SemSentencia getSemSentencia() {
        if (semSentencia ==null){
            semSentencia = new SemSentencia(tabla);
        }
        return semSentencia;
    }

    /**
     * Establece la semantica de sentencia
     *
     * @param semSentencia Semantica de sentencia
     */
    public void setSemSentencia(SemSentencia semSentencia) {
        this.semSentencia = semSentencia;
    }

    /**
     * Obtiene la semantica de variable
     *
     * @return Semantica de variable
     */
    public SemVariable getSemVariable() {
        if (semVariable ==null){
            semVariable = new SemVariable(tabla);
        }
        return semVariable;
    }

    /**
     * Establece la semantica de variable
     *
     * @param semVariable Semantica de variable
     */
    public void setSemVariable(SemVariable semVariable) {
        this.semVariable = semVariable;
    }
}
