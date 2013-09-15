package org.wdbuilder.input;

import org.wdbuilder.domain.DisplayNameAware;


public interface IParameter extends DisplayNameAware {
    
    String getName();
    
    String getString(InputAdapter input);

    int getInt(InputAdapter input);
    
    boolean getBoolean(InputAdapter input);
    
}
