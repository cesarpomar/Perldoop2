package perldoop.modelo.arbol;

import perldoop.modelo.arbol.rango.Rango;
import perldoop.modelo.arbol.abrirbloque.*;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.aritmetica.*;
import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.arbol.binario.*;
import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.arbol.cadena.*;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.cuerpo.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.fuente.*;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.funciondef.*;
import perldoop.modelo.arbol.funcionsub.*;
import perldoop.modelo.arbol.handle.*;
import perldoop.modelo.arbol.lectura.*;
import perldoop.modelo.arbol.lista.*;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.modificador.*;
import perldoop.modelo.arbol.modulos.*;
import perldoop.modelo.arbol.numero.*;
import perldoop.modelo.arbol.paquete.*;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.std.*;
import perldoop.modelo.arbol.variable.*;

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

    void visitar(StcModulos s);

    void visitar(StcComentario s);

    void visitar(StcTipado s);

    void visitar(StcImport s);

    void visitar(StcLineaJava s);

    void visitar(StcError s);

    //Modulos
    void visitar(ModuloPackage s);

    void visitar(ModuloUse s);

    //Expresion
    void visitar(ExpNumero s);

    void visitar(ExpCadena s);

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

    void visitar(ExpLectura s);

    void visitar(ExpStd s);

    void visitar(ExpRegulares s);

    void visitar(ExpRango s);
    
    //Rango
    void visitar(Rango s);
    
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

    void visitar(DLOrIgual s);

    void visitar(LAndIgual s);

    void visitar(XIgual s);

    void visitar(ConcatIgual s);

    //Numero
    void visitar(Entero s);

    void visitar(Decimal s);

    //Cadena
    void visitar(CadenaSimple s);

    void visitar(CadenaDoble s);

    void visitar(CadenaComando s);

    void visitar(CadenaQ s);

    void visitar(CadenaQW s);

    void visitar(CadenaQQ s);

    void visitar(CadenaQX s);

    void visitar(CadenaQR s);

    //Variable
    void visitar(VarExistente s);

    void visitar(VarPaquete s);

    void visitar(VarSigil s);

    void visitar(VarPaqueteSigil s);

    void visitar(VarMy s);

    void visitar(VarOur s);

    //Paquete
    void visitar(Paquetes s);

    //Colección
    void visitar(ColParentesis s);

    void visitar(ColDecMy s);

    void visitar(ColDecOur s);

    void visitar(ColCorchete s);

    void visitar(ColLlave s);

    //Acceso
    void visitar(AccesoCol s);

    void visitar(AccesoColRef s);

    void visitar(AccesoDesRef s);

    void visitar(AccesoRef s);

    //Funcion
    void visitar(FuncionBasica s);

    void visitar(FuncionHandle s);

    void visitar(FuncionBloque s);

    //Handle
    void visitar(HandleErr s);

    void visitar(HandleOut s);

    void visitar(HandleFile s);

    //Std
    void visitar(StdErr s);

    void visitar(StdOut s);

    void visitar(StdIn s);

    //Lectura
    void visitar(LecturaIn s);

    void visitar(LecturaFile s);

    //Regulares
    void visitar(RegularNoMatch s);

    void visitar(RegularMatch s);

    void visitar(RegularSubs s);

    void visitar(RegularTrans s);

    //CadenaTexto
    void visitar(CadenaTexto s);

    //Binario
    void visitar(BinOr s);

    void visitar(BinAnd s);

    void visitar(BinNot s);

    void visitar(BinXor s);

    void visitar(BinDespI s);

    void visitar(BinDespD s);

    //Logico
    void visitar(LogOr s);

    void visitar(DLogOr s);

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
    void visitar(BloqueWhile s);

    void visitar(BloqueUntil s);

    void visitar(BloqueDoWhile s);

    void visitar(BloqueDoUntil s);

    void visitar(BloqueFor s);

    void visitar(BloqueForeachVar s);

    void visitar(BloqueForeach s);

    void visitar(BloqueIf s);

    void visitar(BloqueUnless s);

    void visitar(BloqueVacio s);

    void visitar(SubBloqueElse s);

    void visitar(SubBloqueElsif s);

    void visitar(SubBloqueVacio s);

    //Terminal
    void visitar(Terminal s);

}
