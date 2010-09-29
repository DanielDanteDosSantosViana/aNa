package br.pensario.connector;

import br.pensario.NCLValues.NCLDefaultActionRole;

//DUVIDA - NCLActionRole e NCLDefaultActionRole est�o competindo em responsabilidades.
//DUVIDA - NCLConditionRole e NCLDefaultConditionRole est�o competindo em responsabilidades.

public class NCLActionRole extends NCLRole {

	private NCLDefaultActionRole id;

	public NCLActionRole(NCLDefaultActionRole action_role)	
	{		
		setId(action_role);		
	}
	
	public String getId() {	
		return id.toString();
	}

	public void setId(NCLDefaultActionRole id) {
		this.id = id;
	}
	
}