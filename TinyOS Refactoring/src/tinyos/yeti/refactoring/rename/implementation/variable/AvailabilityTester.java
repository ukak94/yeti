package tinyos.yeti.refactoring.rename.implementation.variable;

import tinyos.yeti.nesc12.parser.ast.nodes.general.Identifier;
import tinyos.yeti.refactoring.rename.RenameAvailabilityTester;
import tinyos.yeti.refactoring.selection.VariableSelectionIdentifier;

public class AvailabilityTester extends RenameAvailabilityTester{

	@Override
	protected boolean isSelectionAppropriate(Identifier selectedIdentifier) {
		VariableSelectionIdentifier selectionIdentifier=new VariableSelectionIdentifier(selectedIdentifier);
		return selectionIdentifier.isImplementationLocalVariable();
	}
	
}