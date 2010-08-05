package tinyos.yeti.refactoring.rename;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.Region;
import org.eclipse.ltk.core.refactoring.FileStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import tinyos.yeti.nesc12.parser.ast.nodes.general.Identifier;
import tinyos.yeti.refactoring.ast.ComponentAstAnalyser;
import tinyos.yeti.refactoring.ast.ConfigurationAstAnalyzer;
import tinyos.yeti.refactoring.utilities.ASTUtil;

public class NameCollissionDetector {
	
	private ASTUtil astUtil=new ASTUtil();
	
	/**
	 * Checks if the new component name already exists in the given configuration as local name for a component or a interface.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 * @param configurationAnalyzer
	 * @param file
	 * @param oldName
	 * @param newName
	 * @param ret
	 */
	public void handleCollisions4NewComponentNameWithConfigurationLocalName(ConfigurationAstAnalyzer configurationAnalyzer, IFile file, String oldName, String newName,RefactoringStatus ret){
		//Check if there is a local component name with the same name.
		Identifier toRename=existsLocalComponentName(configurationAnalyzer, oldName);
		if(toRename==null){
			return;
		}
		handleCollisionsInConfigurationImplementationScope(configurationAnalyzer, file, toRename, newName, ret);
	}
	
	/**
	 * Checks if the new interface name already exists in the given configuration as local name for a component or a interface.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 * @param configurationAnalyzer
	 * @param file
	 * @param oldName
	 * @param newName
	 * @param ret
	 */
	public void handleCollisions4NewInterfaceNameWithConfigurationLocalName(ConfigurationAstAnalyzer configurationAnalyzer, IFile file, String oldName,String newName, RefactoringStatus ret) {
		Identifier toRename=existsLocalInterfaceName(configurationAnalyzer, oldName);
		if(toRename==null){
			return;
		}
		handleCollisionsInConfigurationImplementationScope(configurationAnalyzer, file, toRename, newName, ret);
		
	}
	
	/**
	 * Checks if the newName collides with an existing local interface or component name.
	 * @param configurationAnalyzer
	 * @param file
	 * @param toRename
	 * @param newName
	 * @param ret
	 */
	private void handleCollisionsInConfigurationImplementationScope(ConfigurationAstAnalyzer configurationAnalyzer, IFile file, Identifier toRename,String newName, RefactoringStatus ret){
		newNameWithLocalComponentName(configurationAnalyzer, file, toRename, newName, ret);
		newNameWithLocalInterfaceName(configurationAnalyzer, file, toRename, newName, ret);
	}
	
	/**
	 * Checks if the Name of the toRename identifier already exists in the given configuration implementation as local name for a component.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 */
	public void newNameWithLocalComponentName(ConfigurationAstAnalyzer configurationAnalyzer, IFile representedFile, String oldName, String newName,RefactoringStatus ret){
		Identifier toRename=existsLocalComponentName(configurationAnalyzer, oldName);
		if(toRename==null){
			return;
		}
		newNameWithLocalComponentName(configurationAnalyzer,representedFile,toRename,newName,ret);
	}
	
	/**
	 * Checks if the Name of the toRename identifier already exists in the given configuration implementation as local name for a component.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 */
	public void newNameWithLocalComponentName(ConfigurationAstAnalyzer configurationAnalyzer, IFile representedFile, Identifier toRename, String newName,RefactoringStatus ret){
		if(toRename==null){
			return;
		}
		Identifier sameName=existsLocalComponentName(configurationAnalyzer,newName);
		if(sameName!=null){
			addInfo(toRename,sameName,representedFile,ret);
		}
	}
	
	/**
	 * Checks if the newName already exists in the given component specification as local name for a interface.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 */
	public void newInterfaceNameWithLocalInterfaceName(ComponentAstAnalyser componentAnalyzer, IFile representedFile, String oldName, String newName,RefactoringStatus ret){
		Identifier toRename=existsLocalInterfaceName(componentAnalyzer, oldName);
		if(toRename==null){
			return;
		}
		newNameWithLocalInterfaceName(componentAnalyzer,representedFile,toRename,newName,ret);
	}
	
	/**
	 * Checks if the Name of the toRename identifier already exists in the given component specification as local name for a interface.
	 * If the newName already exists the RefactoringStatus is marked as erroneous  and context information is added.
	 * If the newName doesnt exist the {@link RefactoringStatus} is not modified.
	 * If the oldName doesnt exist this method doesnt have any effect.
	 */
	public void newNameWithLocalInterfaceName(ComponentAstAnalyser componentAnalyzer, IFile representedFile, Identifier toRename, String newName,RefactoringStatus ret){
		if(toRename==null){
			return;
		}
		Identifier sameName=existsLocalInterfaceName(componentAnalyzer, newName);
		//Add Info
		if(sameName!=null){
			addInfo(toRename,sameName,representedFile,ret);
		}
	}
	
	/**
	 * Returns the component name identifier which has the given name in the configuration implementation scope.
	 * Returns null if there is no component identifier with the given name.
	 * @param analyzer
	 * @param oldName
	 * @return
	 */
	private Identifier existsLocalComponentName(ConfigurationAstAnalyzer analyzer,String name){
		Set<Identifier> localComponentNames=analyzer.getComponentLocalName2ComponentGlobalName().keySet();
		return astUtil.getIdentifierWithEqualName(name, localComponentNames);
	}
	
	/**
	 * Returns the interface name identifier which has the given name in the component specification.
	 * Returns null if there is no component identifier with the given name.
	 * @param analyzer
	 * @param oldName
	 * @return
	 */
	private Identifier existsLocalInterfaceName(ComponentAstAnalyser analyzer,String name){
		Set<Identifier> localInterfaceNames=analyzer.getInterfaceLocalName2InterfaceGlobalName().keySet();
		return astUtil.getIdentifierWithEqualName(name, localInterfaceNames);
	}
	
	/**
	 * Adds error messages and context info to the RefactoringStatus, designating the two colliding definitions in the file.
	 * @param toRename
	 * @param sameName
	 * @param containingFile
	 * @param ret
	 */
	private void addInfo(Identifier toRename,Identifier sameName, IFile containingFile,RefactoringStatus ret){
		Region toRenameRegion= new Region(toRename.getRange().getLeft(),toRename.getName().length());
		Region sameNameRegion= new Region(sameName.getRange().getLeft(),sameName.getName().length());
		ret.addError("You intended to rename the alias "+toRename.getName()+" to "+sameName.getName(),new FileStatusContext(containingFile, toRenameRegion));
		ret.addError("This would lead to a collision with this identifier: "+sameName.getName(),new FileStatusContext(containingFile, sameNameRegion));
	}

}
