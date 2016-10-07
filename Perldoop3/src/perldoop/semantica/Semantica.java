package perldoop.semantica;

import perldoop.modelo.semantica.TablaSemantica;
import perldoop.error.GestorErrores;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.*;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.funciondef.*;
import perldoop.modelo.arbol.funcionsub.*;
import perldoop.modelo.arbol.cuerpo.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.*;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.arbol.varmulti.*;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.semantica.TablaSimbolos;
import perldoop.traductor.Acciones;

/**
 * Clase que define la semantica permitida en el codigo fuente Perl
 *
 * @author César Pomar
 */
public class Semantica implements Visitante {

    private TablaSemantica tabla;
    private FachadaSemanticas fachada;

    /**
     * Contruye el generador
     *
     * @param tablaSimbolos Tabla de símbolos
     * @param opciones opciones
     * @param gestorErrores Gestor de errores
     */
    public Semantica(TablaSimbolos tablaSimbolos, Opciones opciones, GestorErrores gestorErrores) {
        tabla = new TablaSemantica(tablaSimbolos, opciones, gestorErrores);
        fachada = new FachadaSemanticas(tabla);
    }

    /**
     * Obtiene las acciones
     *
     * @return Acciones
     */
    public Acciones getAcciones() {
        return tabla.getAcciones();
    }

    /**
     * Establece las acciones
     *
     * @param acciones Acciones
     */
    public void setAcciones(Acciones acciones) {
        tabla.setAcciones(acciones);
    }

    @Override
    public void visitar(Raiz s) {
        //Sin semantica
    }

    @Override
    public void visitar(Fuente s) {
        //Sin semantica
    }

    @Override
    public void visitar(FuncionDef s) {
        fachada.getSemFuncionDef().visitar(s);
    }

    @Override
    public void visitar(FuncionSub s) {
        fachada.getSemFuncionSub().visitar(s);
    }

    @Override
    public void visitar(Cuerpo s) {
        fachada.getSemCuerpo().visitar(s);
    }

    @Override
    public void visitar(StcLista s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcBloque s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcFlujo s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcPaquete s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcComentario s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcDeclaracion s) {
        fachada.getSemSentencia().visitar(s);
    }

    @Override
    public void visitar(StcError s) {
        //Nunca se ejecutara
    }

    @Override
    public void visitar(ExpConstante s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpVariable s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAsignacion s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpBinario s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAritmetica s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpLogico s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpComparacion s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpColeccion s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpAcceso s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpFuncion s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpFuncion5 s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpRegulares s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpRango s) {
        fachada.getSemExpresion().visitar(s);
    }

    @Override
    public void visitar(Lista s) {
        fachada.getSemLista().visitar(s);
    }

