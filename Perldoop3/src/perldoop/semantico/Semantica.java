package perldoop.semantico;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.arbol.expresion.ExpConstante;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.semantico.asignacion.SemAsignacion;
import perldoop.semantico.codigo.SemCodigo;
import perldoop.semantico.constante.SemConstante;
import perldoop.semantico.expresion.SemExpresion;
import perldoop.semantico.lista.SemLista;
import perldoop.semantico.sentencia.SemSentencia;
import perldoop.semantico.variable.SemVariable;

/**
 *
 * @author César
 */
public class Semantica implements Visitante {

    private SemCodigo codigo;
    private SemSentencia sentencia;
    private SemExpresion expresion;
    private SemAsignacion asignacion;
    private SemLista lista;
    private SemVariable variable;
    private SemConstante constante;

    public SemAsignacion getAsignacion() {
        if (asignacion == null) {
            asignacion = new SemAsignacion();
        }
        return asignacion;
    }

    public void setAsignacion(SemAsignacion asignacion) {
        this.asignacion = asignacion;
    }

    public SemCodigo getCodigo() {
        if (codigo == null) {
            codigo = new SemCodigo();
        }
        return codigo;
    }

    public void setCodigo(SemCodigo codigo) {
        this.codigo = codigo;
    }

    public SemSentencia getSentencia() {
        if (sentencia == null) {
            sentencia = new SemSentencia();
        }
        return sentencia;
    }

    public void setSentencia(SemSentencia sentencia) {
        this.sentencia = sentencia;
    }

    public SemExpresion getExpresion() {
        if (expresion == null) {
            expresion = new SemExpresion();
        }
        return expresion;
    }

    public void setExpresion(SemExpresion expresion) {
        this.expresion = expresion;
    }

    public SemLista getLista() {
        if (lista == null) {
            lista = new SemLista();
        }
        return lista;
    }

    public void setLista(SemLista lista) {
        this.lista = lista;
    }

    public SemVariable getVariable() {
        if (variable == null) {
            variable = new SemVariable();
        }
        return variable;
    }

    public void setVariable(SemVariable variable) {
        this.variable = variable;
    }

    public SemConstante getConstante() {
        if (constante == null) {
            constante = new SemConstante();
        }
        return constante;
    }

    public void setConstante(SemConstante constante) {
        this.constante = constante;
    }

    @Override
    public void visitar(Igual s) {
        getAsignacion().visitar(s);
    }

    @Override
    public void visitar(Cuerpo s) {
        getCodigo().visitar(s);
    }




    @Override
    public void visitar(ExpAsignacion s) {
        getExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpConstante s) {
        getExpresion().visitar(s);
    }

    @Override
    public void visitar(ExpVariable s) {
        getExpresion().visitar(s);
    }

    @Override
    public void visitar(Lista s) {
        getLista().visitar(s);
    }

    @Override
    public void visitar(VarExistente s) {
        getVariable().visitar(s);
    }

    @Override
    public void visitar(VarMy s) {
        getVariable().visitar(s);
    }

    @Override
    public void visitar(VarOur s) {
        getVariable().visitar(s);
    }

    @Override
    public void visitar(CadenaComando s) {
        getConstante().visitar(s);
    }

    @Override
    public void visitar(CadenaDoble s) {
        getConstante().visitar(s);
    }

    @Override
    public void visitar(CadenaSimple s) {
        getConstante().visitar(s);
    }

    @Override
    public void visitar(Decimal s) {
        getConstante().visitar(s);
    }

    @Override
    public void visitar(Entero s) {
        getConstante().visitar(s);
    }

    @Override
    public void visitar(Terminal s) {
        //No tiene semantica
    }

    @Override
    public void visitar(Simbolo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visitar(StcLista s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
