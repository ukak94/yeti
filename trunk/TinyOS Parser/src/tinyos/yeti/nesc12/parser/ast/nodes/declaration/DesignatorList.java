/*
 * Yeti 2, NesC development in Eclipse.
 * Copyright (C) 2009 ETH Zurich
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Web:  http://tos-ide.ethz.ch
 * Mail: tos-ide@tik.ee.ethz.ch
 */
package tinyos.yeti.nesc12.parser.ast.nodes.declaration;

import tinyos.yeti.nesc12.parser.ast.ASTException;
import tinyos.yeti.nesc12.parser.ast.ASTVisitor;
import tinyos.yeti.nesc12.parser.ast.AnalyzeStack;
import tinyos.yeti.nesc12.parser.ast.elements.Type;
import tinyos.yeti.nesc12.parser.ast.nodes.AbstractListASTNode;

public class DesignatorList extends AbstractListASTNode<Designator> {
    private Type type;
    
    public DesignatorList(){
        super( "DesignatorList" );
    }

    public DesignatorList( Designator child ){
        this();
        add( child );
    }
    
    public Type resolveType(){
        return type;
    }
    
    @Override
    public void resolve( AnalyzeStack stack ) {
        // TODO errors, warnings missing
        super.resolve( stack );
    }
    
    @Override
    public DesignatorList add( Designator node ) {
        super.add( node );
        return this;
    }
    
    @Override
    protected void checkChild( Designator child ) throws ASTException {
        
    }

    @Override
    protected boolean visit( ASTVisitor visitor ) {
        return visitor.visit( this );
    }

    @Override
    protected void endVisit( ASTVisitor visitor ) {
        visitor.endVisit( this );
    }
}
