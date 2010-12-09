package br.pensario.connector;

import br.pensario.NCLIdentifiableElement;
import java.util.Set;
import java.util.TreeSet;


/**
 * Esta classe define o elemento <i>connectorBase</i> da <i>Nested Context Language</i> (NCL).
 * Este elemento é o elemento que define uma base de conectores de um documento NCL.<br>
 *
 * @see <a
 *      href="http://www.abnt.org.br/imagens/Normalizacao_TV_Digital/ABNTNBR15606-5_2008Ed1.pdf">ABNT
 *      NBR 15606-5:2008</a>
 *
 *
 * @version 1.0.0
 * @author <a href="http://joel.dossantos.eng.br">Joel dos Santos<a/>
 * @author <a href="http://www.cos.ufrj.br/~schau/">Wagner Schau<a/>
 */
public class NCLConnectorBase<C extends NCLCausalConnector> extends NCLIdentifiableElement {

    Set<C> connectors = new TreeSet<C>();
    
    
    /**
     * Adiciona um conector a base de conectores.
     * 
     * @param connector
     *          elemento representando o conector a ser adicionado.
     * @return
     *          verdadeiro se o conector foi adicionado.
     *
     * @see TreeSet#add(java.lang.Object)
     */
    public boolean addCausalConnector(C connector) {
        return connectors.add(connector);        
    }
    
    
    /**
     * Remove um conector da base de conectores.
     * 
     * @param connector
     *          elemento representando o conector a ser removido.
     * @return
     *          verdadeiro se o conector foi removido.
     *
     * @see TreeSet#remove(java.lang.Object)
     */    
    public boolean removeCausalConnector(C connector) {
        return connectors.remove(connector);        
    }
    
    
    /**
     * Verifica se a base de conectores possui um conector.
     * 
     * @param connector
     *          elemento representando o conector a ser verificado.
     * @return
     *          verdadeiro se o conector existir.
     */
    public boolean hasCausalConnector(C connector) {
        return connectors.contains(connector);        
    }
    
    
    /**
     * Verifica se a base de conectores possui pelo menos um conector.
     * 
     * @return
     *          verdadeiro se a base de conectores possui pelo menos um conector.
     */
    public boolean hasCausalConnector() {
        return !connectors.isEmpty();
    }

    
    /**
     * Retorna os conectores da base de conectores.
     * 
     * @return
     *          objeto Iterable contendo os conectores da base de conectores.
     */
    public Iterable<C> getCausalConnectors() {
        return connectors;        
    }
    
    
    public String parse(int ident) {
        String space, content;

        if (ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for (int i = 0; i < ident; i++)
            space += "\t";

        content = space + "<connectorBase ";

        if (getId() != null)
            content += " id='" + getId() + "'";

        content += ">\n";

        if (hasCausalConnector())

            for (C connector: getCausalConnectors())
                content += connector.parse(ident + 1);

        content += space + "<connectorBase/>\n";

        return content;
    }
    
}