    @Override
    public void visitar(ModNada s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(ModIf s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(ModUnless s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(ModWhile s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(ModUntil s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(ModFor s) {
        fachada.getSemModificador().visitar(s);
    }

    @Override
    public void visitar(Next s) {
        fachada.getSemFlujo().visitar(s);
    }

    @Override
    public void visitar(Last s) {
        fachada.getSemFlujo().visitar(s);
    }

    @Override
    public void visitar(Return s) {
        fachada.getSemFlujo().visitar(s);
    }

    @Override
    public void visitar(Igual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(MasIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(MenosIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(MultiIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(DivIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(ModIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(PowIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(AndIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(OrIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(XorIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(DespDIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(DespIIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(LOrIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(DLOrIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(LAndIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(XIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(ConcatIgual s) {
        fachada.getSemAsignacion().visitar(s);
    }

    @Override
    public void visitar(Entero s) {
        fachada.getSemConstante().visitar(s);

    }

    @Override
    public void visitar(Decimal s) {
        fachada.getSemConstante().visitar(s);

    }

    @Override
    public void visitar(CadenaSimple s) {
        fachada.getSemConstante().visitar(s);

    }

    @Override
    public void visitar(CadenaDoble s) {
        fachada.getSemConstante().visitar(s);
    }

    @Override
    public void visitar(CadenaComando s) {
        fachada.getSemConstante().visitar(s);

    }

    @Override
    public void visitar(VarExistente s) {
        fachada.getSemVariable().visitar(s);

    }

    @Override
    public void visitar(VarPaquete s) {
        fachada.getSemVariable().visitar(s);
    }

    @Override
    public void visitar(VarSigil s) {
        fachada.getSemVariable().visitar(s);
    }

    @Override
    public void visitar(VarPaqueteSigil s) {
        fachada.getSemVariable().visitar(s);
    }

    @Override
    public void visitar(VarMy s) {
        fachada.getSemVariable().visitar(s);
    }

    @Override
    public void visitar(VarOur s) {
        fachada.getSemVariable().visitar(s);
    }

    @Override
    public void visitar(VarMultiMy s) {
        fachada.getSemVarMulti().visitar(s);
    }

    @Override
    public void visitar(VarMultiOur s) {
        fachada.getSemVarMulti().visitar(s);
    }

    @Override
    public void visitar(Paquetes s) {
        fachada.getSemPaquetes().visitar(s);
    }

    @Override
    public void visitar(ColParentesis s) {
        fachada.getSemColeccion().visitar(s);

    }

    @Override
    public void visitar(ColCorchete s) {
        fachada.getSemColeccion().visitar(s);
    }

    @Override
    public void visitar(ColLlave s) {
        fachada.getSemColeccion().visitar(s);
    }

    @Override
    public void visitar(AccesoCol s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoColRef s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoRefEscalar s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoRefArray s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoRefMap s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(AccesoRef s) {
        fachada.getSemAcceso().visitar(s);
    }

    @Override
    public void visitar(FuncionPaqueteArgs s) {
        fachada.getSemFuncion().visitar(s);
    }

    @Override
    public void visitar(FuncionPaqueteNoArgs s) {
        fachada.getSemFuncion().visitar(s);
    }

    @Override
    public void visitar(FuncionArgs s) {
        fachada.getSemFuncion().visitar(s);
    }

    @Override
    public void visitar(FuncionNoArgs s) {
        fachada.getSemFuncion().visitar(s);
    }

    @Override
    public void visitar(Argumentos s) {
        fachada.getSemFuncion().visitar(s);
    }

    @Override
    public void visitar(RegularNoMatch s) {
        fachada.getSemRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularMatch s) {
        fachada.getSemRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularSubs s) {
        fachada.getSemRegulares().visitar(s);
    }

    @Override
    public void visitar(RegularTrans s) {
        fachada.getSemRegulares().visitar(s);
    }

    @Override
    public void visitar(BinOr s) {
        fachada.getSemBinario().visitar(s);

    }

    @Override
    public void visitar(BinAnd s) {
        fachada.getSemBinario().visitar(s);
    }

    @Override
    public void visitar(BinNot s) {
        fachada.getSemBinario().visitar(s);
    }

    @Override
    public void visitar(BinXor s) {
        fachada.getSemBinario().visitar(s);
    }

    @Override
    public void visitar(BinDespI s) {
        fachada.getSemBinario().visitar(s);
    }

    @Override
    public void visitar(BinDespD s) {
        fachada.getSemBinario().visitar(s);
    }

    @Override
    public void visitar(LogOr s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(DLogOr s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogAnd s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogNot s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogOrBajo s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogAndBajo s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogNotBajo s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogXorBajo s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(LogTernario s) {
        fachada.getSemLogico().visitar(s);
    }

    @Override
    public void visitar(CompNumEq s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumNe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumLt s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumLe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumGt s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumGe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompNumCmp s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrEq s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrNe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrLt s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrLe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrGt s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrGe s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompStrCmp s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(CompSmart s) {
        fachada.getSemComparacion().visitar(s);
    }

    @Override
    public void visitar(AritSuma s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritResta s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritMulti s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritDiv s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPow s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritX s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritConcat s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritMod s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPositivo s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritNegativo s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPreIncremento s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPreDecremento s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPostIncremento s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AritPostDecremento s) {
        fachada.getSemAritmetica().visitar(s);
    }

    @Override
    public void visitar(AbrirBloque s) {
        fachada.getSemAbrirBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueWhile s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueUntil s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueDoWhile s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueDoUntil s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueFor s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueForeachVar s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueForeach s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueIf s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueUnless s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(BloqueVacio s) {
        fachada.getSemBloque().visitar(s);
    }

    @Override
    public void visitar(CondicionalElse s) {
        fachada.getSemCondicional().visitar(s);

    }

    @Override
    public void visitar(CondicionalElsif s) {
        fachada.getSemCondicional().visitar(s);
    }

    @Override
    public void visitar(CondicionalNada s) {
        fachada.getSemCondicional().visitar(s);

    }

    @Override
    public void visitar(Terminal s) {
        //Sin semantica
    }

}
