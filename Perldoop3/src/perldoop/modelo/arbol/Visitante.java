package perldoop.modelo.arbol;

import perldoop.modelo.arbol.varmulti.VarMultiOur;
import perldoop.modelo.arbol.varmulti.VarMultiMy;
import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.masfuente.*;
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
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.condicional.*;
import perldoop.modelo.arbol.elsif.*;
import perldoop.modelo.arbol.bloqueelsif.*;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.paquete.*;

/**
 * Interfaz para el patron Visitor
 *
 * @author César Pomar
 */
public interface Visitante {

    //Raiz
    void visitar(Raiz s);

    //Fuente
    void visitar(Fuente s);

    //MasFuente
    void visitar(MfNada s);

    void visitar(MfFuente s);

    //FuncionDef
    void visitar(FuncionDef s);

    //FuncionSub
    void visitar(FuncionSub s);

    //Cuerpo
    void visitar(Cuerpo s);

    //Sentencia
    void visitar(StcLista s);

    void visitar(StcBloque s);

    void visitar(StcFlujo s);

    void visitar(StcPaquete s);

    void visitar(StcComentario s);

    void visitar(StcDeclaracion s);

    void visitar(StcError s);
    //Expresion

    void visitar(ExpConstante s);

    void visitar(ExpVariable s);

    void visitar(ExpAsignacion s);

    void visitar(ExpBinario s);

    void visitar(ExpAritmetica s);

    void visitar(ExpLogico s);

    void visitar(ExpComparacion s);

    void visitar(ExpColeccion s);

    void visitar(ExpAcceso s);

    void visitar(ExpFuncion s);

    void visitar(ExpFuncion5 s);

    void visitar(ExpRegulares s);

    //Lista
    void visitar(Lista s);

    //Modificador
    void visitar(ModNada s);

    void visitar(ModIf s);

    void visitar(ModUnless s);

    void visitar(ModWhile s);

    void visitar(ModUntil s);

    void visitar(ModFor s);

    //Flujo
    void visitar(Next s);

    void visitar(Last s);

    void visitar(Return s);

    //Asignacion
    void visitar(Igual s);

    void visitar(MasIgual s);

    void visitar(MenosIgual s);

    void visitar(MultiIgual s);

    void visitar(DivIgual s);

    void visitar(ModIgual s);

    void visitar(PowIgual s);

    void visitar(AndIgual s);

    void visitar(OrIgual s);

    void visitar(XorIgual s);

    void visitar(DespDIgual s);

    void visitar(DespIIgual s);

    void visitar(LOrIgual s);

    void visitar(LAndIgual s);

    void visitar(XIgual s);

    void visitar(ConcatIgual s);

    //Constante
    void visitar(Entero s);

    void visitar(Decimal s);

    void visitar(CadenaSimple s);

    void visitar(CadenaDoble s);

    void visitar(CadenaComando s);

    //Variable
    void visitar(VarExistente s);

    void visitar(VarPaquete s);

    void visitar(VarMy s);

    void visitar(VarOur s);

    //VarMulti
    void visitar(VarMultiMy s);

    void visitar(VarMultiOur s);

    //Paquete
    void visitar(Paquetes s);

    //Colección
    void visitar(ColParentesis s);

    void visitar(ColCorchete s);

    void visitar(ColLlave s);

    //Rango
    void visitar(Rango s);

    //Acceso
    void visitar(AccesoCol s);

    void visitar(AccesoColRef s);

    void visitar(AccesoRefEscalar s);

    void visitar(AccesoRefArray s);

    void visitar(AccesoRefMap s);

    void visitar(AccesoSigil s);

    void visitar(AccesoRef s);

    //Funcion
    void visitar(FuncionPaqueteArgs s);

    void visitar(FuncionPaqueteNoArgs s);

    void visitar(FuncionArgs s);

    void visitar(FuncionNoArgs s);

    void visitar(Argumentos s);

    //Regulares
    void visitar(RegularNoMatch s);

    void visitar(RegularMatch s);

    void visitar(RegularSubs s);

    void visitar(RegularTrans s);

    //Binario
    void visitar(BinOr s);

    void visitar(BinAnd s);

    void visitar(BinNot s);

    void visitar(BinXor s);

    void visitar(BinDespI s);

    void visitar(BinDespD s);

    //Logico
    void visitar(LogOr s);

    void visitar(LogAnd s);

    void visitar(LogNot s);

    void visitar(LogOrBajo s);

    void visitar(LogAndBajo s);

    void visitar(LogNotBajo s);

    void visitar(LogXorBajo s);

    void visitar(LogTernario s);

    //Comparacion
    void visitar(CompNumEq s);

    void visitar(CompNumNe s);

    void visitar(CompNumLt s);

    void visitar(CompNumLe s);

    void visitar(CompNumGt s);

    void visitar(CompNumGe s);

    void visitar(CompNumCmp s);

    void visitar(CompStrEq s);

    void visitar(CompStrNe s);

    void visitar(CompStrLt s);

    void visitar(CompStrLe s);

    void visitar(CompStrGt s);

    void visitar(CompStrGe s);

    void visitar(CompStrCmp s);

    void visitar(CompSmart s);

    //Aritmetica
    void visitar(AritSuma s);

    void visitar(AritResta s);

    void visitar(AritMulti s);

    void visitar(AritDiv s);

    void visitar(AritPow s);

    void visitar(AritX s);

    void visitar(AritConcat s);

    void visitar(AritMod s);

    void visitar(AritPositivo s);

    void visitar(AritNegativo s);

    void visitar(AritPreIncremento s);

    void visitar(AritPreDecremento s);

    void visitar(AritPostIncremento s);

    void visitar(AritPostDecremento s);

    //AbrirBloque
    void visitar(AbrirBloque s);

    //Bloque
    void visitar(BloqueCondicional s);

    void visitar(BloqueWhile s);

    void visitar(BloqueUntil s);

    void visitar(BloqueDoWhile s);

    void visitar(BloqueDoUntil s);

    void visitar(BloqueFor s);

    void visitar(BloqueForeachVar s);

    void visitar(BloqueForeach s);

    //Condicional
    void visitar(CondicionalIf s);

    void visitar(CondicionalUnless s);

    //ElsIf
    void visitar(ElsIfNada s);

    void visitar(ElsIfElsIf s);

    void visitar(ElsIfElse s);

    //BloqueElfIf
    void visitar(BloqueElsIf s);

    //Terminal
    void visitar(Terminal s);

}
